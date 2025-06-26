package STAGE.stage.services.implementation;

import STAGE.stage.models.Utilisateur;
import STAGE.stage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import STAGE.stage.dtos.AdminDTO;
import STAGE.stage.mappers.EntityMapper;
import STAGE.stage.models.Admin;
import STAGE.stage.repositories.AdminRepository;
import STAGE.stage.services.AdminService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private  AdminRepository adminRepository;

    @Autowired
    private  EntityMapper entityMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        Utilisateur user = new Utilisateur();
        user.setEmail(adminDTO.getEmailAd());
        String encodedPassword = passwordEncoder.encode(adminDTO.getMotDePasse());
        user.setPassword(encodedPassword); // Hash the password before saving
        user.setRole("ADMIN");// 2. Create the Admin entity
        Admin admin = new Admin();
        admin.setNomAd(adminDTO.getNomAd());
        admin.setMotDePasse(encodedPassword);
        admin.setEmailAd(adminDTO.getEmailAd());
        admin.setPrenomAd(adminDTO.getPrenomAd());
        admin.setTelephone(adminDTO.getTelephone());
        admin.setUser(user); // Link User to Admin
        userRepository.save(user);
        adminRepository.save(admin);

        return entityMapper.toDto(admin);
    }


    @Override
    public Optional<AdminDTO> findById(Long id) {
        return adminRepository.findById(id)
                .map(entityMapper::toDto);
    }

    @Override
    public List<AdminDTO> findAll() {
        return adminRepository.findAll().stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Long getAdminIdByUserId(Long userId) {
        Optional<Admin> adminOptional = adminRepository.findByUserId(userId);
        if (adminOptional.isPresent()) {
            return adminOptional.get().getId();
        } else {
            throw new IllegalArgumentException("Admin with userId " + userId + " does not exist.");
        }
    }

    @Override
    public void sendEmailWithPassword(String email, String rawPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Account Password");
        message.setText("Your account has been created. Your password is: " + rawPassword);
        mailSender.send(message);
    }

    @Override
    public void sendEmail(String nom, String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bakkalidouae75@gmail.com");
        message.setSubject("StageConnect inquiry from contact section");
        message.setText("Je suis "+nom+"\nMon email est: "+ email+"\n"+ text);
        mailSender.send(message);
    }


}
