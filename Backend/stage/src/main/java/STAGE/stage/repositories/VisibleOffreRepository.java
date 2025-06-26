package STAGE.stage.repositories;

import STAGE.stage.models.VisibleOffre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VisibleOffreRepository extends JpaRepository<VisibleOffre, Long> {
    // Find all VisibleOffre entries by Filiere ID
    List<VisibleOffre> findByFiliere_IdFiliere(Long idFiliere);

    @Query("SELECT COUNT(o.id) FROM VisibleOffre o WHERE o.visible = true AND o.filiere.idFiliere = :idFiliere")
    long countVisibleOffersByFiliere(Long idFiliere);

    // Find all VisibleOffre entries by Offre ID
    List<VisibleOffre> findByOffre_IdOffre(Long idOffre);

    // Find a specific VisibleOffre by Filiere ID and Offre ID
    Optional<VisibleOffre> findByFiliere_IdFiliereAndOffre_IdOffre(Long idFiliere, Long idOffre);

    // Custom Query to Find VisibleOffre by OffreId and FiliereId
    @Query("SELECT vo FROM VisibleOffre vo WHERE vo.offre.idOffre = :offreId AND vo.filiere.idFiliere = :filiereId")
    Optional<VisibleOffre> findByOffreIdAndFiliereId(@Param("offreId") Long offreId, @Param("filiereId") Long filiereId);
}