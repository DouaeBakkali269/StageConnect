package STAGE.stage.services;

import STAGE.stage.dtos.CoordinateurDeStageDTO;

import java.util.List;

public interface CoordinateurDeDStageService {
    CoordinateurDeStageDTO createCoordinateurDeStage(CoordinateurDeStageDTO dto);
    CoordinateurDeStageDTO updateCoordinateurDeStage(Long id, CoordinateurDeStageDTO dto);
    CoordinateurDeStageDTO getCoordinateurDeStageById(Long id);
    List<CoordinateurDeStageDTO> getAllCoordinateursDeStage();
    void deleteCoordinateurDeStage(Long id);

    Long getCoordinateurDeStageIdIdByUserId(Long userId);

    List<CoordinateurDeStageDTO> getCoordinateursByEcoleId(Long ecoleId);
}