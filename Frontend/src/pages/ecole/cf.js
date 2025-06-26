import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import FormComponent from '@/components/FormComponent';
import Table from '@/components/Table';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';

export default function UniversityCFManagement() {
  const router = useRouter();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [accounts, setAccounts] = useState([]);
  const [filteredAccounts, setFilteredAccounts] = useState([]);
  const [formData, setFormData] = useState({});
  const [editAccountId, setEditAccountId] = useState(null);
  const [ecoleId, setEcoleId] = useState(null);
  const [filieres, setFilieres] = useState([]); // State to store filieres
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

  // Fetch the Ecole ID, ChefDeFiliere accounts, and Filieres on component mount
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
      fetchChefDeFiliereAccounts(ecoleId);
      fetchFilieresByEcoleId(ecoleId); // Fetch filieres for the ecole
    } catch (error) {
      console.error('Error fetching Ecole ID:', error);
      setError('Erreur lors de la récupération de l\'ID de l\'école. Veuillez réessayer.');
    }
  };

  const fetchChefDeFiliereAccounts = async (ecoleId) => {
    try {
      const response = await axiosInstance.get(`/chefs-de-filiere/by-ecole/${ecoleId}`);
      setAccounts(response.data);
      setFilteredAccounts(response.data);
    } catch (error) {
      console.error('Error fetching ChefDeFiliere accounts:', error);
      setError('Erreur lors de la récupération des comptes des chefs de filière. Veuillez réessayer.');
    }
  };

  const fetchFilieresByEcoleId = async (ecoleId) => {
    console.log('fetchFilieresByEcoleId called with ecoleId:', ecoleId);
    try {
      console.log('Making API request to:', `/api/filieres/ecole/${ecoleId}`);
      const response = await axiosInstance.get(`/api/filieres/ecole/${ecoleId}`);
      console.log('API response:', response);
      console.log('Setting filieres with response.data:', response.data);
      setFilieres(response.data); // Set the list of filieres
    } catch (error) {
      console.log('Error details:', error);
      console.error('Error fetching filieres:', error);
      console.log('Setting error message');
      setError('Erreur lors de la récupération des filières. Veuillez réessayer.');
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
      // Find the selected filiere by its name
      const selectedFiliere = filieres.find(filiere => filiere.nomFiliere === formData.filiere);
      if (!selectedFiliere) {
        setError('Filière sélectionnée introuvable.');
        return;
      }

      await axiosInstance.post(`/api/admins/send-password/${formData.email}/${formData.motDePasse}`)
      const response = await axiosInstance.post('/chefs-de-filiere', {
        nom: formData.nom,
        prenom: formData.prenom,
        email: formData.email,
        motDePasse: formData.motDePasse,
        telephone: formData.telephone,
        ecoleId: ecoleId,
        filiereId: selectedFiliere.idFiliere, // Include the filiereId
      });
      setAccounts([...accounts, response.data]);
      setFilteredAccounts([...filteredAccounts, response.data]);
      setIsModalOpen(false);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error('Error creating ChefDeFiliere account:', error);
      setError('Erreur lors de la création du compte. Veuillez réessayer.');
    }
  };

  const handleEdit = (id) => {
    const accountToEdit = accounts.find(account => account.idCf === id);
    setFormData({
      ...accountToEdit,
      filiere: filieres.find(filiere => filiere.idFiliere === accountToEdit.filiereId)?.nomFiliere || '',
    });
    setEditAccountId(id);
    setIsModalOpen(true);
    setError(null); // Clear any previous errors
  };

  const handleSaveEdit = async (formData) => {
    try {
      // Find the selected filiere by its name
      const selectedFiliere = filieres.find(filiere => filiere.nomFiliere === formData.filiere);
      if (!selectedFiliere) {
        setError('Filière sélectionnée introuvable.');
        return;
      }

      const response = await axiosInstance.put(`/chefs-de-filiere/${editAccountId}`, {
        nom: formData.nom,
        prenom: formData.prenom,
        email: formData.email,
        motDePasse: formData.motDePasse,
        telephone: formData.telephone,
        ecoleId: ecoleId,
        filiereId: selectedFiliere.idFiliere, // Include the filiereId
      });
      const updatedAccounts = accounts.map(account =>
        account.idCf === editAccountId ? response.data : account
      );
      setAccounts(updatedAccounts);
      setFilteredAccounts(updatedAccounts);
      setIsModalOpen(false);
      setEditAccountId(null);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error('Error updating ChefDeFiliere account:', error);
      setError('Erreur lors de la mise à jour du compte. Veuillez réessayer.');
    }
  };

  console.log('filieres:', filieres);
  console.log('filiere names : ', filieres.map(filiere => filiere.nomFiliere));

  const formFields = [
    { name: 'nom', placeholder: 'Nom', type: 'text', required: true },
    { name: 'prenom', placeholder: 'Prénom', type: 'text', required: true },
    { name: 'email', placeholder: 'Email', type: 'email', required: true },
    { name: 'telephone', placeholder: 'Téléphone', type: 'text' },
    { name: 'motDePasse', placeholder: 'Mot de passe', type: 'password', required: true },
    {
      name: 'filiere',
      placeholder: 'Filière',
      type: 'select',
      options: filieres.map(filiere => ({
        value: filiere.nomFiliere,
        label: filiere.nomFiliere
      })), // Populate options with filiere names
      required: true,
    },
  ];

  return (
    <Layout role="university">
      <h1 className="text-3xl font-bold mb-6">Gestion des Chefs de Filière</h1>
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
        columns={['ID', 'Nom', 'Prénom', 'Email', 'Téléphone', 'Filière']}
        columnKeys={['idCf', 'nom', 'prenom', 'email', 'telephone', 'filiereId']}
        items={filteredAccounts.map(account => ({
          ...account,
          filiereId: filieres.find(filiere => filiere.idFiliere === account.filiereId)?.nomFiliere || 'N/A',
        }))}
        buttons={['Modifier']}
        actions={[handleEdit]}
        idParam="idCf"
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