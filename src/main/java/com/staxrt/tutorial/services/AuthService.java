package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.entity.*;
import com.staxrt.tutorial.repository.*;
import com.staxrt.tutorial.security.JwtTokenUtil;
import com.staxrt.tutorial.util.EmailService;
import com.staxrt.tutorial.util.HtmlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class AuthService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final AvatarService avatarService;
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService senderService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final String configPath = rootPath;

    @Autowired
    public AuthService(AvatarService avatarService,
                       UserRepository userRepository,
                       EmailVerificationRepository emailVerificationRepository,
                       EmailService senderService,
                       ResetPasswordRepository resetPasswordRepository,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       PasswordEncoder passwordEncoder) {
        this.avatarService = avatarService;
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.senderService = senderService;
        this.resetPasswordRepository = resetPasswordRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateJwtToken(String token) {
        return token != null && jwtTokenUtil.validateToken(token);
    }

    public LoginResponseDTO authenticateUser(LogInDTO authUserDTO) throws BadCredentialsException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authUserDTO.getEmail(), authUserDTO.getPassword())
        );

        UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
        LoginResponseDTO response = new LoginResponseDTO();
        response.setDisplayName(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setAuthorization(jwtTokenUtil.generateToken(userEntity));
        return response;
    }

    @Transactional
    public LoginResponseDTO verifyEmail(SignUpDTO verificationDTO) throws MessagingException, IOException {
        List<EmailVerificationEntity> result = emailVerificationRepository.findByEmailAndCode(verificationDTO.getEmail(), verificationDTO.getCode());

        if (result.isEmpty()) {
            return null;
        }

        UserEntity userEntity = userRepository.getByEmail(verificationDTO.getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        emailVerificationRepository.deleteByEmail(verificationDTO.getEmail());

        avatarService.createDefaultAvatar(userEntity.getUsername(), uploadDir);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setDisplayName(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setAuthorization(jwtTokenUtil.generateToken(userEntity));
        return response;
    }

    @Transactional
    public void registerUser(AuthUserDTO newUser) throws MessagingException, IOException {
        if (userRepository.getByEmail(newUser.getEmail()) != null || userRepository.getByUsername(newUser.getUsername()) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        UserEntity userEntity = new UserEntity(newUser);
        userRepository.save(userEntity);

        EmailVerificationEntity newEmailVerificationEntity = new EmailVerificationEntity(userEntity.getEmail());
        emailVerificationRepository.save(newEmailVerificationEntity);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "email_verification.html");
        htmlTemplate.assign("${name}", userEntity.getUsername());
        htmlTemplate.assign("${code}", newEmailVerificationEntity.getCode());
        String html = htmlTemplate.build();

        senderService.sendHtmlEmail(userEntity.getEmail(), "Quiz Project Email Verification", html);
    }

    @Transactional
    public void initiatePasswordReset(ResetPasswordDTO resetPasswordDTO) throws MessagingException, IOException {
        UserEntity userEntity = userRepository.getByEmail(resetPasswordDTO.getEmail());
        if (userEntity == null) {
            return;
        }

        ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity(resetPasswordDTO.getEmail());
        resetPasswordRepository.save(resetPasswordEntity);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "reset_password.html");
        htmlTemplate.assign("${code}", resetPasswordEntity.getCode());
        String html = htmlTemplate.build();

        senderService.sendHtmlEmail(resetPasswordEntity.getEmail(), "Quiz Project Reset Password", html);
    }

    @Transactional
    public void updatePassword(NewPasswordDTO newPasswordDTO) {
        List<ResetPasswordEntity> result = resetPasswordRepository.findByEmailAndCode(newPasswordDTO.getEmail(), newPasswordDTO.getCode());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Invalid code or email");
        }

        UserEntity userEntity = userRepository.getByEmail(newPasswordDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(newPasswordDTO.getPassword()));
        userRepository.save(userEntity);
        resetPasswordRepository.deleteByEmail(newPasswordDTO.getEmail());
    }
}
