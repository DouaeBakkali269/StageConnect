package STAGE.stage.repositories;

import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.Filiere;

import java.util.List;
import java.util.Optional;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    List<Filiere> findByEcoleIdEcole(Long ecoleId);
    Optional<Filiere> findByChefDeFiliereIdCf(Long idCf);
}

