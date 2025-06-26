import axiosInstance from '@/axiosInstance/axiosInstance';

async function downloadConvention(stageId) {
    try {
        const response = await axiosInstance.get(`/stages/${stageId}/download-convention`, {
            responseType: 'blob', // Important for downloading files
        });

        if (response.status === 200) {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const a = document.createElement('a');
            a.href = url;
            a.download = 'convention_de_stage.pdf';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        } else {
            alert('Erreur lors du téléchargement de la convention de stage');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

export default downloadConvention;