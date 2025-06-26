package STAGE.stage.mappers;

import STAGE.stage.dtos.*;
import STAGE.stage.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    // Admin Mapping
    @Mapping(source = "user.id", target = "userId")
    AdminDTO toDto(Admin admin);
    @Mapping(source = "userId", target = "user.id")
    Admin toEntity(AdminDTO adminDTO);

    // Filiere Mapping
    @Mapping(source = "ecole.idEcole", target = "ecoleId")
    FiliereDTO toDto(Filiere filiere);
    @Mapping(source = "ecoleId", target = "ecole.idEcole")
    Filiere toEntity(FiliereDTO filiereDTO);

    // Ecole Mapping
    EcoleDTO toDto(Ecole ecole);
    Ecole toEntity(EcoleDTO ecoleDTO);

    // ChefDeFiliere Mapping
    @Mapping(source = "ecole.idEcole", target = "ecoleId")
    @Mapping(source = "filiere.idFiliere", target = "filiereId")
    @Mapping(source = "user.id", target = "userId")
    ChefDeFiliereDTO toDto(ChefDeFiliere chefDeFiliere);
    @Mapping(source = "ecoleId", target = "ecole.idEcole")
    @Mapping(source = "filiereId", target = "filiere.idFiliere")
    @Mapping(source = "userId", target = "user.id")
    ChefDeFiliere toEntity(ChefDeFiliereDTO chefDeFiliereDTO);

    // Etudiant Mapping
    @Mapping(source = "ecole.idEcole", target = "ecoleId")
    @Mapping(source = "filiere.idFiliere", target = "filiereId")
    @Mapping(source = "user.id", target = "userId")
    EtudiantDTO toDto(Etudiant etudiant);
    @Mapping(source = "ecoleId", target = "ecole.idEcole")
    @Mapping(source = "filiereId", target = "filiere.idFiliere")
    @Mapping(source = "userId", target = "user.id")
    Etudiant toEntity(EtudiantDTO etudiantDTO);

    // Stage Mapping
    @Mapping(source = "etudiant.idEtu", target = "etudiantId")
    @Mapping(source = "offre.idOffre", target = "offreId")
    @Mapping(source = "encadrant.idEncadrant", target = "encadrantId")
    @Mapping(source ="attestationDeStage", target = "attestationDeStage")
    StageDTO toDto(Stage stage);
    @Mapping(source = "etudiantId", target = "etudiant.idEtu")
    @Mapping(source = "offreId", target = "offre.idOffre")
    @Mapping(source = "encadrantId", target = "encadrant.idEncadrant")
    Stage toEntity(StageDTO stageDTO);

    // Offre Mapping
    @Mapping(source = "entreprise.idEntreprise", target = "entrepriseId")
    @Mapping(source = "rh.idRh", target = "rhId")
    OffreDTO toDto(Offre offre);
    @Mapping(source = "entrepriseId", target = "entreprise.idEntreprise")
    @Mapping(source = "rhId", target = "rh.idRh")
    Offre toEntity(OffreDTO offreDTO);

    // Postulation Mapping
    @Mapping(source = "etudiant.idEtu", target = "etudiantId")
    @Mapping(source = "offre.idOffre", target = "offreId")
    PostulationDTO toDto(Postulation postulation);
    @Mapping(source = "etudiantId", target = "etudiant.idEtu")
    @Mapping(source = "offreId", target = "offre.idOffre")
    Postulation toEntity(PostulationDTO postulationDTO);

    // RH Mapping
    @Mapping(source = "entreprise.idEntreprise", target = "entrepriseId")
    @Mapping(source = "user.id", target = "userId")
    RHDTO toDto(RH rh);
    @Mapping(source = "entrepriseId", target = "entreprise.idEntreprise")
    @Mapping(source = "userId", target = "user.id")
    RH toEntity(RHDTO rhDTO);

    // CoordinateurDeStage Mapping
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "ecole.idEcole", target = "ecoleId")
    CoordinateurDeStageDTO toDto(CoordinateurDeStage coordinateurDeStage);
    @Mapping(source = "ecoleId", target = "ecole.idEcole")
    @Mapping(source = "userId", target = "user.id")
    CoordinateurDeStage toEntity(CoordinateurDeStageDTO coordinateurDeStageDTO);

    // Encadrant Mapping
    @Mapping(source = "entreprise.idEntreprise", target = "entrepriseId")
    @Mapping(source = "user.id", target = "userId")
    EncadrantDTO toDto(Encadrant encadrant);
    @Mapping(source = "entrepriseId", target = "entreprise.idEntreprise")
    @Mapping(source = "userId", target = "user.id")
    Encadrant toEntity(EncadrantDTO encadrantDTO);

    // Entreprise Mapping
    EntrepriseDTO toDto(Entreprise entreprise);
    Entreprise toEntity(EntrepriseDTO entrepriseDTO);

    // CompteEcole Mapping
    @Mapping(source = "ecole.idEcole", target = "ecoleId")
    @Mapping(source = "user.id", target = "userId")
    CompteEcoleDTO toDto(CompteEcole compteEcole);
    @Mapping(source = "ecoleId", target = "ecole.idEcole")
    @Mapping(source = "userId", target = "user.id")
    CompteEcole toEntity(CompteEcoleDTO compteEcoleDTO);

    // CompteEntreprise Mapping
    @Mapping(source = "entreprise.idEntreprise", target = "entrepriseId")
    @Mapping(source = "user.id", target = "userId")
    CompteEntrepriseDTO toDto(CompteEntreprise compteEntreprise);
    @Mapping(source = "entrepriseId", target = "entreprise.idEntreprise")
    @Mapping(source = "userId", target = "user.id")
    CompteEntreprise toEntity(CompteEntrepriseDTO compteEntrepriseDTO);

    // Entretien Mapping
    @Mapping(source = "offre.idOffre", target = "offreId")
    @Mapping(source = "etudiant.idEtu", target = "etudiantId")
    EntretienDTO toDto(Entretien entretien);
    @Mapping(source = "offreId", target = "offre.idOffre")
    @Mapping(source = "etudiantId", target = "etudiant.idEtu")
    Entretien toEntity(EntretienDTO entretienDTO);

    // Map between Evaluation and its DTO
    @Mapping(source = "encadrant.idEncadrant", target = "encadrantId")
    @Mapping(source = "stage.idStage", target = "stageId")
    EvaluationDTO toDto(Evaluation evaluation);
    @Mapping(source = "encadrantId", target = "encadrant.idEncadrant")
    @Mapping(source = "stageId", target = "stage.idStage")
    Evaluation toEntity(EvaluationDTO evaluationDTO);
}
