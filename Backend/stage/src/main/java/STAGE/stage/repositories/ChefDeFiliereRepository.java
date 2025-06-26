package STAGE.stage.repositories;

import STAGE.stage.models.CompteEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.ChefDeFiliere;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChefDeFiliereRepository extends JpaRepository<ChefDeFiliere, Long> {
    List<ChefDeFiliere> findByEcole_IdEcole(Long idEcole);
    List<ChefDeFiliere> findByFiliere_IdFiliere(Long idFiliere);

    Optional<ChefDeFiliere> findByUserId(Long userId);

@Query("SELECT c.filiere.idFiliere FROM ChefDeFiliere c WHERE c.idCf = :idCf")
Optional<Long> findFiliereIdFiliereByIdCf(@Param("idCf") Long idCf);

}

