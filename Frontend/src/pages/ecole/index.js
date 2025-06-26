import Layout from '@/components/Layout';
import StatCard from '@/components/StatCard';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { motion } from 'framer-motion';
import { BarChart, Bar, XAxis, YAxis, Tooltip, Legend, PieChart, Pie, Cell } from 'recharts';
import { FaUniversity, FaUsers, FaBriefcase, FaUserGraduate } from 'react-icons/fa';

export default function UniversityDashboard() {
  const router = useRouter();
  const [stats, setStats] = useState([
    { title: 'Université', value: 'Loading...', icon: <FaUniversity /> },
    { title: 'Total des étudiants', value: 'Loading...', icon: <FaUsers /> },
    { title: 'Offres totales', value: 'Loading...', icon: <FaBriefcase /> },
    { title: 'Étudiants sans stage', value: 'Loading...', icon: <FaUserGraduate /> },
  ]);
  const [internshipPercentageByMajor, setInternshipPercentageByMajor] = useState([]);
  const [offersByMajor, setOffersByMajor] = useState([]);
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
      const coordinatorResponse = await axiosInstance.get(`/compte-ecoles/${id}`);
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

      // Fetch filieres by ecoleId
      const filieresResponse = await axiosInstance.get(`/api/filieres/ecole/${ecoleId}`);
      const filieres = filieresResponse.data;

      // Fetch internship percentage by major
      const internshipPercentageByMajor = [];
      for (const filiere of filieres) {
        const percentageResponse = await axiosInstance.get(`/compte-ecoles/internship-percentage/${filiere.idFiliere}`);
        internshipPercentageByMajor.push({
          name: filiere.nomFiliere,
          value: percentageResponse.data,
        });
      }

      // Fetch offers by major
      const offersByMajor = [];
      for (const filiere of filieres) {
        const offersResponse = await axiosInstance.get(`/compte-ecoles/${filiere.idFiliere}/visible-offers`);
        offersByMajor.push({
          name: filiere.nomFiliere,
          offers: offersResponse.data,
        });
      }

      // Update stats state
      setStats([
        { title: 'Université', value: universityName, icon: <FaUniversity /> },
        { title: 'Total des étudiants', value: totalStudents, icon: <FaUsers /> },
        { title: 'Offres totales', value: totalOffers, icon: <FaBriefcase /> },
        { title: 'Étudiants sans stage', value: studentsWithoutInternship, icon: <FaUserGraduate /> },
      ]);

      setInternshipPercentageByMajor(internshipPercentageByMajor);
      setOffersByMajor(offersByMajor);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching statistics:', error);
      setLoading(false);
    }
  };

  // Colors for the pie chart
  const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

  return (
    <Layout role="university">
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

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Pourcentage de stages par filière</h2>
            <PieChart width={400} height={300}>
              <Pie
                data={internshipPercentageByMajor}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(2)}%`}
                outerRadius={80}
                fill="#8884d8"
                dataKey="value"
              >
                {internshipPercentageByMajor.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </div>

          <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Offres par filière</h2>
            <BarChart width={400} height={300} data={offersByMajor}>
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="offers" fill="#8884d8" />
            </BarChart>
          </div>
        </div>
      </div>
    </Layout>
  );
}