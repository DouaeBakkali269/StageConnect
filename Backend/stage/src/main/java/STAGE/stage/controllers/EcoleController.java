package STAGE.stage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import STAGE.stage.dtos.EcoleDTO;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.mappers.EntityMapper;

import STAGE.stage.models.*;
import STAGE.stage.services.EcoleService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ecoles")
@RequiredArgsConstructor
public class EcoleController {

    private final EcoleService ecoleService;
    @Autowired
    private EcoleRepository ecoleRepository;
    @Autowired
    private EntityMapper entityMapper;

    @PostMapping
    public ResponseEntity<EcoleDTO> createEcole(@RequestBody EcoleDTO ecoleDTO) {
        EcoleDTO createdEcole = ecoleService.createEcole(ecoleDTO);
        return new ResponseEntity<>(createdEcole, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EcoleDTO> updateEcole(@PathVariable Long id, @RequestBody EcoleDTO ecoleDTO) {
        EcoleDTO updatedEcole = ecoleService.updateEcole(id, ecoleDTO);
        return new ResponseEntity<>(updatedEcole, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcoleDTO> getEcoleById(@PathVariable Long id) {
        EcoleDTO ecoleDTO = ecoleService.getEcoleById(id);
        return new ResponseEntity<>(ecoleDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EcoleDTO>> getAllEcoles() {
        List<EcoleDTO> ecoles = ecoleService.getAllEcoles();
        return new ResponseEntity<>(ecoles, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcole(@PathVariable Long id) {
        ecoleService.deleteEcole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{ecoleId}/filieres/{filiereId}")
    public ResponseEntity<EcoleDTO> addFiliereToEcole(@PathVariable Long ecoleId, @PathVariable Long filiereId) {
        EcoleDTO updatedEcole = ecoleService.addFiliereToEcole(ecoleId, filiereId);
        return new ResponseEntity<>(updatedEcole, HttpStatus.OK);
    }

    // Supprimer une filière d'une école
    @DeleteMapping("/{ecoleId}/filieres/{filiereId}")
    public ResponseEntity<EcoleDTO> removeFiliereFromEcole(@PathVariable Long ecoleId, @PathVariable Long filiereId) {
        EcoleDTO updatedEcole = ecoleService.removeFiliereFromEcole(ecoleId, filiereId);
        return new ResponseEntity<>(updatedEcole, HttpStatus.OK);
    }

    // Mettre à jour une filière d'une école
    @PutMapping("/{ecoleId}/filieres/{filiereId}/update")
    public ResponseEntity<EcoleDTO> updateFiliereInEcole(@PathVariable Long ecoleId, @PathVariable Long filiereId, @RequestBody Filiere newFiliere) {
        EcoleDTO updatedEcole = ecoleService.updateFiliereInEcole(ecoleId, filiereId, newFiliere);
        return new ResponseEntity<>(updatedEcole, HttpStatus.OK);
    }

    // Endpoint to download photoProfil
    @GetMapping("/download/{ecoleId}/logo")
    public ResponseEntity<org.springframework.core.io.Resource> downloadPhotoProfil(@PathVariable Long ecoleId) {

            Ecole ecole = ecoleRepository.findById(ecoleId)
                    .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));

            byte[] logoData = ecole.getLogo();
            if (logoData == null) {
                return ResponseEntity.noContent().build();
            }
            ByteArrayResource resource = new ByteArrayResource(logoData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=photo_profil.jpg")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
    }

    // Créer un étudiant avec image
    @PutMapping("/upload/{idEcole}")
    public ResponseEntity<EcoleDTO> updateEcoleWithImage(
            @PathVariable("idEcole") Long idEcole,
            @RequestParam("logo") MultipartFile logo){
        try {

            Ecole ecole = ecoleRepository.findById(idEcole)
                    .orElseThrow(() -> new RuntimeException("Ecole non trouvée"));

            ecole.setLogo(logo.getBytes());


            EcoleDTO dto=entityMapper.toDto(ecoleRepository.save(ecole));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
