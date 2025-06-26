package STAGE.stage.services;

import STAGE.stage.dtos.EncadrantDTO;

import java.util.List;

public interface EncadrantService {
    EncadrantDTO createEncadrant(EncadrantDTO dto);
    EncadrantDTO updateEncadrant(Long id, EncadrantDTO dto);
    EncadrantDTO getEncadrantById(Long id);
    List<EncadrantDTO> getAllEncadrants();
    void deleteEncadrant(Long id);

    Long getEncadrantIdByUserId(Long userId);

    List<EncadrantDTO> getEncadrantsByEntrepriseId(Long entrepriseId);
}