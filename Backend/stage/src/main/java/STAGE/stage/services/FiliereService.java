package STAGE.stage.services;


import STAGE.stage.dtos.FiliereDTO;
import STAGE.stage.dtos.OffreDTO;
import STAGE.stage.models.Offre;

import java.util.List;

public interface FiliereService {
    FiliereDTO createFiliere(FiliereDTO filiereDTO);
    FiliereDTO getFiliereById(Long id);
    List<FiliereDTO> getAllFilieres();
    FiliereDTO updateFiliere(Long id, FiliereDTO filiereDTO);
    void deleteFiliere(Long id);
    List<OffreDTO> getVisibleOffresByFiliere(Long filiereId);
    void setOffreVisibility(Long filiereId, Long offreId, Boolean visible);
    List<FiliereDTO> getFilieresByEcoleId(Long idEcole);
}


