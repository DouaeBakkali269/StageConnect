package STAGE.stage.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDTO {
    private Long idEvaluation;
    private Double note;
    private String competances;
    private String commentaire;
    private Long encadrantId; // ID of the Encadrant
    private Long stageId;    // ID of the Stage (to link it)
}