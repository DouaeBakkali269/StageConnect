import { useState, useEffect, useCallback } from 'react';
import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance'; // Corrected import
import getEntrepriseFromOffreId from '@/utils/getEntrepriseFromOffreId';
import getEntrepriseLogoUrl from '@/utils/getEntrepriseLogo';

export default function CFOffers() {
  const router = useRouter();
  const [offersList, setOffersList] = useState([]); // Holds all offers or filtered offers
  const [visibleOffers, setVisibleOffers] = useState([]); // Holds visible offers
  const [filiereId, setFiliereId] = useState(null); // Holds the filiereId from ChefDeFiliere
  const [isLoading, setIsLoading] = useState(true); // Loading state
  const [error, setError] = useState(null); // Error state
  const [activeView, setActiveView] = useState('selectable'); // Tracks active view: 'selectable' or 'visible'
  const [searchQuery, setSearchQuery] = useState(''); // Search query state
  const [logoUrls, setLogoUrls] = useState({}); // State to store logo URLs for each offer

  const fetchVisibleOffers = useCallback(async (filiereId) => {
    try {
      const response = await axiosInstance.get(`/visible-offres/${filiereId}/visible-offres`);
      setVisibleOffers(response.data);
    } catch (err) {
      setError('Échec de la récupération des offres visibles');
    }
  }, []);

  const fetchAllOffers = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/api/offres');
      setOffersList(response.data);
    } catch (err) {
      setError('Échec de la récupération de toutes les offres');
    }
  }, []);

  const fetchChefDeFiliere = useCallback(async () => {
    try {
      const id = localStorage.getItem('id');
      if (!id) {
        throw new Error('Aucun ID trouvé dans le localStorage');
      }

      const response = await axiosInstance.get(`/chefs-de-filiere/${id}`);
      const newFiliereId = response.data.filiereId;
      setFiliereId(newFiliereId);
      fetchVisibleOffers(newFiliereId);
      fetchAllOffers();
    } catch (err) {
      setError(err.message || 'Échec de la récupération des données du ChefDeFiliere');
    } finally {
      setIsLoading(false);
    }
  }, [fetchAllOffers, fetchVisibleOffers]);

  // Set authorization token and fetch ChefDeFiliere data
  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchChefDeFiliere();
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router, fetchChefDeFiliere]);

  // Fetch logo URLs for all offers
  useEffect(() => {
    if (offersList.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const offer of offersList) {
          const entrepriseId = offer.entrepriseId; // Get entrepriseId from the offer
          const url = await getEntrepriseLogoUrl(entrepriseId); // Fetch the logo URL
          urls[offer.idOffre] = url; // Store the URL with the offer ID as the key
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [offersList]);

  // Handle deleting a visible offer
  const handleDeleteOffer = async (offreId) => {
    try {
      await axiosInstance.delete(`/visible-offres/${offreId}/${filiereId}`);
      setVisibleOffers((prev) => prev.filter((offer) => offer.idOffre !== offreId));
    } catch (err) {
      setError('Échec de la suppression de l\'offre');
    }
  };

  // Handle adding an offer to visible offers
  const handleAddOffer = async (offreId) => {
    try {
      await axiosInstance.post(`/visible-offres/filiere/${filiereId}/offre/${offreId}/visibility?visible=true`);
      fetchVisibleOffers(filiereId); // Refresh visible offers
    } catch (err) {
      setError('Échec de l\'ajout de l\'offre');
    }
  };

  // Filter offers based on search query
  const filteredOffers = offersList.filter((offer) => {
    const searchLower = searchQuery.toLowerCase();
    return (
      offer.objetOffre.toLowerCase().includes(searchLower) ||
      offer.descriptionOffre.toLowerCase().includes(searchLower) ||
      offer.posteOffre.toLowerCase().includes(searchLower)
    );
  });

  // Toggle between selectable and visible offers
  const toggleView = (view) => {
    setActiveView(view);
  };

  // Render offers based on active view
  const renderOffers = () => {
    if (activeView === 'selectable') {
      return filteredOffers
        .filter((offer) => !visibleOffers.some((visibleOffer) => visibleOffer.idOffre === offer.idOffre))
        .map((offer) => (
          <Card
            key={offer.idOffre}
            title={offer.objetOffre}
            specifications={[
              { label: 'Description', value: offer.descriptionOffre },
              { label: 'Poste', value: offer.posteOffre },
              { label: 'Durée', value: offer.dureeStage },
              { label: 'Type', value: offer.typeStageOffre },
            ]}
            buttons={[
              {
                label: 'Valider',
                onClick: () => handleAddOffer(offer.idOffre),
              },
            ]}
            imageSrc={logoUrls[offer.idOffre]} // Use the logo URL from state
          />
        ));
    } else {
      return visibleOffers.map((offer) => (
        <Card
          key={offer.idOffre}
          title={offer.objetOffre}
          specifications={[
            { label: 'Description', value: offer.descriptionOffre },
            { label: 'Poste', value: offer.posteOffre },
            { label: 'Durée', value: offer.dureeStage },
            { label: 'Type', value: offer.typeStageOffre },
          ]}
          buttons={[
            {
              label: 'Annuler',
              onClick: () => handleDeleteOffer(offer.idOffre),
            },
          ]}
          imageSrc={logoUrls[offer.idOffre]} // Use the logo URL from state
        />
      ));
    }
  };

  if (isLoading) {
    return <div>Chargement...</div>;
  }

  if (error) {
    return <div>Erreur : {error}</div>;
  }

  return (
    <Layout role="cf">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Offres disponibles</h1>

        <div className="flex items-center justify-between mb-6">
          <SearchBar onSearch={(query) => setSearchQuery(query)} />
          <div className="flex space-x-4">
            <button
              onClick={() => toggleView('selectable')}
              className={`${
                activeView === 'selectable' ? 'bg-green-500' : 'bg-gray-500'
              } text-white px-4 py-2 rounded hover:bg-green-600 disabled:bg-gray-300`}
              disabled={activeView === 'selectable'}
            >
              Sélectionner de nouvelles offres
            </button>
            <button
              onClick={() => toggleView('visible')}
              className={`${
                activeView === 'visible' ? 'bg-green-500' : 'bg-gray-500'
              } text-white px-4 py-2 rounded hover:bg-green-600 disabled:bg-gray-300`}
              disabled={activeView === 'visible'}
            >
              Voir les offres sélectionnées
            </button>
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {renderOffers()}
        </div>
      </div>
    </Layout>
  );
}