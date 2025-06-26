package STAGE.stage.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcoleDTO {
    private Long idEcole;
    private String nomEcole;
    private String villeEcole;
    private String adresseEcole;
    private String description;
    private byte[] logo;
    private String telephoneFix;
    private String typeEcole;
    private String domaineEcole;

    private Long compteEcoleId; // DTO for CompteEcole relation
    private List<Long> etudiantIds; // List of EtudiantDTOs
    private List<Long> coordinateurIds; // List of CoordinateurDeStageDTOs
    private List<Long> chefDeFiliereIds; // List of ChefDeFiliereDTOs
    private List<Long> filiereIds; // List of FiliereDTOs for many-to-many
}
