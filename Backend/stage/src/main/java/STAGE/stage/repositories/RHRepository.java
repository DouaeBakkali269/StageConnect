package STAGE.stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.RH;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RHRepository extends JpaRepository<RH, Long> {
    List<RH> findByEntrepriseIdEntreprise(Long entrepriseId);

    @Query("SELECT COUNT(s) FROM RH s WHERE s.entreprise.idEntreprise = :companyId")
    long countByEntrepriseIdEntreprise(@Param("companyId") Long companyId);

    Optional<RH> findByUserId(Long userId);
}
