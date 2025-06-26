package STAGE.stage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CompteEcole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompte;

    private String nom; // Nom du responsable
    private String prenom; // Prénom du responsable
    private String email; // Email pour la connexion
    private String motDePasse; // Mot de passe hashé
    private String telephone; // Téléphone du responsable

    @OneToOne
    @JoinColumn(name = "ecole_id")
    private Ecole ecole; // Relation avec l'entité Ecole
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Explicitly mapping foreign key
    private Utilisateur user;
}

