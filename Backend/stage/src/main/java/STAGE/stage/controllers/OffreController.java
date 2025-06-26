package STAGE.stage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import STAGE.stage.dtos.OffreDTO;
import STAGE.stage.models.Offre;
import STAGE.stage.services.OffreService;

import java.util.List;

@RestController
@RequestMapping("/api/offres")
@RequiredArgsConstructor
public class OffreController {
    private final OffreService offreService;

    @PostMapping
    public ResponseEntity<OffreDTO> createOffre(@RequestBody OffreDTO offreDTO) {
        OffreDTO createdOffre = offreService.createOffre(offreDTO);
        return new ResponseEntity<>(createdOffre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OffreDTO>> getAllOffres() {
        List<OffreDTO> offres = offreService.getAllOffres();
        return new ResponseEntity<>(offres, HttpStatus.OK);
    }

    @PutMapping("/{offreId}")
    public ResponseEntity<OffreDTO> updateOffre(@PathVariable Long offreId, @RequestBody OffreDTO offreDTO) {
        OffreDTO updatedOffre = offreService.updateOffre(offreId, offreDTO);
        return new ResponseEntity<>(updatedOffre, HttpStatus.OK);
    }
    // Récupérer les offres associées à un responsable RH
    @GetMapping("/rh/{rhId}")
    public ResponseEntity<List<OffreDTO>> getOffresByRH(@PathVariable Long rhId) {
        List<OffreDTO> offres = offreService.getOffresByRH(rhId);
        return new ResponseEntity<>(offres, HttpStatus.OK);
    }

    @GetMapping("/entreprise/{entrepriseId}")
    public List<OffreDTO> getOffresByEntreprise(@PathVariable Long entrepriseId) {
        return offreService.getOffresByEntreprise(entrepriseId);
    }

    /**
     * Delete an Offer by its ID.
     *
     * @param id the ID of the offer to delete
     * @return ResponseEntity indicating the result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        try {
            offreService.deleteOfferById(id);
            return ResponseEntity.ok("Offer with ID " + id + " has been deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getOffreById(@PathVariable Long id) {
        try {
            OffreDTO offreDTO = offreService.getOffreById(id);
            return new ResponseEntity<>(offreDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

