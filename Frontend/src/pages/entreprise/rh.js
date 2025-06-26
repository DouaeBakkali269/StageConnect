import { useState, useEffect } from 'react';
import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Table from '@/components/Table';
import FormComponent from '@/components/FormComponent';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function ComHRManagement() {
  const router = useRouter();
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [accounts, setAccounts] = useState([]);
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
      const compteEntrepriseId = localStorage.getItem("id");
      const response = await axiosInstance.get(`/compte-entreprises/${compteEntrepriseId}`);
      setEntrepriseId(response.data.entrepriseId);
      fetchRHAccounts(response.data.entrepriseId);
    } catch (error) {
      console.error('Error fetching CompteEntreprise:', error);
      alert('Échec de la récupération des données du CompteEntreprise.');
    }
  };

  const fetchRHAccounts = async (entrepriseId) => {
    try {
      const response = await axiosInstance.get(`/api/rh/entreprise/${entrepriseId}`);
      setAccounts(response.data);
    } catch (error) {
      console.error('Error fetching RH accounts:', error);
      alert('Échec de la récupération des comptes RH.');
    }
  };

  const formFields = [
    { name: "firstname", placeholder: "Prénom", required: true },
    { name: "lastname", placeholder: "Nom", required: true },
    { name: "username", placeholder: "Nom d'utilisateur", required: true },
    { name: "password", type: "password", placeholder: "Mot de passe", required: true },
  ];

  const handleCreateAccount = async (data) => {
    try {
      const rhDTO = {
        nom: data.firstname,
        prenom: data.lastname,
        email: data.username,
        motDePasse: data.password,
        entrepriseId: entrepriseId,
      };
      await axiosInstance.post(`/api/admins/send-password/${data.username}/${data.password}`)
      await axiosInstance.post('/api/rh', rhDTO);
      fetchRHAccounts(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Error creating RH account:', error);
      alert('Échec de la création du compte RH.');
    }
  };

  const handleEdit = (accountId) => {
    const account = accounts.find(acc => acc.idRh === accountId);
    setSelectedAccount(account);
    setIsEditMode(true);
    setIsFormOpen(true);
  };

  const handleEditAccount = async (data) => {
    try {
      const rhDTO = {
        idRh: selectedAccount.idRh,
        nom: data.firstname,
        prenom: data.lastname,
        email: data.username,
        motDePasse: data.password,
        entrepriseId: entrepriseId,
      };
      await axiosInstance.put(`/api/rh/${selectedAccount.idRh}`, rhDTO);
      fetchRHAccounts(entrepriseId);
      setIsFormOpen(false);
    } catch (error) {
      console.error('Error updating RH account:', error);
      alert('Échec de la mise à jour du compte RH.');
    }
  };

  const handleDelete = async (accountId) => {
    try {
      await axiosInstance.delete(`/api/rh/${accountId}`);
      fetchRHAccounts(entrepriseId);
    } catch (error) {
      console.error('Error deleting RH account:', error);
      alert('Échec de la suppression du compte RH.');
    }
  };

  return (
    <Layout role="company">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Gestion des comptes de l'entreprise</h1>

        <div className="flex justify-end items-center">
          <button
            onClick={() => {
              setIsEditMode(false);
              setSelectedAccount(null);
              setIsFormOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            Créer un nouveau compte
          </button>
        </div>

        <Table
          columns={["Nom d'utilisateur", "Prénom", "Nom"]}
          columnKeys={["email", "nom", "prenom"]}
          items={accounts}
          buttons={["Modifier", "Supprimer"]}
          actions={[handleEdit, handleDelete]}
          idParam="idRh"
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
          title={isEditMode ? "Modifier le compte" : "Créer un nouveau compte"}
          submitButtonText={isEditMode ? "Enregistrer" : "Créer"}
          prefillData={selectedAccount}
        />
      </div>
    </Layout>
  );
}