package STAGE.stage.services.implementation;

import STAGE.stage.controllers.DateConversionUtil;
import STAGE.stage.models.Stage;
import STAGE.stage.repositories.*;
import STAGE.stage.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    // Repositories Declaration
    private final EntrepriseRepository entrepriseRepository;
    private final EcoleRepository ecoleRepository;
    private final EtudiantRepository etudiantRepository;
    private final OffreRepository offreRepository;
    private final EntretienRepository entretienRepository;
    private final StageRepository stageRepository;
    private final RHRepository rhRepository;
    private final EncadrantRepository encadrantRepository;
    private final PostulationRepository postulationRepository;
    private final UserRepository userRepository;
    private final CompteEntrepriseRepository compteEntrepriseRepository;
    private final CompteEcoleRepository compteEcoleRepository;
    // Admin Statistics
    @Override
    public long countEntreprises() {
        return compteEntrepriseRepository.count();
    }

    @Override
    public long countEcoles() {
        return compteEcoleRepository.count();
    }

    @Override
    public long countEtudiants() {
        return etudiantRepository.count();
    }

    @Override
    public long countTotalOffers() {
        return offreRepository.count();
    }

    @Override
    public long countEntretiens() {
        return entretienRepository.count();
    }

    @Override
    public long countOngoingStages(Date currentDate) {
        return stageRepository.countByDateFinBefore(currentDate);
    }

    @Override
    public long countTotalOpenOffers() {
        LocalDate localDate = LocalDate.now();
        Date date = DateConversionUtil.convertToDate(localDate);
        return offreRepository.countOpenOffers(date);
    }


    // Ecole Statistics
    @Override
    public long countStudentsByEcole(Long idEcole) {
        return etudiantRepository.countByEcole_IdEcole(idEcole);
    }

    @Override
    public long countStudentsWithoutInternship(Long idEcole) {
        return stageRepository.countStudentsWithoutInternship(idEcole);
    }

    @Override
    public long countStudentsWithInternshipByFiliere(Long filiereId) {
        return stageRepository.countStudentsWithInternshipByFiliere(filiereId);
    }

    @Override
    public long countTotalStudentsByFiliere(Long filiereId) {
        return etudiantRepository.countByFiliere_IdFiliere(filiereId);
    }

    @Override
    public long countWithInternshipFoundByFiliere(Long filiereId) {
        return stageRepository.countWithInternshipFoundByFiliere(filiereId);
    }



    // Entreprise Statistics
    //Offre repo
    @Override
    public long countTotalOffersByCompanyId(Long companyId) {
        return offreRepository.countOffersByCompanyId(companyId);
    }

    @Override
    public long countOpenOffersByCompanyId(Long companyId) {
        return offreRepository.countOpenOffersByCompanyId(companyId);
    }

    @Override
    public long countVisibleOffersByFiliere(Long filiereId) {
        return offreRepository.countVisibleOffersByFiliere(filiereId);
    }

    @Override
    public long countTotalInterviewsByCompanyId(Long companyId) {
        return entretienRepository.countByEntrepriseIdEntreprise(companyId);
    }

    @Override
    public long countTotalInternshipsByCompanyId(Long companyId) {
        // Use the new repository method
        return stageRepository.countValidInternshipsByCompanyId(companyId);
    }

    @Override
    public long countRhByCompanyId(Long companyId) {
        return rhRepository.countByEntrepriseIdEntreprise(companyId);
    }

    @Override
    public long countSupervisorsByCompanyId(Long companyId) {
        return encadrantRepository.countByEntrepriseIdEntreprise(companyId);
    }

    @Override
    public long countApplicantsPerOffer(Long offerId) {
        return postulationRepository.countByOffreIdOffre(offerId);
    }

    // Encadrant Statistics
    @Override
    public long countOngoingInternshipsBySupervisor(Long supervisorId) {
        return stageRepository.countOngoingInternshipsBySupervisor(supervisorId);
    }

    @Override
    public long countTotalInternshipsBySupervisor(Long supervisorId) {
        return stageRepository.countByEncadrantIdEncadrant(supervisorId);
    }

    @Override
    public long countActiveInternshipsByCompanyId(Long companyId) {
        return stageRepository.countActiveInternshipsByCompanyId(companyId);
    }

    @Override
    public double getInternshipPercentageByFiliere(Long filiereId) {
        // Use the existing repository method to count students with internships
        long studentsWithInternships = this.countStudentsWithInternshipByFiliere(filiereId);

        // Use the second repository method to count total students in the filière
        long totalStudents = this.countTotalStudentsByFiliere(filiereId);

        // Handle cases where total students is 0 to avoid division by zero
        if (totalStudents == 0) {
            return 0.0;
        }

        // Compute and return the percentage
        return ((double) studentsWithInternships / totalStudents) * 100;
    }



    @Override
    public long countApplicationsByStudentId(Long studentId) {
        return postulationRepository.countApplicationsByStudentId(studentId);
    }
    @Override
    public long countInterviewsByStudentId(Long studentId) {
        LocalDate localDate = LocalDate.now();
        Date date = DateConversionUtil.convertToDate(localDate);
        return entretienRepository.countUpcomingEntretiensByEtudiantId(studentId,date);
    }

    @Override
    public List<Stage> findInternshipsByFiliereId(Long filiereId) {
        return stageRepository.findInternshipsByFiliereId(filiereId);
    }

    //count total stages by entrepriseId
    @Override
    public long countStagesByEntrepriseId(Long entrepriseId) {
        return stageRepository.countAllByEntrepriseId(entrepriseId);
    }

    //count internships offers
    @Override
    public long countTotalInterns() {
        return stageRepository.countTotalInterns();
    }

    //count total users
    @Override
    public long countTotalPlatformUsers() {
        return userRepository.countTotalUsers();
    }

    // etudiant statistics
    // Count Postulations by Student ID
    @Override
    public long countPostulationsByEtudiantId(Long idEtu) {
        return postulationRepository.countPostulationsByEtudiantId(idEtu);
    }

    // Count Internships by Student ID
    @Override
    public long countInternshipsByEtudiantId(Long idEtu) {
        return stageRepository.countInternshipsByEtudiantId(idEtu);
    }

    // count postulations per entreprise
    @Override
    public long countPostulationsByEntrepriseId(Long entrepriseId) {
        return postulationRepository.countPostulationsByEntrepriseId(entrepriseId);
    }


}