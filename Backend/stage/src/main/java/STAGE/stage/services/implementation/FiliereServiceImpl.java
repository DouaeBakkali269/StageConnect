package STAGE.stage.services.implementation;

import STAGE.stage.dtos.ChefDeFiliereDTO;
import STAGE.stage.dtos.OffreDTO;
import STAGE.stage.models.*;
import STAGE.stage.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.FiliereDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.services.FiliereService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FiliereServiceImpl implements FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;
    @Autowired
    private ChefDeFiliereRepository chefDeFiliereRepository;
    private EtudiantRepository etudiantRepository;
    @Autowired
    private EcoleRepository ecoleRepository;
    private final VisibleOffreRepository visibleOffreRepository;
    private final OffreRepository offreRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public FiliereDTO createFiliere(FiliereDTO filiereDTO) {
        Filiere filiere = entityMapper.toEntity(filiereDTO);
        Filiere savedFiliere = filiereRepository.save(filiere);
        return entityMapper.toDto(savedFiliere);
    }

    @Override
    public FiliereDTO getFiliereById(Long id) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere not found with id: " + id));
        return entityMapper.toDto(filiere);
    }

    @Override
    public List<FiliereDTO> getAllFilieres() {
        return filiereRepository.findAll()
                .stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FiliereDTO updateFiliere(Long id, FiliereDTO filiereDTO) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiere not found with id: " + id));

        filiere.setNomFiliere(filiereDTO.getNomFiliere());
        filiere.setAbrvFiliere(filiereDTO.getAbrvFiliere());

        if (filiereDTO.getChefDeFiliereId() != null) {
            ChefDeFiliere chefDeFiliere = chefDeFiliereRepository.findById(filiereDTO.getChefDeFiliereId())
                    .orElseThrow(() -> new RuntimeException("Chef de filiere not found with id: " + filiereDTO.getChefDeFiliereId()));
            filiere.setChefDeFiliere(chefDeFiliere);
        }

        if (filiereDTO.getEtudiantIds() != null) {
            List<Etudiant> etudiants = filiereDTO.getEtudiantIds()
                    .stream()
                    .map(etudiantId -> etudiantRepository.findById(etudiantId)
                            .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + etudiantId)))
                    .collect(Collectors.toList());
            filiere.setEtudiants(etudiants);
        }

        if (filiereDTO.getEcoleId() != null) {
            Ecole ecole = ecoleRepository.findById(filiereDTO.getEcoleId())
                            .orElseThrow(() -> new RuntimeException("Ecole not found with id: " + filiereDTO.getEcoleId()));
            filiere.setEcole(ecole);
        }

        Filiere updatedFiliere = filiereRepository.save(filiere);
        return entityMapper.toDto(updatedFiliere);
    }

    @Override
    public void deleteFiliere(Long id) {
        if (!filiereRepository.existsById(id)) {
            throw new RuntimeException("Filiere not found with id: " + id);
        }
        filiereRepository.deleteById(id);
    }

    @Override
    public List<OffreDTO> getVisibleOffresByFiliere(Long filiereId) {
        // Find all visible offers for a given Filiere
        return visibleOffreRepository.findByFiliere_IdFiliere(filiereId).stream()
                .filter(VisibleOffre::getVisible)
                .map(VisibleOffre::getOffre)
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public void setOffreVisibility(Long idFiliere, Long idOffre, Boolean visible) {
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new RuntimeException("Filiere not found"));
        Offre offre = offreRepository.findById(idOffre)
                .orElseThrow(() -> new RuntimeException("Offre not found"));

        // Check if a visibility record already exists
        VisibleOffre visibleOffre = visibleOffreRepository.findByFiliere_IdFiliereAndOffre_IdOffre(idFiliere, idOffre)
                .orElse(new VisibleOffre(null, filiere, offre, visible));

        // Update visible flag
        visibleOffre.setVisible(visible);
        visibleOffreRepository.save(visibleOffre);
    }

    @Override
    public List<FiliereDTO> getFilieresByEcoleId(Long idEcole) {
        return filiereRepository.findByEcoleIdEcole(idEcole)
                .stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }
}
