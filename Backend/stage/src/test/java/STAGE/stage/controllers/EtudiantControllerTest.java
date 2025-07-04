package STAGE.stage.controllers;

import STAGE.stage.dtos.EtudiantDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.VisibleOffreRepository;
import STAGE.stage.services.EtudiantService;
import STAGE.stage.services.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtudiantController.class)
public class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private EtudiantService etudiantService;

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private VisibleOffreRepository visibleOffreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EntityMapper entityMapper;

    private EtudiantDTO etudiantDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setIdEtu(1L);
        etudiantDTO.setNom("Jane");
        etudiantDTO.setPrenom("Doe");
        etudiantDTO.setEmail("jane.doe@example.com");
        etudiantDTO.setMotDePasse("securepassword");
    }

    @Test
    void testCreateEtudiant_Success() throws Exception {
        // Arrange
        when(etudiantService.createEtudiant(any(EtudiantDTO.class))).thenReturn(etudiantDTO);

        // Act & Assert
        mockMvc.perform(post("/api/etudiants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }
}