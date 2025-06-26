package STAGE.stage.controllers;

import STAGE.stage.dtos.EncadrantDTO;
import STAGE.stage.services.EncadrantService;
import STAGE.stage.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encadrants")
@RequiredArgsConstructor
public class EncadrantController {

    private final EncadrantService encadrantService;
    private final StatisticsService statisticsService;

    // Create a new Encadrant
    @PostMapping
    public ResponseEntity<EncadrantDTO> createEncadrant(@RequestBody EncadrantDTO dto) {
        EncadrantDTO created = encadrantService.createEncadrant(dto);
        return ResponseEntity.ok(created);
    }

    // Update an existing Encadrant
    @PutMapping("/{id}")
    public ResponseEntity<EncadrantDTO> updateEncadrant(
            @PathVariable Long id,
            @RequestBody EncadrantDTO dto) {
        EncadrantDTO updated = encadrantService.updateEncadrant(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Get Encadrant by ID
    @GetMapping("/{id}")
    public ResponseEntity<EncadrantDTO> getEncadrantById(@PathVariable Long id) {
        EncadrantDTO encadrant = encadrantService.getEncadrantById(id);
        return ResponseEntity.ok(encadrant);
    }

    // Get All Encadrants
    @GetMapping
    public ResponseEntity<List<EncadrantDTO>> getAllEncadrants() {
        List<EncadrantDTO> encadrants = encadrantService.getAllEncadrants();
        return ResponseEntity.ok(encadrants);
    }

    // Delete Encadrant by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncadrant(@PathVariable Long id) {
        encadrantService.deleteEncadrant(id);
        return ResponseEntity.noContent().build();
    }
    // Encadrant Statistics
    @GetMapping("/{supervisorId}/ongoing-internships")
    public long countOngoingInternshipsBySupervisor(@PathVariable Long supervisorId) {
        return statisticsService.countOngoingInternshipsBySupervisor(supervisorId);
    }

    @GetMapping("/{supervisorId}/total-internships")
    public long countTotalInternshipsBySupervisor(@PathVariable Long supervisorId) {
        return statisticsService.countTotalInternshipsBySupervisor(supervisorId);
    }

    @GetMapping("/company/{companyId}/active-internships")
    public long getActiveInternshipsByCompany(@PathVariable Long companyId) {
        return statisticsService.countActiveInternshipsByCompanyId(companyId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getEncadrantIdByUserId(@PathVariable Long userId) {
        try {
            Long encadrantId = encadrantService.getEncadrantIdByUserId(userId);
            return ResponseEntity.ok(encadrantId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public ResponseEntity<List<EncadrantDTO>> getEncadrantsByEntrepriseId(@PathVariable Long entrepriseId) {
        List<EncadrantDTO> encadrants = encadrantService.getEncadrantsByEntrepriseId(entrepriseId);
        return ResponseEntity.ok(encadrants);
    }
}