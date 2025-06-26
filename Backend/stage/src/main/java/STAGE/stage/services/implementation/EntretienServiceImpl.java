package STAGE.stage.services.implementation;

import STAGE.stage.dtos.EntretienDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Entretien;
import STAGE.stage.models.Etudiant;
import STAGE.stage.models.Offre;
import STAGE.stage.repositories.EntretienRepository;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.OffreRepository;
import STAGE.stage.services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntretienServiceImpl implements EntretienService {

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public EntretienDTO createEntretien(EntretienDTO entretienDTO) {
        Entretien entretien = new Entretien();
        entretien.setDateEntretien(entretienDTO.getDateEntretien());
        entretien.setAdresse(entretienDTO.getAdresse());
        entretien.setDuree(entretienDTO.getDuree());
        entretien.setEtat(entretienDTO.getEtat());
        entretien.setResultat(entretienDTO.getResultat());
        entretien.setLien(entretienDTO.getLien());

        Offre offre = offreRepository.findById(entretienDTO.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable")); // Handle Offre
        Etudiant etudiant = etudiantRepository.findById(entretienDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable")); // Handle Etudiant

        entretien.setOffre(offre);       // Associate Offre
        entretien.setEtudiant(etudiant); // Associate Etudiant

        Entretien savedEntretien = entretienRepository.save(entretien);
        entretienDTO.setIdEntretien(savedEntretien.getIdEntretien());
        return entretienDTO;
    }

    @Override
    public EntretienDTO updateEntretien(Long id, EntretienDTO entretienDTO) {
        Entretien entretien = entretienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entretien non trouvé"));

        entretien.setDateEntretien(entretienDTO.getDateEntretien());
        entretien.setAdresse(entretienDTO.getAdresse());
        entretien.setDuree(entretienDTO.getDuree());
        entretien.setEtat(entretienDTO.getEtat());
        entretien.setResultat(entretienDTO.getResultat());
        entretien.setLien(entretienDTO.getLien());


        Offre offre = offreRepository.findById(entretienDTO.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));
        Etudiant etudiant = etudiantRepository.findById(entretienDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));

        entretien.setOffre(offre);       // Update Offre association
        entretien.setEtudiant(etudiant); // Update Etudiant association

        Entretien updatedEntretien = entretienRepository.save(entretien);
        entretienDTO.setIdEntretien(updatedEntretien.getIdEntretien());
        return entretienDTO;
    }

    @Override
    public List<EntretienDTO> getAllEntretiens() {
        return entretienRepository.findAll().stream().map(entretien -> {
            EntretienDTO dto = new EntretienDTO();
            dto.setIdEntretien(entretien.getIdEntretien());
            dto.setDateEntretien(entretien.getDateEntretien());
            dto.setAdresse(entretien.getAdresse());
            dto.setDuree(entretien.getDuree());
            dto.setEtat(entretien.getEtat());
            dto.setResultat(entretien.getResultat());
            dto.setOffreId(entretien.getOffre().getIdOffre());
            dto.setEtudiantId(entretien.getEtudiant().getIdEtu());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public EntretienDTO getEntretienById(Long id) {
        Entretien entretien = entretienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entretien non trouvé"));
        EntretienDTO dto = new EntretienDTO();
        dto.setIdEntretien(entretien.getIdEntretien());
        dto.setDateEntretien(entretien.getDateEntretien());
        dto.setAdresse(entretien.getAdresse());
        dto.setDuree(entretien.getDuree());
        dto.setEtat(entretien.getEtat());
        dto.setResultat(entretien.getResultat());
        dto.setOffreId(entretien.getOffre().getIdOffre());
        dto.setEtudiantId(entretien.getEtudiant().getIdEtu());
        return dto;
    }

    @Override
    public void deleteEntretien(Long id) {
        if (!entretienRepository.existsById(id)) {
            throw new RuntimeException("Entretien non trouvé");
        }
        entretienRepository.deleteById(id);
    }
    @Override
    public List<EntretienDTO> getEntretiensByEntrepriseId(Long entrepriseId) {
        List<Entretien> entretiens = entretienRepository.findByOffre_EntrepriseId(entrepriseId);

        return entretiens.stream().map(entretien -> {
            EntretienDTO dto = new EntretienDTO();
            dto.setIdEntretien(entretien.getIdEntretien());
            dto.setDateEntretien(entretien.getDateEntretien());
            dto.setAdresse(entretien.getAdresse());
            dto.setDuree(entretien.getDuree());
            dto.setEtat(entretien.getEtat());
            dto.setResultat(entretien.getResultat());
            dto.setOffreId(entretien.getOffre().getIdOffre());
            dto.setEtudiantId(entretien.getEtudiant().getIdEtu());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EntretienDTO> getEntretiensByEtudiant(Long etudiantId) {
        // Fetch interviews by student ID
        List<Entretien> entretiens = entretienRepository.findByEtudiantIdEtu(etudiantId);

        // Convert entities to DTOs
        return entretiens.stream()
                .map(entityMapper::toDto) // Assuming entretienMapper is available
                .collect(Collectors.toList());
    }
}