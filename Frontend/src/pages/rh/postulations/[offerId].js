import Layout from '@/components/Layout';
import SearchBar from '@/components/university/SearchBar';
import Card from '@/components/Card';
import FormComponent from '@/components/FormComponent';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import getEtudiantLogoUrl from '@/utils/getEtudiantLogo';

export default function HRApplicationManagement() {
  const router = useRouter();
  const { offerId } = router.query;

  const [isInterviewFormOpen, setIsInterviewFormOpen] = useState(false);
  const [selectedPostulationId, setSelectedPostulationId] = useState(null);
  const [postulations, setPostulations] = useState([]);
  const [filteredPostulations, setFilteredPostulations] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [logoUrls, setLogoUrls] = useState({}); // State to store logo URLs for each postulation

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        if (offerId) fetchPostulations(offerId);
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router, offerId]);

  // Fetch logo URLs for all postulations
  useEffect(() => {
    if (postulations.length > 0) {
      const fetchLogoUrls = async () => {
        const urls = {};
        for (const postulation of postulations) {
          const url = await getEtudiantLogoUrl(postulation.etudiantId); // Fetch the logo URL
          urls[postulation.id] = url; // Store the URL with the postulation ID as the key
        }
        setLogoUrls(urls);
      };

      fetchLogoUrls();
    }
  }, [postulations]);

  const fetchPostulations = async (offerId) => {
    try {
      const response = await axiosInstance.get(`/api/postulations/offre/${offerId}`);
      const pendingPostulations = response.data.filter(
        (postulation) => postulation.etatPostulation === 'En attente'
      );
      setPostulations(pendingPostulations);
      setFilteredPostulations(pendingPostulations);
    } catch (error) {
      console.error('Erreur lors de la récupération des postulations:', error);
    }
  };

  const handleSearch = (query) => {
    setSearchQuery(query);
    const filtered = postulations.filter(
      (postulation) =>
        postulation.etudiantId.toString().includes(query) ||
        postulation.offreId.toString().includes(query)
    );
    setFilteredPostulations(filtered);
  };

  const handleScheduleInterview = (postulationId) => {
    setSelectedPostulationId(postulationId);
    setIsInterviewFormOpen(true);
  };

  const handleRefuse = async (postulationId) => {
    try {
      await axiosInstance.patch(`/api/postulations/${postulationId}/etat`, null, {
        params: { etat: 'refusé' },
      });
      fetchPostulations(offerId); // Refresh the list
    } catch (error) {
      console.error('Erreur lors du refus de la postulation:', error);
    }
  };

  const handleCreateInterview = async (data) => {
    try {
      const postulation = postulations.find((p) => p.id === selectedPostulationId);
      const entretienDTO = {
        dateEntretien: new Date(`${data.date}T${data.time}`).toISOString(),
        adresse: data.location,
        duree: data.duration,
        etat: 'Planifié',
        resultat: 'nouveau',
        lien: data.link,
        offreId: postulation.offreId,
        etudiantId: postulation.etudiantId,
      };

      await axiosInstance.post('/entretiens', entretienDTO);
      await axiosInstance.patch(`/api/postulations/${selectedPostulationId}/etat`, null, {
        params: { etat: 'accepté' },
      });

      setIsInterviewFormOpen(false);
      fetchPostulations(offerId); // Refresh the list
    } catch (error) {
      console.error('Erreur lors de la création de l\'entretien:', error);
    }
  };

  if (!offerId) return null;

  return (
    <Layout role="hr">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold">Gestion des candidatures</h1>
        <div className="mb-6">
          <SearchBar onSearch={handleSearch} />
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {filteredPostulations.map((postulation) => (
            <PostulationCard
              key={postulation.id}
              postulation={postulation}
              handleScheduleInterview={handleScheduleInterview}
              handleRefuse={handleRefuse}
              logoUrl={logoUrls[postulation.etudiantId]} // Pass the logo URL to the PostulationCard
            />
          ))}
        </div>

        <FormComponent
          isOpen={isInterviewFormOpen}
          onClose={() => setIsInterviewFormOpen(false)}
          onSubmit={handleCreateInterview}
          fields={[
            { name: 'date', type: 'date', placeholder: "Date de l'entretien", required: true },
            { name: 'time', type: 'time', placeholder: "Heure de l'entretien", required: true },
            { name: 'location', placeholder: 'Lieu de l\'entretien', required: true },
            { name: 'duration', placeholder: 'Durée', required: true },
            { name: 'link', placeholder: 'Lien de la réunion', required: true },
          ]}
          title="Planifier un entretien"
          submitButtonText="Créer"
        />
      </div>
    </Layout>
  );
}

function PostulationCard({ postulation, handleScheduleInterview, handleRefuse, logoUrl }) {
  const [offer, setOffer] = useState(null);
  const [student, setStudent] = useState(null);

  useEffect(() => {
    axiosInstance
      .get(`/api/offres/${postulation.offreId}`)
      .then((response) => setOffer(response.data))
      .catch((error) => console.error('Erreur lors de la récupération de l\'offre:', error));

    axiosInstance
      .get(`/api/etudiants/${postulation.etudiantId}`)
      .then((response) => setStudent(response.data))
      .catch((error) => console.error('Erreur lors de la récupération de l\'étudiant:', error));
  }, [postulation.offreId, postulation.etudiantId]);

  const handleViewPdf = async (postulationId, type) => {
    try {
      const endpoint =
        type === 'cv'
          ? `/api/postulations/download/${postulationId}/cv`
          : `/api/postulations/download/${postulationId}/lettre-motivation`;

      const response = await axiosInstance.get(endpoint, {
        responseType: 'blob', // Important: tells axios to expect binary data
      });

      const blob = new Blob([response.data], { type: 'application/pdf' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = type === 'cv' ? 'cv.pdf' : 'lettre_motivation.pdf';

      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(link.href);
    } catch (error) {
      console.error('Erreur lors du téléchargement du PDF:', error);
    }
  };

  if (!offer || !student) return <div>Chargement...</div>;

  return (
    <Card
      title={`Postulation #${postulation.id}`}
      specifications={[
        { label: 'Étudiant', value: `${student.nom} ${student.prenom}` },
        { label: 'Offre', value: offer.objetOffre },
        { label: 'État', value: postulation.etatPostulation },
      ]}
      buttons={[
        {
          label: 'Accepter',
          onClick: () => handleScheduleInterview(postulation.id),
        },
        {
          label: 'Refuser',
          onClick: () => handleRefuse(postulation.id),
        },
      ]}
      extraContent={
        <div>
          <a
            href="#"
            onClick={() => handleViewPdf(postulation.id, 'cv')}
            className="text-blue-600 hover:underline"
          >
            Voir CV
          </a>
          <br />
          <a
            href="#"
            onClick={() => handleViewPdf(postulation.id, 'ldm')}
            className="text-blue-600 hover:underline"
          >
            Voir Lettre de Motivation
          </a>
        </div>
      }
      imageSrc={logoUrl} // Use the logo URL passed from the parent component
    />
  );
}