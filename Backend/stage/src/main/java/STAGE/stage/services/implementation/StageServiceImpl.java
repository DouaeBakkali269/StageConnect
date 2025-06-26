package STAGE.stage.services.implementation;

import STAGE.stage.dtos.StageDTO;
import STAGE.stage.exception.NoContentException;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.*;
import STAGE.stage.repositories.*;
import STAGE.stage.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StageServiceImpl implements StageService {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EncadrantRepository encadrantRepository;
    @Autowired
    private ChefDeFiliereRepository chefDeFiliereRepository;
    @Autowired
    private FiliereRepository filiereRepository;
    @Autowired
    private EntityMapper mapper;

    @Override
    public StageDTO createStage(StageDTO stageDTO) {
        Stage stage = new Stage();
        stage.setTitre(stageDTO.getTitre());
        stage.setDescription(stageDTO.getDescription());
        stage.setDateDebut(stageDTO.getDateDebut());
        stage.setDateFin(stageDTO.getDateFin());
        stage.setDuree(stageDTO.getDuree());
        stage.setLocalisation(stageDTO.getLocalisation());
        stage.setMontantRemuneration(stageDTO.getMontantRemuneration());
        stage.setStatut(stageDTO.getStatut()); // status at the begining is "nouveau"
        stage.setType(stageDTO.getType());
        stage.setConventionDeStage(stageDTO.getConventionDeStage());
        stage.setAttestationDeStage(stageDTO.getAttestationDeStage());

        Offre offre = offreRepository.findById(stageDTO.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));
        Etudiant etudiant = etudiantRepository.findById(stageDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));
        Encadrant encadrant = encadrantRepository.findById(stageDTO.getEncadrantId())
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));

        stage.setOffre(offre);
        stage.setEtudiant(etudiant);
        stage.setEncadrant(encadrant);

        Stage savedStage = stageRepository.save(stage);
        stageDTO.setIdStage(savedStage.getIdStage());
        return stageDTO;
    }

    @Override
    public StageDTO updateStage(Long id, StageDTO stageDTO) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage introuvable"));

        stage.setTitre(stageDTO.getTitre());
        stage.setDescription(stageDTO.getDescription());
        stage.setDateDebut(stageDTO.getDateDebut());
        stage.setDateFin(stageDTO.getDateFin());
        stage.setDuree(stageDTO.getDuree());
        stage.setLocalisation(stageDTO.getLocalisation());
        stage.setMontantRemuneration(stageDTO.getMontantRemuneration());
        stage.setStatut(stageDTO.getStatut());
        stage.setType(stageDTO.getType());
        stage.setConventionDeStage(stageDTO.getConventionDeStage());
        stage.setAttestationDeStage(stageDTO.getAttestationDeStage());

        Offre offre = offreRepository.findById(stageDTO.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));
        Etudiant etudiant = etudiantRepository.findById(stageDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));
        Encadrant encadrant = encadrantRepository.findById(stageDTO.getEncadrantId())
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));

        stage.setOffre(offre);
        stage.setEtudiant(etudiant);
        stage.setEncadrant(encadrant);

        Stage updatedStage = stageRepository.save(stage);
        stageDTO.setIdStage(updatedStage.getIdStage());
        return stageDTO;
    }

    @Override
    public StageDTO getStageById(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage introuvable"));

        return mapToStageDTO(stage);
    }

    @Override
    public List<StageDTO> getStagesByEntrepriseId(Long entrepriseId) {
        List<Stage> stages = stageRepository.findByOffre_EntrepriseId(entrepriseId);

        return stages.stream().map(this::mapToStageDTO).collect(Collectors.toList());
    }

    @Override
    public List<StageDTO> getStagesByStatus(String status) {
        List<Stage> stages = stageRepository.findByStatut(status);

        return stages.stream().map(this::mapToStageDTO).collect(Collectors.toList());
    }

    @Override
    public StageDTO updateStageStatus(Long id, String newStatus) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage introuvable"));

        stage.setStatut(newStatus);
        Stage updatedStage = stageRepository.save(stage);
        return mapToStageDTO(updatedStage);
    }

    @Override
    public List<StageDTO> getAllStages() {
        return stageRepository.findAll().stream()
                .map(this::mapToStageDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteStage(Long id) {
        if (!stageRepository.existsById(id)) {
            throw new RuntimeException("Stage non trouvé");
        }
        stageRepository.deleteById(id);
    }

    private StageDTO mapToStageDTO(Stage stage) {
        StageDTO dto = new StageDTO();
        dto.setIdStage(stage.getIdStage());
        dto.setTitre(stage.getTitre());
        dto.setDescription(stage.getDescription());
        dto.setDateDebut(stage.getDateDebut());
        dto.setDateFin(stage.getDateFin());
        dto.setDuree(stage.getDuree());
        dto.setLocalisation(stage.getLocalisation());
        dto.setMontantRemuneration(stage.getMontantRemuneration());
        dto.setStatut(stage.getStatut());
        dto.setType(stage.getType());
        dto.setOffreId(stage.getOffre().getIdOffre());
        dto.setEtudiantId(stage.getEtudiant().getIdEtu());
        dto.setEncadrantId(stage.getEncadrant().getIdEncadrant());
        dto.setConventionDeStage(stage.getConventionDeStage());
        dto.setAttestationDeStage(stage.getAttestationDeStage());
        return dto;
    }

    @Override
    public List<StageDTO> getStagesByCF(Long idCf) {
        // Fetch the ChefDeFiliere entity by idCf
        ChefDeFiliere chefDeFiliere = chefDeFiliereRepository.findById(idCf)
                .orElseThrow(() -> new RuntimeException("ChefDeFiliere not found with id: " + idCf));

        // Access the associated Filiere
        Filiere filiere = chefDeFiliere.getFiliere();

        // Check if Filiere is not null to avoid NullPointerException

            Long filiereId = filiere.getIdFiliere();


        // Step 2: Get Etudiant IDs by Filiere ID
            List<Long> etudiantIds = etudiantRepository.findByFiliereIdFiliere(filiereId)
                    .stream()
                    .map(Etudiant::getIdEtu)
                    .toList();

            // Step 3: Get Stages by Etudiant IDs with status "To validate"
            List<Stage> stages = stageRepository.findByEtudiantIdEtuInAndStatut(etudiantIds, "To validate");

            // Step 4: Map entities to DTOs and return result
            return stages.stream()
                    .map(mapper::toDto)
                    .toList();
        }

        @Override
        public List<StageDTO> getValidatedStagesByEcoleId(Long ecoleId) {
            // Fetch validated stages via repository
            List<Stage> stages = stageRepository.findValidatedStagesByEcoleId(ecoleId);

            // Map list of Stage entities to StageDTO
            return stages.stream()
                    .map(stage -> new StageDTO(
                            stage.getIdStage(), stage.getTitre(), stage.getDescription(),
                            stage.getDateDebut(), stage.getDateFin(), stage.getDuree(),
                            stage.getLocalisation(), stage.getMontantRemuneration(),
                            stage.getStatut(), stage.getType(),
                            stage.getEtudiant().getIdEtu(),
                            stage.getOffre() != null ? stage.getOffre().getIdOffre() : null,
                            stage.getEncadrant() != null ? stage.getEncadrant().getIdEncadrant() : null,
                            stage.getConventionDeStage(),
                            stage.getAttestationDeStage()))
                    .toList();
        }

    @Override
    public List<StageDTO> getAValiderStagesByEcoleId(Long ecoleId) {
        // Fetch validated stages via repository
        List<Stage> stages = stageRepository.findAValiderStagesByEcoleId(ecoleId);

        // Map list of Stage entities to StageDTO
        return stages.stream()
                .map(stage -> new StageDTO(
                        stage.getIdStage(),
                        stage.getTitre(),
                        stage.getDescription(),
                        stage.getDateDebut(),
                        stage.getDateFin(),
                        stage.getDuree(),
                        stage.getLocalisation(),
                        stage.getMontantRemuneration(),
                        stage.getStatut(),
                        stage.getType(),
                        stage.getEtudiant().getIdEtu(),
                        stage.getOffre() != null ? stage.getOffre().getIdOffre() : null,
                        stage.getEncadrant() != null ? stage.getEncadrant().getIdEncadrant() : null,
                        stage.getConventionDeStage(),
                        stage.getAttestationDeStage()))
                .toList();
    }


        @Override
        public void setStatusAndDeleteRest(Long etudiantId, Long stageId) {
            // Step 1: Get all stages associated with the student
            List<Stage> stages = stageRepository.findByEtudiantIdEtu(etudiantId);


            // Step 2: Find the stage with the given stageId and update its status
            stages.stream()
                    .filter(stage -> stage.getIdStage().equals(stageId))
                    .findFirst()
                    .ifPresent(stage -> {
                        stage.setStatut("a valider");
                        stageRepository.save(stage); // Save the status update
                    });


            // Step 3: Change the status of all other stages for the same student to "Refusé temporairement"
            stages.stream()
                    .filter(stage -> !stage.getIdStage().equals(stageId)) // Exclude the stage with the given ID
                    .filter(stage -> !List.of("en cours", "terminé", "évalué", "refusé", "refusé temporairement", "valide")
                            .contains(stage.getStatut())) // Exclude stages with specific statuses
                    .forEach(stage -> {
                        stage.setStatut("refusé temporairement");
                        stageRepository.save(stage); // Persist the status update
                    });
        }
    @Override
    public void setStatus(Long etudiantId, Long stageId) {
        // Step 1: Get all stages associated with the student
        List<Stage> stages = stageRepository.findByEtudiantIdEtu(etudiantId);


        // Step 2: Find the stage with the given stageId and update its status
        stages.stream()
                .filter(stage -> stage.getIdStage().equals(stageId))
                .findFirst()
                .ifPresent(stage -> {
                    stage.setStatut("valide");
                    stageRepository.save(stage); // Save the status update
                });


        stages.stream()
                .filter(stage -> !stage.getIdStage().equals(stageId)) // Exclude the stage with the given ID
                .forEach(stage -> {
                    // Check current status and update only if it is "refusé temporairement" or "valide"
                    if ("refusé temporairement".equals(stage.getStatut()) || "valide".equals(stage.getStatut())) {
                        stage.setStatut("refusé");
                        stageRepository.save(stage); // Persist the status update
                    }
                });
    }
    @Override
    public void setStatuscf(Long etudiantId, Long stageId) {
        // Step 1: Get all stages associated with the student
        List<Stage> stages = stageRepository.findByEtudiantIdEtu(etudiantId);


        // Step 2: Find the stage with the given stageId and update its status
        stages.stream()
                .filter(stage -> stage.getIdStage().equals(stageId))
                .findFirst()
                .ifPresent(stage -> {
                    stage.setStatut("refusé");
                    stageRepository.save(stage); // Save the status update
                });


        stages.stream()
                .filter(stage -> !stage.getIdStage().equals(stageId))
                .forEach(stage -> {
                    // Check current status and update only if it is NOT "refusé temporairement"
                    if ("refusé temporairement".equals(stage.getStatut())) {
                        stage.setStatut("nouveau");
                        stageRepository.save(stage); // Persist the status update
                    }
                });
    }

    @Override
    public List<StageDTO> getStagesByEtudiantId(Long etudiantId) {
        // Fetch stages by Etudiant ID
        return stageRepository.findByEtudiantIdEtu(etudiantId).stream()
                .map(mapper::toDto) // Convert entities to DTOs using EntityMapper
                .collect(Collectors.toList());
    }

    @Override
    public List<StageDTO> findStagesByEcoleId(Long ecoleId) {
        List<Stage> stages = stageRepository.findStagesByEcoleId(ecoleId);
        return stages.stream()
                .map(mapper::toDto) // Convert entities to DTOs using EntityMapper
                .collect(Collectors.toList());
    }

    //convention de stage
    @Override
    public Stage getStageEntityById(Long id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage introuvable"));
    }

    @Override
    public void uploadConventionDeStage(Long stageId, MultipartFile file) {
        Stage stage = getStageEntityById(stageId);
        try {
            stage.setConventionDeStage(file.getBytes());
            stageRepository.save(stage);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier", e);
        }
    }

    @Override
    public Resource downloadConventionDeStage(Long stageId) {
        Stage stage = getStageEntityById(stageId);
        if (stage.getConventionDeStage() == null) {
            throw new NoContentException("No convention available for the specified stage.");
        }
        return new ByteArrayResource(stage.getConventionDeStage());
    }

    //Attestation de stage
    @Override
    public void uploadAttestationDeStage(Long id, MultipartFile file) throws IOException {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));

        stage.setAttestationDeStage(file.getBytes());
        stageRepository.save(stage);
    }

    @Override
    public Resource downloadAttestationDeStage(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));
        if (stage.getAttestationDeStage() == null) {
            // Return a custom exception signaling no content
            throw new NoContentException("No attestation available for the specified stage.");
        }

        return new ByteArrayResource(stage.getAttestationDeStage());
    }
}
