package STAGE.stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.CompteEcole;

import java.util.Optional;

public interface CompteEcoleRepository extends JpaRepository<CompteEcole, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
    Optional<CompteEcole> findByEcole_IdEcole(Long ecoleId);

    Optional<CompteEcole> findByUserId(Long userId);
}

