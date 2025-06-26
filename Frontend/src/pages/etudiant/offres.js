import { useState, useEffect } from 'react';
import Image from 'next/image';
import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import Modal from '@/components/Modal'; // Import a Modal component
import getEntrepriseLogoUrl from '@/utils/getEntrepriseLogo';

export default function StudentOffers() {
  const router = useRouter();
  const [offers, setOffers] = useState([]); // State for visible offers
  const [filteredOffers, setFilteredOffers] = useState([]); // State for filtered offers (after search)
  const [postulations, setPostulations] = useState([]); // State for postulations
  const [isLoading, setIsLoading] = useState(true); // Loading state
  const [searchQuery, setSearchQuery] = useState(''); // Search query state
  const [isModalOpen, setIsModalOpen] = useState(false); // State for modal visibility
  const [selectedOfferId, setSelectedOfferId] = useState(null); // State to store the selected offer ID
  const [cvFile, setCvFile] = useState(null); // State for CV file
  const [lettreMotivationFile, setLettreMotivationFile] = useState(null); // State for Lettre de Motivation file
  const [successMessage, setSuccessMessage] = useState(null); // State for success message
  const [logoUrls, setLogoUrls] = useState({});
  const [entrepriseNames, setEntrepriseNames] = useState({}); // State to store logo URLs for each offer
  const [studentId, setStudentId] = useState(null);

  // Fetch student data, visible offers, and postulations
  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      const id = localStorage.getItem('id');
      if (token && id) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setStudentId(id);
        fetchData(id);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  // Fetch logo URLs for all offers
  useEffect(() => {
    if (offers.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const offer of offers) {
          const url = await getEntrepriseLogoUrl(offer.entrepriseId);
          urls[offer.idOffre] = url;
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [offers]);

  const fetchData = async (id) => {
    try {
      // Fetch student data to get filiereId
      const studentResponse = await axiosInstance.get(`/api/etudiants/${id}`);
      const filiereId = studentResponse.data.filiereId;

      // Fetch visible offers by filiereId
      const offersResponse = await axiosInstance.get(`/visible-offres/${filiereId}/visible-offres`);
      const visibleOffers = offersResponse.data;
      console.log('Debugging: visibleOffers', visibleOffers);

      // Fetch postulations by studentId
      const postulationsResponse = await axiosInstance.get(`/api/postulations/etudiant/${id}`);
      const postulations = postulationsResponse.data;

      // Debugging: print the type of visibleOffers
      console.log('Debugging: Type of visibleOffers', typeof visibleOffers);

      // Filter out offers that the student has already applied to
      const filteredVisibleOffers = visibleOffers.filter(
        (offer) => !postulations.some((postulation) => postulation.offreId === offer.idOffre)
      );

      // Update state
      setOffers(filteredVisibleOffers);
      setFilteredOffers(filteredVisibleOffers);
      setPostulations(postulations);
      setIsLoading(false);
    } catch (error) {
      console.error('Erreur lors de la récupération des données :', error);
      setIsLoading(false);
    }
  };

  // Handle search functionality
  const handleSearch = (query) => {
    setSearchQuery(query);
    const filtered = offers.filter((offer) =>
      offer.objetOffre.toLowerCase().includes(query.toLowerCase()) ||
      offer.descriptionOffre.toLowerCase().includes(query.toLowerCase()) ||
      (entrepriseNames[offer.idOffre] && entrepriseNames[offer.idOffre].toLowerCase().includes(query.toLowerCase()))
    );
    setFilteredOffers(filtered);
  };

  // Handle postulation
  const handlePostuler = async () => {
    try {
      const formData = new FormData();
      formData.append('cv', cvFile);
      formData.append('lettreMotivation', lettreMotivationFile);
      formData.append('etudiantId', studentId);
      formData.append('offreId', selectedOfferId);

      // Send POST request to create postulation
      const response = await axiosInstance.post('/api/postulations/create', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      if (response.status === 201) {
        setSuccessMessage('Postulation réussie !');
        setIsModalOpen(false);
        fetchData(studentId); // Refresh data after successful postulation
      } else {
        setSuccessMessage('Erreur lors de la postulation.');
      }
    } catch (error) {
      console.error('Erreur lors de la soumission de la postulation :', error);
      setSuccessMessage('Erreur lors de la postulation.'); // Set error message
      setTimeout(() => setSuccessMessage(null), 5000); // Clear error message after 5 seconds
    }
  };

  // Handle file selection
  const handleFileChange = (e, type) => {
    const file = e.target.files[0];
    if (type === 'cv') {
      setCvFile(file);
    } else if (type === 'lettreMotivation') {
      setLettreMotivationFile(file);
    }
  };

  // Handle closing the modal without submitting
  const handleAnnuler = () => {
    setIsModalOpen(false); // Close the modal
    setCvFile(null); // Reset CV file
    setLettreMotivationFile(null); // Reset Lettre de Motivation file
  };

  useEffect(() => {
    const fetchEntreprises = async () => {
      const entrepriseNames = {};
      for (const offer of offers) {
        const response = await getEntrepriseFromOffreId(offer.idOffre);
        entrepriseNames[offer.idOffre] = response.nomEntreprise;
      }
      setEntrepriseNames(entrepriseNames);
    };
  
    fetchEntreprises();
  }, [offers]);

  return (
    <Layout role="student">
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Offres disponibles</h1>
        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>
        {/* Display success or error message */}
        {successMessage && (
          <p className={`mb-4 ${successMessage.includes('Erreur') ? 'text-red-500' : 'text-green-500'}`}>
            {successMessage}
          </p>
        )}
        {isLoading ? (
          <p>Chargement...</p>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            {filteredOffers.map((offer) => (
              <Card
                key={offer.idOffre}
                title={offer.objetOffre}
                specifications={[
                  { label: 'Entreprise', value: entrepriseNames[offer.idOffre] },
                  { label: 'Description', value: offer.descriptionOffre },
                  { label: 'Poste', value: offer.posteOffre },
                  { label: 'Durée', value: offer.dureeStage },
                  { label: 'Rémunération', value: offer.remuneration },
                ]}
                buttons={[
                  {
                    label: 'Postuler',
                    onClick: () => {
                      setSelectedOfferId(offer.idOffre); // Set the selected offer ID
                      setIsModalOpen(true); // Open the modal
                    },
                  },
                ]}
                imageSrc={logoUrls[offer.idOffre]} // Use the logo URL from state
              />
            ))}
          </div>
        )}

        {/* Modal for CV and Lettre de Motivation upload */}
        <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
          <div className="p-6">
            <h2 className="text-xl font-bold mb-4">Postuler à l'offre</h2>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-2">CV (PDF)</label>
                <input
                  type="file"
                  accept=".pdf"
                  onChange={(e) => handleFileChange(e, 'cv')}
                  className="w-full p-2 border rounded"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium mb-2">Lettre de Motivation (PDF)</label>
                <input
                  type="file"
                  accept=".pdf"
                  onChange={(e) => handleFileChange(e, 'lettreMotivation')}
                  className="w-full p-2 border rounded"
                  required
                />
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={handleAnnuler}
                  className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
                >
                  Annuler
                </button>
                <button
                  onClick={handlePostuler}
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                  Soumettre
                </button>
              </div>
            </div>
          </div>
        </Modal>
      </div>
    </Layout>
  );
}