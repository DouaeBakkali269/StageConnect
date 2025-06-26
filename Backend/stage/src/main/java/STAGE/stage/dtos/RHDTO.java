package STAGE.stage.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RHDTO {
    private Long idRh;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private Long entrepriseId;
    private List<Long> offreIds;
    private Long userId;
}
