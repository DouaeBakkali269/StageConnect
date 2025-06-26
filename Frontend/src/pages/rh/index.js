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

export default function HRDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState([
    { title: 'Offres ouvertes', value: 'Chargement...', iconCard: <FaBriefcase /> },
    { title: 'Total des offres', value: 'Chargement...', iconCard: <FaClipboardList /> },
    { title: 'Total des entretiens', value: 'Chargement...', iconCard: <FaCalendarAlt /> },
    { title: 'Total des RH', value: 'Chargement...', iconCard: <FaUserTie /> },
    { title: 'Total des superviseurs', value: 'Chargement...', iconCard: <FaUserGraduate /> },
    { title: 'Stages confirmés', value: 'Chargement...', iconCard: <FaChartLine /> },
    { title: 'Total des offres de stages', value: 'Chargement...', iconCard: <FaUsers /> },
    { title: 'Total des candidatures', value: 'Chargement...', iconCard: <FaBuilding /> },
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async (token, id) => {
      try {
        // Set authorization header
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;

        // Fetch HR data
        const hrResponse = await axiosInstance.get(`/api/rh/${id}`);
        const entrepriseId = hrResponse.data.entrepriseId;

        // Fetch statistics
        const endpoints = [
          { title: 'Offres ouvertes', endpoint: `/compte-entreprises/${entrepriseId}/open-offers` },
          { title: 'Total des offres', endpoint: `/compte-entreprises/${entrepriseId}/total-offers` },
          { title: 'Total des entretiens', endpoint: `/compte-entreprises/${entrepriseId}/total-interviews` },
          { title: 'Total des RH', endpoint: `/compte-entreprises/${entrepriseId}/rh` },
          { title: 'Total des superviseurs', endpoint: `/compte-entreprises/${entrepriseId}/supervisors` },
          { title: 'Stages confirmés', endpoint: `/compte-entreprises/${entrepriseId}/total-internships-confirmed` },
          { title: 'Total des offres de stages', endpoint: `/compte-entreprises/${entrepriseId}/total-internships` },
          { title: 'Total des candidatures', endpoint: `/compte-entreprises/countByEntreprise/${entrepriseId}` },
        ];

        const statsData = await Promise.all(
          endpoints.map(async (stat) => {
            const response = await axiosInstance.get(stat.endpoint);
            return { title: stat.title, value: response.data, icon: stat.icon };
          })
        );

        setStats(statsData);
        setLoading(false);
      } catch (error) {
        console.error('Erreur lors de la récupération des données:', error);
        router.push('/');
      }
    };

    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      const id = localStorage.getItem('id');
      if (token && id) {
        fetchData(token, id);
      } else {
        router.push('/');
      }
    }
  }, [router]);

  if (loading) {
    return <div>Chargement...</div>;
  }

  return (
    <Layout role="hr">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold mb-6">Tableau de bord RH</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
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