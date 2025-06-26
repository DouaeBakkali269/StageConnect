import Layout from '@/components/Layout';
import StatCard from '@/components/StatCard';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { motion } from 'framer-motion';
import { FaUniversity, FaUsers, FaBriefcase, FaUserGraduate } from 'react-icons/fa';

export default function CoordinatorDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState([
    { title: 'Université', value: 'Chargement...', icon: <FaUniversity /> },
    { title: 'Total des étudiants', value: 'Chargement...', icon: <FaUsers /> },
    { title: 'Offres totales', value: 'Chargement...', icon: <FaBriefcase /> },
    { title: 'Étudiants sans stage', value: 'Chargement...', icon: <FaUserGraduate /> },
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        axiosInstance.defaults.headers.Authorization = `Bearer ${token}`;
        fetchStatistics();
      } else {
        router.push('/');
      }
    } else {
      router.push('/');
    }
  }, [router]);

  const fetchStatistics = async () => {
    try {
      const id = localStorage.getItem('id'); // Get the coordinator ID from localStorage

      // Fetch coordinator details to get ecoleId
      const coordinatorResponse = await axiosInstance.get(`/api/coordinateurs/${id}`);
      const ecoleId = coordinatorResponse.data.ecoleId;

      // Fetch university name
      const universityResponse = await axiosInstance.get(`/api/ecoles/${ecoleId}`);
      const universityName = universityResponse.data.nomEcole;

      // Fetch total students
      const totalStudentsResponse = await axiosInstance.get(`/compte-ecoles/${ecoleId}/students`);
      const totalStudents = totalStudentsResponse.data;

      // Fetch total offers
      const totalOffersResponse = await axiosInstance.get('/api/admins/open-offers/count');
      const totalOffers = totalOffersResponse.data;

      // Fetch students without internships
      const studentsWithoutInternshipResponse = await axiosInstance.get(`/compte-ecoles/${ecoleId}/students-without-internship`);
      const studentsWithoutInternship = studentsWithoutInternshipResponse.data;

      // Update stats state
      setStats([
        { title: 'Université', value: universityName, icon: <FaUniversity /> },
        { title: 'Total des étudiants', value: totalStudents, icon: <FaUsers /> },
        { title: 'Offres totales', value: totalOffers, icon: <FaBriefcase /> },
        { title: 'Étudiants sans stage', value: studentsWithoutInternship, icon: <FaUserGraduate /> },
      ]);

      setLoading(false);
    } catch (error) {
      console.error('Erreur lors de la récupération des statistiques:', error);
      setLoading(false);
    }
  };

  return (
    <Layout role="coordinator">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold mb-6">Tableau de bord du coordinateur</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {stats.map((stat, index) => (
            <motion.div
              key={index}
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