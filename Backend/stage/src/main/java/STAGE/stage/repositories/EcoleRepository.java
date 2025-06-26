package STAGE.stage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import STAGE.stage.models.Ecole;
import org.springframework.data.jpa.repository.Query;

public interface EcoleRepository extends JpaRepository<Ecole, Long> {
    // Ajoutez des méthodes de recherche personnalisées si nécessaire
    @Query("SELECT COUNT(e.id) FROM Ecole e")
    long countAllEcoles();}

