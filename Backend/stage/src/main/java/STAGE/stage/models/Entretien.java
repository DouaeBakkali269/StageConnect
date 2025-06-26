package STAGE.stage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEntretien;
    private Date dateEntretien;
    private String adresse;
    private String duree;
    private String etat;
    private String resultat;
    private String lien;

    @ManyToOne
    private Offre offre;

    @ManyToOne
    @JoinColumn(name = "etudiant_id") // Clé étrangère dans la table Entretien
    private Etudiant etudiant;
}
