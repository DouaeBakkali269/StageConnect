package STAGE.stage.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChefDeFiliereDTO {
    private Long idCf;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private Long filiereId; // Utilisé pour associer une filière
    private Long ecoleId;
    private Long userId;
// Utilisé pour associer une école
}
