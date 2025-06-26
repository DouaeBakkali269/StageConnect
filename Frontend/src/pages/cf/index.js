import Layout from '@/components/Layout';
import StatCard from '@/components/StatCard';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { motion } from 'framer-motion';
import { FaUsers, FaUserCheck, FaBriefcase, FaClipboardCheck } from 'react-icons/fa';

export default function CFDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState([
    { title: 'Étudiants dans la filière', value: 'Chargement...', icon: <FaUsers /> },
    { title: 'Étudiants avec stages', value: 'Chargement...', icon: <FaUserCheck /> },
    { title: 'Offres visibles', value: 'Chargement...', icon: <FaBriefcase /> },
    { title: 'Stages trouvés', value: 'Chargement...', icon: <FaClipboardCheck /> },
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
      const id = localStorage.getItem('id'); // Get the CF ID from localStorage

      // Fetch ChefDeFiliere details to get filiereId
      const chefDeFiliereResponse = await axiosInstance.get(`/chefs-de-filiere/${id}`);
      const filiereId = chefDeFiliereResponse.data.filiereId;

      // Fetch total students in the major
      const totalStudentsResponse = await axiosInstance.get(`/compte-ecoles/${filiereId}/total-students`);
      const totalStudents = totalStudentsResponse.data;

      // Fetch students with internships
      const studentsWithInternshipsResponse = await axiosInstance.get(`/compte-ecoles/students-with-internship/${filiereId}`);
      const studentsWithInternships = studentsWithInternshipsResponse.data;

      // Fetch internships found
      const internshipsFoundResponse = await axiosInstance.get(`/compte-ecoles/students-with-internship/${filiereId}`);
      const internshipsFound = internshipsFoundResponse.data;

      // Fetch visible offers
      const visibleOffersResponse = await axiosInstance.get(`/compte-ecoles/${filiereId}/visible-offers`);
      const visibleOffers = visibleOffersResponse.data;

      // Update stats state
      setStats([
        { title: 'Étudiants dans la filière', value: totalStudents, icon: <FaUsers /> },
        { title: 'Étudiants avec stages', value: studentsWithInternships, icon: <FaUserCheck /> },
        { title: 'Offres visibles', value: visibleOffers, icon: <FaBriefcase /> },
        { title: 'Stages trouvés', value: internshipsFound, icon: <FaClipboardCheck /> },
      ]);

      setLoading(false);
    } catch (error) {
      console.error('Erreur lors de la récupération des statistiques:', error);
      setLoading(false);
    }
  };

  return (
    <Layout role="cf">
      <div className="space-y-6">
        <h1 className="text-2xl font-bold mb-6">Tableau de bord du chef de filière</h1>
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