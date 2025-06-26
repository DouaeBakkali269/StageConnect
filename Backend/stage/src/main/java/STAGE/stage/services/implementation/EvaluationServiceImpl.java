package STAGE.stage.services.implementation;

import STAGE.stage.dtos.EvaluationDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Evaluation;
import STAGE.stage.models.Encadrant;
import STAGE.stage.models.Stage;
import STAGE.stage.repositories.EvaluationRepository;
import STAGE.stage.repositories.EncadrantRepository;
import STAGE.stage.repositories.StageRepository;
import STAGE.stage.services.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EncadrantRepository encadrantRepository;
    private final StageRepository stageRepository;
    private final EntityMapper mapper;

    @Override
    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) {
        Encadrant encadrant = encadrantRepository.findById(evaluationDTO.getEncadrantId())
                .orElseThrow(() -> new RuntimeException("Encadrant introuvable"));
        Stage stage = stageRepository.findById(evaluationDTO.getStageId())
                .orElseThrow(() -> new RuntimeException("Stage introuvable"));

        Evaluation evaluation = mapper.toEntity(evaluationDTO);
        evaluation.setEncadrant(encadrant);
        evaluation.setStage(stage);

        return mapper.toDto(evaluationRepository.save(evaluation));
    }

    @Override
    public EvaluationDTO updateEvaluation(Long id, EvaluationDTO evaluationDTO) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Évaluation introuvable"));

        evaluation.setNote(evaluationDTO.getNote());
        evaluation.setCompetances(evaluationDTO.getCompetances());
        evaluation.setCommentaire(evaluationDTO.getCommentaire());

        return mapper.toDto(evaluationRepository.save(evaluation));
    }

    @Override
    public EvaluationDTO getEvaluationById(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Évaluation introuvable"));
        return mapper.toDto(evaluation);
    }

    @Override
    public List<EvaluationDTO> getAllEvaluations() {
        return evaluationRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    @Override
    public List<EvaluationDTO> getEvaluationsByEncadrantId(Long encadrantId) {
        return evaluationRepository.findByEncadrant_IdEncadrant(encadrantId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluationDTO> getEvaluationsByStageId(Long stageId) {
        return evaluationRepository.findByStage_IdStage(stageId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluationDTO> getEvaluationsByEtudiantId(Long etudiantId) {
        return evaluationRepository.findByStage_Etudiant_IdEtu(etudiantId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
     @Override
     public List<EvaluationDTO> getEvaluationsByEcoleId(Long ecoleId) {
         return evaluationRepository.findByEcoleId(ecoleId).stream()
                 .map(mapper::toDto)
                 .collect(Collectors.toList());
     }

}