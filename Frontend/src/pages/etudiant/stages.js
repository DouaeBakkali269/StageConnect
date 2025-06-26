import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import getFicheDescriptiveDeStage from '@/utils/downloadFicheDescriptive';
import getEntrepriseLogoUrl from '@/utils/getEntrepriseLogo'; // Import the logo fetching function
import getEntrepriseFromOffreId from '@/utils/getEntrepriseFromOffreId';

export default function StudentInternships() {
  const router = useRouter();
  const [internships, setInternships] = useState([]);
  const [logoUrls, setLogoUrls] = useState({}); // State to store logo URLs for each internship
  const [entrepriseNames, setEntrepriseNames] = useState({}); // State to store entreprise names for each internship
  const [searchQuery, setSearchQuery] = useState(''); // State to store search query
  const [etudiantId, setEtudiantId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      const localEtudiantId = localStorage.getItem('id');
      if (token && localEtudiantId) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setEtudiantId(localEtudiantId);
        fetchInternships(localEtudiantId);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  // Fetch logo URLs for all internships
  useEffect(() => {
    if (internships.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const internship of internships) {
          const entreprise = await getEntrepriseFromOffreId(internship.offreId); // Get entreprise details
          if (entreprise && entreprise.idEntreprise) {
            const url = await getEntrepriseLogoUrl(entreprise.idEntreprise); // Fetch the logo URL
            urls[internship.idStage] = url; // Store the URL with the internship ID as the key
          }
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [internships]);

  const fetchInternships = async (id) => {
    try {
      const response = await axiosInstance.get(`/stages/by-etudiant/${id}`);
      const stages = response.data;

      const updatedStages = await Promise.all(
        stages.map(async (stage) => {
          if (stage.statut !== 'terminé' && stage.statut !== 'évalué' && stage.statut !== 'nouveau' && stage.statut !== 'refusé' && stage.statut !== 'refusé temporairement' ) {
            const currentDate = new Date();
            const dateFin = new Date(stage.dateFin);
            const dateDebut = new Date(stage.dateDebut);
            if (dateFin > currentDate && dateDebut < currentDate && stage.statut !== 'en cours') {
              stage.statut = 'en cours';
              const newStatus = 'en cours';
              await axiosInstance.put(`/stages/${stage.idStage}/status`, null, {
                params: { newStatus },
              });
            } else if (dateFin < currentDate && stage.statut !== 'terminé') {
              stage.statut = 'terminé';
              const newStatus = 'terminé';
              await axiosInstance.put(`/stages/${stage.idStage}/status`, null, {
                params: { newStatus },
              });
            }
          }
          return stage;
        })
      );

      const stagesWithEtudiant = await Promise.all(
        updatedStages.map(async (stage) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${stage.etudiantId}`);
          return { ...stage, etudiant: etudiantResponse.data };
        })
      );

      setInternships(stagesWithEtudiant);
    } catch (error) {
      console.error('Erreur lors de la récupération des stages :', error);
    }
  };

  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toLocaleDateString(); // Format as human-readable date
  };

  const handleSearch = (query) => {
    setSearchQuery(query.toLowerCase());
  };

  const handleStatusUpdate = async (stageId) => {
    try {
      await axiosInstance.put(`/stages/set-status-delete-others/${etudiantId}/${stageId}`);
      fetchInternships(etudiantId); // Refresh the list after updating the status
    } catch (error) {
      console.error('Erreur lors de la mise à jour du statut :', error);
    }
  };

  const getButtonLabel = (status) => {
    const labels = {
      nouveau: 'Valider',
      'a valider': 'Demande en attente',
      valide: 'Validé',
      refusé: 'Refusé',
      'en cours': 'En Cours',
      terminé: 'Terminé',
      évalué: 'Évalué',
      'refusé temporairement' : 'Refusé Temporairement'
    };
    return labels[status] || 'Postuler';
  };

  useEffect(() => {
    const fetchEntreprises = async () => {
      const entrepriseNames = {};
      for (const internship of internships) {
        const response = await getEntrepriseFromOffreId(internship.offreId);
        entrepriseNames[internship.offreId] = response.nomEntreprise;
      }
      setEntrepriseNames(entrepriseNames);
    };

    fetchEntreprises();
  }, [internships]);

  const filteredInternships = internships.filter((internship) => {
    if (!searchQuery) return true; // If search query is empty, return all internships
    const entrepriseNom = entrepriseNames[internship.offreId] || '';
    const searchString = `${internship.titre} ${entrepriseNom} ${internship.description} ${formatDate(internship.dateDebut)} ${formatDate(internship.dateFin)} ${internship.duree} ${internship.localisation} ${internship.montantRemuneration} ${internship.statut} ${internship.type} ${internship.etudiant.nom} ${internship.etudiant.prenom}`.toLowerCase();
    return searchString.includes(searchQuery);
  });

  return (
    <Layout role="student" onLogout={() => {}}>
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Mes stages</h1>
        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {filteredInternships.map((internship) => {
            const entrepriseNom = entrepriseNames[internship.offreId]; // Get entreprise details
            return (
              <Card
                key={internship.idStage}
                title={internship.titre}
                specifications={[
                  { label: 'Entreprise', value: entrepriseNom },
                  { label: 'Description', value: internship.description },
                  { label: 'Date de début', value: formatDate(internship.dateDebut) },
                  { label: 'Date de fin', value: formatDate(internship.dateFin) },
                  { label: 'Durée', value: internship.duree },
                  { label: 'Localisation', value: internship.localisation },
                  { label: 'Montant', value: internship.montantRemuneration },
                  { label: 'Statut', value: internship.statut },
                  { label: 'Type', value: internship.type },
                  { label: 'Étudiant', value: `${internship.etudiant.nom} ${internship.etudiant.prenom}` },
                ]}
                buttons={[
                  {
                    label: getButtonLabel(internship.statut),
                    onClick: () => handleStatusUpdate(internship.idStage),
                  },
                  {
                    label: 'Fiche Descriptive',
                    onClick: () => getFicheDescriptiveDeStage(internship),
                  },
                ]}
                imageSrc={logoUrls[internship.idStage]} // Use the logo URL from state
              />
            );
          })}
        </div>
      </div>
    </Layout>
  );
}