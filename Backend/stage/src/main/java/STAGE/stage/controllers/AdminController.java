package STAGE.stage.controllers;

import STAGE.stage.dtos.AdminDTO;
import STAGE.stage.services.AdminService;
import STAGE.stage.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AdminController {

    @Autowired
    private AdminService adminService;

    private final StatisticsService statisticsService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        AdminDTO savedAdmin = adminService.save(adminDTO);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        Optional<AdminDTO> adminDTO = adminService.findById(id);
        return adminDTO.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/entreprises")
    public long countEntreprises() {
        return statisticsService.countEntreprises();
    }

    @GetMapping("/ecoles")
    public long countEcoles() {
        return statisticsService.countEcoles();
    }

    @GetMapping("/etudiants")
    public long countEtudiants() {
        return statisticsService.countEtudiants();
    }

    @GetMapping("/offers")
    public long countTotalOffers() {
        return statisticsService.countTotalOffers();
    }

    @GetMapping("/entretiens")
    public long countEntretiens() {
        return statisticsService.countEntretiens();
    }

    @GetMapping("/stages/ongoing")
    public long countOngoingStages() {
        LocalDate localDate = LocalDate.now();
        Date date = DateConversionUtil.convertToDate(localDate);
        return statisticsService.countOngoingStages(date);
    }

    @GetMapping("/open-offers/count")
    public long getTotalOpenOffers() {
        return statisticsService.countTotalOpenOffers();
    }

    @GetMapping("/stages-offres")
    public long countTotalInterns() {
        return statisticsService.countTotalInterns();
    }

    @GetMapping("/count-platform-users")
    public long countTotalPlatformUsers() {
        return statisticsService.countTotalPlatformUsers();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getAdminIdByUserId(@PathVariable Long userId) {
        try {
            Long adminId = adminService.getAdminIdByUserId(userId);
            return ResponseEntity.ok(adminId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/send-password/{email}/{rawPassword}")
    public void sendPassword(@PathVariable String email, @PathVariable String rawPassword) {
        adminService.sendEmailWithPassword(email, rawPassword);
    }

    @PostMapping("/send/{nom}/{email}/{text}")
    public void sendEmail(@PathVariable String nom, @PathVariable String email, @PathVariable String text) {
        adminService.sendEmail(nom,email,text);
    }
}