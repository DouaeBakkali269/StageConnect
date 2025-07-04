package STAGE.stage.services;

import STAGE.stage.dtos.EtudiantDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Ecole;
import STAGE.stage.models.Etudiant;
import STAGE.stage.models.Filiere;
import STAGE.stage.models.Utilisateur;
import STAGE.stage.repositories.EcoleRepository;
import STAGE.stage.repositories.EtudiantRepository;
import STAGE.stage.repositories.FiliereRepository;
import STAGE.stage.repositories.UserRepository;
import STAGE.stage.services.implementation.EtudiantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EcoleRepository ecoleRepository;

    @Mock
    private FiliereRepository filiereRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private EtudiantDTO etudiantDTO;
    private Etudiant etudiant;
    private Ecole ecole;
    private Filiere filiere;
    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNom("John");
        etudiantDTO.setPrenom("Doe");
        etudiantDTO.setEmail("john.doe@example.com");
        etudiantDTO.setMotDePasse("password123");
        etudiantDTO.setEcoleId(1L);
        etudiantDTO.setFiliereId(1L);

        ecole = new Ecole();
        ecole.setIdEcole(1L);
        ecole.setNomEcole("Test School");

        filiere = new Filiere();
        filiere.setIdFiliere(1L);
        filiere.setNomFiliere("Computer Science");

        utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail(etudiantDTO.getEmail());

        etudiant = new Etudiant();
        etudiant.setIdEtu(1L);
        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setUser(utilisateur);
    }

    @Test
    void testCreateEtudiant_Success() {
        // Arrange
        when(ecoleRepository.findById(1L)).thenReturn(Optional.of(ecole));
        when(filiereRepository.findById(1L)).thenReturn(Optional.of(filiere));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(mapper.toDto(any(Etudiant.class))).thenReturn(etudiantDTO);

        // Act
        EtudiantDTO result = etudiantService.createEtudiant(etudiantDTO);

        // Assert
        assertNotNull(result);
        assertEquals(etudiantDTO.getNom(), result.getNom());
        verify(userRepository, times(1)).save(any(Utilisateur.class));
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testCreateEtudiant_EcoleNotFound() {
        // Arrange
        when(ecoleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            etudiantService.createEtudiant(etudiantDTO);
        });
        assertEquals("École introuvable", exception.getMessage());
        verify(etudiantRepository, never()).save(any());
    }

    @Test
    void testCreateEtudiant_FiliereNotFound() {
        // Arrange
        when(ecoleRepository.findById(1L)).thenReturn(Optional.of(ecole));
        when(filiereRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            etudiantService.createEtudiant(etudiantDTO);
        });
        assertEquals("Filière introuvable", exception.getMessage());
        verify(etudiantRepository, never()).save(any());
    }

    @Test
    void testCreateEtudiant_PasswordIsNull() {
        // Arrange
        etudiantDTO.setMotDePasse(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.createEtudiant(etudiantDTO);
        });
        assertEquals("Password cannot be null or empty.", exception.getMessage());
        verify(etudiantRepository, never()).save(any());
    }
}
