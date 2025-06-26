package STAGE.stage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VisibleOffre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the visibility link

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere; // The Filiere this visibility applies to

    @ManyToOne
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre; // The Offre being linked

    private Boolean visible; // Flag: Is the offer visible for this Filiere
}