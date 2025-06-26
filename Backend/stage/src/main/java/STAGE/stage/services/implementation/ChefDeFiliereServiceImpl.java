package STAGE.stage.services.implementation;

import STAGE.stage.models.*;
import STAGE.stage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.ChefDeFiliereDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.repositories.ChefDeFiliereRepository;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.repositories.FiliereRepository;
import STAGE.stage.services.ChefDeFiliereService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChefDeFiliereServiceImpl implements ChefDeFiliereService {
    private final ChefDeFiliereRepository chefRepository;
    private final FiliereRepository filiereRepository;
    private final EcoleRepository ecoleRepository;
    private final EntityMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userrepository;


    @Override
    public ChefDeFiliereDTO createChefDeFiliere(ChefDeFiliereDTO dto) {
        if (dto.getMotDePasse() == null || dto.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        Utilisateur user = new Utilisateur();
        user.setRole("CHEF_DE_FILIERE");
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getMotDePasse()));
        userrepository.save(user);

        ChefDeFiliere chefDeFiliere = new ChefDeFiliere();
        chefDeFiliere.setNom(dto.getNom());
        chefDeFiliere.setPrenom(dto.getPrenom());
        chefDeFiliere.setEmail(dto.getEmail());
        chefDeFiliere.setTelephone(dto.getTelephone());
        chefDeFiliere.setEcole(ecoleRepository.findById(dto.getEcoleId())
                .orElseThrow(() -> new RuntimeException("École introuvable"))); // Associate Ecole
        chefDeFiliere.setFiliere(filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new RuntimeException("Filière introuvable"))); // Associate Filiere
        chefDeFiliere.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        chefDeFiliere.setUser(user); // Associate User

        return mapper.toDto(chefRepository.save(chefDeFiliere));
    }
    @Override
    public ChefDeFiliereDTO updateChefDeFiliere(Long id, ChefDeFiliereDTO dto) {
        ChefDeFiliere chef = chefRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chef de filière introuvable"));

        Filiere filiere = filiereRepository.findById(dto.getFiliereId())
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        Ecole ecole = ecoleRepository.findById(dto.getEcoleId())
                .orElseThrow(() -> new RuntimeException("École introuvable"));

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

        chef.setNom(dto.getNom());
        chef.setPrenom(dto.getPrenom());
        chef.setEmail(dto.getEmail());
        chef.setTelephone(dto.getTelephone());
        chef.setMotDePasse(dto.getMotDePasse());

        chef.setFiliere(filiere);
        chef.setEcole(ecole);

        return mapper.toDto(chefRepository.save(chef));
    }

    @Override
    public ChefDeFiliereDTO getChefDeFiliereById(Long id) {
        ChefDeFiliere chef = chefRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chef de filière introuvable"));
        return mapper.toDto(chef);
    }

    @Override
    public List<ChefDeFiliereDTO> getAllChefs() {
        return chefRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChefDeFiliere(Long id) {
        chefRepository.deleteById(id);
    }

    @Override
    public List<ChefDeFiliereDTO> getChefsByEcoleId(Long ecoleId) {
        return chefRepository.findByEcole_IdEcole(ecoleId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChefDeFiliereDTO> getChefsByFiliereId(Long filiereId) {
        return chefRepository.findByFiliere_IdFiliere(filiereId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public Long getChefDeFiliereIdIdByUserId(Long userId) {
        Optional<ChefDeFiliere> compteEntrepriseOptional = chefRepository.findByUserId(userId);
        if (compteEntrepriseOptional.isPresent()) {
            return compteEntrepriseOptional.get().getIdCf();
        } else {
            throw new IllegalArgumentException("CompteEntreprise with userId " + userId + " does not exist.");
        }
    }
}
