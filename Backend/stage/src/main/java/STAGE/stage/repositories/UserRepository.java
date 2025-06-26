package STAGE.stage.repositories;

import STAGE.stage.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository  extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByEmail(String email);
    @Query("SELECT COUNT(u.id) FROM Utilisateur u")
    long countTotalUsers();
}
