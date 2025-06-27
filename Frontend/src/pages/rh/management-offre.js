import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import FormComponent from '@/components/FormComponent';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function HROfferManagement() {
  const router = useRouter();
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [selectedOffer, setSelectedOffer] = useState(null);
  const [offers, setOffers] = useState([]);
  const [entrepriseId, setEntrepriseId] = useState(null);
  const [rhId, setRhId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      const localRhId = localStorage.getItem('id');
      if (token && localRhId) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setRhId(localRhId);
        fetchCompteEntreprise(localRhId);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  const fetchCompteEntreprise = async (id) => {
    try {
      const response = await axiosInstance.get(`/api/rh/${id}`);
      setEntrepriseId(response.data.entrepriseId);
      fetchOffers(response.data.entrepriseId);
    } catch (error) {
      console.error('Error fetching CompteEntreprise:', error);
      alert('Échec de la récupération des données du CompteEntreprise.');
    }
  };

  const fetchOffers = async (entrepriseId) => {
    try {
      console.log('Entreprise ID:', entrepriseId); // Log entreprise id
      const response = await axiosInstance.get(`/api/offres/entreprise/${entrepriseId}`);
      console.log('Response:', response); // Log response for debugging
      const formattedOffers = response.data.map(offer => ({
        ...offer,
        dateLancement: formatDate(offer.dateLancement),
        dateLimite: formatDate(offer.dateLimite),
      }));
      console.log('Formatted Offers:', formattedOffers); // Log formattedOffers for debugging
      setOffers(formattedOffers);
    } catch (error) {
      console.error('Error fetching offers:', error);
      alert('Échec de la récupération des offres.');
    }
  };

  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toDateString(); // Format as a human-readable date string
  };

  const formFields = [
    { name: "objetOffre", placeholder: "Titre", required: true },
    { name: "posteOffre", placeholder: "Poste", required: true },
    { name: "dateLancement", placeholder: "Date de début", type: "date", required: true },
    { name: "dateLimite", placeholder: "Date de fin", type: "date", required: true },
    { name: "descriptionOffre", placeholder: "Description", required: true },
    { name: "dureeStage", placeholder: "Durée", required: true },
    { name: "modeOffre", placeholder: "Mode", required: true },
    { name: "remuneration", placeholder: "Rémunération", required: true },
    { name: "typeStageOffre", placeholder: "Type", required: true },
    { name: "niveauRequisOffre", placeholder: "Niveau requis", required: true },
  ];

  const handleCreateOffer = async (data) => {
    try {
      const offreDTO = {
        objetOffre: data.objetOffre,
        posteOffre: data.posteOffre,
        dateLancement: data.dateLancement,
        dateLimite: data.dateLimite,
        descriptionOffre: data.descriptionOffre,
        dureeStage: data.dureeStage,
        modeOffre: data.modeOffre,
        remuneration: data.remuneration,
        typeStageOffre: data.typeStageOffre,
        niveauRequisOffre: data.niveauRequisOffre,
        entrepriseId: entrepriseId,
        rhId: rhId,
      };
      await axiosInstance.post('/api/offres', offreDTO);
      fetchOffers(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Error creating offer:', error);
      alert('Échec de la création de l\'offre.');
    }
  };

  const handleEdit = (offerId) => {
    const offer = offers.find(off => off.idOffre === offerId);
    setSelectedOffer(offer);
    setIsEditMode(true);
    setIsFormOpen(true);
  };

  const handleEditOffer = async (data) => {
    try {
      const offreDTO = {
        idOffre: selectedOffer.idOffre,
        objetOffre: data.objetOffre,
        posteOffre: data.posteOffre,
        dateLancement: typeof data.dateLancement === 'string' ? new Date(data.dateLancement).toISOString() : data.dateLancement,
        dateLimite: typeof data.dateLimite === 'string' ? new Date(data.dateLimite).toISOString() : data.dateLimite,
        descriptionOffre: data.descriptionOffre,
        dureeStage: data.dureeStage,
        modeOffre: data.modeOffre,
        remuneration: data.remuneration,
        typeStageOffre: data.typeStageOffre,
        niveauRequisOffre: data.niveauRequisOffre,
        entrepriseId: entrepriseId,
        rhId: rhId,
      };
      await axiosInstance.put(`/api/offres/${selectedOffer.idOffre}`, offreDTO);
      fetchOffers(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Error updating offer:', error);
      alert('Échec de la mise à jour de l\'offre.');
    }
  };

  const handleDelete = async (offerId) => {
    try {
      await axiosInstance.delete(`/api/offres/${offerId}`);
      fetchOffers(entrepriseId);
    } catch (error) {
      console.error('Error deleting offer:', error);
      alert('Échec de la suppression de l\'offre.');
    }
  };

  const handlePostulations = (offerId) => {
    router.push(`/rh/postulations/${offerId}`);
  };

  return (
    <Layout role="hr">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Offres de l&apos;entreprise</h1>

        <div className="flex justify-end items-center">
          <button
            onClick={() => {
              setIsEditMode(false);
              setSelectedOffer(null);
              setIsFormOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            Créer une nouvelle offre
          </button>
        </div>

        <Table
          columns={[
            "ID Offre",
            "Titre", 
            "Poste", 
            "Date de début", 
            "Date de fin", 
            "Description", 
            "Durée", 
            "Mode", 
            "Rémunération", 
            "Type", 
            "Niveau requis", 
          ]}
          columnKeys={[
            "idOffre",
            "objetOffre", 
            "posteOffre", 
            "dateLancement", 
            "dateLimite", 
            "descriptionOffre", 
            "dureeStage", 
            "modeOffre", 
            "remuneration", 
            "typeStageOffre", 
            "niveauRequisOffre", 
          ]}
          items={offers}
          buttons={["Modifier", "Supprimer", "Postulations"]}
          actions={[handleEdit, handleDelete, handlePostulations]}
          idParam="idOffre"
          formatData={(key, value) => {
            if (key === "dateLancement" || key === "dateLimite") {
              return formatDate(value); // Format date fields
            }
            return value; // Return other values as-is
          }}
        />

        <FormComponent
          isOpen={isFormOpen}
          onClose={() => {
            setIsFormOpen(false);
            setIsEditMode(false);
            setSelectedOffer(null);
          }}
          onSubmit={isEditMode ? handleEditOffer : handleCreateOffer}
          fields={formFields}
          title={isEditMode ? "Modifier l&apos;offre" : "Créer une nouvelle offre"}
          submitButtonText={isEditMode ? "Enregistrer" : "Créer"}
          prefillData={selectedOffer}
        />
      </div>
    </Layout>
  );
}