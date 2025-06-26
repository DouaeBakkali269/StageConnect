package STAGE.stage.controllers;

import STAGE.stage.dtos.CompteEcoleDTO;
import STAGE.stage.services.CompteEcoleService;
import STAGE.stage.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compte-ecoles")
@RequiredArgsConstructor
public class CompteEcoleController {

    @Autowired
    private CompteEcoleService compteEcoleService;

    @Autowired
    private final StatisticsService statisticsService;


    /**
     * Create a new CompteEcole
     */
    @PostMapping
    public CompteEcoleDTO createCompteEcole(@RequestBody CompteEcoleDTO dto) {
        return compteEcoleService.createCompteEcole(dto);
    }

    /**
     * Update an existing CompteEcole by ID
     */
    @PutMapping("/{id}")
    public CompteEcoleDTO updateCompteEcole(@PathVariable Long id, @RequestBody CompteEcoleDTO dto) {
        return compteEcoleService.updateCompteEcole(id, dto);
    }

    /**
     * Get a CompteEcole by ID
     */
    @GetMapping("/{id}")
    public CompteEcoleDTO getCompteEcoleById(@PathVariable Long id) {
        return compteEcoleService.getCompteEcoleById(id);
    }

    /**
     * Get all CompteEcoles
     */
    @GetMapping
    public List<CompteEcoleDTO> getAllComptesEcole() {
        return compteEcoleService.getAllComptesEcole();
    }

    /**
     * Delete a CompteEcole by ID
     */
    @DeleteMapping("/{id}")
    public void deleteCompteEcole(@PathVariable Long id) {
        compteEcoleService.deleteCompteEcole(id);
    }

    @GetMapping("/by-ecole/{ecoleId}")
    public CompteEcoleDTO getCompteEcoleByEcoleId(@PathVariable Long ecoleId) {
        return compteEcoleService.getCompteEcoleByEcoleId(ecoleId);
    }





    // Ecole Statistics
    @GetMapping("/{idEcole}/students")
    public long countStudentsByEcole(@PathVariable Long idEcole) {
        return statisticsService.countStudentsByEcole(idEcole);
    }

    @GetMapping("/{idEcole}/students-without-internship")
    public long countStudentsWithoutInternship(@PathVariable Long idEcole) {
        return statisticsService.countStudentsWithoutInternship(idEcole);
    }


    @GetMapping("/students-with-internship/{filiereId}")
    public long countStudentsWithInternshipByFiliere(@PathVariable Long filiereId) {
        return statisticsService.countStudentsWithInternshipByFiliere(filiereId);
    }

    @GetMapping("/found-internship/{filiereId}")
    public long countInternshipFoundByFiliere(@PathVariable Long filiereId) {
        return statisticsService.countStudentsWithInternshipByFiliere(filiereId);
    }

    @GetMapping("/{filiereId}/total-students")
    public long countTotalStudentsByFiliere(@PathVariable Long filiereId) {
        return statisticsService.countTotalStudentsByFiliere(filiereId);
    }

    @GetMapping("/{filiereId}/visible-offers")
    public long countVisibleOffersByFiliere(@PathVariable Long filiereId) {
        return statisticsService.countVisibleOffersByFiliere(filiereId);
    }

    @GetMapping("/internship-percentage/{filiereId}")
    public double getInternshipPercentage(@PathVariable Long filiereId
    ) {
        return statisticsService.getInternshipPercentageByFiliere(filiereId);
    }


    // Disable CompteEcole
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> disableCompteEcole(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            compteEcoleService.disableCompteEcole(id, newPassword);
            return ResponseEntity.ok("CompteEcole with ID " + id + " has been disabled and its password updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Get by Iser ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getCompteEcoleIdByUserId(@PathVariable Long userId) {
        try {
            Long compteEcoleId = compteEcoleService.getCompteEcoleyUserId(userId);
            return ResponseEntity.ok(compteEcoleId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}