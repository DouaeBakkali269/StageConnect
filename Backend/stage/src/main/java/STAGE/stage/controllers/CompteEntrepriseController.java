package STAGE.stage.controllers;

import STAGE.stage.dtos.CompteEntrepriseDTO;
import STAGE.stage.services.CompteEntrepriseService;
import STAGE.stage.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compte-entreprises")
@RequiredArgsConstructor
public class CompteEntrepriseController {

    @Autowired
    private CompteEntrepriseService compteEntrepriseService;

    @Autowired
    private final StatisticsService statisticsService;
    @PostMapping
    public CompteEntrepriseDTO createCompteEntreprise(@RequestBody CompteEntrepriseDTO dto) {
        return compteEntrepriseService.createCompteEntreprise(dto);
    }

    @PutMapping("/{id}")
    public CompteEntrepriseDTO updateCompteEntreprise(@PathVariable Long id, @RequestBody CompteEntrepriseDTO dto) {
        return compteEntrepriseService.updateCompteEntreprise(id, dto);
    }

    @GetMapping("/{id}")
    public CompteEntrepriseDTO getCompteEntrepriseById(@PathVariable Long id) {
        return compteEntrepriseService.getCompteEntrepriseById(id);
    }

    @GetMapping
    public List<CompteEntrepriseDTO> getAllComptesEntreprise() {
        return compteEntrepriseService.getAllComptesEntreprise();
    }

    @DeleteMapping("/{id}")
    public void deleteCompteEntreprise(@PathVariable Long id) {
        compteEntrepriseService.deleteCompteEntreprise(id);
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public CompteEntrepriseDTO getCompteEntrepriseByEntrepriseId(@PathVariable Long entrepriseId) {
        return compteEntrepriseService.getCompteEntrepriseByEntrepriseId(entrepriseId);
    }

    // Entreprise Statistics
    @GetMapping("/{companyId}/open-offers")
    public long countOpenOffersByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countOpenOffersByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/total-offers")
    public long countTotalOffersByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countTotalOffersByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/total-interviews")
    public long countTotalInterviewsByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countTotalInterviewsByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/total-internships-confirmed")
    public long countTotalInternshipsByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countTotalInternshipsByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/rh")
    public long countRhByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countRhByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/supervisors")
    public long countSupervisorsByCompanyId(@PathVariable Long companyId) {
        return statisticsService.countSupervisorsByCompanyId(companyId);
    }

    @GetMapping("/{entrepriseId}/total-internships")
    public long countStagesByEntrepriseId(@PathVariable Long entrepriseId) {
        return statisticsService.countStagesByEntrepriseId(entrepriseId);
    }
    @GetMapping("/countByEntreprise/{entrepriseId}")
    public ResponseEntity<Long> countPostulationsByEntreprise(@PathVariable Long entrepriseId) {
        long count = statisticsService.countPostulationsByEntrepriseId(entrepriseId);
        return ResponseEntity.ok(count);
    }


//    @GetMapping("/offers/{offerId}/applicants")
//    public long countApplicantsPerOffer(@PathVariable Long offerId) {
//        return statisticsService.countApplicantsPerOffer(offerId);
//    }

    // Disable compte entreprise
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> disableCompteEntreprise(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            compteEntrepriseService.disableCompteEntreprise(id, newPassword);
            return ResponseEntity.ok("CompteEntreprise with ID " + id + " has been disabled and its password updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getCompteEntrepriseIdByUserId(@PathVariable Long userId) {
        try {
            Long compteEntrepriseId = compteEntrepriseService.getCompteEntrepriseIdByUserId(userId);
            return ResponseEntity.ok(compteEntrepriseId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}