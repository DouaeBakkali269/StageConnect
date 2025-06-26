import Link from 'next/link';
import { useRouter } from 'next/router';

export default function Navbar({ links, onLogout }) {
  return (
    <nav className="w-full bg-white shadow-sm font-['Roboto'] text-black shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <div className="flex items-center">
            <Link href="" className="text-2xl font-bold">
              InternConn
            </Link>
          </div>

          <div className="flex items-center space-x-10">
            {links.map((link, index) => (
              <Link 
                key={index}
                href={link.href}
                className={`
                  inline-flex items-center px-2 pt-1
                  text-lg font-bold text-black
                  relative group transition-colors
                  hover:text-gray-700
                `}
              >
                <span className="relative">
                  {link.label}
                  <span className="absolute inset-x-0 bottom-0 h-0.5 bg-black transform scale-x-0 group-hover:scale-x-100 transition-transform origin-left duration-500"></span>
                </span>
              </Link>
            ))}
          </div>

          <button
            onClick={onLogout}
            className="
              px-6 py-2.5 rounded-full text-base 
              text-white bg-black border border-gray-300
              shadow-md
              hover:bg-white hover:text-black hover:bg-opacity-90
              transition-colors duration-200 hover:font-bold
            "
          >
            Se Déconnecter
          </button>
        </div>
      </div>
    </nav>
  );
}