package STAGE.stage.controllers;

import STAGE.stage.dtos.CoordinateurDeStageDTO;
import STAGE.stage.services.CoordinateurDeDStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinateurs")
@RequiredArgsConstructor
public class CoordinateurDeStageController {

    private final CoordinateurDeDStageService coordinateurDeDStageService;

    // Create a new CoordinateurDeStage
    @PostMapping
    public ResponseEntity<CoordinateurDeStageDTO> createCoordinateurDeStage(@RequestBody CoordinateurDeStageDTO dto) {
        CoordinateurDeStageDTO created = coordinateurDeDStageService.createCoordinateurDeStage(dto);
        return ResponseEntity.ok(created);
    }

    // Update an existing CoordinateurDeStage
    @PutMapping("/{id}")
    public ResponseEntity<CoordinateurDeStageDTO> updateCoordinateurDeStage(
            @PathVariable Long id,
            @RequestBody CoordinateurDeStageDTO dto) {
        CoordinateurDeStageDTO updated = coordinateurDeDStageService.updateCoordinateurDeStage(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Get CoordinateurDeStage by ID
    @GetMapping("/{id}")
    public ResponseEntity<CoordinateurDeStageDTO> getCoordinateurDeStageById(@PathVariable Long id) {
        CoordinateurDeStageDTO coordinateur = coordinateurDeDStageService.getCoordinateurDeStageById(id);
        return ResponseEntity.ok(coordinateur);
    }

    // Get All CoordinateursDeStage
    @GetMapping
    public ResponseEntity<List<CoordinateurDeStageDTO>> getAllCoordinateursDeStage() {
        List<CoordinateurDeStageDTO> coordinateurs = coordinateurDeDStageService.getAllCoordinateursDeStage();
        return ResponseEntity.ok(coordinateurs);
    }

    // Delete CoordinateurDeStage by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinateurDeStage(@PathVariable Long id) {
        coordinateurDeDStageService.deleteCoordinateurDeStage(id);
        return ResponseEntity.noContent().build();
    }

    // Get CoordinateurDeStage by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getCoordinateurDeStageIdByUserId(@PathVariable Long userId) {
        try {
            Long coordinateurDeStageId = coordinateurDeDStageService.getCoordinateurDeStageIdIdByUserId(userId);
            return ResponseEntity.ok(coordinateurDeStageId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/by-ecole/{ecoleId}")
    public ResponseEntity<List<CoordinateurDeStageDTO>> getCoordinateursByEcoleId(@PathVariable Long ecoleId) {
        List<CoordinateurDeStageDTO> coordinateurs = coordinateurDeDStageService.getCoordinateursByEcoleId(ecoleId);
        return ResponseEntity.ok(coordinateurs);
    }
}