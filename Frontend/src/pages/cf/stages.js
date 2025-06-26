import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import getFicheDescriptiveDeStage from '@/utils/downloadFicheDescriptive';
import getEntrepriseLogoUrl from '@/utils/getEntrepriseLogo';
import getEntrepriseIdFromOffre from '@/utils/getEntrepriseIdFromOffre';

export default function CFInternships() {
  const router = useRouter();
  const [stages, setStages] = useState([]); // Combined stage and student data
  const [loading, setLoading] = useState(false);
  const [searchQuery, setSearchQuery] = useState(''); // For search functionality
  const [logoUrls, setLogoUrls] = useState({}); // State to store logo URLs for each stage

  // Fetch initial data
  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
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

  // Fetch logo URLs for all stages
  useEffect(() => {
    if (stages.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const stage of stages) {
          const entrepriseId = await getEntrepriseIdFromOffre(stage.offreId); // Get entrepriseId from the stage
          const url = await getEntrepriseLogoUrl(entrepriseId); // Fetch the logo URL
          urls[stage.idStage] = url; // Store the URL with the stage ID as the key
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [stages]);

  // Fetch all stages and corresponding students
  const fetchData = async () => {
    setLoading(true);
    try {
      // Fetch cf account to get ecoleId
      const cfId = localStorage.getItem('id');
      const cfResponse = await axiosInstance.get(`/chefs-de-filiere/${cfId}`);
      const ecoleId = cfResponse.data.ecoleId;

      // Fetch all stages for the ecoleId
      const stagesResponse = await axiosInstance.get(`/stages/AValider/ecole/${ecoleId}`);

      // Fetch students for each stage
      const stagesWithStudents = await Promise.all(
        stagesResponse.data.map(async (stage) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${stage.etudiantId}`);
          return { ...stage, etudiant: etudiantResponse.data };
        })
      );

      setStages(stagesWithStudents); // Set all stages with student data
    } catch (error) {
      console.error('Erreur lors de la récupération des données:', error);
    } finally {
      setLoading(false);
    }
  };

  // Handle search (client-side filtering)
  const handleSearch = (query) => {
    setSearchQuery(query);
  };

  // Filter stages based on search query
  const filteredStages = stages.filter((stage) => {
    const searchString = `${stage.titre} ${stage.description} ${stage.localisation} ${stage.etudiant?.nom} ${stage.etudiant?.prenom} ${stage.etudiant?.email}`.toLowerCase();
    return searchString.includes(searchQuery.toLowerCase());
  });

  // Format date to a human-readable format
  const formatDate = (date) => {
    if (!date) return "N/A"; // Handle null or undefined dates
    const dateObj = new Date(date);
    return dateObj.toLocaleDateString(); // Format as human-readable date
  };

  // Handle stage status update
  const accepterStageStatus = async (idStage, idEtudiant) => {
    try {
      console.log(`/stages/set-status/${idEtudiant}/${idStage}`);
      await axiosInstance.put(`/stages/set-status/${idEtudiant}/${idStage}`);

      await fetchData();
    } catch (error) {
      console.error('Erreur lors de la mise à jour du statut du stage:', error);
    }
  };

  const refuserStageStatus = async (idStage, idEtudiant) => {
    try {
      console.log(`/stages/set-status-cf/${idEtudiant}/${idStage}`);
      await axiosInstance.put(`/stages/set-status-cf/${idEtudiant}/${idStage}`);

      await fetchData();
    } catch (error) {
      console.error('Erreur lors de la mise à jour du statut du stage:', error);
    }
  };

  return (
    <Layout role="cf">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Demandes de validation de stages</h1>

        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {filteredStages.map((stage) => (
            <Card
              key={stage.idStage}
              title={stage.titre || 'Aucun titre'}
              specifications={[
                { label: 'Description', value: stage.description || 'Aucune description' },
                { label: 'Date de début', value: formatDate(stage.dateDebut) || 'Aucune date de début' },
                { label: 'Date de fin', value: formatDate(stage.dateFin) || 'Aucune date de fin' },
                { label: 'Localisation', value: stage.localisation || 'Aucune localisation' },
                { label: 'Étudiant', value: `${stage.etudiant?.prenom || 'Aucun prénom'} ${stage.etudiant?.nom || 'Aucun nom'}` },
                { label: 'Email', value: stage.etudiant?.email || 'Aucun email' },
                { label: 'Statut', value: stage.statut || 'Aucun statut' },
              ]}
              buttons={[
                { label: 'Accepter', onClick: () => accepterStageStatus(stage.idStage, stage.etudiantId) },
                { label: 'Refuser', onClick: () => refuserStageStatus(stage.idStage, stage.etudiantId) },
                { label: 'Fiche Descriptive', onClick: () => getFicheDescriptiveDeStage(stage) },
              ]}
              imageSrc={logoUrls[stage.idStage]} // Use the logo URL from state
            />
          ))}
        </div>

        {loading && <p className="text-center">Chargement...</p>}
        {!loading && filteredStages.length === 0 && <p className="text-center">Aucun stage trouvé.</p>}
      </div>
    </Layout>
  );

}