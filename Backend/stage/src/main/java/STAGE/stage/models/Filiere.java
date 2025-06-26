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
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFiliere;

    private String nomFiliere;
    private String abrvFiliere;

    @OneToOne(mappedBy = "filiere")
    private ChefDeFiliere chefDeFiliere;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL)
    private List<Etudiant> etudiants; // Une filière peut avoir plusieurs étudiants

    @ManyToOne
    @JoinColumn(name = "ecole_id") // Foreign key in Filiere table
    private Ecole ecole; // Updated association
    public Filiere(String nomFiliere, String abrvFiliere, Ecole ecole) {
        this.nomFiliere = nomFiliere;
        this.abrvFiliere = abrvFiliere;
        this.ecole = ecole;
    }
}