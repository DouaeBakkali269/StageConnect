package STAGE.stage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import STAGE.stage.dtos.EntrepriseDTO;
import STAGE.stage.services.EntrepriseService;
import STAGE.stage.models.Entreprise;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {

    @Autowired
    private EntrepriseService entrepriseService;
    @Autowired
    private STAGE.stage.repositories.EntrepriseRepository entrepriseRepository;
    @Autowired
    private STAGE.stage.mappers.EntityMapper entityMapper;


    @PostMapping
    public EntrepriseDTO createEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
        return entrepriseService.createEntreprise(entrepriseDTO);
    }

    @GetMapping("/{id}")
    public EntrepriseDTO getEntrepriseById(@PathVariable Long id) {
        return entrepriseService.getEntrepriseById(id);
    }

    @GetMapping
    public List<EntrepriseDTO> getAllEntreprises() {
        return entrepriseService.getAllEntreprises();
    }

    @PutMapping("/{id}")
    public EntrepriseDTO updateEntreprise(@PathVariable Long id, @RequestBody EntrepriseDTO entrepriseDTO) {
        return entrepriseService.updateEntreprise(id, entrepriseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEntreprise(@PathVariable Long id) {
        entrepriseService.deleteEntreprise(id);
    }

    // Endpoint to download photoProfil
    @GetMapping("/download/{entrepriseId}/logo")
    public ResponseEntity<Resource> downloadLogo(@PathVariable Long entrepriseId) {

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));

        byte[] logoData = entreprise.getLogo();
        if (logoData == null) {
            return ResponseEntity.noContent().build();
        }
        ByteArrayResource resource = new ByteArrayResource(logoData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logo.jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    // Créer un étudiant avec image
    @PutMapping("/upload/{idEntreprise}")
    public ResponseEntity<EntrepriseDTO> updateEntrepriseWithImage(
            @PathVariable("idEntreprise") Long idEntreprsie,
            @RequestParam("logo") MultipartFile logo){
        try {

            Entreprise entreprise = entrepriseRepository.findById(idEntreprsie)
                    .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));

            entreprise.setLogo(logo.getBytes());


            EntrepriseDTO dto=entityMapper.toDto(entrepriseRepository.save(entreprise));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

