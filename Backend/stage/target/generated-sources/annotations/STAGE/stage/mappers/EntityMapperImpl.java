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
    date = "2025-06-25T20:02:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
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
        adminDTO.setId( admin.getId() );
        adminDTO.setNomAd( admin.getNomAd() );
        adminDTO.setPrenomAd( admin.getPrenomAd() );
        adminDTO.setEmailAd( admin.getEmailAd() );
        adminDTO.setMotDePasse( admin.getMotDePasse() );
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
        admin.setId( adminDTO.getId() );
        admin.setNomAd( adminDTO.getNomAd() );
        admin.setPrenomAd( adminDTO.getPrenomAd() );
        admin.setEmailAd( adminDTO.getEmailAd() );
        admin.setMotDePasse( adminDTO.getMotDePasse() );
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
        filiereDTO.setIdFiliere( filiere.getIdFiliere() );
        filiereDTO.setNomFiliere( filiere.getNomFiliere() );
        filiereDTO.setAbrvFiliere( filiere.getAbrvFiliere() );

        return filiereDTO;
    }

    @Override
    public Filiere toEntity(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setEcole( filiereDTOToEcole( filiereDTO ) );
        filiere.setIdFiliere( filiereDTO.getIdFiliere() );
        filiere.setNomFiliere( filiereDTO.getNomFiliere() );
        filiere.setAbrvFiliere( filiereDTO.getAbrvFiliere() );

        return filiere;
    }

    @Override
    public EcoleDTO toDto(Ecole ecole) {
        if ( ecole == null ) {
            return null;
        }

        EcoleDTO ecoleDTO = new EcoleDTO();

        ecoleDTO.setIdEcole( ecole.getIdEcole() );
        ecoleDTO.setNomEcole( ecole.getNomEcole() );
        ecoleDTO.setVilleEcole( ecole.getVilleEcole() );
        ecoleDTO.setAdresseEcole( ecole.getAdresseEcole() );
        ecoleDTO.setDescription( ecole.getDescription() );
        byte[] logo = ecole.getLogo();
        if ( logo != null ) {
            ecoleDTO.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        ecoleDTO.setTelephoneFix( ecole.getTelephoneFix() );
        ecoleDTO.setTypeEcole( ecole.getTypeEcole() );
        ecoleDTO.setDomaineEcole( ecole.getDomaineEcole() );

        return ecoleDTO;
    }

    @Override
    public Ecole toEntity(EcoleDTO ecoleDTO) {
        if ( ecoleDTO == null ) {
            return null;
        }

        Ecole ecole = new Ecole();

        ecole.setIdEcole( ecoleDTO.getIdEcole() );
        ecole.setNomEcole( ecoleDTO.getNomEcole() );
        ecole.setVilleEcole( ecoleDTO.getVilleEcole() );
        ecole.setAdresseEcole( ecoleDTO.getAdresseEcole() );
        byte[] logo = ecoleDTO.getLogo();
        if ( logo != null ) {
            ecole.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        ecole.setDescription( ecoleDTO.getDescription() );
        ecole.setTelephoneFix( ecoleDTO.getTelephoneFix() );
        ecole.setTypeEcole( ecoleDTO.getTypeEcole() );
        ecole.setDomaineEcole( ecoleDTO.getDomaineEcole() );

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
        chefDeFiliereDTO.setIdCf( chefDeFiliere.getIdCf() );
        chefDeFiliereDTO.setNom( chefDeFiliere.getNom() );
        chefDeFiliereDTO.setPrenom( chefDeFiliere.getPrenom() );
        chefDeFiliereDTO.setEmail( chefDeFiliere.getEmail() );
        chefDeFiliereDTO.setMotDePasse( chefDeFiliere.getMotDePasse() );
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
        chefDeFiliere.setIdCf( chefDeFiliereDTO.getIdCf() );
        chefDeFiliere.setNom( chefDeFiliereDTO.getNom() );
        chefDeFiliere.setPrenom( chefDeFiliereDTO.getPrenom() );
        chefDeFiliere.setEmail( chefDeFiliereDTO.getEmail() );
        chefDeFiliere.setMotDePasse( chefDeFiliereDTO.getMotDePasse() );
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
        etudiantDTO.setIdEtu( etudiant.getIdEtu() );
        etudiantDTO.setNom( etudiant.getNom() );
        etudiantDTO.setPrenom( etudiant.getPrenom() );
        etudiantDTO.setTel( etudiant.getTel() );
        etudiantDTO.setEmail( etudiant.getEmail() );
        etudiantDTO.setMotDePasse( etudiant.getMotDePasse() );
        etudiantDTO.setCodeEtu( etudiant.getCodeEtu() );
        byte[] photoProfil = etudiant.getPhotoProfil();
        if ( photoProfil != null ) {
            etudiantDTO.setPhotoProfil( Arrays.copyOf( photoProfil, photoProfil.length ) );
        }
        byte[] photoCouverture = etudiant.getPhotoCouverture();
        if ( photoCouverture != null ) {
            etudiantDTO.setPhotoCouverture( Arrays.copyOf( photoCouverture, photoCouverture.length ) );
        }
        etudiantDTO.setStatutEtudiant( etudiant.getStatutEtudiant() );

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
        etudiant.setIdEtu( etudiantDTO.getIdEtu() );
        etudiant.setNom( etudiantDTO.getNom() );
        etudiant.setPrenom( etudiantDTO.getPrenom() );
        etudiant.setTel( etudiantDTO.getTel() );
        etudiant.setEmail( etudiantDTO.getEmail() );
        etudiant.setMotDePasse( etudiantDTO.getMotDePasse() );
        etudiant.setCodeEtu( etudiantDTO.getCodeEtu() );
        byte[] photoProfil = etudiantDTO.getPhotoProfil();
        if ( photoProfil != null ) {
            etudiant.setPhotoProfil( Arrays.copyOf( photoProfil, photoProfil.length ) );
        }
        byte[] photoCouverture = etudiantDTO.getPhotoCouverture();
        if ( photoCouverture != null ) {
            etudiant.setPhotoCouverture( Arrays.copyOf( photoCouverture, photoCouverture.length ) );
        }
        etudiant.setStatutEtudiant( etudiantDTO.getStatutEtudiant() );

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
        stageDTO.setIdStage( stage.getIdStage() );
        stageDTO.setTitre( stage.getTitre() );
        stageDTO.setDescription( stage.getDescription() );
        stageDTO.setDateDebut( stage.getDateDebut() );
        stageDTO.setDateFin( stage.getDateFin() );
        stageDTO.setDuree( stage.getDuree() );
        stageDTO.setLocalisation( stage.getLocalisation() );
        stageDTO.setMontantRemuneration( stage.getMontantRemuneration() );
        stageDTO.setStatut( stage.getStatut() );
        stageDTO.setType( stage.getType() );
        byte[] conventionDeStage = stage.getConventionDeStage();
        if ( conventionDeStage != null ) {
            stageDTO.setConventionDeStage( Arrays.copyOf( conventionDeStage, conventionDeStage.length ) );
        }

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
        stage.setIdStage( stageDTO.getIdStage() );
        stage.setTitre( stageDTO.getTitre() );
        stage.setDescription( stageDTO.getDescription() );
        stage.setDateDebut( stageDTO.getDateDebut() );
        stage.setDateFin( stageDTO.getDateFin() );
        stage.setDuree( stageDTO.getDuree() );
        stage.setLocalisation( stageDTO.getLocalisation() );
        stage.setMontantRemuneration( stageDTO.getMontantRemuneration() );
        stage.setStatut( stageDTO.getStatut() );
        stage.setType( stageDTO.getType() );
        byte[] conventionDeStage = stageDTO.getConventionDeStage();
        if ( conventionDeStage != null ) {
            stage.setConventionDeStage( Arrays.copyOf( conventionDeStage, conventionDeStage.length ) );
        }
        byte[] attestationDeStage = stageDTO.getAttestationDeStage();
        if ( attestationDeStage != null ) {
            stage.setAttestationDeStage( Arrays.copyOf( attestationDeStage, attestationDeStage.length ) );
        }

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
        offreDTO.setIdOffre( offre.getIdOffre() );
        offreDTO.setObjetOffre( offre.getObjetOffre() );
        offreDTO.setDescriptionOffre( offre.getDescriptionOffre() );
        offreDTO.setDateLancement( offre.getDateLancement() );
        offreDTO.setDateLimite( offre.getDateLimite() );
        offreDTO.setPosteOffre( offre.getPosteOffre() );
        offreDTO.setDureeStage( offre.getDureeStage() );
        offreDTO.setModeOffre( offre.getModeOffre() );
        offreDTO.setRemuneration( offre.getRemuneration() );
        offreDTO.setTypeStageOffre( offre.getTypeStageOffre() );
        offreDTO.setNiveauRequisOffre( offre.getNiveauRequisOffre() );

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
        offre.setIdOffre( offreDTO.getIdOffre() );
        offre.setObjetOffre( offreDTO.getObjetOffre() );
        offre.setDescriptionOffre( offreDTO.getDescriptionOffre() );
        offre.setDateLancement( offreDTO.getDateLancement() );
        offre.setDateLimite( offreDTO.getDateLimite() );
        offre.setPosteOffre( offreDTO.getPosteOffre() );
        offre.setDureeStage( offreDTO.getDureeStage() );
        offre.setModeOffre( offreDTO.getModeOffre() );
        offre.setRemuneration( offreDTO.getRemuneration() );
        offre.setTypeStageOffre( offreDTO.getTypeStageOffre() );
        offre.setNiveauRequisOffre( offreDTO.getNiveauRequisOffre() );

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
        postulationDTO.setId( postulation.getId() );
        postulationDTO.setEtatPostulation( postulation.getEtatPostulation() );
        byte[] cv = postulation.getCv();
        if ( cv != null ) {
            postulationDTO.setCv( Arrays.copyOf( cv, cv.length ) );
        }
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
        postulation.setId( postulationDTO.getId() );
        byte[] cv = postulationDTO.getCv();
        if ( cv != null ) {
            postulation.setCv( Arrays.copyOf( cv, cv.length ) );
        }
        byte[] lettreMotivation = postulationDTO.getLettreMotivation();
        if ( lettreMotivation != null ) {
            postulation.setLettreMotivation( Arrays.copyOf( lettreMotivation, lettreMotivation.length ) );
        }
        postulation.setEtatPostulation( postulationDTO.getEtatPostulation() );

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
        rHDTO.setIdRh( rh.getIdRh() );
        rHDTO.setNom( rh.getNom() );
        rHDTO.setPrenom( rh.getPrenom() );
        rHDTO.setEmail( rh.getEmail() );
        rHDTO.setMotDePasse( rh.getMotDePasse() );
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
        rH.setIdRh( rhDTO.getIdRh() );
        rH.setNom( rhDTO.getNom() );
        rH.setPrenom( rhDTO.getPrenom() );
        rH.setEmail( rhDTO.getEmail() );
        rH.setMotDePasse( rhDTO.getMotDePasse() );
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
        coordinateurDeStageDTO.setIdCs( coordinateurDeStage.getIdCs() );
        coordinateurDeStageDTO.setNom( coordinateurDeStage.getNom() );
        coordinateurDeStageDTO.setPrenom( coordinateurDeStage.getPrenom() );
        coordinateurDeStageDTO.setEmail( coordinateurDeStage.getEmail() );
        coordinateurDeStageDTO.setMotDePasse( coordinateurDeStage.getMotDePasse() );
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
        coordinateurDeStage.setIdCs( coordinateurDeStageDTO.getIdCs() );
        coordinateurDeStage.setNom( coordinateurDeStageDTO.getNom() );
        coordinateurDeStage.setPrenom( coordinateurDeStageDTO.getPrenom() );
        coordinateurDeStage.setEmail( coordinateurDeStageDTO.getEmail() );
        coordinateurDeStage.setMotDePasse( coordinateurDeStageDTO.getMotDePasse() );
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
        encadrantDTO.setIdEncadrant( encadrant.getIdEncadrant() );
        encadrantDTO.setNom( encadrant.getNom() );
        encadrantDTO.setPrenom( encadrant.getPrenom() );
        encadrantDTO.setEmail( encadrant.getEmail() );
        encadrantDTO.setMotDePasse( encadrant.getMotDePasse() );
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
        encadrant.setIdEncadrant( encadrantDTO.getIdEncadrant() );
        encadrant.setNom( encadrantDTO.getNom() );
        encadrant.setPrenom( encadrantDTO.getPrenom() );
        encadrant.setEmail( encadrantDTO.getEmail() );
        encadrant.setMotDePasse( encadrantDTO.getMotDePasse() );
        encadrant.setTelephone( encadrantDTO.getTelephone() );

        return encadrant;
    }

    @Override
    public EntrepriseDTO toDto(Entreprise entreprise) {
        if ( entreprise == null ) {
            return null;
        }

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();

        entrepriseDTO.setIdEntreprise( entreprise.getIdEntreprise() );
        entrepriseDTO.setNomEntreprise( entreprise.getNomEntreprise() );
        entrepriseDTO.setDescription( entreprise.getDescription() );
        entrepriseDTO.setVilleEntreprise( entreprise.getVilleEntreprise() );
        entrepriseDTO.setAdresseEntreprise( entreprise.getAdresseEntreprise() );
        entrepriseDTO.setTelephoneFix( entreprise.getTelephoneFix() );
        entrepriseDTO.setDomaineEntreprise( entreprise.getDomaineEntreprise() );
        byte[] logo = entreprise.getLogo();
        if ( logo != null ) {
            entrepriseDTO.setLogo( Arrays.copyOf( logo, logo.length ) );
        }

        return entrepriseDTO;
    }

    @Override
    public Entreprise toEntity(EntrepriseDTO entrepriseDTO) {
        if ( entrepriseDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setIdEntreprise( entrepriseDTO.getIdEntreprise() );
        entreprise.setNomEntreprise( entrepriseDTO.getNomEntreprise() );
        entreprise.setDescription( entrepriseDTO.getDescription() );
        byte[] logo = entrepriseDTO.getLogo();
        if ( logo != null ) {
            entreprise.setLogo( Arrays.copyOf( logo, logo.length ) );
        }
        entreprise.setVilleEntreprise( entrepriseDTO.getVilleEntreprise() );
        entreprise.setAdresseEntreprise( entrepriseDTO.getAdresseEntreprise() );
        entreprise.setTelephoneFix( entrepriseDTO.getTelephoneFix() );
        entreprise.setDomaineEntreprise( entrepriseDTO.getDomaineEntreprise() );

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
        compteEcoleDTO.setIdCompte( compteEcole.getIdCompte() );
        compteEcoleDTO.setNom( compteEcole.getNom() );
        compteEcoleDTO.setPrenom( compteEcole.getPrenom() );
        compteEcoleDTO.setEmail( compteEcole.getEmail() );
        compteEcoleDTO.setMotDePasse( compteEcole.getMotDePasse() );
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
        compteEcole.setIdCompte( compteEcoleDTO.getIdCompte() );
        compteEcole.setNom( compteEcoleDTO.getNom() );
        compteEcole.setPrenom( compteEcoleDTO.getPrenom() );
        compteEcole.setEmail( compteEcoleDTO.getEmail() );
        compteEcole.setMotDePasse( compteEcoleDTO.getMotDePasse() );
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
        compteEntrepriseDTO.setIdCompte( compteEntreprise.getIdCompte() );
        compteEntrepriseDTO.setNom( compteEntreprise.getNom() );
        compteEntrepriseDTO.setPrenom( compteEntreprise.getPrenom() );
        compteEntrepriseDTO.setEmail( compteEntreprise.getEmail() );
        compteEntrepriseDTO.setMotDePasse( compteEntreprise.getMotDePasse() );
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
        compteEntreprise.setIdCompte( compteEntrepriseDTO.getIdCompte() );
        compteEntreprise.setNom( compteEntrepriseDTO.getNom() );
        compteEntreprise.setPrenom( compteEntrepriseDTO.getPrenom() );
        compteEntreprise.setEmail( compteEntrepriseDTO.getEmail() );
        compteEntreprise.setMotDePasse( compteEntrepriseDTO.getMotDePasse() );
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
        entretienDTO.setIdEntretien( entretien.getIdEntretien() );
        entretienDTO.setDateEntretien( entretien.getDateEntretien() );
        entretienDTO.setAdresse( entretien.getAdresse() );
        entretienDTO.setDuree( entretien.getDuree() );
        entretienDTO.setEtat( entretien.getEtat() );
        entretienDTO.setResultat( entretien.getResultat() );
        entretienDTO.setLien( entretien.getLien() );

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
        entretien.setIdEntretien( entretienDTO.getIdEntretien() );
        entretien.setDateEntretien( entretienDTO.getDateEntretien() );
        entretien.setAdresse( entretienDTO.getAdresse() );
        entretien.setDuree( entretienDTO.getDuree() );
        entretien.setEtat( entretienDTO.getEtat() );
        entretien.setResultat( entretienDTO.getResultat() );
        entretien.setLien( entretienDTO.getLien() );

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
        evaluationDTO.setIdEvaluation( evaluation.getIdEvaluation() );
        evaluationDTO.setNote( evaluation.getNote() );
        evaluationDTO.setCompetances( evaluation.getCompetances() );
        evaluationDTO.setCommentaire( evaluation.getCommentaire() );

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
        evaluation.setIdEvaluation( evaluationDTO.getIdEvaluation() );
        evaluation.setNote( evaluationDTO.getNote() );
        evaluation.setCompetances( evaluationDTO.getCompetances() );
        evaluation.setCommentaire( evaluationDTO.getCommentaire() );

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
