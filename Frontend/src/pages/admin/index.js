import Layout from '@/components/Layout';
import StatCard from '@/components/StatCard';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { motion } from 'framer-motion';
import { FaUniversity, FaBuilding, FaUsers, FaBriefcase, FaUserGraduate, FaClipboardList, FaCalendarAlt, FaChartLine } from 'react-icons/fa';

export default function Dashboard() {
  const router = useRouter();
  const [stats, setStats] = useState({
    universityAccounts: 0,
    companyAccounts: 0,
    students: 0,
    openOffers: 0,
    totalOffers: 0,
    internships: 0,
    ongoingInternships: 0,
    interviews: 0,
    totalPlatformUsers: 0,
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchStats();
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  const fetchStats = async () => {
    try {
      const universityAccountsRes = await axiosInstance.get('/api/admins/ecoles');
      const companyAccountsRes = await axiosInstance.get('/api/admins/entreprises');
      const studentsRes = await axiosInstance.get('/api/admins/etudiants');
      const openOffersRes = await axiosInstance.get('/api/admins/open-offers/count');
      const totalOffersRes = await axiosInstance.get('/api/admins/offers');
      const internshipsRes = await axiosInstance.get('/api/admins/stages-offres');
      const ongoingInternshipsRes = await axiosInstance.get('/api/admins/stages/ongoing');
      const interviewsRes = await axiosInstance.get('/api/admins/entretiens');
      const totalPlatformUsersRes = await axiosInstance.get('/api/admins/count-platform-users');

      setStats({
        universityAccounts: universityAccountsRes.data,
        companyAccounts: companyAccountsRes.data,
        students: studentsRes.data,
        openOffers: openOffersRes.data,
        totalOffers: totalOffersRes.data,
        internships: internshipsRes.data,
        ongoingInternships: ongoingInternshipsRes.data,
        interviews: interviewsRes.data,
        totalPlatformUsers: totalPlatformUsersRes.data,
      });
      setLoading(false);
    } catch (error) {
      console.error('Error fetching stats:', error);
      setLoading(false);
    }
  };

  return (
    <Layout role="admin">
      <div className="space-y-6">
        <h1 className="text-3xl font-bold mb-12 mt-8">Tableau de bord Admin</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5">
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.1 }}
          >
            <StatCard title="Comptes Universités" value={stats.universityAccounts} iconCard={<FaUniversity />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 }}
          >
            <StatCard title="Comptes Entreprises" value={stats.companyAccounts} iconCard={<FaBuilding />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
          >
            <StatCard title="Étudiants" value={stats.students} iconCard={<FaUsers />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.4 }}
          >
            <StatCard title="Offres Ouvertes" value={stats.openOffers} iconCard={<FaBriefcase />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.5 }}
          >
            <StatCard title="Total des Offres" value={stats.totalOffers} iconCard={<FaClipboardList />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.6 }}
          >
            <StatCard title="Stages Créés" value={stats.internships} iconCard={<FaUserGraduate />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.7 }}
          >
            <StatCard title="Stages en Cours" value={stats.ongoingInternships} iconCard={<FaCalendarAlt />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.8 }}
          >
            <StatCard title="Entretiens" value={stats.interviews} iconCard={<FaChartLine />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.9 }}
          >
            <StatCard title="Utilisateurs Totaux" value={stats.totalPlatformUsers} iconCard={<FaUsers />} />
          </motion.div>
        </div>
      </div>
    </Layout>
  );
}