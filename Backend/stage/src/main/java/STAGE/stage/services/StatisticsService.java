package STAGE.stage.services;

import STAGE.stage.models.Stage;


import java.util.Date;
import java.util.List;

public interface StatisticsService {

    // Admin Statistics
    long countEntreprises();
    long countEcoles();
    long countEtudiants();
    long countTotalOffers();
    long countEntretiens();
    long countOngoingStages(Date currentDate);
    long countTotalOpenOffers();

    // Ecole Statistics
    long countStudentsByEcole(Long idEcole);
    long countStudentsWithoutInternship(Long idEcole);
    long countStudentsWithInternshipByFiliere(Long filiereId);
    long countTotalStudentsByFiliere(Long filiereId);

    long countActiveInternshipsByCompanyId(Long companyId);

    double getInternshipPercentageByFiliere(Long filiereId);
    long countVisibleOffersByFiliere(Long filiereId);

    // Entreprise Statistics
    long countOpenOffersByCompanyId(Long companyId);

    long countWithInternshipFoundByFiliere(Long filiereId);

    long countTotalOffersByCompanyId(Long companyId);
    long countTotalInterviewsByCompanyId(Long companyId);
    long countTotalInternshipsByCompanyId(Long companyId);

    //count total stages by entrepriseId
    long countStagesByEntrepriseId(Long entrepriseId);

    long countTotalInterns();
    long countRhByCompanyId(Long companyId);
    long countSupervisorsByCompanyId(Long companyId);
    long countApplicantsPerOffer(Long offerId);

    // Encadrant Statistics
    long countOngoingInternshipsBySupervisor(Long supervisorId);
    long countTotalInternshipsBySupervisor(Long supervisorId);


    long countApplicationsByStudentId(Long studentId);

    long countInterviewsByStudentId(Long studentId);

    List<Stage> findInternshipsByFiliereId(Long filiereId);

    //count total users
    long countTotalPlatformUsers();

    // etudiant statistics
    // Count Postulations by Student ID
    long countPostulationsByEtudiantId(Long idEtu);

    // Count Internships by Student ID
    long countInternshipsByEtudiantId(Long idEtu);

    // count postulations per entreprise
    long countPostulationsByEntrepriseId(Long entrepriseId);

    //Student Statistics

}