package STAGE.stage.dtos;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PostulationDTO {
    private Long id;
    private Long offreId;
    private Long etudiantId;
    private String etatPostulation;
    private byte[] cv;
    private byte[] lettreMotivation;
}

