package STAGE.stage.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.PostulationDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Etudiant;
import STAGE.stage.models.Offre;
import STAGE.stage.models.Postulation;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.OffreRepository;
import STAGE.stage.repositories.PostulationRepository;
import STAGE.stage.services.PostulationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostulationServiceImpl implements PostulationService {
    private final PostulationRepository postulationRepository;
    private final OffreRepository offreRepository;
    private final EtudiantRepository etudiantRepository;
    private final EntityMapper mapper;

    @Override
    public PostulationDTO postuler(PostulationDTO dto) {
        Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
        Offre offre = offreRepository.findById(dto.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        Postulation postulation = new Postulation();
        postulation.setEtudiant(etudiant);
        postulation.setOffre(offre);
        postulation.setCv(dto.getCv());
        postulation.setLettreMotivation(dto.getLettreMotivation());
        postulation.setEtatPostulation("En attente");

        // Sauvegarder la postulation
        return mapper.toDto(postulationRepository.save(postulation));
    }

    @Override
    public List<PostulationDTO> getAllPostulations() {
        return postulationRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulationDTO> getPostulationsByOffreId(Long offreId) {
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        return postulationRepository.findByOffre(offre).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulationDTO> getPostulationsByEtudiantId(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        return postulationRepository.findByEtudiant(etudiant).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEtatPostulation(Long postulationId, String etat) {
        Postulation postulation = postulationRepository.findById(postulationId)
                .orElseThrow(() -> new RuntimeException("Postulation introuvable"));

        postulation.setEtatPostulation(etat);
        postulationRepository.save(postulation);
    }

    @Override
    public void deletePostulationById(Long id) {
        if (!postulationRepository.existsById(id)) {
            throw new IllegalArgumentException("Postulation with ID " + id + " does not exist.");
        }
        postulationRepository.deleteById(id);
    }

    @Override
    public Postulation getPostulationById(Long postulationId) {
        // Retrieve the postulation by ID
        Postulation postulation = postulationRepository.findById(postulationId)
                .orElseThrow(() -> new RuntimeException("Postulation introuvable avec l'ID : " + postulationId));

        // Map the retrieved entity to a DTO and return
        return postulation;
    }
}

