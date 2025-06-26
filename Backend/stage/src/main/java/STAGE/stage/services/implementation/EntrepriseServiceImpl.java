package STAGE.stage.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.EntrepriseDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Entreprise;
import STAGE.stage.repositories.EntrepriseRepository;
import STAGE.stage.services.EntrepriseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseServiceImpl implements EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public EntrepriseDTO createEntreprise(EntrepriseDTO entrepriseDTO) {
        Entreprise entreprise = entityMapper.toEntity(entrepriseDTO);
        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);
        return entityMapper.toDto(savedEntreprise);
    }

    @Override
    public EntrepriseDTO getEntrepriseById(Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise not found with id: " + id));
        return entityMapper.toDto(entreprise);
    }

    @Override
    public List<EntrepriseDTO> getAllEntreprises() {
        return entrepriseRepository.findAll()
                .stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EntrepriseDTO updateEntreprise(Long id, EntrepriseDTO entrepriseDTO) {
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise not found with id: " + id));

        entreprise.setNomEntreprise(entrepriseDTO.getNomEntreprise());
        entreprise.setDescription(entrepriseDTO.getDescription());
        entreprise.setVilleEntreprise(entrepriseDTO.getVilleEntreprise());
        entreprise.setAdresseEntreprise(entrepriseDTO.getAdresseEntreprise());
        entreprise.setTelephoneFix(entrepriseDTO.getTelephoneFix());
        entreprise.setDomaineEntreprise(entrepriseDTO.getDomaineEntreprise());

        Entreprise updatedEntreprise = entrepriseRepository.save(entreprise);
        return entityMapper.toDto(updatedEntreprise);
    }

    @Override
    public void deleteEntreprise(Long id) {
        if (!entrepriseRepository.existsById(id)) {
            throw new RuntimeException("Entreprise not found with id: " + id);
        }
        entrepriseRepository.deleteById(id);
    }

}
