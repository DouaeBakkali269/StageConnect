package STAGE.stage.dtos;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OffreDTO {
    private Long idOffre;
    private String objetOffre;
    private String descriptionOffre;
    private Date dateLancement;
    private Date dateLimite;
    private String posteOffre;
    private String dureeStage;
    private String modeOffre;
    private String remuneration;
    private String typeStageOffre;
    private String niveauRequisOffre;
    private Long rhId;
    private Long entrepriseId;
    private List<Long> entretienIds;
    private List<Long> postulationIds;
    private List<Long> stageIds;
}