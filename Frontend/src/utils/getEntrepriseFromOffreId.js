import axiosInstance from '@/axiosInstance/axiosInstance';

async function getEntrepriseFromOffreId(offreId) {
  try {
    const offreResponse = await axiosInstance.get(`/api/offres/${offreId}`);
    const entrepriseId = offreResponse.data.entrepriseId;
    const entrepriseResponse = await axiosInstance.get(`/api/entreprises/${entrepriseId}`);
    return entrepriseResponse.data;
  } catch (error) {
    console.error('Error fetching entreprise from offre ID:', error);
    return null;
  }
}

export default getEntrepriseFromOffreId;
