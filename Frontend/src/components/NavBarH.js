import { Menu } from 'lucide-react';

export default function NavbarH({ onSignInClick }) {
  return (
    <nav className="flex justify-between items-center py-4 bg-white shadow-md">
      <div className="flex items-center">
        <Menu className="w-6 h-6 mr-2" />
        <span className="text-xl font-bold">InternConn</span>
      </div>
      <button
        className="hidden md:block px-4 py-2 bg-black text-white rounded-full hover:bg-gray-800 transition-colors duration-300"
        onClick={onSignInClick}
      >
        Se connecter
      </button>
      <button className="md:hidden">
        <Menu className="w-6 h-6" />
      </button>
    </nav>
  );
}

