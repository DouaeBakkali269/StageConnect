import { motion } from 'framer-motion';

const StatCard = ({ title, value, iconCard }) => {
  return (
    <motion.div
      className="bg-white p-4 rounded-lg shadow-md"
      whileHover={{ scale: 1.05 }}
      transition={{ type: 'spring', stiffness: 300 }}
    >
      <div className="flex items-center space-x-4">
        <div className="text-2xl text-blue-500">{iconCard}</div>
        <div>
          <h3 className="text-lg font-semibold">{title}</h3>
          <p className="text-3xl font-bold">{value}</p>
        </div>
      </div>
    </motion.div>
  );
};

export default StatCard;