package STAGE.stage.controllers;

import STAGE.stage.dtos.EntretienDTO;
import STAGE.stage.services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entretiens")
public class EntretienController {

    @Autowired
    private EntretienService entretienService;

    @PostMapping
    public EntretienDTO createEntretien(@RequestBody EntretienDTO entretienDTO) {
        return entretienService.createEntretien(entretienDTO);
    }

    @PutMapping("/{id}")
    public EntretienDTO updateEntretien(@PathVariable Long id, @RequestBody EntretienDTO entretienDTO) {
        return entretienService.updateEntretien(id, entretienDTO);
    }

    @GetMapping
    public List<EntretienDTO> getAllEntretiens() {
        return entretienService.getAllEntretiens();
    }

    @GetMapping("/{id}")
    public EntretienDTO getEntretienById(@PathVariable Long id) {
        return entretienService.getEntretienById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEntretien(@PathVariable Long id) {
        entretienService.deleteEntretien(id);
    }
    @GetMapping("/by-entreprise/{entrepriseId}")
    public List<EntretienDTO> getEntretiensByEntrepriseId(@PathVariable Long entrepriseId) {
        return entretienService.getEntretiensByEntrepriseId(entrepriseId);
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<EntretienDTO>> getEntretiensByEtudiant(@PathVariable Long etudiantId) {
        List<EntretienDTO> entretiens = entretienService.getEntretiensByEtudiant(etudiantId);
        return ResponseEntity.ok(entretiens);
    }

}