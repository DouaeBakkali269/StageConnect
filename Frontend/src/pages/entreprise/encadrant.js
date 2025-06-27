import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import FormComponent from '@/components/FormComponent';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function ComSupervisorManagement() {
  const router = useRouter();
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [accounts, setAccounts] = useState([]);
  const [entrepriseId, setEntrepriseId] = useState(0);

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
      console.log(`CompteEntrepriseId: ${compteEntrepriseId}`);
      const response = await axiosInstance.get(`/compte-entreprises/${compteEntrepriseId}`);
      const entreId = response.data.entrepriseId;
      setEntrepriseId(entreId);
      fetchEncadrantAccounts(entreId);
    } catch (error) {
      console.error('Erreur lors de la récupération du CompteEntreprise:', error);
      alert('Échec de la récupération des données du CompteEntreprise.');
    }
  };

  const fetchEncadrantAccounts = async (entrepriseId) => {
    try {
      console.log(`EntrepriseId: ${entrepriseId}`);
      const response = await axiosInstance.get(`/api/encadrants/by-entreprise/${entrepriseId}`);
      console.log('Comptes des encadrants:', response.data); // Debugging
      setAccounts(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération des comptes des encadrants:', error);
      alert('Échec de la récupération des comptes des encadrants.');
    }
  };

  const formFields = [
    { name: "nom", placeholder: "Prénom", required: true },
    { name: "prenom", placeholder: "Nom", required: true },
    { name: "email", placeholder: "Email", required: true },
    { name: "motDePasse", type: "password", placeholder: "Mot de passe", required: true },
  ];

  const handleCreateAccount = async (data) => {
    try {
      const encadrantDTO = {
        nom: data.nom,
        prenom: data.prenom,
        email: data.email,
        motDePasse: data.motDePasse,
        entrepriseId: entrepriseId,
      };
      await axiosInstance.post(`/api/admins/send-password/${data.email}/${data.motDePasse}`)
      await axiosInstance.post('/api/encadrants', encadrantDTO);
      fetchEncadrantAccounts(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Erreur lors de la création du compte encadrant:', error);
      alert('Échec de la création du compte encadrant.');
    }
  };

  const handleEdit = (accountId) => {
    const account = accounts.find(acc => acc.idEncadrant === accountId);
    setSelectedAccount(account);
    setIsEditMode(true);
    setIsFormOpen(true);
  };

  const handleEditAccount = async (data) => {
    try {
      const encadrantDTO = {
        idEncadrant: selectedAccount.idEncadrant,
        nom: data.nom,
        prenom: data.prenom,
        email: data.email,
        motDePasse: data.motDePasse,
        entrepriseId: entrepriseId,
      };
      await axiosInstance.put(`/api/encadrants/${selectedAccount.idEncadrant}`, encadrantDTO);
      fetchEncadrantAccounts(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Erreur lors de la mise à jour du compte encadrant:', error);
      alert('Échec de la mise à jour du compte encadrant.');
    }
  };

  const handleDelete = async (accountId) => {
    try {
      await axiosInstance.delete(`/api/encadrants/${accountId}`);
      fetchEncadrantAccounts(entrepriseId);
    } catch (error) {
      console.error('Erreur lors de la suppression du compte encadrant:', error);
      alert('Échec de la suppression du compte encadrant.');
    }
  };

  return (
    <Layout role="company">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Gestion des superviseurs de l&apos;entreprise</h1>

        <div className="flex justify-end items-center">
          <button
            onClick={() => {
              setIsEditMode(false);
              setSelectedAccount(null);
              setIsFormOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            Créer un nouveau superviseur
          </button>
        </div>

        <Table
          columns={["Email", "Prénom", "Nom"]}
          columnKeys={["email", "nom", "prenom"]}
          items={accounts}
          buttons={["Modifier", "Supprimer"]}
          actions={[handleEdit, handleDelete]}
          idParam="idEncadrant"
        />

        <FormComponent
          isOpen={isFormOpen}
          onClose={() => {
            setIsFormOpen(false);
            setIsEditMode(false);
            setSelectedAccount(null);
          }}
          onSubmit={isEditMode ? handleEditAccount : handleCreateAccount}
          fields={formFields}
          title={isEditMode ? "Modifier le superviseur" : "Créer un nouveau superviseur"}
          submitButtonText={isEditMode ? "Enregistrer" : "Créer"}
          prefillData={selectedAccount}
        />
      </div>
    </Layout>
  );
}