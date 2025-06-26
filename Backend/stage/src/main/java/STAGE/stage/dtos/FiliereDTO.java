package STAGE.stage.dtos;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FiliereDTO {
    private Long idFiliere; // Identifiant de la filière
    private String nomFiliere; // Nom de la filière
    private String abrvFiliere; // Abréviation de la filière
    private Long ecoleId;


    // DTO du chef de filière (relation One-to-One)
    private Long chefDeFiliereId;

    // Liste des étudiants (relation One-to-Many)
    private List<Long> etudiantIds;

}
