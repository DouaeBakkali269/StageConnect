package STAGE.stage.services.implementation;

import STAGE.stage.models.VisibleOffre;
import STAGE.stage.repositories.VisibleOffreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisibleOffreService {

    private final VisibleOffreRepository visibleOffreRepository;

    public void deleteVisibleOffre(Long offreId, Long filiereId) {
        // Check if the VisibleOffre exists with these IDs
        VisibleOffre visibleOffre = visibleOffreRepository.findByOffreIdAndFiliereId(offreId, filiereId)
                .orElseThrow(() -> new IllegalArgumentException("VisibleOffre not found for the given OffreId and FiliereId."));

        // Delete the VisibleOffre
        visibleOffreRepository.delete(visibleOffre);
    }
}
