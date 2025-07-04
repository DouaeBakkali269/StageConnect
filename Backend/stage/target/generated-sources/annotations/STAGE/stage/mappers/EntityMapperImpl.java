package STAGE.stage.mappers;

import STAGE.stage.dtos.AdminDTO;
import STAGE.stage.dtos.ChefDeFiliereDTO;
import STAGE.stage.dtos.CompteEcoleDTO;
import STAGE.stage.dtos.CompteEntrepriseDTO;
import STAGE.stage.dtos.CoordinateurDeStageDTO;
import STAGE.stage.dtos.EcoleDTO;
import STAGE.stage.dtos.EncadrantDTO;
import STAGE.stage.dtos.EntrepriseDTO;
import STAGE.stage.dtos.EntretienDTO;
import STAGE.stage.dtos.EtudiantDTO;
import STAGE.stage.dtos.EvaluationDTO;
import STAGE.stage.dtos.FiliereDTO;
import STAGE.stage.dtos.OffreDTO;
import STAGE.stage.dtos.PostulationDTO;
import STAGE.stage.dtos.RHDTO;
import STAGE.stage.dtos.StageDTO;
import STAGE.stage.models.Admin;
import STAGE.stage.models.ChefDeFiliere;
import STAGE.stage.models.CompteEcole;
import STAGE.stage.models.CompteEntreprise;
import STAGE.stage.models.CoordinateurDeStage;
import STAGE.stage.models.Ecole;
import STAGE.stage.models.Encadrant;
import STAGE.stage.models.Entreprise;
import STAGE.stage.models.Entretien;
import STAGE.stage.models.Etudiant;
import STAGE.stage.models.Evaluation;
import STAGE.stage.models.Filiere;
import STAGE.stage.models.Offre;
import STAGE.stage.models.Postulation;
import STAGE.stage.models.RH;
import STAGE.stage.models.Stage;
import STAGE.stage.models.Utilisateur;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-04T17:05:26+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public AdminDTO toDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminDTO adminDTO = new AdminDTO();

        adminDTO.setUserId( adminUserId( admin ) );
        adminDTO.setEmailAd( admin.getEmailAd() );
        adminDTO.setId( admin.getId() );
        adminDTO.setMotDePasse( admin.getMotDePasse() );
        adminDTO.setNomAd( admin.getNomAd() );
        adminDTO.setPrenomAd( admin.getPrenomAd() );
        adminDTO.setTelephone( admin.getTelephone() );

        return adminDTO;
    }

    @Override
    public Admin toEntity(AdminDTO adminDTO) {
        if ( adminDTO == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setUser( adminDTOToUtilisateur( adminDTO ) );
        admin.setEmailAd( adminDTO.getEmailAd() );
        admin.setId( adminDTO.getId() );
        admin.setMotDePasse( adminDTO.getMotDePasse() );
        admin.setNomAd( adminDTO.getNomAd() );
        admin.setPrenomAd( adminDTO.getPrenomAd() );
        admin.setTelephone( adminDTO.getTelephone() );

        return admin;
    }

    @Override
    public FiliereDTO toDto(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }

        FiliereDTO filiereDTO = new FiliereDTO();

        filiereDTO.setEcoleId( filiereEcoleIdEcole( filiere ) );
        filiereDTO.setAbrvFiliere( filiere.getAbrvFiliere() );
        filiereDTO.setIdFiliere( filiere.getIdFiliere() );
        filiereDTO.setNomFiliere( filiere.getNomFiliere() );

        return filiereDTO;
    }

    @Override
    public Filiere toEntity(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setEcole( filiereDTOToEcole( filiereDTO ) );
        filiere.setAbrvFiliere( filiereDTO.getAbrvFiliere() );
        filiere.setIdFiliere( filiereDTO.getIdFiliere() );
        filiere.setNomFiliere( filiereDTO.getNomFiliere() );

        return filiere;
    }

    @Override
    public EcoleDTO toDto(Ecole ecole) {
        if ( ecole == null ) {
            return null;
        }

        EcoleDTO ecoleDTO = new EcoleDTO();

        ecoleDTO.setAdresseEcole( ecole.getAdresseEcole() );
        ecoleDTO.setDescription( ecole.getDescription() );
        ecoleDTO.setDomaineEcole( ecole.getDomaineEcole() );
        ecoleDTO.setIdEcole( ecole.getIdEcole() );
        byte[] logo = ecole.getLogo();
        if ( logo != null ) {
            ecoleDTO.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        ecoleDTO.setNomEcole( ecole.getNomEcole() );
        ecoleDTO.setTelephoneFix( ecole.getTelephoneFix() );
        ecoleDTO.setTypeEcole( ecole.getTypeEcole() );
        ecoleDTO.setVilleEcole( ecole.getVilleEcole() );

        return ecoleDTO;
    }

    @Override
    public Ecole toEntity(EcoleDTO ecoleDTO) {
        if ( ecoleDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setAdresseEcole( ecoleDTO.getAdresseEcole() );
        ecole.setDescription( ecoleDTO.getDescription() );
        ecole.setDomaineEcole( ecoleDTO.getDomaineEcole() );
        ecole.setIdEcole( ecoleDTO.getIdEcole() );
        byte[] logo = ecoleDTO.getLogo();
        if ( logo != null ) {
            ecole.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        ecole.setNomEcole( ecoleDTO.getNomEcole() );
        ecole.setTelephoneFix( ecoleDTO.getTelephoneFix() );
        ecole.setTypeEcole( ecoleDTO.getTypeEcole() );
        ecole.setVilleEcole( ecoleDTO.getVilleEcole() );

        return ecole;
    }

    @Override
    public ChefDeFiliereDTO toDto(ChefDeFiliere chefDeFiliere) {
        if ( chefDeFiliere == null ) {
            return null;
        }

        ChefDeFiliereDTO chefDeFiliereDTO = new ChefDeFiliereDTO();

        chefDeFiliereDTO.setEcoleId( chefDeFiliereEcoleIdEcole( chefDeFiliere ) );
        chefDeFiliereDTO.setFiliereId( chefDeFiliereFiliereIdFiliere( chefDeFiliere ) );
        chefDeFiliereDTO.setUserId( chefDeFiliereUserId( chefDeFiliere ) );
        chefDeFiliereDTO.setEmail( chefDeFiliere.getEmail() );
        chefDeFiliereDTO.setIdCf( chefDeFiliere.getIdCf() );
        chefDeFiliereDTO.setMotDePasse( chefDeFiliere.getMotDePasse() );
        chefDeFiliereDTO.setNom( chefDeFiliere.getNom() );
        chefDeFiliereDTO.setPrenom( chefDeFiliere.getPrenom() );
        chefDeFiliereDTO.setTelephone( chefDeFiliere.getTelephone() );

        return chefDeFiliereDTO;
    }

    @Override
    public ChefDeFiliere toEntity(ChefDeFiliereDTO chefDeFiliereDTO) {
        if ( chefDeFiliereDTO == null ) {
            return null;
        }

        ChefDeFiliere chefDeFiliere = new ChefDeFiliere();

        chefDeFiliere.setEcole( chefDeFiliereDTOToEcole( chefDeFiliereDTO ) );
        chefDeFiliere.setFiliere( chefDeFiliereDTOToFiliere( chefDeFiliereDTO ) );
        chefDeFiliere.setUser( chefDeFiliereDTOToUtilisateur( chefDeFiliereDTO ) );
        chefDeFiliere.setEmail( chefDeFiliereDTO.getEmail() );
        chefDeFiliere.setIdCf( chefDeFiliereDTO.getIdCf() );
        chefDeFiliere.setMotDePasse( chefDeFiliereDTO.getMotDePasse() );
        chefDeFiliere.setNom( chefDeFiliereDTO.getNom() );
        chefDeFiliere.setPrenom( chefDeFiliereDTO.getPrenom() );
        chefDeFiliere.setTelephone( chefDeFiliereDTO.getTelephone() );

        return chefDeFiliere;
    }

    @Override
    public EtudiantDTO toDto(Etudiant etudiant) {
        if ( etudiant == null ) {
            return null;
        }

        EtudiantDTO etudiantDTO = new EtudiantDTO();

        etudiantDTO.setEcoleId( etudiantEcoleIdEcole( etudiant ) );
        etudiantDTO.setFiliereId( etudiantFiliereIdFiliere( etudiant ) );
        etudiantDTO.setUserId( etudiantUserId( etudiant ) );
        etudiantDTO.setCodeEtu( etudiant.getCodeEtu() );
        etudiantDTO.setEmail( etudiant.getEmail() );
        etudiantDTO.setIdEtu( etudiant.getIdEtu() );
        etudiantDTO.setMotDePasse( etudiant.getMotDePasse() );
        etudiantDTO.setNom( etudiant.getNom() );
        byte[] photoCouverture = etudiant.getPhotoCouverture();
        if ( photoCouverture != null ) {
            etudiantDTO.setPhotoCouverture( Arrays.copyOf( photoCouverture, photoCouverture.length ) );
        }
        byte[] photoProfil = etudiant.getPhotoProfil();
        if ( photoProfil != null ) {
            etudiantDTO.setPhotoProfil( Arrays.copyOf( photoProfil, photoProfil.length ) );
        }
        etudiantDTO.setPrenom( etudiant.getPrenom() );
        etudiantDTO.setStatutEtudiant( etudiant.getStatutEtudiant() );
        etudiantDTO.setTel( etudiant.getTel() );

        return etudiantDTO;
    }

    @Override
    public Etudiant toEntity(EtudiantDTO etudiantDTO) {
        if ( etudiantDTO == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setEcole( etudiantDTOToEcole( etudiantDTO ) );
        etudiant.setFiliere( etudiantDTOToFiliere( etudiantDTO ) );
        etudiant.setUser( etudiantDTOToUtilisateur( etudiantDTO ) );
        etudiant.setCodeEtu( etudiantDTO.getCodeEtu() );
        etudiant.setEmail( etudiantDTO.getEmail() );
        etudiant.setIdEtu( etudiantDTO.getIdEtu() );
        etudiant.setMotDePasse( etudiantDTO.getMotDePasse() );
        etudiant.setNom( etudiantDTO.getNom() );
        byte[] photoCouverture = etudiantDTO.getPhotoCouverture();
        if ( photoCouverture != null ) {
            etudiant.setPhotoCouverture( Arrays.copyOf( photoCouverture, photoCouverture.length ) );
        }
        byte[] photoProfil = etudiantDTO.getPhotoProfil();
        if ( photoProfil != null ) {
            etudiant.setPhotoProfil( Arrays.copyOf( photoProfil, photoProfil.length ) );
        }
        etudiant.setPrenom( etudiantDTO.getPrenom() );
        etudiant.setStatutEtudiant( etudiantDTO.getStatutEtudiant() );
        etudiant.setTel( etudiantDTO.getTel() );

        return etudiant;
    }

    @Override
    public StageDTO toDto(Stage stage) {
        if ( stage == null ) {
            return null;
        }

        StageDTO stageDTO = new StageDTO();

        stageDTO.setEtudiantId( stageEtudiantIdEtu( stage ) );
        stageDTO.setOffreId( stageOffreIdOffre( stage ) );
        stageDTO.setEncadrantId( stageEncadrantIdEncadrant( stage ) );
        byte[] attestationDeStage = stage.getAttestationDeStage();
        if ( attestationDeStage != null ) {
            stageDTO.setAttestationDeStage( Arrays.copyOf( attestationDeStage, attestationDeStage.length ) );
        }
        byte[] conventionDeStage = stage.getConventionDeStage();
        if ( conventionDeStage != null ) {
            stageDTO.setConventionDeStage( Arrays.copyOf( conventionDeStage, conventionDeStage.length ) );
        }
        stageDTO.setDateDebut( stage.getDateDebut() );
        stageDTO.setDateFin( stage.getDateFin() );
        stageDTO.setDescription( stage.getDescription() );
        stageDTO.setDuree( stage.getDuree() );
        stageDTO.setIdStage( stage.getIdStage() );
        stageDTO.setLocalisation( stage.getLocalisation() );
        stageDTO.setMontantRemuneration( stage.getMontantRemuneration() );
        stageDTO.setStatut( stage.getStatut() );
        stageDTO.setTitre( stage.getTitre() );
        stageDTO.setType( stage.getType() );

        return stageDTO;
    }

    @Override
    public Stage toEntity(StageDTO stageDTO) {
        if ( stageDTO == null ) {
            return null;
        }

        Stage stage = new Stage();

        stage.setEtudiant( stageDTOToEtudiant( stageDTO ) );
        stage.setOffre( stageDTOToOffre( stageDTO ) );
        stage.setEncadrant( stageDTOToEncadrant( stageDTO ) );
        byte[] attestationDeStage = stageDTO.getAttestationDeStage();
        if ( attestationDeStage != null ) {
            stage.setAttestationDeStage( Arrays.copyOf( attestationDeStage, attestationDeStage.length ) );
        }
        byte[] conventionDeStage = stageDTO.getConventionDeStage();
        if ( conventionDeStage != null ) {
            stage.setConventionDeStage( Arrays.copyOf( conventionDeStage, conventionDeStage.length ) );
        }
        stage.setDateDebut( stageDTO.getDateDebut() );
        stage.setDateFin( stageDTO.getDateFin() );
        stage.setDescription( stageDTO.getDescription() );
        stage.setDuree( stageDTO.getDuree() );
        stage.setIdStage( stageDTO.getIdStage() );
        stage.setLocalisation( stageDTO.getLocalisation() );
        stage.setMontantRemuneration( stageDTO.getMontantRemuneration() );
        stage.setStatut( stageDTO.getStatut() );
        stage.setTitre( stageDTO.getTitre() );
        stage.setType( stageDTO.getType() );

        return stage;
    }

    @Override
    public OffreDTO toDto(Offre offre) {
        if ( offre == null ) {
            return null;
        }

        OffreDTO offreDTO = new OffreDTO();

        offreDTO.setEntrepriseId( offreEntrepriseIdEntreprise( offre ) );
        offreDTO.setRhId( offreRhIdRh( offre ) );
        offreDTO.setDateLancement( offre.getDateLancement() );
        offreDTO.setDateLimite( offre.getDateLimite() );
        offreDTO.setDescriptionOffre( offre.getDescriptionOffre() );
        offreDTO.setDureeStage( offre.getDureeStage() );
        offreDTO.setIdOffre( offre.getIdOffre() );
        offreDTO.setModeOffre( offre.getModeOffre() );
        offreDTO.setNiveauRequisOffre( offre.getNiveauRequisOffre() );
        offreDTO.setObjetOffre( offre.getObjetOffre() );
        offreDTO.setPosteOffre( offre.getPosteOffre() );
        offreDTO.setRemuneration( offre.getRemuneration() );
        offreDTO.setTypeStageOffre( offre.getTypeStageOffre() );

        return offreDTO;
    }

    @Override
    public Offre toEntity(OffreDTO offreDTO) {
        if ( offreDTO == null ) {
            return null;
        }

        Offre offre = new Offre();

        offre.setEntreprise( offreDTOToEntreprise( offreDTO ) );
        offre.setRh( offreDTOToRH( offreDTO ) );
        offre.setDateLancement( offreDTO.getDateLancement() );
        offre.setDateLimite( offreDTO.getDateLimite() );
        offre.setDescriptionOffre( offreDTO.getDescriptionOffre() );
        offre.setDureeStage( offreDTO.getDureeStage() );
        offre.setIdOffre( offreDTO.getIdOffre() );
        offre.setModeOffre( offreDTO.getModeOffre() );
        offre.setNiveauRequisOffre( offreDTO.getNiveauRequisOffre() );
        offre.setObjetOffre( offreDTO.getObjetOffre() );
        offre.setPosteOffre( offreDTO.getPosteOffre() );
        offre.setRemuneration( offreDTO.getRemuneration() );
        offre.setTypeStageOffre( offreDTO.getTypeStageOffre() );

        return offre;
    }

    @Override
    public PostulationDTO toDto(Postulation postulation) {
        if ( postulation == null ) {
            return null;
        }

        PostulationDTO postulationDTO = new PostulationDTO();

        postulationDTO.setEtudiantId( postulationEtudiantIdEtu( postulation ) );
        postulationDTO.setOffreId( postulationOffreIdOffre( postulation ) );
        byte[] cv = postulation.getCv();
        if ( cv != null ) {
            postulationDTO.setCv( Arrays.copyOf( cv, cv.length ) );
        }
        postulationDTO.setEtatPostulation( postulation.getEtatPostulation() );
        postulationDTO.setId( postulation.getId() );
        byte[] lettreMotivation = postulation.getLettreMotivation();
        if ( lettreMotivation != null ) {
            postulationDTO.setLettreMotivation( Arrays.copyOf( lettreMotivation, lettreMotivation.length ) );
        }

        return postulationDTO;
    }

    @Override
    public Postulation toEntity(PostulationDTO postulationDTO) {
        if ( postulationDTO == null ) {
            return null;
        }

        Postulation postulation = new Postulation();

        postulation.setEtudiant( postulationDTOToEtudiant( postulationDTO ) );
        postulation.setOffre( postulationDTOToOffre( postulationDTO ) );
        byte[] cv = postulationDTO.getCv();
        if ( cv != null ) {
            postulation.setCv( Arrays.copyOf( cv, cv.length ) );
        }
        postulation.setEtatPostulation( postulationDTO.getEtatPostulation() );
        postulation.setId( postulationDTO.getId() );
        byte[] lettreMotivation = postulationDTO.getLettreMotivation();
        if ( lettreMotivation != null ) {
            postulation.setLettreMotivation( Arrays.copyOf( lettreMotivation, lettreMotivation.length ) );
        }

        return postulation;
    }

    @Override
    public RHDTO toDto(RH rh) {
        if ( rh == null ) {
            return null;
        }

        RHDTO rHDTO = new RHDTO();

        rHDTO.setEntrepriseId( rhEntrepriseIdEntreprise( rh ) );
        rHDTO.setUserId( rhUserId( rh ) );
        rHDTO.setEmail( rh.getEmail() );
        rHDTO.setIdRh( rh.getIdRh() );
        rHDTO.setMotDePasse( rh.getMotDePasse() );
        rHDTO.setNom( rh.getNom() );
        rHDTO.setPrenom( rh.getPrenom() );
        rHDTO.setTelephone( rh.getTelephone() );

        return rHDTO;
    }

    @Override
    public RH toEntity(RHDTO rhDTO) {
        if ( rhDTO == null ) {
            return null;
        }

        RH rH = new RH();

        rH.setEntreprise( rHDTOToEntreprise( rhDTO ) );
        rH.setUser( rHDTOToUtilisateur( rhDTO ) );
        rH.setEmail( rhDTO.getEmail() );
        rH.setIdRh( rhDTO.getIdRh() );
        rH.setMotDePasse( rhDTO.getMotDePasse() );
        rH.setNom( rhDTO.getNom() );
        rH.setPrenom( rhDTO.getPrenom() );
        rH.setTelephone( rhDTO.getTelephone() );

        return rH;
    }

    @Override
    public CoordinateurDeStageDTO toDto(CoordinateurDeStage coordinateurDeStage) {
        if ( coordinateurDeStage == null ) {
            return null;
        }

        CoordinateurDeStageDTO coordinateurDeStageDTO = new CoordinateurDeStageDTO();

        coordinateurDeStageDTO.setUserId( coordinateurDeStageUserId( coordinateurDeStage ) );
        coordinateurDeStageDTO.setEcoleId( coordinateurDeStageEcoleIdEcole( coordinateurDeStage ) );
        coordinateurDeStageDTO.setEmail( coordinateurDeStage.getEmail() );
        coordinateurDeStageDTO.setIdCs( coordinateurDeStage.getIdCs() );
        coordinateurDeStageDTO.setMotDePasse( coordinateurDeStage.getMotDePasse() );
        coordinateurDeStageDTO.setNom( coordinateurDeStage.getNom() );
        coordinateurDeStageDTO.setPrenom( coordinateurDeStage.getPrenom() );
        coordinateurDeStageDTO.setTelephone( coordinateurDeStage.getTelephone() );

        return coordinateurDeStageDTO;
    }

    @Override
    public CoordinateurDeStage toEntity(CoordinateurDeStageDTO coordinateurDeStageDTO) {
        if ( coordinateurDeStageDTO == null ) {
            return null;
        }

        CoordinateurDeStage coordinateurDeStage = new CoordinateurDeStage();

        coordinateurDeStage.setEcole( coordinateurDeStageDTOToEcole( coordinateurDeStageDTO ) );
        coordinateurDeStage.setUser( coordinateurDeStageDTOToUtilisateur( coordinateurDeStageDTO ) );
        coordinateurDeStage.setEmail( coordinateurDeStageDTO.getEmail() );
        coordinateurDeStage.setIdCs( coordinateurDeStageDTO.getIdCs() );
        coordinateurDeStage.setMotDePasse( coordinateurDeStageDTO.getMotDePasse() );
        coordinateurDeStage.setNom( coordinateurDeStageDTO.getNom() );
        coordinateurDeStage.setPrenom( coordinateurDeStageDTO.getPrenom() );
        coordinateurDeStage.setTelephone( coordinateurDeStageDTO.getTelephone() );

        return coordinateurDeStage;
    }

    @Override
    public EncadrantDTO toDto(Encadrant encadrant) {
        if ( encadrant == null ) {
            return null;
        }

        EncadrantDTO encadrantDTO = new EncadrantDTO();

        encadrantDTO.setEntrepriseId( encadrantEntrepriseIdEntreprise( encadrant ) );
        encadrantDTO.setUserId( encadrantUserId( encadrant ) );
        encadrantDTO.setEmail( encadrant.getEmail() );
        encadrantDTO.setIdEncadrant( encadrant.getIdEncadrant() );
        encadrantDTO.setMotDePasse( encadrant.getMotDePasse() );
        encadrantDTO.setNom( encadrant.getNom() );
        encadrantDTO.setPrenom( encadrant.getPrenom() );
        encadrantDTO.setTelephone( encadrant.getTelephone() );

        return encadrantDTO;
    }

    @Override
    public Encadrant toEntity(EncadrantDTO encadrantDTO) {
        if ( encadrantDTO == null ) {
            return null;
        }

        Encadrant encadrant = new Encadrant();

        encadrant.setEntreprise( encadrantDTOToEntreprise( encadrantDTO ) );
        encadrant.setUser( encadrantDTOToUtilisateur( encadrantDTO ) );
        encadrant.setEmail( encadrantDTO.getEmail() );
        encadrant.setIdEncadrant( encadrantDTO.getIdEncadrant() );
        encadrant.setMotDePasse( encadrantDTO.getMotDePasse() );
        encadrant.setNom( encadrantDTO.getNom() );
        encadrant.setPrenom( encadrantDTO.getPrenom() );
        encadrant.setTelephone( encadrantDTO.getTelephone() );

        return encadrant;
    }

    @Override
    public EntrepriseDTO toDto(Entreprise entreprise) {
        if ( entreprise == null ) {
            return null;
        }

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();

        entrepriseDTO.setAdresseEntreprise( entreprise.getAdresseEntreprise() );
        entrepriseDTO.setDescription( entreprise.getDescription() );
        entrepriseDTO.setDomaineEntreprise( entreprise.getDomaineEntreprise() );
        entrepriseDTO.setIdEntreprise( entreprise.getIdEntreprise() );
        byte[] logo = entreprise.getLogo();
        if ( logo != null ) {
            entrepriseDTO.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        entrepriseDTO.setNomEntreprise( entreprise.getNomEntreprise() );
        entrepriseDTO.setTelephoneFix( entreprise.getTelephoneFix() );
        entrepriseDTO.setVilleEntreprise( entreprise.getVilleEntreprise() );

        return entrepriseDTO;
    }

    @Override
    public Entreprise toEntity(EntrepriseDTO entrepriseDTO) {
        if ( entrepriseDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setAdresseEntreprise( entrepriseDTO.getAdresseEntreprise() );
        entreprise.setDescription( entrepriseDTO.getDescription() );
        entreprise.setDomaineEntreprise( entrepriseDTO.getDomaineEntreprise() );
        entreprise.setIdEntreprise( entrepriseDTO.getIdEntreprise() );
        byte[] logo = entrepriseDTO.getLogo();
        if ( logo != null ) {
            entreprise.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        entreprise.setNomEntreprise( entrepriseDTO.getNomEntreprise() );
        entreprise.setTelephoneFix( entrepriseDTO.getTelephoneFix() );
        entreprise.setVilleEntreprise( entrepriseDTO.getVilleEntreprise() );

        return entreprise;
    }

    @Override
    public CompteEcoleDTO toDto(CompteEcole compteEcole) {
        if ( compteEcole == null ) {
            return null;
        }

        CompteEcoleDTO compteEcoleDTO = new CompteEcoleDTO();

        compteEcoleDTO.setEcoleId( compteEcoleEcoleIdEcole( compteEcole ) );
        compteEcoleDTO.setUserId( compteEcoleUserId( compteEcole ) );
        compteEcoleDTO.setEmail( compteEcole.getEmail() );
        compteEcoleDTO.setIdCompte( compteEcole.getIdCompte() );
        compteEcoleDTO.setMotDePasse( compteEcole.getMotDePasse() );
        compteEcoleDTO.setNom( compteEcole.getNom() );
        compteEcoleDTO.setPrenom( compteEcole.getPrenom() );
        compteEcoleDTO.setTelephone( compteEcole.getTelephone() );

        return compteEcoleDTO;
    }

    @Override
    public CompteEcole toEntity(CompteEcoleDTO compteEcoleDTO) {
        if ( compteEcoleDTO == null ) {
            return null;
        }

        CompteEcole compteEcole = new CompteEcole();

        compteEcole.setEcole( compteEcoleDTOToEcole( compteEcoleDTO ) );
        compteEcole.setUser( compteEcoleDTOToUtilisateur( compteEcoleDTO ) );
        compteEcole.setEmail( compteEcoleDTO.getEmail() );
        compteEcole.setIdCompte( compteEcoleDTO.getIdCompte() );
        compteEcole.setMotDePasse( compteEcoleDTO.getMotDePasse() );
        compteEcole.setNom( compteEcoleDTO.getNom() );
        compteEcole.setPrenom( compteEcoleDTO.getPrenom() );
        compteEcole.setTelephone( compteEcoleDTO.getTelephone() );

        return compteEcole;
    }

    @Override
    public CompteEntrepriseDTO toDto(CompteEntreprise compteEntreprise) {
        if ( compteEntreprise == null ) {
            return null;
        }

        CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();

        compteEntrepriseDTO.setEntrepriseId( compteEntrepriseEntrepriseIdEntreprise( compteEntreprise ) );
        compteEntrepriseDTO.setUserId( compteEntrepriseUserId( compteEntreprise ) );
        compteEntrepriseDTO.setEmail( compteEntreprise.getEmail() );
        compteEntrepriseDTO.setIdCompte( compteEntreprise.getIdCompte() );
        compteEntrepriseDTO.setMotDePasse( compteEntreprise.getMotDePasse() );
        compteEntrepriseDTO.setNom( compteEntreprise.getNom() );
        compteEntrepriseDTO.setPrenom( compteEntreprise.getPrenom() );
        compteEntrepriseDTO.setTelephone( compteEntreprise.getTelephone() );

        return compteEntrepriseDTO;
    }

    @Override
    public CompteEntreprise toEntity(CompteEntrepriseDTO compteEntrepriseDTO) {
        if ( compteEntrepriseDTO == null ) {
            return null;
        }

        CompteEntreprise compteEntreprise = new CompteEntreprise();

        compteEntreprise.setEntreprise( compteEntrepriseDTOToEntreprise( compteEntrepriseDTO ) );
        compteEntreprise.setUser( compteEntrepriseDTOToUtilisateur( compteEntrepriseDTO ) );
        compteEntreprise.setEmail( compteEntrepriseDTO.getEmail() );
        compteEntreprise.setIdCompte( compteEntrepriseDTO.getIdCompte() );
        compteEntreprise.setMotDePasse( compteEntrepriseDTO.getMotDePasse() );
        compteEntreprise.setNom( compteEntrepriseDTO.getNom() );
        compteEntreprise.setPrenom( compteEntrepriseDTO.getPrenom() );
        compteEntreprise.setTelephone( compteEntrepriseDTO.getTelephone() );

        return compteEntreprise;
    }

    @Override
    public EntretienDTO toDto(Entretien entretien) {
        if ( entretien == null ) {
            return null;
        }

        EntretienDTO entretienDTO = new EntretienDTO();

        entretienDTO.setOffreId( entretienOffreIdOffre( entretien ) );
        entretienDTO.setEtudiantId( entretienEtudiantIdEtu( entretien ) );
        entretienDTO.setAdresse( entretien.getAdresse() );
        entretienDTO.setDateEntretien( entretien.getDateEntretien() );
        entretienDTO.setDuree( entretien.getDuree() );
        entretienDTO.setEtat( entretien.getEtat() );
        entretienDTO.setIdEntretien( entretien.getIdEntretien() );
        entretienDTO.setLien( entretien.getLien() );
        entretienDTO.setResultat( entretien.getResultat() );

        return entretienDTO;
    }

    @Override
    public Entretien toEntity(EntretienDTO entretienDTO) {
        if ( entretienDTO == null ) {
            return null;
        }

        Entretien entretien = new Entretien();

        entretien.setOffre( entretienDTOToOffre( entretienDTO ) );
        entretien.setEtudiant( entretienDTOToEtudiant( entretienDTO ) );
        entretien.setAdresse( entretienDTO.getAdresse() );
        entretien.setDateEntretien( entretienDTO.getDateEntretien() );
        entretien.setDuree( entretienDTO.getDuree() );
        entretien.setEtat( entretienDTO.getEtat() );
        entretien.setIdEntretien( entretienDTO.getIdEntretien() );
        entretien.setLien( entretienDTO.getLien() );
        entretien.setResultat( entretienDTO.getResultat() );

        return entretien;
    }

    @Override
    public EvaluationDTO toDto(Evaluation evaluation) {
        if ( evaluation == null ) {
            return null;
        }

        EvaluationDTO evaluationDTO = new EvaluationDTO();

        evaluationDTO.setEncadrantId( evaluationEncadrantIdEncadrant( evaluation ) );
        evaluationDTO.setStageId( evaluationStageIdStage( evaluation ) );
        evaluationDTO.setCommentaire( evaluation.getCommentaire() );
        evaluationDTO.setCompetances( evaluation.getCompetances() );
        evaluationDTO.setIdEvaluation( evaluation.getIdEvaluation() );
        evaluationDTO.setNote( evaluation.getNote() );

        return evaluationDTO;
    }

    @Override
    public Evaluation toEntity(EvaluationDTO evaluationDTO) {
        if ( evaluationDTO == null ) {
            return null;
        }

        Evaluation evaluation = new Evaluation();

        evaluation.setEncadrant( evaluationDTOToEncadrant( evaluationDTO ) );
        evaluation.setStage( evaluationDTOToStage( evaluationDTO ) );
        evaluation.setCommentaire( evaluationDTO.getCommentaire() );
        evaluation.setCompetances( evaluationDTO.getCompetances() );
        evaluation.setIdEvaluation( evaluationDTO.getIdEvaluation() );
        evaluation.setNote( evaluationDTO.getNote() );

        return evaluation;
    }

    private Long adminUserId(Admin admin) {
        if ( admin == null ) {
            return null;
        }
        Utilisateur user = admin.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Utilisateur adminDTOToUtilisateur(AdminDTO adminDTO) {
        if ( adminDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( adminDTO.getUserId() );

        return utilisateur;
    }

    private Long filiereEcoleIdEcole(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }
        Ecole ecole = filiere.getEcole();
        if ( ecole == null ) {
            return null;
        }
        Long idEcole = ecole.getIdEcole();
        if ( idEcole == null ) {
            return null;
        }
        return idEcole;
    }

    protected Ecole filiereDTOToEcole(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( filiereDTO.getEcoleId() );

        return ecole;
    }

    private Long chefDeFiliereEcoleIdEcole(ChefDeFiliere chefDeFiliere) {
        if ( chefDeFiliere == null ) {
            return null;
        }
        Ecole ecole = chefDeFiliere.getEcole();
        if ( ecole == null ) {
            return null;
        }
        Long idEcole = ecole.getIdEcole();
        if ( idEcole == null ) {
            return null;
        }
        return idEcole;
    }

    private Long chefDeFiliereFiliereIdFiliere(ChefDeFiliere chefDeFiliere) {
        if ( chefDeFiliere == null ) {
            return null;
        }
        Filiere filiere = chefDeFiliere.getFiliere();
        if ( filiere == null ) {
            return null;
        }
        Long idFiliere = filiere.getIdFiliere();
        if ( idFiliere == null ) {
            return null;
        }
        return idFiliere;
    }

    private Long chefDeFiliereUserId(ChefDeFiliere chefDeFiliere) {
        if ( chefDeFiliere == null ) {
            return null;
        }
        Utilisateur user = chefDeFiliere.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Ecole chefDeFiliereDTOToEcole(ChefDeFiliereDTO chefDeFiliereDTO) {
        if ( chefDeFiliereDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( chefDeFiliereDTO.getEcoleId() );

        return ecole;
    }

    protected Filiere chefDeFiliereDTOToFiliere(ChefDeFiliereDTO chefDeFiliereDTO) {
        if ( chefDeFiliereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setIdFiliere( chefDeFiliereDTO.getFiliereId() );

        return filiere;
    }

    protected Utilisateur chefDeFiliereDTOToUtilisateur(ChefDeFiliereDTO chefDeFiliereDTO) {
        if ( chefDeFiliereDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( chefDeFiliereDTO.getUserId() );

        return utilisateur;
    }

    private Long etudiantEcoleIdEcole(Etudiant etudiant) {
        if ( etudiant == null ) {
            return null;
        }
        Ecole ecole = etudiant.getEcole();
        if ( ecole == null ) {
            return null;
        }
        Long idEcole = ecole.getIdEcole();
        if ( idEcole == null ) {
            return null;
        }
        return idEcole;
    }

    private Long etudiantFiliereIdFiliere(Etudiant etudiant) {
        if ( etudiant == null ) {
            return null;
        }
        Filiere filiere = etudiant.getFiliere();
        if ( filiere == null ) {
            return null;
        }
        Long idFiliere = filiere.getIdFiliere();
        if ( idFiliere == null ) {
            return null;
        }
        return idFiliere;
    }

    private Long etudiantUserId(Etudiant etudiant) {
        if ( etudiant == null ) {
            return null;
        }
        Utilisateur user = etudiant.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Ecole etudiantDTOToEcole(EtudiantDTO etudiantDTO) {
        if ( etudiantDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( etudiantDTO.getEcoleId() );

        return ecole;
    }

    protected Filiere etudiantDTOToFiliere(EtudiantDTO etudiantDTO) {
        if ( etudiantDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setIdFiliere( etudiantDTO.getFiliereId() );

        return filiere;
    }

    protected Utilisateur etudiantDTOToUtilisateur(EtudiantDTO etudiantDTO) {
        if ( etudiantDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( etudiantDTO.getUserId() );

        return utilisateur;
    }

    private Long stageEtudiantIdEtu(Stage stage) {
        if ( stage == null ) {
            return null;
        }
        Etudiant etudiant = stage.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        Long idEtu = etudiant.getIdEtu();
        if ( idEtu == null ) {
            return null;
        }
        return idEtu;
    }

    private Long stageOffreIdOffre(Stage stage) {
        if ( stage == null ) {
            return null;
        }
        Offre offre = stage.getOffre();
        if ( offre == null ) {
            return null;
        }
        Long idOffre = offre.getIdOffre();
        if ( idOffre == null ) {
            return null;
        }
        return idOffre;
    }

    private Long stageEncadrantIdEncadrant(Stage stage) {
        if ( stage == null ) {
            return null;
        }
        Encadrant encadrant = stage.getEncadrant();
        if ( encadrant == null ) {
            return null;
        }
        Long idEncadrant = encadrant.getIdEncadrant();
        if ( idEncadrant == null ) {
            return null;
        }
        return idEncadrant;
    }

    protected Etudiant stageDTOToEtudiant(StageDTO stageDTO) {
        if ( stageDTO == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setIdEtu( stageDTO.getEtudiantId() );

        return etudiant;
    }

    protected Offre stageDTOToOffre(StageDTO stageDTO) {
        if ( stageDTO == null ) {
            return null;
        }

        Offre offre = new Offre();

        offre.setIdOffre( stageDTO.getOffreId() );

        return offre;
    }

    protected Encadrant stageDTOToEncadrant(StageDTO stageDTO) {
        if ( stageDTO == null ) {
            return null;
        }

        Encadrant encadrant = new Encadrant();

        encadrant.setIdEncadrant( stageDTO.getEncadrantId() );

        return encadrant;
    }

    private Long offreEntrepriseIdEntreprise(Offre offre) {
        if ( offre == null ) {
            return null;
        }
        Entreprise entreprise = offre.getEntreprise();
        if ( entreprise == null ) {
            return null;
        }
        Long idEntreprise = entreprise.getIdEntreprise();
        if ( idEntreprise == null ) {
            return null;
        }
        return idEntreprise;
    }

    private Long offreRhIdRh(Offre offre) {
        if ( offre == null ) {
            return null;
        }
        RH rh = offre.getRh();
        if ( rh == null ) {
            return null;
        }
        Long idRh = rh.getIdRh();
        if ( idRh == null ) {
            return null;
        }
        return idRh;
    }

    protected Entreprise offreDTOToEntreprise(OffreDTO offreDTO) {
        if ( offreDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setIdEntreprise( offreDTO.getEntrepriseId() );

        return entreprise;
    }

    protected RH offreDTOToRH(OffreDTO offreDTO) {
        if ( offreDTO == null ) {
            return null;
        }

        RH rH = new RH();

        rH.setIdRh( offreDTO.getRhId() );

        return rH;
    }

    private Long postulationEtudiantIdEtu(Postulation postulation) {
        if ( postulation == null ) {
            return null;
        }
        Etudiant etudiant = postulation.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        Long idEtu = etudiant.getIdEtu();
        if ( idEtu == null ) {
            return null;
        }
        return idEtu;
    }

    private Long postulationOffreIdOffre(Postulation postulation) {
        if ( postulation == null ) {
            return null;
        }
        Offre offre = postulation.getOffre();
        if ( offre == null ) {
            return null;
        }
        Long idOffre = offre.getIdOffre();
        if ( idOffre == null ) {
            return null;
        }
        return idOffre;
    }

    protected Etudiant postulationDTOToEtudiant(PostulationDTO postulationDTO) {
        if ( postulationDTO == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setIdEtu( postulationDTO.getEtudiantId() );

        return etudiant;
    }

    protected Offre postulationDTOToOffre(PostulationDTO postulationDTO) {
        if ( postulationDTO == null ) {
            return null;
        }

        Offre offre = new Offre();

        offre.setIdOffre( postulationDTO.getOffreId() );

        return offre;
    }

    private Long rhEntrepriseIdEntreprise(RH rH) {
        if ( rH == null ) {
            return null;
        }
        Entreprise entreprise = rH.getEntreprise();
        if ( entreprise == null ) {
            return null;
        }
        Long idEntreprise = entreprise.getIdEntreprise();
        if ( idEntreprise == null ) {
            return null;
        }
        return idEntreprise;
    }

    private Long rhUserId(RH rH) {
        if ( rH == null ) {
            return null;
        }
        Utilisateur user = rH.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Entreprise rHDTOToEntreprise(RHDTO rHDTO) {
        if ( rHDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setIdEntreprise( rHDTO.getEntrepriseId() );

        return entreprise;
    }

    protected Utilisateur rHDTOToUtilisateur(RHDTO rHDTO) {
        if ( rHDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( rHDTO.getUserId() );

        return utilisateur;
    }

    private Long coordinateurDeStageUserId(CoordinateurDeStage coordinateurDeStage) {
        if ( coordinateurDeStage == null ) {
            return null;
        }
        Utilisateur user = coordinateurDeStage.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long coordinateurDeStageEcoleIdEcole(CoordinateurDeStage coordinateurDeStage) {
        if ( coordinateurDeStage == null ) {
            return null;
        }
        Ecole ecole = coordinateurDeStage.getEcole();
        if ( ecole == null ) {
            return null;
        }
        Long idEcole = ecole.getIdEcole();
        if ( idEcole == null ) {
            return null;
        }
        return idEcole;
    }

    protected Ecole coordinateurDeStageDTOToEcole(CoordinateurDeStageDTO coordinateurDeStageDTO) {
        if ( coordinateurDeStageDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( coordinateurDeStageDTO.getEcoleId() );

        return ecole;
    }

    protected Utilisateur coordinateurDeStageDTOToUtilisateur(CoordinateurDeStageDTO coordinateurDeStageDTO) {
        if ( coordinateurDeStageDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( coordinateurDeStageDTO.getUserId() );

        return utilisateur;
    }

    private Long encadrantEntrepriseIdEntreprise(Encadrant encadrant) {
        if ( encadrant == null ) {
            return null;
        }
        Entreprise entreprise = encadrant.getEntreprise();
        if ( entreprise == null ) {
            return null;
        }
        Long idEntreprise = entreprise.getIdEntreprise();
        if ( idEntreprise == null ) {
            return null;
        }
        return idEntreprise;
    }

    private Long encadrantUserId(Encadrant encadrant) {
        if ( encadrant == null ) {
            return null;
        }
        Utilisateur user = encadrant.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Entreprise encadrantDTOToEntreprise(EncadrantDTO encadrantDTO) {
        if ( encadrantDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setIdEntreprise( encadrantDTO.getEntrepriseId() );

        return entreprise;
    }

    protected Utilisateur encadrantDTOToUtilisateur(EncadrantDTO encadrantDTO) {
        if ( encadrantDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( encadrantDTO.getUserId() );

        return utilisateur;
    }

    private Long compteEcoleEcoleIdEcole(CompteEcole compteEcole) {
        if ( compteEcole == null ) {
            return null;
        }
        Ecole ecole = compteEcole.getEcole();
        if ( ecole == null ) {
            return null;
        }
        Long idEcole = ecole.getIdEcole();
        if ( idEcole == null ) {
            return null;
        }
        return idEcole;
    }

    private Long compteEcoleUserId(CompteEcole compteEcole) {
        if ( compteEcole == null ) {
            return null;
        }
        Utilisateur user = compteEcole.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Ecole compteEcoleDTOToEcole(CompteEcoleDTO compteEcoleDTO) {
        if ( compteEcoleDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( compteEcoleDTO.getEcoleId() );

        return ecole;
    }

    protected Utilisateur compteEcoleDTOToUtilisateur(CompteEcoleDTO compteEcoleDTO) {
        if ( compteEcoleDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( compteEcoleDTO.getUserId() );

        return utilisateur;
    }

    private Long compteEntrepriseEntrepriseIdEntreprise(CompteEntreprise compteEntreprise) {
        if ( compteEntreprise == null ) {
            return null;
        }
        Entreprise entreprise = compteEntreprise.getEntreprise();
        if ( entreprise == null ) {
            return null;
        }
        Long idEntreprise = entreprise.getIdEntreprise();
        if ( idEntreprise == null ) {
            return null;
        }
        return idEntreprise;
    }

    private Long compteEntrepriseUserId(CompteEntreprise compteEntreprise) {
        if ( compteEntreprise == null ) {
            return null;
        }
        Utilisateur user = compteEntreprise.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Entreprise compteEntrepriseDTOToEntreprise(CompteEntrepriseDTO compteEntrepriseDTO) {
        if ( compteEntrepriseDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setIdEntreprise( compteEntrepriseDTO.getEntrepriseId() );

        return entreprise;
    }

    protected Utilisateur compteEntrepriseDTOToUtilisateur(CompteEntrepriseDTO compteEntrepriseDTO) {
        if ( compteEntrepriseDTO == null ) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId( compteEntrepriseDTO.getUserId() );

        return utilisateur;
    }

    private Long entretienOffreIdOffre(Entretien entretien) {
        if ( entretien == null ) {
            return null;
        }
        Offre offre = entretien.getOffre();
        if ( offre == null ) {
            return null;
        }
        Long idOffre = offre.getIdOffre();
        if ( idOffre == null ) {
            return null;
        }
        return idOffre;
    }

    private Long entretienEtudiantIdEtu(Entretien entretien) {
        if ( entretien == null ) {
            return null;
        }
        Etudiant etudiant = entretien.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        Long idEtu = etudiant.getIdEtu();
        if ( idEtu == null ) {
            return null;
        }
        return idEtu;
    }

    protected Offre entretienDTOToOffre(EntretienDTO entretienDTO) {
        if ( entretienDTO == null ) {
            return null;
        }

        Offre offre = new Offre();

        offre.setIdOffre( entretienDTO.getOffreId() );

        return offre;
    }

    protected Etudiant entretienDTOToEtudiant(EntretienDTO entretienDTO) {
        if ( entretienDTO == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setIdEtu( entretienDTO.getEtudiantId() );

        return etudiant;
    }

    private Long evaluationEncadrantIdEncadrant(Evaluation evaluation) {
        if ( evaluation == null ) {
            return null;
        }
        Encadrant encadrant = evaluation.getEncadrant();
        if ( encadrant == null ) {
            return null;
        }
        Long idEncadrant = encadrant.getIdEncadrant();
        if ( idEncadrant == null ) {
            return null;
        }
        return idEncadrant;
    }

    private Long evaluationStageIdStage(Evaluation evaluation) {
        if ( evaluation == null ) {
            return null;
        }
        Stage stage = evaluation.getStage();
        if ( stage == null ) {
            return null;
        }
        Long idStage = stage.getIdStage();
        if ( idStage == null ) {
            return null;
        }
        return idStage;
    }

    protected Encadrant evaluationDTOToEncadrant(EvaluationDTO evaluationDTO) {
        if ( evaluationDTO == null ) {
            return null;
        }

        Encadrant encadrant = new Encadrant();

        encadrant.setIdEncadrant( evaluationDTO.getEncadrantId() );

        return encadrant;
    }

    protected Stage evaluationDTOToStage(EvaluationDTO evaluationDTO) {
        if ( evaluationDTO == null ) {
            return null;
        }

        Stage stage = new Stage();

        stage.setIdStage( evaluationDTO.getStageId() );

        return stage;
    }
}
