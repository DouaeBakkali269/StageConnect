//package STAGE.stage.config;
//
//    import STAGE.stage.dtos.EtudiantDTO;
//    import STAGE.stage.mappers.EntityMapper;
//    import STAGE.stage.models.*;
//    import STAGE.stage.repositories.*;
//    import STAGE.stage.services.EtudiantService;
//    import lombok.RequiredArgsConstructor;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.CommandLineRunner;
//    import org.springframework.stereotype.Component;
//    import java.text.SimpleDateFormat;
//    import java.util.Date;
//
//    import java.util.List;
//
//    @Component
//    @RequiredArgsConstructor
//    public class DatabaseSeeder implements CommandLineRunner {
//
//        private final EcoleRepository ecoleRepository;
//        private final FiliereRepository filiereRepository;
//        private final EtudiantRepository etudiantRepository;
//        private final EntrepriseRepository entrepriseRepository;
//        private final OffreRepository offreRepository;
//        private final EncadrantRepository encadrantRepository;
//        private final RHRepository rhRepository;
//        @Autowired
//        private EtudiantService etudiantService;
//        private final EntityMapper entityMapper;
//        private final UserRepository utilisateurRepository;
//
//
//        @Override
//        public void run(String... args) throws Exception {
//            // Seed Ecoles
//            Ecole ecole1 = new Ecole("Ecole Polytechnique", "Paris", "123 Rue de Paris", "Top engineering school", "0123456789", "Public", "Engineering");
//            Ecole ecole2 = new Ecole("Ecole Centrale", "Lyon", "456 Rue de Lyon", "Renowned engineering school", "0987654321", "Private", "Engineering");
//            ecoleRepository.saveAll(List.of(ecole1, ecole2));
//
//            // Seed Filieres
//            Filiere filiere1 = new Filiere("Computer Science", "CS", ecole1);
//            Filiere filiere2 = new Filiere("Mechanical Engineering", "ME", ecole2);
//            filiereRepository.saveAll(List.of(filiere1, filiere2));
//
//            // Seed Etudiants
//            Etudiant etudiant1 = new Etudiant("John", "Doe", "john1.doe@example.com","user123", "123456789", "photo1.jpg", "cover1.jpg", "Active", "CS123", ecole1, filiere1, 1L);
//            Etudiant etudiant2 = new Etudiant("Jane", "Smith", "jane.smith@example.com", "user123","987654321", "photo2.jpg", "cover2.jpg", "Active", "ME456", ecole2, filiere2, 2L);
//            // Fetch or create the Utilisateur
//            Utilisateur user1 = utilisateurRepository.findById(1L)
//                    .orElseGet(() -> utilisateurRepository.save(new Utilisateur("john1.doe@example.com", "password", "etudiant")));
//            Utilisateur user2 = utilisateurRepository.findById(2L)
//                    .orElseGet(() -> utilisateurRepository.save(new Utilisateur("jane.smith@example.com", "password", "etudiant")));
//
//            // Associate the Utilisateur with the Etudiant
//            etudiant1.setUser(user1);
//            etudiant2.setUser(user2);
//
//            // Save the Etudiant
//            etudiantService.createEtudiant(entityMapper.toDto(etudiant1));
//            etudiantService.createEtudiant(entityMapper.toDto(etudiant2));
//            //save the etudiant using the method save of the service that takes as input etudiant dto use the mapper method to convert the etudiant to etudiant dto
//            EtudiantDTO createdEtudiant1 = etudiantService.createEtudiant(entityMapper.toDto(etudiant1));
//            EtudiantDTO createdEtudiant2 = etudiantService.createEtudiant(entityMapper.toDto(etudiant2));
//
//            // Seed Entreprises
//            Entreprise entreprise1 = new Entreprise("TechCorp", "Innovative tech solutions", "Paris", "789 Rue de Paris", "0123456789", "Technology");
//            Entreprise entreprise2 = new Entreprise("MechaWorks", "Mechanical engineering experts", "Lyon", "321 Rue de Lyon", "0987654321", "Engineering");
//            entrepriseRepository.saveAll(List.of(entreprise1, entreprise2));
//
//            // Seed RHs
//            RH rh1 = new RH("Alice", "Brown", "alice.brown@techcorp.com", "123456789", "password", entreprise1);
//            RH rh2 = new RH("Bob", "Green", "bob.green@mechaworks.com", "987654321", "password", entreprise2);
//            rhRepository.saveAll(List.of(rh1, rh2));
//
//            // Seed Encadrants
//            Encadrant encadrant1 = new Encadrant("Dr. Emily", "White", "emily.white@techcorp.com", "123456789", "password", entreprise1);
//            Encadrant encadrant2 = new Encadrant("Dr. Mark", "Black", "mark.black@mechaworks.com", "987654321", "password", entreprise2);
//            encadrantRepository.saveAll(List.of(encadrant1, encadrant2));
//
//
//            // Seed Offres
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date startDate1 = dateFormat.parse("2023-01-01");
//            Date endDate1 = dateFormat.parse("2023-06-01");
//            Date startDate2 = dateFormat.parse("2023-02-01");
//            Date endDate2 = dateFormat.parse("2023-07-01");
//
//            Offre offre1 = new Offre("Software Engineer Internship", "Develop software solutions", startDate1, endDate1, "Software Engineer", "6 mois", "On-site", "1500.0", "Internship", "Bachelor",rh1,entreprise1);
//            Offre offre2 = new Offre("Mechanical Design Internship", "Design mechanical systems", startDate2, endDate2, "Mechanical Designer", "6 mois", "Hybrid", "1200.0", "Internship", "Master", rh2, entreprise2);
//            offreRepository.saveAll(List.of(offre1, offre2));
//
//
//            System.out.println("Database seeding completed!");
//        }
//    }