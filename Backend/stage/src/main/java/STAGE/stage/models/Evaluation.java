package STAGE.stage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvaluation;

    private Double note;
    private String competances;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "encadrant_id", nullable = false)
    private Encadrant encadrant; // Many evaluations can belong to one Encadrant

    @OneToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage; // One evaluation belongs to one Stage
}