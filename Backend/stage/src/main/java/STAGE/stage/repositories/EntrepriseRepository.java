package STAGE.stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}

