import { ArrowRight } from 'lucide-react';

export default function Hero({ onCtaClick }) {
  return (
    <section className="text-center py-20 bg-gray-100">
      <h1 className="text-4xl md:text-5xl font-bold mb-4 animate-fade-in-up">
        Bienvenue sur InternConn
      </h1>
      <p className="text-xl md:text-2xl mb-8 animate-fade-in-up animation-delay-200">
        Connecter les étudiants, les universités et les entreprises pour des stages réussis
      </p>
      <button
        className="inline-flex items-center px-6 py-3 bg-black text-white rounded-full hover:bg-gray-800 transition-colors duration-300 animate-fade-in-up animation-delay-400"
        onClick={onCtaClick}
      >
        Commencer maintenant
        <ArrowRight className="ml-2 w-5 h-5" />
      </button>
    </section>
  );
}

