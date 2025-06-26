package STAGE.stage.services.implementation;

import STAGE.stage.models.Utilisateur;
import STAGE.stage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.CompteEntrepriseDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.CompteEntreprise;
import STAGE.stage.models.Entreprise;
import STAGE.stage.repositories.CompteEntrepriseRepository;
import STAGE.stage.repositories.EntrepriseRepository;
import STAGE.stage.services.CompteEntrepriseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompteEntrepriseServiceImpl implements CompteEntrepriseService {
    private final CompteEntrepriseRepository compteRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EntityMapper mapper;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userrepository;


    @Override
    public CompteEntrepriseDTO createCompteEntreprise(CompteEntrepriseDTO dto) {
        if (dto.getMotDePasse() == null || dto.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        Utilisateur user = new Utilisateur();
        user.setRole("ENTREPRISE");
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getMotDePasse()));
        userrepository.save(user);

        CompteEntreprise compteEntreprise = new CompteEntreprise();
        compteEntreprise.setNom(dto.getNom());
        compteEntreprise.setPrenom(dto.getPrenom());
        compteEntreprise.setEmail(dto.getEmail());
        compteEntreprise.setTelephone(dto.getTelephone());
        compteEntreprise.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        compteEntreprise.setEntreprise(entrepriseRepository.findById(dto.getEntrepriseId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"))); // Associate Entreprise
        compteEntreprise.setUser(user); // Associate User

        return mapper.toDto(compteRepository.save(compteEntreprise));
    }
    @Override
    public CompteEntrepriseDTO updateCompteEntreprise(Long id, CompteEntrepriseDTO dto) {
        CompteEntreprise compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));


        // Update User (email and password) if needed
        if (dto.getUserId() != null) {
            Utilisateur user = userrepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            user.setEmail(dto.getEmail()); // Update email
            if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
                user.setPassword(passwordEncoder.encode(dto.getMotDePasse())); // Update password
            }
            userrepository.save(user); // Save updated User
        }
        compte.setNom(dto.getNom());
        compte.setPrenom(dto.getPrenom());
        compte.setEmail(dto.getEmail());
        compte.setTelephone(dto.getTelephone());

        return mapper.toDto(compteRepository.save(compte));
    }

    @Override
    public CompteEntrepriseDTO getCompteEntrepriseById(Long id) {
        return compteRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));
    }

    @Override
    public List<CompteEntrepriseDTO> getAllComptesEntreprise() {
        return compteRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompteEntreprise(Long id) {
        compteRepository.deleteById(id);
    }

    @Override
    public CompteEntrepriseDTO getCompteEntrepriseByEntrepriseId(Long entrepriseId) {
        CompteEntreprise compteEntreprise = compteRepository.findByEntreprise_IdEntreprise(entrepriseId)
                .orElseThrow(() -> new RuntimeException("Compte lié à cette entreprise introuvable"));

        return mapper.toDto(compteEntreprise);
    }
    @Override
    public void disableCompteEntreprise(Long id, String newPassword) {
        // Fetch the CompteEntreprise by ID
        CompteEntreprise compte = compteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CompteEntreprise with ID " + id + " does not exist."));

        // Encode the new password
        String hashedPassword = passwordEncoder.encode(newPassword);

        // Update password in the CompteEntreprise table
        compte.setMotDePasse(hashedPassword);

        // Fetch and update the User table via userId
        Long userId = compte.getUser().getId(); // Assuming CompteEntreprise has a field `userId`
        Utilisateur user = userrepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist."));

        user.setPassword(hashedPassword); // Update user's password as well

        // Save both entities
        compteRepository.save(compte);
        userrepository.save(user);
    }

    @Override
    public Long getCompteEntrepriseIdByUserId(Long userId) {
        Optional<CompteEntreprise> compteEntrepriseOptional = compteRepository.findByUserId(userId);
        if (compteEntrepriseOptional.isPresent()) {
            return compteEntrepriseOptional.get().getIdCompte();
        } else {
            throw new IllegalArgumentException("CompteEntreprise with userId " + userId + " does not exist.");
        }
    }
}
