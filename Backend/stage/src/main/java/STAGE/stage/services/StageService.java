package STAGE.stage.services;

import STAGE.stage.dtos.StageDTO;
import STAGE.stage.models.Stage;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.FiliereRepository;
import STAGE.stage.repositories.StageRepository;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StageService {

    StageDTO createStage(StageDTO stageDTO);
    StageDTO updateStage(Long id, StageDTO stageDTO);

    //convention de stage
    StageDTO getStageById(Long id);
    List<StageDTO> getStagesByEntrepriseId(Long entrepriseId); // Fetch stages by entreprise ID
    List<StageDTO> getStagesByStatus(String status); // Fetch stages by status
    StageDTO updateStageStatus(Long id, String newStatus); // Update status of a stage
    List<StageDTO> getAllStages();
    void deleteStage(Long id);

    List<StageDTO> getStagesByCF(Long idCF);

    List<StageDTO> getValidatedStagesByEcoleId(Long ecoleId);

    List<StageDTO> getAValiderStagesByEcoleId(Long ecoleId);

    void setStatusAndDeleteRest(Long etudiantId, Long stageId);

    void setStatus(Long etudiantId, Long stageId);

    void setStatuscf(Long etudiantId, Long stageId);

    List<StageDTO> getStagesByEtudiantId(Long etudiantId);

    //get stage by ecoleId
    List<StageDTO> findStagesByEcoleId(Long ecoleId);

    //convention de stage
    Stage getStageEntityById(Long id);

    //Convention de Stage
    void uploadConventionDeStage(Long stageId, MultipartFile file);
    Resource downloadConventionDeStage(Long stageId);

    //Attestation de stage
    void uploadAttestationDeStage(Long id, MultipartFile file) throws IOException;

    Resource downloadAttestationDeStage(Long id);
}