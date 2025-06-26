package STAGE.stage.services;

import STAGE.stage.dtos.CompteEcoleDTO;

import java.util.List;

public interface CompteEcoleService {
    CompteEcoleDTO createCompteEcole(CompteEcoleDTO dto);
    CompteEcoleDTO updateCompteEcole(Long id, CompteEcoleDTO dto);
    CompteEcoleDTO getCompteEcoleById(Long id);
    List<CompteEcoleDTO> getAllComptesEcole();
    void deleteCompteEcole(Long id);

    CompteEcoleDTO getCompteEcoleByEcoleId(Long ecoleId);
    void disableCompteEcole(Long id, String newPassword);

    Long getCompteEcoleyUserId(Long userId);
}
