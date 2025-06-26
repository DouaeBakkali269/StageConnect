package STAGE.stage.services;

import STAGE.stage.dtos.RHDTO;
import STAGE.stage.models.RH;

import java.util.List;

public interface RHService {

    RHDTO createRH(RHDTO rhDTO);

    RHDTO getRHById(Long id);

    List<RHDTO> getAllRHs();

    RHDTO updateRH(Long id, RHDTO rhDTO);

    void deleteRH(Long id);

    List<RH> getRHByEntreprise(Long entrepriseId);

    Long getRHIdByUserId(Long userId);
}
