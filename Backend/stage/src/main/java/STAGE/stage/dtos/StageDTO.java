package STAGE.stage.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StageDTO {
    private Long idStage;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String duree;
    private String localisation;
    private Float montantRemuneration;
    private String statut;
    private String type;
    private Long etudiantId;
    private Long offreId;
    private Long encadrantId;
    private byte[] conventionDeStage;
    private byte[] attestationDeStage;
}
