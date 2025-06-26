package STAGE.stage.config;

import STAGE.stage.models.*;
import STAGE.stage.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final EcoleRepository ecoleRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EtudiantRepository etudiantRepository;
    private final FiliereRepository filiereRepository;
    private final OffreRepository offreRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {
        // Seed Users
        for (int i = 1; i <= 5; i++) {
            Utilisateur user = new Utilisateur();
            user.setEmail("user" + i + "@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("USER");
            userRepository.save(user);
        }

        // Seed Admins
        List<Utilisateur> users = userRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Admin admin = new Admin();
            admin.setNomAd("AdminNom" + (i + 1));
            admin.setPrenomAd("AdminPrenom" + (i + 1));
            admin.setEmailAd("admin" + (i + 1) + "@example.com");
            admin.setMotDePasse(passwordEncoder.encode("password"));
            admin.setTelephone("12345678" + i);
            admin.setUser(users.get(i % users.size()));
            adminRepository.save(admin);
        }

        // Seed Ecoles
        for (int i = 1; i <= 5; i++) {
            Ecole ecole = new Ecole();
            ecole.setNomEcole("Ecole " + i);
            ecole.setVilleEcole("Ville " + i);
            ecole.setAdresseEcole("Adresse " + i);
            ecole.setDescription("Description de l'école " + i);
            ecole.setTelephoneFix("052200000" + i);
            ecole.setTypeEcole("Type " + i);
            ecole.setDomaineEcole("Domaine " + i);
            ecoleRepository.save(ecole);
        }

        // Seed Filieres
        List<Ecole> ecoles = ecoleRepository.findAll();
        for (int i = 1; i <= 5; i++) {
            Filiere filiere = new Filiere();
            filiere.setNomFiliere("Filiere " + i);
            filiere.setAbrvFiliere("F" + i);
            filiere.setEcole(ecoles.get((i - 1) % ecoles.size()));
            filiereRepository.save(filiere);
        }

        // Seed Etudiants
        List<Filiere> filieres = filiereRepository.findAll();
        for (int i = 1; i <= 5; i++) {
            Etudiant etudiant = new Etudiant();
            etudiant.setNom("NomEtudiant" + i);
            etudiant.setPrenom("PrenomEtudiant" + i);
            etudiant.setFiliere(filieres.get((i-1) % filieres.size()));
            etudiantRepository.save(etudiant);
        }

        // Seed Entreprises
        for (int i = 1; i <= 5; i++) {
            Entreprise entreprise = new Entreprise();
            entreprise.setNom("Entreprise " + i);
            entreprise.setSecteurActivite("Secteur " + i);
            entrepriseRepository.save(entreprise);
        }

        // Seed Offres
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        for (int i = 1; i <= 5; i++) {
            Offre offre = new Offre();
            offre.setTitre("Offre " + i);
            offre.setDescription("Description de l'offre " + i);
            offre.setEntreprise(entreprises.get((i-1) % entreprises.size()));
            offreRepository.save(offre);
        }
    }
}