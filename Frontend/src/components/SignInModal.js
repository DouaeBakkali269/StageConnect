import { useState } from 'react';
import axiosInstance from '@/axiosInstance/axiosInstance';
import { useRouter } from 'next/router';
import axios from 'axios'


export default function SignInModal({ onClose }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    
    try {
      // Send login request
      const response = await axios.post('http://localhost:8080/api/auth/signin', {
        email: email,
        password : password,
      });

      // Store auth data in localStorage
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('userId', response.data.id);
      localStorage.setItem('role', response.data.roles[0]); // Assuming single role

      // Update axios instance header with new token
      axiosInstance.defaults.headers.Authorization = `Bearer ${response.data.token}`;

      console.log(axiosInstance.defaults);
      console.log(response);
      // Get role-specific ID based on user role
      const role = response.data.roles[0];
      let roleEndpoint = '';
      let idVariableReturnName = '';
      let navigationRoute = '';

      switch (role) {
        case 'ROLE_ADMIN':
          roleEndpoint = '/api/admins/user';
          idVariableReturnName = 'id';
          navigationRoute = '/admin';
          break;
        case 'ROLE_CHEF_DE_FILIERE':
          roleEndpoint = '/chefs-de-filiere/user';
          idVariableReturnName = 'idCf';
          navigationRoute = '/cf';
          break;
        case 'ROLE_ECOLE':
          roleEndpoint = '/compte-ecoles/user';
          idVariableReturnName = 'idCompte';
          navigationRoute = '/ecole';
          break;
        case 'ROLE_ENTREPRISE':
          roleEndpoint = '/compte-entreprises/user';
          idVariableReturnName = 'idCompte';
          navigationRoute = '/entreprise';
          break;
        case 'ROLE_COORDINATEUR_DE_STAGE':
          roleEndpoint = '/api/coordinateurs/user';
          idVariableReturnName = 'idCs';
          navigationRoute = '/coordinateur';
          break;
        case 'ROLE_RH':
          roleEndpoint = '/api/rh/user';
          idVariableReturnName = 'idRh';
          navigationRoute = '/rh';
          break;
        case 'ROLE_ENCADRANT':
          roleEndpoint = '/api/encadrants/user';
          idVariableReturnName = 'idEncadrant';
          navigationRoute = '/encadrant';
          break;
        case 'ROLE_ETUDIANT':
          roleEndpoint = '/api/etudiants/user';
          idVariableReturnName = 'idEtu';
          navigationRoute = '/etudiant';
          break;
        default:
          throw new Error('Unknown role');
      }

      
      console.log(`http://localhost:8080${roleEndpoint}/${response.data.id}`);
      console.log(`Bearer ${localStorage.getItem('token')}`);
      console.log(`${response.data.id}`);

      const roleResponseId = await axios.get(`http://localhost:8080${roleEndpoint}/${response.data.id}`, 
        {
          headers: {
            'Authorization' : `Bearer ${localStorage.getItem('token')}`,
            'Content-Type' : `application/json`
          }
        });


      localStorage.setItem("id", roleResponseId.data);

      console.log(roleResponseId);

      // Redirect based on role
      router.push(navigationRoute);

      onClose(); // Close the modal after successful login
    } catch (err) {
      if (err.response && err.response.status === 401) {
        setError('Email ou mot de passe incorrect');
      } else {
        setError(err.response?.data?.message || 'Une erreur est survenue lors de la connexion');
      }
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-white p-8 rounded-lg w-full max-w-md animate-fade-in-down">
        <h2 className="text-2xl font-bold mb-6">Se connecter</h2>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="block mb-1">
              Email :
            </label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black"
              required
            />
          </div>
          <div>
            <label htmlFor="password" className="block mb-1">
              Mot de passe :
            </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full px-4 py-2 bg-black text-white rounded-full hover:bg-gray-800 transition-colors duration-300"
          >
            Se connecter
          </button>
        </form>
        <button
          className="mt-4 text-gray-600 hover:text-gray-800"
          onClick={onClose}
        >
          Fermer
        </button>
      </div>
    </div>
  );
}
