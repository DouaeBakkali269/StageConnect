package STAGE.stage.controllers;

import STAGE.stage.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import STAGE.stage.dtos.RHDTO;
import STAGE.stage.models.RH;
import STAGE.stage.services.RHService;

import java.util.List;

@RestController
@RequestMapping("/api/rh")
public class RHController {
    @Autowired
    private RHService rhService;
    @Autowired
    private EntityMapper entityMapper;

    @PostMapping
    public RHDTO createRH(@RequestBody RHDTO rhDTO) {
        return rhService.createRH(rhDTO);
    }

    // Récupérer un RH par son ID
    @GetMapping("/{id}")
    public RHDTO getRHById(@PathVariable Long id) {
        return rhService.getRHById(id);
    }

    // Récupérer tous les RH
    @GetMapping
    public List<RHDTO> getAllRHs() {
        return rhService.getAllRHs();
    }

    // Mettre à jour un RH existant
    @PutMapping("/{id}")
    public RHDTO updateRH(@PathVariable Long id, @RequestBody RHDTO rhDTO) {
        return rhService.updateRH(id, rhDTO);
    }

    // Supprimer un RH par son ID
    @DeleteMapping("/{id}")
    public void deleteRH(@PathVariable Long id) {
        rhService.deleteRH(id);
    }
    @GetMapping("/entreprise/{entrepriseId}")
    public List<RHDTO> getRHByEntreprise(@PathVariable Long entrepriseId) {
        List<RH> rhs = rhService.getRHByEntreprise(entrepriseId);
        return rhs.stream().map(entityMapper::toDto).toList();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Long> getRHIdByUserId(@PathVariable Long userId) {
        try {
            Long rhId = rhService.getRHIdByUserId(userId);
            return ResponseEntity.ok(rhId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
