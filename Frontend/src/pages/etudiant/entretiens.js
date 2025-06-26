import { useState, useEffect } from 'react';
import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import getEntrepriseLogoUrl from '@/utils/getEntrepriseLogo'; // Import the logo fetching function

export default function StudentInterviews() {
  const router = useRouter();
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState(null);
  const [logoUrls, setLogoUrls] = useState({}); // State to store logo URLs for each interview

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      const etudiantId = localStorage.getItem('id');

      if (token && etudiantId) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchInterviews(etudiantId);
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  // Fetch logo URLs for all interviews
  useEffect(() => {
    if (interviews.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const interview of interviews) {
          const entrepriseId = interview.offre.entrepriseId; // Get entrepriseId from the offer
          const url = await getEntrepriseLogoUrl(entrepriseId); // Fetch the logo URL
          urls[interview.idEntretien] = url; // Store the URL with the interview ID as the key
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [interviews]);

  const fetchInterviews = async (etudiantId) => {
    try {
      const response = await axiosInstance.get(`/entretiens/etudiant/${etudiantId}`);
      console.log(response.data);

      // Fetch additional details for each interview
      const interviewsWithDetails = await Promise.all(
        response.data.map(async (entretien) => {
          const etudiantResponse = await axiosInstance.get(`/api/etudiants/${entretien.etudiantId}`);
          const offreResponse = await axiosInstance.get(`/api/offres/${entretien.offreId}`);

          return {
            ...entretien,
            etudiant: etudiantResponse.data,
            offre: offreResponse.data,
          };
        })
      );

      setInterviews(interviewsWithDetails);
    } catch (error) {
      console.error('Erreur lors de la récupération des entretiens :', error);
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };

  const handleSearch = (query) => {
    console.log('Recherche pour :', query);
  };

  const handleJoinMeeting = (meetingLink) => {
    if (meetingLink && meetingLink.startsWith('http')) {
      window.open(meetingLink, '_blank');
    } else {
      setErrorMessage('Lien introuvable');
      setTimeout(() => setErrorMessage(null), 3000);
    }
  };

  if (loading) {
    return <div>Chargement...</div>;
  }

  return (
    <Layout role="student">
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Mes entretiens</h1>
        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {interviews.map((interview) => (
            <Card
              key={interview.idEntretien}
              title={interview.offre.objetOffre}
              specifications={[
                { label: 'Date', value: formatDate(interview.dateEntretien) },
                { label: 'Adresse', value: interview.adresse },
                { label: 'Durée', value: interview.duree },
                { label: 'Statut', value: interview.etat },
                { label: 'Résultat', value: interview.resultat },
                { label: 'Lien de réunion', value: interview.lien, isLink: true },
              ]}
              buttons={[
                {
                  label: 'Rejoindre réunion',
                  onClick: () => handleJoinMeeting(interview.lien),
                },
              ]}
              imageSrc={logoUrls[interview.idEntretien]} // Use the logo URL from state
            >
              {errorMessage && <p className="text-red-500">{errorMessage}</p>}
            </Card>
          ))}
        </div>
      </div>
    </Layout>
  );
}