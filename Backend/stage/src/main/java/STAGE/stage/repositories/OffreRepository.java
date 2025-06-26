package STAGE.stage.repositories;

import STAGE.stage.models.Offre;
import STAGE.stage.models.RH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {

    @Query("SELECT COUNT(o) FROM Offre o WHERE o.entreprise.idEntreprise = :companyId")
    long countOffersByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(o) FROM Offre o WHERE o.entreprise.idEntreprise = :companyId AND o.dateLimite > CURRENT_DATE")
    long countOpenOffersByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT o FROM Offre o WHERE o.entreprise.idEntreprise = :companyId")
    List<Offre> findOffersByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(vo) FROM VisibleOffre vo WHERE vo.filiere.idFiliere = :filiereId AND vo.visible = true")
    long countVisibleOffersByFiliere(@Param("filiereId") Long filiereId);

    List<Offre> findByRh(RH rh);

    @Query("SELECT COUNT(o) FROM Offre o WHERE o.dateLimite > :date")
    long countOpenOffers(Date date);
}