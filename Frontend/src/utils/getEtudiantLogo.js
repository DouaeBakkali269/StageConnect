import axiosInstance from '@/axiosInstance/axiosInstance';

async function getEtudiantLogoUrl(etudiantId) {
  try {
    console.log(`Etudiant Id collected for getting logo is: ${etudiantId}`);
    const logoResponse = await axiosInstance.get(`/api/etudiants/download/${etudiantId}/photo-profil`, {
      responseType: 'blob',
    });
    if (logoResponse.data.size > 0) {
      const logoBlob = new Blob([logoResponse.data], { type: 'image/jpeg' });
      console.log(`URL collected for image is: ${URL.createObjectURL(logoBlob)}`);
      
      return URL.createObjectURL(logoBlob);
    }
  } catch (error) {
    console.error('Error fetching etudiant logo:', error);
  }
  return null;
}

export default getEtudiantLogoUrl;