package STAGE.stage.services.implementation;

import STAGE.stage.dtos.CoordinateurDeStageDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.CoordinateurDeStage;
import STAGE.stage.models.Ecole;
import STAGE.stage.models.Utilisateur;
import STAGE.stage.repositories.CoordinateurDeStageRepository;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.repositories.UserRepository;
import STAGE.stage.services.CoordinateurDeDStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinateurDeStageImpl implements CoordinateurDeDStageService {

    private final CoordinateurDeStageRepository coordinateurDeStageRepository;
    private final EntityMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userrepository;

    @Autowired
    private EcoleRepository ecoleRepository;

    @Override
    public CoordinateurDeStageDTO createCoordinateurDeStage(CoordinateurDeStageDTO dto) {
        if (dto.getMotDePasse() == null || dto.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        Ecole ecole = ecoleRepository.findById(dto.getEcoleId())
                .orElseThrow(() -> new RuntimeException("Ecole not found"));


        Utilisateur user = new Utilisateur();
        user.setRole("COORDINATEUR_DE_STAGE");
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getMotDePasse()));
        userrepository.save(user);

        CoordinateurDeStage coordinateur = new CoordinateurDeStage();
        coordinateur.setNom(dto.getNom());
        coordinateur.setPrenom(dto.getPrenom());
        coordinateur.setEmail(dto.getEmail());
        coordinateur.setTelephone(dto.getTelephone());
        coordinateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        coordinateur.setUser(user); // Associate User
        coordinateur.setEcole(ecole);

        return mapper.toDto(coordinateurDeStageRepository.save(coordinateur));
    }

    @Override
    public CoordinateurDeStageDTO updateCoordinateurDeStage(Long id, CoordinateurDeStageDTO dto) {
        CoordinateurDeStage existing = coordinateurDeStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordinateur de Stage not found"));

        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());

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

        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
            existing.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }

        return mapper.toDto(coordinateurDeStageRepository.save(existing));
    }

    @Override
    public CoordinateurDeStageDTO getCoordinateurDeStageById(Long id) {
        CoordinateurDeStage coordinateur = coordinateurDeStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordinateur de Stage not found"));
        return mapper.toDto(coordinateur);
    }

    @Override
    public List<CoordinateurDeStageDTO> getAllCoordinateursDeStage() {
        return coordinateurDeStageRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCoordinateurDeStage(Long id) {
        if (!coordinateurDeStageRepository.existsById(id)) {
            throw new RuntimeException("Coordinateur de Stage not found");
        }
        coordinateurDeStageRepository.deleteById(id);
    }

    @Override
    public Long getCoordinateurDeStageIdIdByUserId(Long userId) {
        Optional<CoordinateurDeStage> coordinateurDeStageOptional = coordinateurDeStageRepository.findByUserId(userId);
        if (coordinateurDeStageOptional.isPresent()) {
            return coordinateurDeStageOptional.get().getIdCs();
        } else {
            throw new IllegalArgumentException("CompteEntreprise with userId " + userId + " does not exist.");
        }
    }

    @Override
    public List<CoordinateurDeStageDTO> getCoordinateursByEcoleId(Long ecoleId) {
        return coordinateurDeStageRepository.findByEcoleIdEcole(ecoleId)
                .stream()
                .map(mapper::toDto) // Assuming a mapper is used to convert entities to DTOs
                .collect(Collectors.toList());
    }
}
