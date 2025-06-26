package STAGE.stage.repositories;

import STAGE.stage.dtos.EncadrantDTO;
import STAGE.stage.models.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EncadrantRepository extends JpaRepository<Encadrant, Long> {

    @Query("SELECT COUNT(e) FROM Encadrant e WHERE e.entreprise.idEntreprise = :companyId")
    long countByEntrepriseIdEntreprise(@Param("companyId") Long companyId);

    Optional<Encadrant> findByUserId(Long userId);


    List<Encadrant> findByEntrepriseIdEntreprise(Long entrepriseId);
}