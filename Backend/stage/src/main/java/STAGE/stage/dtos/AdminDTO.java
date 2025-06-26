package STAGE.stage.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AdminDTO {
    private Long id;
    private String nomAd;
    private String prenomAd;
    private String emailAd;
    private String motDePasse;
    private String telephone;
    private Long userId;

}

