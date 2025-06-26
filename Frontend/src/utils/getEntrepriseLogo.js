import axiosInstance from '@/axiosInstance/axiosInstance';

async function getEntrepriseLogoUrl(entrepriseId) {
  try {
    console.log(`Entreprise Id collected for  getting logo is: ${entrepriseId}`)
    const logoResponse = await axiosInstance.get(`/api/entreprises/download/${entrepriseId}/logo`, {
      responseType: 'blob',
    });
    if (logoResponse.data.size > 0) {
      const logoBlob = new Blob([logoResponse.data], { type: 'image/jpeg' });
      console.log(`URL collected for image is: ${URL.createObjectURL(logoBlob)}`);
      
      return URL.createObjectURL(logoBlob);
    }
  } catch (error) {
    console.error('Error fetching entreprise logo:', error);
  }
  return null;
}

export default getEntrepriseLogoUrl;
