import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function StudentApplications() {
  const router = useRouter();
  const [applications, setApplications] = useState({
    columns: ['Entreprise', 'Poste', 'Type', 'Objet de l\'offre', 'Statut'],
    columnKeys: ['company', 'position', 'type', 'objetOffre', 'status'], // Map column keys to match the keys in your data
    items: []
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      const etudiantId = localStorage.getItem("id");
      if (token && etudiantId) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchPostulations(etudiantId);
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  const fetchPostulations = async (etudiantId) => {
    try {
      const response = await axiosInstance.get(`/api/postulations/etudiant/${etudiantId}`);
      const postulations = response.data;

      // Fetch additional offer details for each postulation
      const transformedPostulations = await Promise.all(postulations.map(async (postulation) => {
        try {
          console.log(`Fetching offer details for postulation ID: ${postulation.id}`);
          console.log(`Postulation: ${postulation}`);

          const offerResponse = await axiosInstance.get(`/api/offres/${postulation.offreId}`);
          const offer = offerResponse.data;
          console.log(`Offer details for postulation ID ${postulation.id}:`, offer);

          const entrepriseResponse = await axiosInstance.get(`/api/entreprises/${offer.entrepriseId}`);
          const entreprise = entrepriseResponse.data;
          console.log(`Entreprise details for postulation ID ${postulation.id}:`, entreprise);

          return {
            id: postulation.id,
            company: entreprise.nomEntreprise, // Set company name as entreprise.nomEntreprise
            position: offer.posteOffre, // Use posteOffre from offer
            type: offer.typeStageOffre, // Use typeStageOffre from offer
            objetOffre: offer.objetOffre, // Use objetOffre from offer
            status: postulation.etatPostulation
          };
        } catch (offerError) {
          console.error(`Error fetching offer details for postulation ${postulation.id}:`, offerError);
          return {
            id: postulation.id,
            company: 'Nom de l\'entreprise',
            position: 'Nom du poste',
            type: 'Type',
            objetOffre: 'Objet de l\'offre',
            status: postulation.etatPostulation
          };
        }
      }));

      setApplications(prevState => ({
        ...prevState,
        items: transformedPostulations
      }));
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const getStatusColor = (status) => {
    const colors = {
      refused: 'text-red-600',
      pending: 'text-orange-600',
      accepted: 'text-green-600'
    };
    return colors[status] || 'text-gray-600';
  };

  const handleSearch = (query) => {
    // Implement search logic
    console.log("Recherche pour :", query);
  };

  const handleLogout = () => {
    // Add logout logic here
    router.push('/');
  };

  if (loading) {
    return <div>Chargement...</div>;
  }

  if (error) {
    return <div>Erreur : {error}</div>;
  }

  return (
    <Layout role="student">
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Mes candidatures</h1>
        <div className="overflow-x-auto">
          <Table
            columns={applications.columns}
            columnKeys={applications.columnKeys} // Pass columnKeys to the Table component
            items={applications.items.map(item => ({
              ...item,
              status: <span className={getStatusColor(item.status)}>{item.status}</span>
            }))}
          />
        </div>
      </div>
    </Layout>
  );
}