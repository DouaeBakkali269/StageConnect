package STAGE.stage.services;

import STAGE.stage.dtos.EtudiantDTO;

import java.util.List;

public interface EtudiantService {
    EtudiantDTO createEtudiant(EtudiantDTO dto);

    EtudiantDTO getEtudiantById(Long etudiantId);

    List<EtudiantDTO> getEtudiantsByEcole(Long ecoleId);

    List<EtudiantDTO> getEtudiantsByFiliere(Long filiereId);

    void deleteEtudiantById(Long id);

    Long getEtudiantIdByUserId(Long userId);

    EtudiantDTO updateEtudiant(Long id, EtudiantDTO dto);
}
