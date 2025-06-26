import Layout from '@/components/Layout';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import FormComponent from '@/components/FormComponent';

export default function EtudiantProfile() {
  const router = useRouter();
  const [etudiant, setEtudiant] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [isEditingImages, setIsEditingImages] = useState(false);
  const [photoProfil, setPhotoProfil] = useState(null);
  const [photoCouverture, setPhotoCouverture] = useState(null);
  const [profilePictureUrl, setProfilePictureUrl] = useState(null);
  const [coverPictureUrl, setCoverPictureUrl] = useState(null);
  const [etudiantId, setEtudiantId] = useState(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      const id = localStorage.getItem('id');
      if (!token || !id) {
        router.push('/');
      } else {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        setEtudiantId(id);
        fetchProfileData(id);
        fetchProfilePicture(id);
        fetchCoverPicture(id);
      }
    }
  }, [router]);

  const fetchProfileData = async (id) => {
    try {
      const response = await axiosInstance.get(`/api/etudiants/${id}`);
      setEtudiant(response.data);
    } catch (error) {
      console.error('Erreur lors de la récupération des données du profil :', error);
    }
  };

  const fetchProfilePicture = async (id) => {
    try {
      const response = await axiosInstance.get(`/api/etudiants/download/${id}/photo-profil`, {
        responseType: 'blob', // Important for handling binary data (images)
      });
      if (response.data) {
        const imageUrl = URL.createObjectURL(response.data);
        setProfilePictureUrl(imageUrl);
      }
    } catch (error) {
      console.error('Erreur lors de la récupération de la photo de profil :', error);
      setProfilePictureUrl(null); // Handle null response
    }
  };

  const fetchCoverPicture = async (id) => {
    try {
      const response = await axiosInstance.get(`/api/etudiants/download/${id}/photo-couverture`, {
        responseType: 'blob', // Important for handling binary data (images)
      });
      if (response.data) {
        const imageUrl = URL.createObjectURL(response.data);
        setCoverPictureUrl(imageUrl);
      }
    } catch (error) {
      console.error('Erreur lors de la récupération de la photo de couverture :', error);
      setCoverPictureUrl(null); // Handle null response
    }
  };

  const handleSave = async (updatedData) => {
    try {
      const response = await axiosInstance.put(`/api/etudiants/${etudiant.idEtu}`, {
        ...updatedData,
        filiereId: etudiant.filiereId,
        userId: etudiant.userId,
        codeEtu: etudiant.codeEtu,
      });
      setEtudiant(response.data);
      setIsEditing(false);
    } catch (error) {
      console.error('Erreur lors de la mise à jour de l\'étudiant :', error);
    }
  };

  const handleSaveImages = async () => {
    try {
      const formData = new FormData();
      if (photoProfil) formData.append('photoProfil', photoProfil);
      if (photoCouverture) formData.append('photoCouverture', photoCouverture);

      const response = await axiosInstance.put(`/api/etudiants/upload/${etudiantId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setEtudiant(response.data);
      setIsEditingImages(false);
      // Refresh the images after updating
      fetchProfilePicture(etudiantId);
      fetchCoverPicture(etudiantId);
    } catch (error) {
      console.error('Erreur lors de la mise à jour des images :', error);
    }
  };

  return (
    <Layout role="student">
      <div className="p-6 font-roboto">
        <h1 className="text-2xl font-bold mb-6 text-black">Profil de l'étudiant</h1>

        {/* Cover Picture */}
        {coverPictureUrl && (
          <div className="mb-6">
            <img
              src={coverPictureUrl}
              alt="Photo de couverture"
              className="w-full h-48 object-cover rounded-lg"
            />
          </div>
        )}

        {/* Etudiant Information Card */}
        <div className="bg-white shadow-lg rounded-lg p-6">
          <div className="flex items-center space-x-4">
            {/* Profile Picture */}
            {profilePictureUrl && (
              <img
                src={profilePictureUrl}
                alt="Photo de profil"
                className="w-24 h-24 rounded-full object-cover"
              />
            )}
            <div>
              <h2 className="text-xl font-semibold mb-4 text-black">Informations de l'étudiant</h2>
              {etudiant && (
                <div className="space-y-2 text-black">
                  <p><strong>Nom :</strong> {etudiant.nom} {etudiant.prenom}</p>
                  <p><strong>Email :</strong> {etudiant.email}</p>
                  <p><strong>Téléphone :</strong> {etudiant.tel}</p>
                  <div className="flex space-x-4">
                    <button
                      onClick={() => setIsEditing(true)}
                      className="bg-black text-white px-4 py-2 rounded-lg hover:bg-gray-800 transition-colors"
                    >
                      Modifier
                    </button>
                    <button
                      onClick={() => setIsEditingImages(true)}
                      className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition-colors"
                    >
                      Changer les photos
                    </button>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Modal for Editing */}
        {isEditing && (
          <FormComponent
            isOpen={isEditing}
            onClose={() => setIsEditing(false)}
            onSubmit={handleSave}
            fields={[
              { name: 'nom', placeholder: 'Nom', type: 'text', required: true },
              { name: 'prenom', placeholder: 'Prénom', type: 'text', required: true },
              { name: 'email', placeholder: 'Email', type: 'email', required: true },
              { name: 'tel', placeholder: 'Téléphone', type: 'text' },
              { name: 'motDePasse', placeholder: 'Mot de passe', type: 'password', required: true },
            ]}
            title="Modifier le profil de l'étudiant"
            submitButtonText="Enregistrer"
            prefillData={etudiant}
          />
        )}

        {/* Modal for Editing Images */}
        {isEditingImages && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg w-96">
              <h2 className="text-xl font-semibold mb-4 text-black">Changer les photos</h2>
              <form onSubmit={(e) => { e.preventDefault(); handleSaveImages(); }}>
                <div className="space-y-4">
                  {/* Current Profile Picture */}
                  {profilePictureUrl && (
                    <div>
                      <label className="block text-sm font-medium text-gray-700">Photo de profil actuelle</label>
                      <img
                        src={profilePictureUrl}
                        alt="Photo de profil actuelle"
                        className="w-24 h-24 rounded-full object-cover"
                      />
                    </div>
                  )}
                  <div>
                    <label className="block text-sm font-medium text-gray-700">Nouvelle photo de profil</label>
                    <input
                      type="file"
                      onChange={(e) => setPhotoProfil(e.target.files[0])}
                      className="mt-1 block w-full"
                      required // Make the field required
                    />
                  </div>

                  {/* Current Cover Picture */}
                  {coverPictureUrl && (
                    <div>
                      <label className="block text-sm font-medium text-gray-700">Photo de couverture actuelle</label>
                      <img
                        src={coverPictureUrl}
                        alt="Photo de couverture actuelle"
                        className="w-full h-24 object-cover rounded-lg"
                      />
                    </div>
                  )}
                  <div>
                    <label className="block text-sm font-medium text-gray-700">Nouvelle photo de couverture</label>
                    <input
                      type="file"
                      onChange={(e) => setPhotoCouverture(e.target.files[0])}
                      className="mt-1 block w-full"
                      required // Make the field required
                    />
                  </div>
                </div>
                <div className="mt-6 flex justify-end space-x-4">
                  <button
                    type="button"
                    onClick={() => setIsEditingImages(false)}
                    className="bg-gray-500 text-white px-4 py-2 rounded-lg hover:bg-gray-600 transition-colors"
                  >
                    Annuler
                  </button>
                  <button
                    type="submit"
                    className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition-colors"
                  >
                    Enregistrer
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}