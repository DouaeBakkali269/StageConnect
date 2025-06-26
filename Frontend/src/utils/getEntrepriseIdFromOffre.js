import axiosInstance from '@/axiosInstance/axiosInstance';

async function getEntrepriseIdFromOffre(offreId) {
  try {
    const response = await axiosInstance.get(`/api/offres/${offreId}`);
    return response.data.entrepriseId;
  } catch (error) {
    console.error('Error fetching entreprise ID from offre:', error);
    return null;
  }
}

export default getEntrepriseIdFromOffre;
