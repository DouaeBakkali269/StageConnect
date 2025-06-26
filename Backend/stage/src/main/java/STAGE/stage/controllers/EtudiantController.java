package STAGE.stage.controllers;

import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Ecole;
import STAGE.stage.models.Etudiant;
import STAGE.stage.models.Filiere;
import STAGE.stage.models.Postulation;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.FiliereRepository;
import STAGE.stage.repositories.VisibleOffreRepository;
import STAGE.stage.services.StatisticsService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import STAGE.stage.dtos.EtudiantDTO;
import STAGE.stage.services.EtudiantService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private VisibleOffreRepository visibleOffreRepository;

    @Autowired
    private  EtudiantRepository etudiantRepository;
    @Autowired
    private EntityMapper entityMapper;


    // Créer un étudiant
    @PostMapping
    public ResponseEntity<EtudiantDTO> createEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
        EtudiantDTO createdEtudiant = etudiantService.createEtudiant(etudiantDTO);
        return new ResponseEntity<>(createdEtudiant, HttpStatus.CREATED);
    }

    // Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiantById(@PathVariable Long id) {
        EtudiantDTO etudiantDTO = etudiantService.getEtudiantById(id);
        return new ResponseEntity<>(etudiantDTO, HttpStatus.OK);
    }

    // Récupérer les étudiants par école
    @GetMapping("/ecole/{ecoleId}")
    public ResponseEntity<List<EtudiantDTO>> getEtudiantsByEcole(@PathVariable Long ecoleId) {
        List<EtudiantDTO> etudiants = etudiantService.getEtudiantsByEcole(ecoleId);
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Récupérer les étudiants par filière
    @GetMapping("/filiere/{filiereId}")
    public ResponseEntity<List<EtudiantDTO>> getEtudiantsByFiliere(@PathVariable Long filiereId) {
        List<EtudiantDTO> etudiants = etudiantService.getEtudiantsByFiliere(filiereId);
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Créer un étudiant avec image
    @PutMapping("/upload/{idEtu}")
    public ResponseEntity<EtudiantDTO> updateEtudiantWithImage(
            @PathVariable("idEtu") Long idEtu,
            @RequestParam("photoProfil") MultipartFile photoProfil,
            @RequestParam("photoCouverture") MultipartFile photoCouverture){
        try {

            Etudiant etudiant = etudiantRepository.findById(idEtu)
                    .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

            etudiant.setPhotoProfil(photoProfil.getBytes());
            etudiant.setPhotoCouverture(photoCouverture.getBytes());

            EtudiantDTO dto=entityMapper.toDto(etudiantRepository.save(etudiant));
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Endpoint to download photoProfil
    @GetMapping("/download/{etudiantId}/photo-profil")
    public ResponseEntity<org.springframework.core.io.Resource> downloadPhotoProfil(@PathVariable Long etudiantId) {
        EtudiantDTO etudiant = etudiantService.getEtudiantById(etudiantId);
        if (etudiant == null || etudiant.getPhotoProfil() == null) {
            return ResponseEntity.noContent().build();
        }

        byte[] photoProfilData = etudiant.getPhotoProfil();
        ByteArrayResource resource = new ByteArrayResource(photoProfilData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=photo_profil.jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    // Endpoint to download photoCouverture
    @GetMapping("/download/{etudiantId}/photo-couverture")
    public ResponseEntity<org.springframework.core.io.Resource> downloadPhotoCouverture(@PathVariable Long etudiantId) {
        EtudiantDTO etudiant = etudiantService.getEtudiantById(etudiantId);
        if (etudiant == null || etudiant.getPhotoCouverture() == null) {
            return ResponseEntity.noContent().build();
        }

        byte[] photoCouvertureData = etudiant.getPhotoCouverture();
        ByteArrayResource resource = new ByteArrayResource(photoCouvertureData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=photo_couverture.jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEtudiant(@PathVariable Long id) {
        try {
            etudiantService.deleteEtudiantById(id);
            return ResponseEntity.ok("Etudiant with ID " + id + " has been deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getEtudiantIdByUserId(@PathVariable Long userId) {
        try {
            Long etudiantId = etudiantService.getEtudiantIdByUserId(userId);
            return ResponseEntity.ok(etudiantId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update an existing student by ID
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDTO> updateEtudiant(
            @PathVariable Long id,
            @RequestBody EtudiantDTO studentData) {
        try {
            EtudiantDTO updatedEtudiant = etudiantService.updateEtudiant(id, studentData);
            return ResponseEntity.ok(updatedEtudiant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/student/{studentId}/count-applications")
    public long countApplicationsByStudent(@PathVariable Long studentId) {
        return statisticsService.countApplicationsByStudentId(studentId);
    }

    @GetMapping("/student/{studentId}/count-interviews")
    public long countInterviewsByStudent(@PathVariable Long studentId) {
        return statisticsService.countInterviewsByStudentId(studentId);
    }

    // Endpoint to count postulations by student ID
    @GetMapping("/{idEtu}/postulations/count")
    public long countPostulationsByEtudiantId(@PathVariable Long idEtu) {
        return statisticsService.countPostulationsByEtudiantId(idEtu);
    }

    // Endpoint to count internships by student ID
    @GetMapping("/{idEtu}/internships/count")
    public long countInternshipsByEtudiantId(@PathVariable Long idEtu) {
        return statisticsService.countInternshipsByEtudiantId(idEtu);
    }

    @GetMapping("/{idFiliere}/visible/count")
    public long countVisibleOffersByFiliere(@PathVariable Long idFiliere) {
        return visibleOffreRepository.countVisibleOffersByFiliere(idFiliere);
    }
}


