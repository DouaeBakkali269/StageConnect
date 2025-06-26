import Navbar from '@/components/NavBar'
import { getNavLinks } from '@/config/navigation';
import { useRouter } from 'next/router';
import axiosInstance from '@/axiosInstance/axiosInstance';

export default function Layout({ children, role }) {
  const router = useRouter();

  const handleLogout = () => {
    console.log("Current Item");
    console.log(localStorage.getItem('token'));
    console.log("Removing Items");
    if (typeof window !== 'undefined') { // Ensure localStorage is available
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('role');
      localStorage.removeItem('id');
    }
    axiosInstance.defaults.headers.Authorization = ''; // Set axiosInstance headers.Authorization to null
    console.log(localStorage.getItem('token'));
    setTimeout(() => {
      router.push('/');
    }, 100); 
  };

  return (
    <div className="min-h-screen bg-gray-100 text-black">
      <Navbar 
              links={getNavLinks(role)} 
              onLogout={handleLogout}
            />
      <main className="p-8">{children}</main>
    </div>
  )
}
