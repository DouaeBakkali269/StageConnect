package STAGE.stage.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.OffreDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Entreprise;
import STAGE.stage.models.Offre;
import STAGE.stage.models.RH;
import STAGE.stage.repositories.EntrepriseRepository;
import STAGE.stage.repositories.OffreRepository;
import STAGE.stage.repositories.RHRepository;
import STAGE.stage.services.OffreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OffreServiceImpl implements OffreService {
    private final OffreRepository offreRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final RHRepository rhRepository;
    private final EntityMapper mapper;

    @Override
    public OffreDTO createOffre(OffreDTO dto) {
        Offre offre = new Offre();
        offre.setObjetOffre(dto.getObjetOffre());
        offre.setDescriptionOffre(dto.getDescriptionOffre());
        offre.setDateLancement(dto.getDateLancement());
        offre.setDateLimite(dto.getDateLimite());
        offre.setPosteOffre(dto.getPosteOffre());
        offre.setDureeStage(dto.getDureeStage());
        offre.setModeOffre(dto.getModeOffre());
        offre.setRemuneration(dto.getRemuneration());
        offre.setTypeStageOffre(dto.getTypeStageOffre());
        offre.setNiveauRequisOffre(dto.getNiveauRequisOffre());

        // Associer à l'entreprise et RH
        Entreprise entreprise = entrepriseRepository.findById(dto.getEntrepriseId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));
        RH rh = rhRepository.findById(dto.getRhId())
                .orElseThrow(() -> new RuntimeException("RH introuvable"));

        offre.setEntreprise(entreprise);
        offre.setRh(rh);

        // Sauvegarder l'offre
        return mapper.toDto(offreRepository.save(offre));
    }

    @Override
    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OffreDTO updateOffre(Long offreId, OffreDTO offreDTO) {
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        // Mise à jour des informations de l'offre
        if (offreDTO.getObjetOffre() != null) {
            offre.setObjetOffre(offreDTO.getObjetOffre());
        }
        if (offreDTO.getDescriptionOffre() != null) {
            offre.setDescriptionOffre(offreDTO.getDescriptionOffre());
        }
        if (offreDTO.getDateLimite() != null) {
            offre.setDateLimite(offreDTO.getDateLimite());
        }
        if (offreDTO.getRemuneration() != null) {
            offre.setRemuneration(offreDTO.getRemuneration());
        }
        if (offreDTO.getDureeStage() != null) {
            offre.setDureeStage(offreDTO.getDureeStage());
        }
        if (offreDTO.getModeOffre() != null) {
            offre.setModeOffre(offreDTO.getModeOffre());
        }
        if (offreDTO.getDateLancement() != null) {
            offre.setDateLancement(offreDTO.getDateLancement());
        }
        if (offreDTO.getTypeStageOffre() != null) {
            offre.setTypeStageOffre(offreDTO.getTypeStageOffre());
        }
        if (offreDTO.getPosteOffre() != null) {
            offre.setPosteOffre(offreDTO.getPosteOffre());
        }
        if (offreDTO.getNiveauRequisOffre() != null) {
            offre.setNiveauRequisOffre(offreDTO.getNiveauRequisOffre());
        }

        offreRepository.save(offre);
        return mapper.toDto(offre);
    }
    @Override
    public List<OffreDTO> getOffresByRH(Long rhId) {
        RH rh = rhRepository.findById(rhId)
                .orElseThrow(() -> new RuntimeException("RH not found with id: " + rhId));

        List<Offre> offres = offreRepository.findByRh(rh);
        return offres.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<OffreDTO> getOffresByEntreprise(Long entrepriseId) {
        List<Offre> offres =offreRepository.findOffersByCompanyId(entrepriseId); // Assurez-vous que la méthode existe dans le repository
        return offres.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOfferById(Long id) {
        // Verifies if the offer exists, throwing an exception if needed for safety
        if (!offreRepository.existsById(id)) {
            throw new IllegalArgumentException("Offer with ID " + id + " does not exist.");
        }
        // Deletes the offer by ID
        offreRepository.deleteById(id);
    }

    @Override
    public OffreDTO getOffreById(Long id) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offre not found with ID: " + id));
        return mapper.toDto(offre);
    }
}







