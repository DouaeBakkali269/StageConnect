import Layout from '@/components/Layout';
import StatCard from '@/components/StatCard';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { motion } from 'framer-motion';
import {
  FaBriefcase,
  FaClipboardList,
  FaCalendarAlt,
  FaUserTie,
  FaUserGraduate,
  FaChartLine,
  FaUsers,
  FaBuilding,
} from 'react-icons/fa';

export default function CompanyDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState([
    { title: 'Offres ouvertes', value: 'Chargement...', icon: <FaBriefcase /> },
    { title: 'Total des offres', value: 'Chargement...', icon: <FaClipboardList /> },
    { title: 'Total des entretiens', value: 'Chargement...', icon: <FaCalendarAlt /> },
    { title: 'Total des RH', value: 'Chargement...', icon: <FaUserTie /> },
    { title: 'Total des superviseurs', value: 'Chargement...', icon: <FaUserGraduate /> },
    { title: 'Stages confirmés', value: 'Chargement...', icon: <FaChartLine /> },
    { title: 'Total des offres de stages', value: 'Chargement...', icon: <FaUsers /> },
    { title: 'Stages actifs de l\'entreprise', value: 'Chargement...', icon: <FaBuilding /> },
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Check for token
        const token = localStorage.getItem('token');
        if (!token) {
          router.push('/');
          return;
        }

        // Set authorization header
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;

        // Fetch compte-entreprise data
        const id = localStorage.getItem('id');
        const compteResponse = await axiosInstance.get(`/compte-entreprises/${id}`);
        const entrepriseId = compteResponse.data.entrepriseId;

        // Fetch statistics
        const openOffersRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/open-offers`);
        const totalOffersRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/total-offers`);
        const totalInterviewsRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/total-interviews`);
        const totalHRRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/rh`);
        const totalSupervisorsRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/supervisors`);
        const confirmedInternshipsRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/total-internships-confirmed`);
        const totalInternshipsRes = await axiosInstance.get(`/compte-entreprises/${entrepriseId}/total-internships`);

        // Fetch company active internships
        const activeInternshipsRes = await axiosInstance.get(`/api/encadrants/company/${entrepriseId}/active-internships`);

        // Update stats state
        setStats([
          { title: 'Offres ouvertes', value: openOffersRes.data, icon: <FaBriefcase /> },
          { title: 'Total des offres', value: totalOffersRes.data, icon: <FaClipboardList /> },
          { title: 'Total des entretiens', value: totalInterviewsRes.data, icon: <FaCalendarAlt /> },
          { title: 'Total des RH', value: totalHRRes.data, icon: <FaUserTie /> },
          { title: 'Total des superviseurs', value: totalSupervisorsRes.data, icon: <FaUserGraduate /> },
          { title: 'Stages confirmés', value: confirmedInternshipsRes.data, icon: <FaChartLine /> },
          { title: 'Total des offres de stages', value: totalInternshipsRes.data, icon: <FaUsers /> },
          { title: 'Stages actifs de l\'entreprise', value: activeInternshipsRes.data, icon: <FaBuilding /> },
        ]);

        setLoading(false);
      } catch (error) {
        console.error('Erreur lors de la récupération des données:', error);
        router.push('/');
      }
    };

    fetchData();
  }, [router]);

  if (loading) {
    return <div>Chargement...</div>;
  }

  return (
    <Layout role="company">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold mb-6">Tableau de bord de l&apos;entreprise</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {stats.map((stat, index) => (
            <motion.div
              key={stat.title}
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.2 }}
            >
              <StatCard title={stat.title} value={stat.value} iconCard={stat.icon} />
            </motion.div>
          ))}
        </div>
      </div>
    </Layout>
  );
}