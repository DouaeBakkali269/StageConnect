package STAGE.stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.CompteEntreprise;

import java.util.Optional;

public interface CompteEntrepriseRepository extends JpaRepository<CompteEntreprise, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
    Optional<CompteEntreprise> findByEntreprise_IdEntreprise(Long idEntreprise);

    Optional<CompteEntreprise> findByUserId(Long userId);
}

