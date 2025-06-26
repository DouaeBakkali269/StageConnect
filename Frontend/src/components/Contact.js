import { Mail, Phone, MapPin } from 'lucide-react';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { useState } from 'react'; // Missing useState import

export default function Contact() {
  const [formData, setFormData] = useState({
    name: '',
    email: '', 
    message: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post(`/api/admins/send/${formData.name}/${formData.email}/${formData.message}`);
      // Reset form after successful submission
      setFormData({
        name: '',
        email: '',
        message: ''
      });
      // Add success message/notification
    } catch (error) {
      console.error('Error sending message:', error);
      // Add error handling/user feedback
    }
  };
  
  return (
    <section className="py-20 bg-gray-100">
      <h2 className="text-3xl font-bold text-center mb-12">Contactez-nous</h2>
      <p className="text-center mb-12">
        Vous avez des questions ou besoin d&apos;assistance ? N&apos;hésitez pas à nous contacter :
      </p>
      <div className="flex flex-col md:flex-row justify-around mb-12">
        <div className="flex items-center mb-4 md:mb-0">
          <Mail className="w-6 h-6 mr-2" />
          <p>contact@internconn.com</p>
        </div>
        <div className="flex items-center mb-4 md:mb-0">
          <Phone className="w-6 h-6 mr-2" />
          <p>+33 1 23 45 67 89</p>
        </div>
        <div className="flex items-center">
          <MapPin className="w-6 h-6 mr-2" />
          <p>123 Rue de l&apos;Innovation, 75001 Paris, France</p>
        </div>
      </div>
      <form className="max-w-lg mx-auto" onSubmit={handleSubmit}>
        <div className="mb-4">
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Votre nom"
            className="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black"
            required
          />
        </div>
        <div className="mb-4">
          <input
            type="email" 
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Votre email"
            className="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black"
            required
          />
        </div>
        <div className="mb-4">
          <textarea
            name="message"
            value={formData.message}
            onChange={handleChange}
            placeholder="Votre message"
            className="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black h-32"
            required
          ></textarea>
        </div>
        <button
          type="submit"
          className="w-full px-4 py-2 bg-black text-white rounded-full hover:bg-gray-800 transition-colors duration-300"
        >
          Envoyer
        </button>
      </form>
    </section>
  );
}
