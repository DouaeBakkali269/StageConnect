import { useState } from 'react';
import Head from 'next/head';
import Navbar from '@/components/NavBarH';
import Hero from '@/components/Hero';
import About from '@/components/About';
import Contact from '@/components/Contact';
import SignInModal from '@/components/SignInModal';

export default function Home() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const toggleModal = () => {
    setIsModalOpen(!isModalOpen);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-black">
      <Head>
        <title>InternConn - Connecter les étudiants et les entreprises</title>
        <meta name="description" content="Plateforme de gestion de stages pour étudiants, universités et entreprises" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <Navbar onSignInClick={toggleModal} />
      <Hero onCtaClick={toggleModal} />
      <About />
      <Contact />
      {isModalOpen && <SignInModal onClose={toggleModal} />}
    </div>
  );
}

