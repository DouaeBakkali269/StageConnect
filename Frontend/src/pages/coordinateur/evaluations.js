import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import downloadAttestation from '@/utils/downloadAttestation';

export default function CoordinatorInternships() {
  const router = useRouter();
  const [evaluations, setEvaluations] = useState([]); // State for evaluations
  const [filteredEvaluations, setFilteredEvaluations] = useState([]); // State for filtered evaluations
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
      const id = localStorage.getItem("id");
      const response = await axiosInstance.get(`/api/coordinateurs/${id}`);
      const coordinateur = response.data;
      const ecoleId = coordinateur.ecoleId;

      const evaluationsResponse = await axiosInstance.get(`/evaluations/by-ecole/${ecoleId}`);
      setEvaluations(evaluationsResponse.data);
      setFilteredEvaluations(evaluationsResponse.data);
    } catch (error) {
      setError(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearch = (query) => {
    const filtered = evaluations.filter(evaluation =>
      Object.values(evaluation).some(value =>
        String(value).toLowerCase().includes(query.toLowerCase())
      )
    );
    setFilteredEvaluations(filtered);
  };

  const columns = ["ID Evaluation", "Note", "Competences", "Commentaire", "Encadrant ID", "Stage ID"];
  const columnKeys = ["idEvaluation", "note", "competances", "commentaire", "encadrantId", "stageId"];

  return (
    <Layout role="coordinator">
      <div className="p-4">
        {isLoading ? (
          <p>Loading...</p>
        ) : error ? (
          <p className="text-red-500">{error}</p>
        ) : (
          <Table
            columns={columns}
            columnKeys={columnKeys}
            items={filteredEvaluations}
            buttons={["Fiche d'evaluation"]}
            actions={[]}
            idParam="idEvaluation"
          />
        )}
      </div>
    </Layout>
  );
}