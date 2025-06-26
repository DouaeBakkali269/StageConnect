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
  FaUserGraduate,
  FaUsers,
} from 'react-icons/fa';

export default function StudentDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState({
    visibleOffers: 'Chargement...',
    applications: 'Chargement...',
    interviews: 'Chargement...',
    internships: 'Chargement...',
    universityStudents: 'Chargement...',
  });
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
      const idEtu = localStorage.getItem('id'); // Get the student ID from localStorage

      // Fetch student details to get ecoleId
      const studentResponse = await axiosInstance.get(`/api/etudiants/${idEtu}`);
      const ecoleId = studentResponse.data.ecoleId;
      const filiereId = studentResponse.data.filiereId;

      // Fetch available offers for the student's filiere
      const visibleOffersResponse = await axiosInstance.get(`/api/etudiants/${filiereId}/visible/count`);
      const visibleOffers = visibleOffersResponse.data;

      // Fetch my applications
      const applicationsResponse = await axiosInstance.get(`/api/etudiants/${idEtu}/postulations/count`);
      const applications = applicationsResponse.data;

      // Fetch internships found
      const internshipsResponse = await axiosInstance.get(`/api/etudiants/${idEtu}/internships/count`);
      const internships = internshipsResponse.data;

      // Fetch upcoming interviews
      const interviewsResponse = await axiosInstance.get(`/api/etudiants/student/${idEtu}/count-interviews`);
      const interviews = interviewsResponse.data;

      // Fetch university students
      const universityStudentsResponse = await axiosInstance.get(`/compte-ecoles/${ecoleId}/students`);
      const universityStudents = universityStudentsResponse.data;

      // Update stats state
      setStats({
        visibleOffers: visibleOffers,
        applications: applications,
        interviews: interviews,
        internships: internships,
        universityStudents: universityStudents,
      });

      setLoading(false);
    } catch (error) {
      console.error('Erreur lors de la récupération des statistiques:', error);
      setLoading(false);
    }
  };

  return (
    <Layout role="student">
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Tableau de bord</h1>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.1 }}
          >
            <StatCard title="Offres disponibles" value={stats.visibleOffers} iconCard={<FaBriefcase />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 }}
          >
            <StatCard title="Mes candidatures" value={stats.applications} iconCard={<FaClipboardList />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
          >
            <StatCard title="Entretiens à venir" value={stats.interviews} iconCard={<FaCalendarAlt />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.4 }}
          >
            <StatCard title="Stages actifs" value={stats.internships} iconCard={<FaUserGraduate />} />
          </motion.div>
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.5 }}
          >
            <StatCard title="Étudiants de l'université" value={stats.universityStudents} iconCard={<FaUsers />} />
          </motion.div>
        </div>
      </div>
    </Layout>
  );
}