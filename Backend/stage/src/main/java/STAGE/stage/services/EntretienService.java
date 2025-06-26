package STAGE.stage.services;

import STAGE.stage.dtos.EntretienDTO;

import java.util.List;

public interface EntretienService {
    EntretienDTO createEntretien(EntretienDTO entretienDTO);
    EntretienDTO updateEntretien(Long id, EntretienDTO entretienDTO);
    List<EntretienDTO> getAllEntretiens();
    EntretienDTO getEntretienById(Long id);
    void deleteEntretien(Long id);
    List<EntretienDTO> getEntretiensByEntrepriseId(Long entrepriseId);

    List<EntretienDTO> getEntretiensByEtudiant(Long etudiantId);
}