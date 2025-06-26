import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import FormComponent from '@/components/FormComponent';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function HRInterviews() {
  const router = useRouter();
  const [isInternshipFormOpen, setIsInternshipFormOpen] = useState(false);
  const [interviews, setInterviews] = useState([]);
  const [entrepriseId, setEntrepriseId] = useState(null);
  const [selectedInterview, setSelectedInterview] = useState(null);
  const [encadrants, setEncadrants] = useState([]); // State to store encadrants
  const [error, setError] = useState(null); // State to manage error messages

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      const rhId = localStorage.getItem('id');
      if (token && rhId) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchCompteEntreprise(rhId);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  // Function to set an error message and clear it after 5 seconds
  const setErrorMessage = (message) => {
    setError(message);
    setTimeout(() => setError(null), 5000); // Clear error after 5 seconds
  };

  const fetchCompteEntreprise = async (rhId) => {
    try {
      const response = await axiosInstance.get(`/api/rh/${rhId}`);
      setEntrepriseId(response.data.entrepriseId);
      fetchInterviews(response.data.entrepriseId);
      fetchEncadrants(response.data.entrepriseId); // Fetch encadrants after setting entrepriseId
    } catch (error) {
      console.error('Error fetching CompteEntreprise:', error);
      setErrorMessage('Échec de la récupération des données du CompteEntreprise.');
    }
  };

  const fetchEncadrants = async (entrepriseId) => {
    try {
      const response = await axiosInstance.get(`/api/encadrants/by-entreprise/${entrepriseId}`);
      setEncadrants(response.data); // Store encadrants in state
    } catch (error) {
      console.error('Error fetching encadrants:', error);
      setErrorMessage('Échec de la récupération des encadrants.');
    }
  };

  const fetchInterviews = async (entrepriseId) => {
    try {
      console.log('Debugging: EntrepriseId', entrepriseId);
      const response = await axiosInstance.get(`/entretiens/by-entreprise/${entrepriseId}`);
      const filteredInterviews = response.data.filter(interview => interview.resultat === "nouveau");

      // Fetch Etudiant and Offre details for each interview
      const interviewsWithDetails = await Promise.all(
        filteredInterviews.map(async (interview) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${interview.etudiantId}`);
          const offreResponse = await axiosInstance.get(`/api/offres/${interview.offreId}`);
          return {
            ...interview,
            dateEntretien: formatDate(interview.dateEntretien), // Format the interview date
            etudiantNom: etudiantResponse.data.nom, // Add Etudiant details to the interview object
            etudiantPrenom: etudiantResponse.data.prenom,
            etudiantTelephone: etudiantResponse.data.tel,
            etudiantEmail: etudiantResponse.data.email, // Add Etudiant details to the interview object
            offreObjet: offreResponse.data.objetOffre,
            offreID: offreResponse.data.idOffre // Add Offre details to the interview object
          };
        })
      );

      setInterviews(interviewsWithDetails);
    } catch (error) {
      console.error('Error fetching interviews, Etudiant, or Offre details:', error);
      setErrorMessage('Échec de la récupération des entretiens, des détails de l\'étudiant ou de l\'offre.');
    }
  };

  // Updated formatDate function to use ISO 8601 format
  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toISOString(); // Format as ISO 8601
  };

  const internshipFormFields = [
    { name: "titre", placeholder: "Titre", required: true },
    { name: "description", placeholder: "Description", required: true },
    { name: "dateDebut", placeholder: "Date de début", type: "date", required: true },
    { name: "dateFin", placeholder: "Date de fin", type: "date", required: true },
    { name: "duree", placeholder: "Durée", required: true },
    { name: "localisation", placeholder: "Localisation", required: true },
    { name: "montantRemuneration", placeholder: "Rémunération", type: "number", required: true },
    { name: "type", placeholder: "Type", required: true },
    {
      name: "encadrant",
      placeholder: "Encadrant",
      type: "select",
      options: encadrants.map(encadrant => ({
        label: `${encadrant.nom} ${encadrant.prenom}`,
        value: `${encadrant.nom} ${encadrant.prenom}`,
      })),
      required: true,
    },
  ];

  const handleAccept = (interviewId) => {
    const interview = interviews.find(int => int.idEntretien === interviewId);
    setSelectedInterview(interview);
    setIsInternshipFormOpen(true);
  };

  const handleRefuse = async (interviewId) => {
    try {
      await axiosInstance.put(`/entretiens/${interviewId}`, { resultat: "refusé" });
      fetchInterviews(entrepriseId);
    } catch (error) {
      console.error('Error refusing interview:', error);
      setErrorMessage('Échec du refus de l\'entretien.');
    }
  };

  const handleCreateInternship = async (data) => {
    try {
      // Find the selected encadrant by nom and prenom
      const selectedEncadrant = encadrants.find(
        (encadrant) => `${encadrant.nom} ${encadrant.prenom}` === data.encadrant
      );

      if (!selectedEncadrant) {
        throw new Error("Selected encadrant not found.");
      }

      // Remove encadrant from data
      const { encadrant, ...updatedData } = data;


      const stageDTO = {
        ...updatedData,
        statut: "nouveau", // Set statut to "nouveau" by default
        etudiantId: selectedInterview.etudiantId, // Take etudiantId from selectedInterview
        offreId: selectedInterview.offreId,
        encadrantId: selectedEncadrant.idEncadrant, // Use the idEncadrant from the selected encadrant
      };


      await axiosInstance.post('/stages', stageDTO);

      const { etudiantEmail, etudiantNom, etudiantPrenom, etudiantTelephone, offreID, offreObjet, ...updatedInterview } = selectedInterview;
      // Exclude etudiant and offre objects
      console.log(`Updated interview is:`);
      console.log(updatedInterview);
      updatedInterview.resultat = "accepté"; // Create the internship
      await axiosInstance.put(`/entretiens/${selectedInterview.idEntretien}`, updatedInterview); // Update interview result
      fetchInterviews(entrepriseId); // Refresh the interviews list
      setIsInternshipFormOpen(false); // Close the form
    } catch (error) {
      console.error('Error creating internship:', error);
      setErrorMessage('Échec de la création du stage.');
    }
  };

  return (
    <Layout role="hr">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Gestion des entretiens</h1>

        {/* Display error message if any */}
        {error && <p className="text-red-500">{error}</p>}

        <Table
          columns={["Nom", "Prénom", "Email", "Téléphone", "Objet de l'offre", "ID de l'offre", "Date"]}
          columnKeys={[
            "etudiantNom",
            "etudiantPrenom",
            "etudiantEmail",
            "etudiantTelephone",
            "offreObjet",
            "offreID", // Display the offer object
            "dateEntretien"
          ]}
          items={interviews}
          buttons={["Accepter", "Refuser"]}
          actions={[handleAccept, handleRefuse]}
          idParam="idEntretien"
        />

        <FormComponent
          isOpen={isInternshipFormOpen}
          onClose={() => setIsInternshipFormOpen(false)}
          onSubmit={handleCreateInternship}
          fields={internshipFormFields}
          title="Créer un stage"
          submitButtonText="Créer"
        />
      </div>
    </Layout>
  );
}