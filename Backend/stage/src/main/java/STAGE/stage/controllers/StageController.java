package STAGE.stage.controllers;

import STAGE.stage.dtos.StageDTO;
import STAGE.stage.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stages")
public class StageController {

    @Autowired
    private StageService stageService;

    @PostMapping
    public StageDTO createStage(@RequestBody StageDTO stageDTO) {
        return stageService.createStage(stageDTO);
    }

    @PutMapping("/{id}")
    public StageDTO updateStage(@PathVariable Long id, @RequestBody StageDTO stageDTO) {
        return stageService.updateStage(id, stageDTO);
    }

    @PutMapping("/{id}/status")
    public StageDTO updateStageStatus(@PathVariable Long id, @RequestParam String newStatus) {
        return stageService.updateStageStatus(id, newStatus);
    }

    @GetMapping
    public List<StageDTO> getAllStages() {
        return stageService.getAllStages();
    }

    @GetMapping("/{id}")
    public StageDTO getStageById(@PathVariable Long id) {
        return stageService.getStageById(id);
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public List<StageDTO> getStagesByEntrepriseId(@PathVariable Long entrepriseId) {
        return stageService.getStagesByEntrepriseId(entrepriseId);
    }

    @GetMapping("/status/{status}")
    public List<StageDTO> getStagesByStatus(@PathVariable String status) {
        return stageService.getStagesByStatus(status);
    }

    @DeleteMapping("/{id}")
    public void deleteStage(@PathVariable Long id) {
        stageService.deleteStage(id);
    }

    @GetMapping("/cf/{idCF}/to-validate ")
    public List<StageDTO> getStagesByCF(@PathVariable Long idCF) {
        return stageService.getStagesByCF(idCF);
    }

    @GetMapping("/validated/ecole/{ecoleId}")
    public ResponseEntity<List<StageDTO>> getValidatedStagesByEcoleId(@PathVariable Long ecoleId) {
        List<StageDTO> validatedStages = stageService.getValidatedStagesByEcoleId(ecoleId);
        return ResponseEntity.ok(validatedStages);
    }

    @GetMapping("/AValider/ecole/{ecoleId}")
    public ResponseEntity<List<StageDTO>> getAValidertagesByEcoleId(@PathVariable Long ecoleId) {
        List<StageDTO> validatedStages = stageService.getAValiderStagesByEcoleId(ecoleId);
        return ResponseEntity.ok(validatedStages);
    }

    @PutMapping("/set-status-delete-others/{etudiantId}/{stageId}")
    public ResponseEntity<String> setStatusAndDeleteRest(@PathVariable Long etudiantId, @PathVariable Long stageId) {
        stageService.setStatusAndDeleteRest(etudiantId, stageId);
        return ResponseEntity.ok("Stage status updated .");
    }
    @PutMapping("/set-status/{etudiantId}/{stageId}")
    public ResponseEntity<String> setStatutConfirmation(@PathVariable Long etudiantId, @PathVariable Long stageId) {
        stageService.setStatus(etudiantId, stageId);
        return ResponseEntity.ok("Stage status updated .");
    }
    @PutMapping("/set-status-cf/{etudiantId}/{stageId}")
    public ResponseEntity<String> setStatutRefus(@PathVariable Long etudiantId, @PathVariable Long stageId) {
        stageService.setStatuscf(etudiantId, stageId);
        return ResponseEntity.ok("Stage status updated .");
    }

    @GetMapping("/by-etudiant/{etudiantId}")
    public ResponseEntity<List<StageDTO>> getStagesByEtudiantId(@PathVariable Long etudiantId) {
        List<StageDTO> stages = stageService.getStagesByEtudiantId(etudiantId);
        return ResponseEntity.ok(stages);
    }

    // get stages by ecoleId
    @GetMapping("/by-ecole/{ecoleId}")
    public List<StageDTO> getStagesByEcoleId(@PathVariable Long ecoleId) {
        return stageService.findStagesByEcoleId(ecoleId);
    }

    //Convention de stage
    @PostMapping("/{stageId}/upload-convention")
    public ResponseEntity<String> uploadConventionDeStage(@PathVariable Long stageId, @RequestParam("file") MultipartFile file) {
        stageService.uploadConventionDeStage(stageId, file);
        return ResponseEntity.ok("Convention de stage téléversée avec succès");
    }

    @GetMapping("/{stageId}/download-convention")
    public ResponseEntity<Resource> downloadConventionDeStage(@PathVariable Long stageId) {
        Resource resource = stageService.downloadConventionDeStage(stageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=convention_de_stage.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    //attestation de stage
    // Upload attestationDeStage
    @PostMapping("/{id}/attestation")
    public ResponseEntity<String> uploadAttestationDeStage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        stageService.uploadAttestationDeStage(id, file);
        return ResponseEntity.ok("Attestation de stage uploaded successfully.");
    }
    // Download attestationDeStage
    @GetMapping("/{id}/attestation")
    public ResponseEntity<Resource> downloadAttestationDeStage(@PathVariable Long id) {
        Resource attestationDeStage = stageService.downloadAttestationDeStage(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // Adjust the media type based on your file type
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"attestation_de_stage.pdf\"")
                .body(attestationDeStage);
    }

}

