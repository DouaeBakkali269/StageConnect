import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function CompanyInternships() {
  const router = useRouter();
  const [internships, setInternships] = useState([]);
  const [entrepriseId, setEntrepriseId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchCompteEntreprise();
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  // Fetch RH by ID to get entrepriseId
  const fetchCompteEntreprise = async () => {
    try {
      const id = localStorage.getItem('id');
      const compteEntrepriseResponse = await axiosInstance.get(`/compte-entreprises/${id}`);
      setEntrepriseId(compteEntrepriseResponse.data.entrepriseId);
      fetchInternships(compteEntrepriseResponse.data.entrepriseId);
      // Fetch internships for the company
    } catch (error) {
      console.error('Erreur lors de la récupération des détails Entreprise:', error);
      alert('Échec de la récupération des détails Entreprise.');
    }
  };

  // Fetch internships by entrepriseId and enrich with Etudiant details
  const fetchInternships = async (entrepriseId) => {
    try {
      console.log(`Entreprise ID found is: ${entrepriseId}`);
      const response = await axiosInstance.get(`/stages/by-entreprise/${entrepriseId}`);
      
      // Fetch Etudiant details for each internship
      const internshipsWithEtudiant = await Promise.all(
        response.data.map(async (internship) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${internship.etudiantId}`);
          return {
            ...internship,
            etudiantNom: etudiantResponse.data.nom,
            etudiantPrenom: etudiantResponse.data.prenom,
            etudiantEmail:  etudiantResponse.data.email,
            etudiantTelephone:  etudiantResponse.data.tel,// Add Etudiant details to the internship object
            dateDebut: formatDate(internship.dateDebut), // Format start date
            dateFin: formatDate(internship.dateFin), // Format end date
          };
        })
      );

      setInternships(internshipsWithEtudiant);
    } catch (error) {
      console.error('Erreur lors de la récupération des stages ou des détails de l\'étudiant:', error);
      alert('Échec de la récupération des stages ou des détails de l\'étudiant.');
    }
  };

  // Format date to a human-readable format
  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toISOString().split('T')[0]; // Format as YYYY-MM-DD
  };

  // Handle delete action for an internship
  const handleDelete = async (internshipId) => {
    try {
      await axiosInstance.delete(`/stages/${internshipId}`);
      fetchInternships(entrepriseId); // Refresh the list after deletion
    } catch (error) {
      console.error('Erreur lors de la suppression du stage:', error);
      alert('Échec de la suppression du stage.');
    }
  };

  return (
    <Layout role="company">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Gestion des stages</h1>
      

        <Table 
          columns={["ID", "Titre", "Nom", "Prenom", "Email", "Téléphone", "Date de début", "Date de fin", "Statut"]}
          columnKeys={[
            "idStage", 
            "titre", 
            "etudiantNom",
            "etudiantPrenom" ,
            "etudiantEmail", 
            "etudiantTelephone", 
            "dateDebut", 
            "dateFin", 
            "statut"
          ]}
          items={internships}
          buttons={["Supprimer", "Fiche Descriptive"]}
          actions={[handleDelete]}
          idParam="idStage"
          formatData={(key, value) => {
            if (key === "dateDebut" || key === "dateFin") {
              return formatDate(value); // Format date fields
            }
            return value; // Return other values as-is
          }}
        />
      </div>
    </Layout>
  );
}