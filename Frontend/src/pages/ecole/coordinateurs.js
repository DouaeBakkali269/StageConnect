import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import FormComponent from '@/components/FormComponent';
import Table from '@/components/Table';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';

export default function UniversityCoordinatorsManagement() {
  const router = useRouter();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [accounts, setAccounts] = useState([]);
  const [filteredAccounts, setFilteredAccounts] = useState([]);
  const [formData, setFormData] = useState({});
  const [editAccountId, setEditAccountId] = useState(null);
  const [ecoleId, setEcoleId] = useState(null);
  const [error, setError] = useState(null); // State to manage error messages
  const [compteEcoleId, setCompteEcoleId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem("token");
      const id = localStorage.getItem('id');
      if (token && id) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setCompteEcoleId(id);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  // Fetch the Ecole ID and CoordinateurDeStage accounts on component mount
  useEffect(() => {
    if (compteEcoleId) {
      fetchEcoleId();
    }
  }, [compteEcoleId]);

  const fetchEcoleId = async () => {
    try {
      const response = await axiosInstance.get(`/compte-ecoles/${compteEcoleId}`);
      const ecoleId = response.data.ecoleId;
      setEcoleId(ecoleId);
      fetchCoordinateurDeStageAccounts(ecoleId);
    } catch (error) {
      console.error('Error fetching Ecole ID:', error);
      setError('Erreur lors de la récupération de l\'ID de l\'école. Veuillez réessayer.');
    }
  };

  const fetchCoordinateurDeStageAccounts = async (ecoleId) => {
    try {
      const response = await axiosInstance.get(`/api/coordinateurs/by-ecole/${ecoleId}`);
      setAccounts(response.data);
      setFilteredAccounts(response.data);
    } catch (error) {
      console.error('Error fetching CoordinateurDeStage accounts:', error);
      setError('Erreur lors de la récupération des comptes des coordinateurs de stage. Veuillez réessayer.');
    }
  };

  const handleSearch = (query) => {
    if (query.trim() === '') {
      setFilteredAccounts(accounts);
      return;
    }
    const filtered = accounts.filter(account =>
      account.nom.toLowerCase().includes(query.toLowerCase()) ||
      account.prenom.toLowerCase().includes(query.toLowerCase()) ||
      account.email.toLowerCase().includes(query.toLowerCase())
    );
    setFilteredAccounts(filtered);
  };

  const handleCreateAccount = async (formData) => {
    try {
      await axiosInstance.post(`/api/admins/send-password/${formData.email}/${formData.motDePasse}`)
      const response = await axiosInstance.post('/api/coordinateurs', {
        nom: formData.nom,
        prenom: formData.prenom,
        email: formData.email,
        motDePasse: formData.motDePasse,
        telephone: formData.telephone,
        ecoleId: ecoleId,
      });
      setAccounts([...accounts, response.data]);
      setFilteredAccounts([...filteredAccounts, response.data]);
      setIsModalOpen(false);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error('Error creating CoordinateurDeStage account:', error);
      setError('Erreur lors de la création du compte. Veuillez réessayer.');
    }
  };

  const handleEdit = (id) => {
    const accountToEdit = accounts.find(account => account.idCs === id);
    setFormData(accountToEdit);
    setEditAccountId(id);
    setIsModalOpen(true);
    setError(null); // Clear any previous errors
  };

  const handleSaveEdit = async (formData) => {
    try {
      const response = await axiosInstance.put(`/api/coordinateurs/${editAccountId}`, formData);
      const updatedAccounts = accounts.map(account =>
        account.idCs === editAccountId ? response.data : account
      );
      setAccounts(updatedAccounts);
      setFilteredAccounts(updatedAccounts);
      setIsModalOpen(false);
      setEditAccountId(null);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error('Error updating CoordinateurDeStage account:', error);
      setError('Erreur lors de la mise à jour du compte. Veuillez réessayer.');
    }
  };

  const formFields = [
    { name: 'nom', placeholder: 'Nom', type: 'text', required: true },
    { name: 'prenom', placeholder: 'Prénom', type: 'text', required: true },
    { name: 'email', placeholder: 'Email', type: 'email', required: true },
    { name: 'telephone', placeholder: 'Téléphone', type: 'text', required: true },
    { name: 'motDePasse', placeholder: 'Mot de passe', type: 'password', required: true },
  ];

  return (
    <Layout role="university">
      <h1 className="text-3xl font-bold mb-6">Gestion des Coordinateurs de Stage</h1>
      <div className="flex justify-end items-center mb-6">
        <button
          onClick={() => {
            setFormData({});
            setIsModalOpen(true);
          }}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Créer un compte
        </button>
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
          <span className="block sm:inline">{error}</span>
        </div>
      )}
      
      <Table 
        columns={['ID', 'Nom', 'Prénom', 'Email', 'Téléphone']}
        columnKeys={['idCs', 'nom', 'prenom', 'email', 'telephone']}
        items={filteredAccounts}
        buttons={['Modifier']}
        actions={[handleEdit]}
        idParam="idCs"
      />

      <FormComponent
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditAccountId(null);
        }}
        onSubmit={editAccountId ? handleSaveEdit : handleCreateAccount}
        fields={formFields}
        title={editAccountId ? "Modifier le compte" : "Créer un nouveau compte"}
        submitButtonText={editAccountId ? "Enregistrer" : "Créer"}
        prefillData={formData}
      />
    </Layout>
  );
}