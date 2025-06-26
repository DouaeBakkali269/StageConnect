import Layout from '@/components/Layout';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import FormComponent from '@/components/FormComponent';

export default function CompteEntrepriseProfile() {
  const router = useRouter();
  const [compteEntreprise, setCompteEntreprise] = useState(null);
  const [entreprise, setEntreprise] = useState(null);
  const [isEditingCompteEntreprise, setIsEditingCompteEntreprise] = useState(false);
  const [isEditingEntreprise, setIsEditingEntreprise] = useState(false);
  const [logoUrl, setLogoUrl] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (!token) router.push('/');
      else fetchProfileData();
    }
  }, [router]);

  const fetchProfileData = async () => {
    try {
      const id = localStorage.getItem('id');
      const compteEntrepriseResponse = await axiosInstance.get(`/compte-entreprises/${id}`);
      setCompteEntreprise(compteEntrepriseResponse.data);

      const entrepriseResponse = await axiosInstance.get(`/api/entreprises/${compteEntrepriseResponse.data.entrepriseId}`);
      setEntreprise(entrepriseResponse.data);

      // Fetch the logo
      if (entrepriseResponse.data.idEntreprise) {
        const logoResponse = await axiosInstance.get(`/api/entreprises/download/${entrepriseResponse.data.idEntreprise}/logo`, {
          responseType: 'blob',
        });
        if (logoResponse.data.size > 0) {
          const logoBlob = new Blob([logoResponse.data], { type: 'image/jpeg' });
          const logoUrl = URL.createObjectURL(logoBlob);
          setLogoUrl(logoUrl);
        }
      }
    } catch (error) {
      console.error('Erreur lors de la récupération des données du profil:', error);
    }
  };

  const handleSaveCompteEntreprise = async (updatedData) => {
    try {
      const response = await axiosInstance.put(`/compte-entreprises/${compteEntreprise.idCompte}`, updatedData);
      setCompteEntreprise(response.data);
      setIsEditingCompteEntreprise(false);
    } catch (error) {
      console.error('Erreur lors de la mise à jour du CompteEntreprise:', error);
    }
  };

  const handleSaveEntreprise = async (updatedData) => {
    try {
      const response = await axiosInstance.put(`/api/entreprises/${entreprise.idEntreprise}`, updatedData);
      setEntreprise(response.data);
      setIsEditingEntreprise(false);
    } catch (error) {
      console.error('Erreur lors de la mise à jour de l\'Entreprise:', error);
    }
  };

  const handleLogoUpload = async (event) => {
    const file = event.target.files[0];
    if (file && entreprise) {
      const formData = new FormData();
      formData.append('logo', file);

      try {
        const response = await axiosInstance.put(`/api/entreprises/upload/${entreprise.idEntreprise}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        setEntreprise(response.data);

        // Update the logo URL
        const logoBlob = new Blob([file], { type: 'image/jpeg' });
        const logoUrl = URL.createObjectURL(logoBlob);
        setLogoUrl(logoUrl);
      } catch (error) {
        console.error('Erreur lors du téléchargement du logo:', error);
      }
    }
  };

  return (
    <Layout role="company">
      <div className="p-6 font-roboto">
        <h1 className="text-2xl font-bold mb-6 text-black">Profil du gestionnaire de l'entreprise</h1>

        {/* CompteEntreprise and Entreprise Information Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
          {/* CompteEntreprise Information Card */}
          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-xl font-semibold mb-4 text-black">Informations du compte entreprise</h2>
            {compteEntreprise && (
              <div className="space-y-2 text-black">
                <p><strong>Nom :</strong> {compteEntreprise.nom} {compteEntreprise.prenom}</p>
                <p><strong>Email :</strong> {compteEntreprise.email}</p>
                <p><strong>Téléphone :</strong> {compteEntreprise.telephone}</p>
                <button
                  onClick={() => setIsEditingCompteEntreprise(true)}
                  className="bg-black text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition-colors"
                >
                  Modifier
                </button>
              </div>
            )}
          </div>

          {/* Entreprise Information Card */}
          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-xl font-semibold mb-4 text-black">Informations de l'entreprise</h2>
            {entreprise && (
              <div className="space-y-2 text-black">
                {logoUrl && (
                  <div className="mb-4">
                    <img src={logoUrl} alt="Logo de l'entreprise" className="w-32 h-32 object-cover rounded-full" />
                  </div>
                )}
                <p><strong>Nom de l'entreprise :</strong> {entreprise.nomEntreprise}</p>
                <p><strong>Ville :</strong> {entreprise.villeEntreprise}</p>
                <p><strong>Adresse :</strong> {entreprise.adresseEntreprise}</p>
                <button
                  onClick={() => setIsEditingEntreprise(true)}
                  className="bg-black text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition-colors"
                >
                  Modifier
                </button>
                <input
                  type="file"
                  id="logo-upload"
                  className="hidden"
                  onChange={handleLogoUpload}
                />
                <label
                  htmlFor="logo-upload"
                  className="bg-black text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition-colors cursor-pointer"
                >
                  Changer la photo de profil
                </label>
              </div>
            )}
          </div>
        </div>

        {/* Modals for Editing */}
        {isEditingCompteEntreprise && (
          <FormComponent
            isOpen={isEditingCompteEntreprise}
            onClose={() => setIsEditingCompteEntreprise(false)}
            onSubmit={handleSaveCompteEntreprise}
            fields={[
              { name: 'nom', placeholder: 'Nom', type: 'text', required: true },
              { name: 'prenom', placeholder: 'Prénom', type: 'text', required: true },
              { name: 'email', placeholder: 'Email', type: 'email', required: true },
              { name: 'telephone', placeholder: 'Téléphone', type: 'text' },
              { name: 'motDePasse', placeholder: 'Mot de passe', type: 'password', required: true },
            ]}
            title="Modifier le compte entreprise"
            submitButtonText="Enregistrer"
            prefillData={compteEntreprise}
          />
        )}

        {isEditingEntreprise && (
          <FormComponent
            isOpen={isEditingEntreprise}
            onClose={() => setIsEditingEntreprise(false)}
            onSubmit={handleSaveEntreprise}
            fields={[
              { name: 'nomEntreprise', placeholder: 'Nom de l\'entreprise', type: 'text', required: true },
              { name: 'villeEntreprise', placeholder: 'Ville', type: 'text', required: true },
              { name: 'adresseEntreprise', placeholder: 'Adresse', type: 'text', required: true },
            ]}
            title="Modifier l'entreprise"
            submitButtonText="Enregistrer"
            prefillData={entreprise}
          />
        )}
      </div>
    </Layout>
  );
}