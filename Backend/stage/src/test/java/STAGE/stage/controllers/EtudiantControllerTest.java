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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EtudiantControllerTest {

    private MockMvc mockMvc;

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

    @InjectMocks
    private EtudiantController etudiantController;

    private EtudiantDTO etudiantDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // init mocks

        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantController).build();

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
                .andExpect(jsonPath("$.idEtu").value(1L))
                .andExpect(jsonPath("$.nom").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }
}
