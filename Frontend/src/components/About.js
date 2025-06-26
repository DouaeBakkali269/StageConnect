import { BookOpen, Building, Users } from 'lucide-react';

export default function About() {
  return (
    <section className="py-20">
      <h2 className="text-3xl font-bold text-center mb-12 text-black">À propos d&apos;InternConn</h2>
      <p className="text-center max-w-3xl mx-auto mb-16 text-black">
        InternConn est une plateforme innovante qui facilite la recherche et la gestion de stages
        pour les étudiants universitaires, les universités et les entreprises. Notre objectif est
        de simplifier le processus de recherche de stages et d&apos;offrir une expérience fluide
        à toutes les parties prenantes.
      </p>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div className="bg-gray-50 p-6 rounded-lg text-center hover:shadow-md transition-shadow duration-300">
          <Users className="w-12 h-12 mx-auto mb-4" />
          <h3 className="text-xl font-semibold mb-2">Étudiants</h3>
          <p>Trouvez facilement des stages adaptés à vos compétences et aspirations.</p>
        </div>
        <div className="bg-gray-50 p-6 rounded-lg text-center hover:shadow-md transition-shadow duration-300">
          <BookOpen className="w-12 h-12 mx-auto mb-4" />
          <h3 className="text-xl font-semibold mb-2">Universités</h3>
          <p>Gérez efficacement les stages de vos étudiants et suivez leur progression.</p>
        </div>
        <div className="bg-gray-50 p-6 rounded-lg text-center hover:shadow-md transition-shadow duration-300">
          <Building className="w-12 h-12 mx-auto mb-4" />
          <h3 className="text-xl font-semibold mb-2">Entreprises</h3>
          <p>Recrutez les meilleurs talents et contribuez à la formation de la future génération.</p>
        </div>
      </div>
    </section>
  );
}

