package STAGE.stage.services;

import STAGE.stage.dtos.PostulationDTO;
import STAGE.stage.models.Postulation;

import java.util.List;

public interface PostulationService {

    PostulationDTO postuler(PostulationDTO dto);

    List<PostulationDTO> getAllPostulations();

    List<PostulationDTO> getPostulationsByOffreId(Long offreId);

    List<PostulationDTO> getPostulationsByEtudiantId(Long etudiantId);

    void updateEtatPostulation(Long postulationId, String etat);

    void deletePostulationById(Long id);

    Postulation getPostulationById(Long postulationId);
}
