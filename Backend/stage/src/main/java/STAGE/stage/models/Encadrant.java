package STAGE.stage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Encadrant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEncadrant;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    @ManyToOne
    @JoinColumn(name="entreprise_id")
    private Entreprise entreprise;


    @OneToMany(mappedBy = "encadrant", cascade = CascadeType.ALL)
    private List<Stage> stages;

    @OneToMany(mappedBy = "encadrant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations; // An Encadrant can evaluate many Stages

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Explicitly mapping foreign key
    private Utilisateur user;

    public Encadrant(String nom, String prenom, String email, String motDePasse, String telephone, Entreprise entreprise) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.telephone = telephone;
        this.entreprise = entreprise;
    }
}
