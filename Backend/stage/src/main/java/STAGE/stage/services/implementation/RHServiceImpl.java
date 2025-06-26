package STAGE.stage.services.implementation;

import STAGE.stage.models.Utilisateur;
import STAGE.stage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.RHDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Entreprise;
import STAGE.stage.models.RH;
import STAGE.stage.repositories.EntrepriseRepository;
import STAGE.stage.repositories.RHRepository;
import STAGE.stage.services.RHService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RHServiceImpl implements RHService {

    private final RHRepository rhRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EntityMapper entityMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userrepository;

    @Override
    public RHDTO createRH(RHDTO dto) {
        if (dto.getMotDePasse() == null || dto.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        Utilisateur user = new Utilisateur();
        user.setRole("RH");
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getMotDePasse()));
        userrepository.save(user);

        RH rh = new RH();
        rh.setNom(dto.getNom());
        rh.setPrenom(dto.getPrenom());
        rh.setEmail(dto.getEmail());
        rh.setTelephone(dto.getTelephone());
        rh.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        rh.setEntreprise(entrepriseRepository.findById(dto.getEntrepriseId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"))); // Associate Entreprise
        rh.setUser(user); // Associate User

        return entityMapper.toDto(rhRepository.save(rh));
    }

    @Override
    public RHDTO getRHById(Long id) {
        RH rh = rhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RH not found with id: " + id));
        return entityMapper.toDto(rh);
    }

    @Override
    public List<RHDTO> getAllRHs() {
        return rhRepository.findAll()
                .stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RHDTO updateRH(Long id, RHDTO rhDTO) {
        RH rh = rhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RH not found with id: " + id));

        rh.setNom(rhDTO.getNom());
        rh.setPrenom(rhDTO.getPrenom());
        rh.setEmail(rhDTO.getEmail());
        rh.setMotDePasse(rhDTO.getMotDePasse());
        rh.setTelephone(rhDTO.getTelephone());


        // Update User (email and password) if needed
        if (rhDTO.getUserId() != null) {
            Utilisateur user = userrepository.findById(rhDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + rhDTO.getUserId()));
            user.setEmail(rhDTO.getEmail()); // Update email
            if (rhDTO.getMotDePasse() != null && !rhDTO.getMotDePasse().isEmpty()) {
                user.setPassword(passwordEncoder.encode(rhDTO.getMotDePasse())); // Update password
            }
            userrepository.save(user); // Save updated User
        }

        Entreprise entreprise = entrepriseRepository.findById(rhDTO.getEntrepriseId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
        rh.setEntreprise(entreprise);

        RH updatedRH = rhRepository.save(rh);
        return entityMapper.toDto(updatedRH);
    }

    @Override
    public void deleteRH(Long id) {
        if (!rhRepository.existsById(id)) {
            throw new RuntimeException("RH not found with id: " + id);
        }
        rhRepository.deleteById(id);
    }
    @Override
    public List<RH> getRHByEntreprise(Long entrepriseId) {
        return rhRepository.findByEntrepriseIdEntreprise(entrepriseId); // Assurez-vous que la méthode existe dans le repository
    }

    @Override
    public Long getRHIdByUserId(Long userId) {
        Optional<RH> rhOptional = rhRepository.findByUserId(userId);
        if (rhOptional.isPresent()) {
            return rhOptional.get().getIdRh();
        } else {
            throw new IllegalArgumentException("RH with userId " + userId + " does not exist.");
        }
    }
}
