import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import uploadConvention from '@/utils/uploadConvention';
import downloadAttestation from '@/utils/downloadAttestation';

export default function CoordinatorInternships() {
  const router = useRouter();
  const [internships, setInternships] = useState([]); // State for internships
  const [error, setError] = useState(null); // State for error messages
  const [isLoading, setIsLoading] = useState(true); // Loading state

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchData();
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  const fetchData = async () => {
    try {
      // Get CoordinateurDeStage ID from localStorage
      const coordinateurId = localStorage.getItem('id');
      if (!coordinateurId) {
        router.push('/');
        return;
      }

      // Fetch CoordinateurDeStage by ID
      const coordinateurResponse = await axiosInstance.get(`/api/coordinateurs/${coordinateurId}`);
      console.log("Coordinateur Object: ");
      console.log(coordinateurResponse);
      const ecoleId = coordinateurResponse.data.ecoleId;

      // Fetch stages by ecoleId
      console.log(`Ecole ID: ${ecoleId}`);
      const stagesResponse = await axiosInstance.get(`/stages/by-ecole/${ecoleId}`);
      console.log('API Response:', stagesResponse);
      const updatedStages = await Promise.all(stagesResponse.data.map(async (stage) => {
        if (stage.statut !== "terminé" && stage.statut !== "évalué" && stage.statut !== "nouveau" && stage.statut !== "refusé" && stage.statut !== "refusé temporairement") {
          const currentDate = new Date();
          const dateFin = new Date(stage.dateFin);
          const dateDebut = new Date(stage.dateDebut);
          if (dateFin > currentDate && dateDebut < currentDate && stage.statut !== "en cours") {
            stage.statut = "en cours";
            const newStatus = "en cours";
            await axiosInstance.put(`/stages/${stage.idStage}/status`, null, {
              params: { newStatus },
            });
          } else if (dateFin < currentDate && stage.statut !== "terminé") {
            stage.statut = "terminé";
            const newStatus = "terminé";
            await axiosInstance.put(`/stages/${stage.idStage}/status`, null, {
              params: { newStatus },
            });
          }
        }
        return stage;
      }));
      
      const filteredStages = updatedStages.filter(
        (stage) => stage.statut !== "nouveau" && stage.statut !== "a valider"
      );

      // Fetch Etudiant data for each stage
      const internshipsWithEtudiant = await Promise.all(
        filteredStages.map(async (stage) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${stage.etudiantId}`);
          return {
            ...stage,
            etudiant: etudiantResponse.data, // Add Etudiant details to the stage object
          };
        })
      );

      setInternships(internshipsWithEtudiant);
      setIsLoading(false);
    } catch (error) {
      console.error('Erreur lors de la récupération des données:', error);
      setError('Échec de la récupération des données. Veuillez réessayer plus tard.');
      setIsLoading(false);
    }
  };

  const handleSearch = (query) => {
    console.log('Recherche de:', query);
    // Implement search logic here
  };

  // Format date to human-readable format
  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toLocaleDateString(); // Format as human-readable date
  };

  return (
    <Layout role="coordinator">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Validations des stages</h1>

        {/* Display error message if any */}
        {error && <p className="text-red-500">{error}</p>}

        {isLoading ? (
          <p>Chargement...</p>
        ) : (
          <Table
            columns={["Email de l'étudiant", "Titre du stage", "Date de début", "Date de fin", "Statut"]}
            columnKeys={[
              "etudiant.email", // Student email
              "titre", // Stage description
              "dateDebut", // Start date
              "dateFin", // End date
              "statut", // Stage status
            ]}
            items={internships.map((internship) => ({
              ...internship,
              "etudiant.email": internship.etudiant.email,
              "description": internship.description,
              "dateDebut": formatDate(internship.dateDebut), // Format start date
              "dateFin": formatDate(internship.dateFin), // Format end date
              "statut": internship.statut,
            }))}
            buttons={["Déposer Convention", "Télécharger Attestation", "Fiche Descriptive"]}
            actions = {[uploadConvention, downloadAttestation]}
            idParam= "idStage"
          />
        )}
      </div>
    </Layout>
  );
}