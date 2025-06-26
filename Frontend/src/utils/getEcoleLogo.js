import axiosInstance from '@/axiosInstance/axiosInstance';

async function getEcoleLogoUrl(ecoleId) {
  try {
    const logoResponse = await axiosInstance.get(`/api/ecoles/download/${ecoleId}/logo`, {
      responseType: 'blob',
    });
    if (logoResponse.data.size > 0) {
      const logoBlob = new Blob([logoResponse.data], { type: 'image/jpeg' });
      return URL.createObjectURL(logoBlob);
    }
  } catch (error) {
    console.error('Error fetching logo:', error);
  }
  return null;
}

export default getEcoleLogoUrl;
