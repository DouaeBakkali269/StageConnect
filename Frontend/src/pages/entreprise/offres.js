import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import FormComponent from '@/components/FormComponent';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function ComOffers() {
  const router = useRouter();
  const [offers, setOffers] = useState([]);
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

  const fetchCompteEntreprise = async () => {
    try {
      const compteEntrepriseId = localStorage.getItem('id');
      const response = await axiosInstance.get(`/compte-entreprises/${compteEntrepriseId}`);
      setEntrepriseId(response.data.entrepriseId);
      fetchOffers(response.data.entrepriseId);
    } catch (error) {
      console.error('Erreur lors de la récupération du CompteEntreprise:', error);
      alert('Échec de la récupération des données du CompteEntreprise.');
    }
  };

  const fetchOffers = async (entrepriseId) => {
    try {
      const response = await axiosInstance.get(`/api/offres/entreprise/${entrepriseId}`);
      setOffers(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération des offres:', error);
      alert('Échec de la récupération des offres.');
    }
  };

  return (
    <Layout role="company">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Offres de l&apos;entreprise</h1>

        <Table
          columns={["ID Offre", "Titre", "Poste", "Date de début"]}
          columnKeys={["idOffre", "objetOffre", "posteOffre", "dateLancement"]}
          items={offers}
          idParam="idOffre"
        />
      </div>
    </Layout>
  );
}