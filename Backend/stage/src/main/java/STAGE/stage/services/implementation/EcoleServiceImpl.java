package STAGE.stage.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.EcoleDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Ecole;
import STAGE.stage.models.Filiere;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.repositories.FiliereRepository;
import STAGE.stage.services.EcoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EcoleServiceImpl implements EcoleService {

    private final EcoleRepository ecoleRepository;
    private final FiliereRepository filiereRepository;
    private final EntityMapper mapper;

    @Override
    public EcoleDTO createEcole(EcoleDTO ecoleDTO) {
        // Mapper le DTO en entité
        Ecole ecole = mapper.toEntity(ecoleDTO);

        // Si la liste des filières est null, on l'initialise à une liste vide
        //if (ecole.getFilieres() == null) {
            //ecole.setFilieres(new ArrayList<>());
       // }

        // Sauvegarder l'école dans la base de données
        ecole = ecoleRepository.save(ecole);

        // Retourner l'école créée
        return mapper.toDto(ecole);
    }

    @Override
    public EcoleDTO updateEcole(Long id, EcoleDTO ecoleDTO) {
        // Récupérer l'Ecole
        Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));

        // Mettre à jour les informations de l'école
        ecole.setNomEcole(ecoleDTO.getNomEcole());
        ecole.setVilleEcole(ecoleDTO.getVilleEcole());
        ecole.setAdresseEcole(ecoleDTO.getAdresseEcole());
        ecole.setDescription(ecoleDTO.getDescription());
        ecole.setTelephoneFix(ecoleDTO.getTelephoneFix());
        ecole.setTypeEcole(ecoleDTO.getTypeEcole());
        ecole.setDomaineEcole(ecoleDTO.getDomaineEcole());

        // Sauvegarder les modifications dans la base de données
        ecole = ecoleRepository.save(ecole);

        // Retourner l'école mise à jour
        return mapper.toDto(ecole);
    }

    @Override
    public EcoleDTO getEcoleById(Long id) {
        return ecoleRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));
    }

    @Override
    public List<EcoleDTO> getAllEcoles() {
        return ecoleRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEcole(Long id) {
        ecoleRepository.deleteById(id);
    }

    @Override
    public EcoleDTO addFiliereToEcole(Long ecoleId, Long filiereId) {
        // Récupérer l'Ecole
        Ecole ecole = ecoleRepository.findById(ecoleId)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));

        // Récupérer la Filière
        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));

        // Ajouter la filière à la liste des filières de l'école
        if (!ecole.getFilieres().contains(filiere)) {
            ecole.getFilieres().add(filiere);
            ecoleRepository.save(ecole); // Sauvegarder les modifications
        }

        return mapper.toDto(ecole);
    }

    @Override
    public EcoleDTO removeFiliereFromEcole(Long ecoleId, Long filiereId) {
        Ecole ecole = ecoleRepository.findById(ecoleId)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));
        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));

        if (ecole.getFilieres().contains(filiere)) {
            ecole.getFilieres().remove(filiere);
            ecoleRepository.save(ecole);
        }

        return mapper.toDto(ecole);
    }

    @Override
    public EcoleDTO updateFiliereInEcole(Long ecoleId, Long filiereId, Filiere newFiliere) {
        Ecole ecole = ecoleRepository.findById(ecoleId)
                .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));
        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));

        // Remplacer l'ancienne filière par la nouvelle
        ecole.getFilieres().remove(filiere);
        ecole.getFilieres().add(newFiliere);
        ecoleRepository.save(ecole);

        return mapper.toDto(ecole);
    }
}
