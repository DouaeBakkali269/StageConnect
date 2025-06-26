package STAGE.stage.services;

import STAGE.stage.dtos.CompteEntrepriseDTO;

import java.util.List;

public interface CompteEntrepriseService {
    CompteEntrepriseDTO createCompteEntreprise(CompteEntrepriseDTO dto);
    CompteEntrepriseDTO updateCompteEntreprise(Long id, CompteEntrepriseDTO dto);
    CompteEntrepriseDTO getCompteEntrepriseById(Long id);
    List<CompteEntrepriseDTO> getAllComptesEntreprise();
    void deleteCompteEntreprise(Long id);

    CompteEntrepriseDTO getCompteEntrepriseByEntrepriseId(Long entrepriseId);
    void disableCompteEntreprise(Long id, String newPassword);

    Long getCompteEntrepriseIdByUserId(Long userId);
}
