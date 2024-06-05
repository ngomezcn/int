package com.staxrt.tutorial.services;

import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.entity.*;
import com.staxrt.tutorial.exception.UserAlreadyRegistered;
import com.staxrt.tutorial.exception.UserAlreadyRegisteredAndVerifiedException;
import com.staxrt.tutorial.repository.*;
import com.staxrt.tutorial.security.JwtTokenUtil;
import com.staxrt.tutorial.util.HtmlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class AuthenticationService {

    private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final String configPath = rootPath;
    private final AvatarService avatarService;
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailSenderService emailSenderService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AvatarService avatarService,
                                 UserRepository userRepository,
                                 EmailVerificationRepository emailVerificationRepository,
                                 EmailSenderService senderService,
                                 ResetPasswordRepository resetPasswordRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtTokenUtil jwtTokenUtil,
                                 PasswordEncoder passwordEncoder) {
        this.avatarService = avatarService;
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailSenderService = senderService;
        this.resetPasswordRepository = resetPasswordRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validateJwtToken(String token) {
        return token != null && jwtTokenUtil.validateToken(token);
    }

    @Transactional(dontRollbackOn = {DisabledException.class})
    public AuthUserReactDTO authenticateUser(LogInDTO dto) throws BadCredentialsException, MessagingException, IOException {
        UserEntity userEntity = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
        } catch (DisabledException exception) {
            handleEmailVerification(userEntity.getEmail(), userEntity.getUsername());
            throw exception;
        }

        AuthUserReactDTO response = new AuthUserReactDTO();
        response.setDisplayName(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setAuthorization(jwtTokenUtil.generateToken(userEntity));
        return response;
    }

    @Transactional
    public AuthUserReactDTO verifyEmail(SignUpDTO verificationDTO) throws MessagingException, IOException {
        List<EmailVerificationEntity> result = emailVerificationRepository.findByEmailAndCode(verificationDTO.getEmail(), verificationDTO.getCode());

        if (result.isEmpty()) {
            return null;
        }

        UserEntity user = userRepository.getByEmail(verificationDTO.getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        emailVerificationRepository.deleteByEmail(verificationDTO.getEmail());

        avatarService.createAvatar(user);

        AuthUserReactDTO response = new AuthUserReactDTO();
        response.setDisplayName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAuthorization(jwtTokenUtil.generateToken(user));
        return response;
    }

    @Transactional
    public void registerUser(AuthUserDTO dto) throws MessagingException, IOException {
        UserEntity findByUsername = userRepository.getByUsername(dto.getUsername());
        UserEntity findByEmail = userRepository.getByEmail(dto.getEmail());

        if (findByUsername != null || findByEmail != null) {
            UserEntity user = (findByUsername != null) ? findByUsername : findByEmail;
            if (user.isEnabled()) {
                throw new UserAlreadyRegisteredAndVerifiedException("The user is already registered and verified.");
            }
            throw new UserAlreadyRegistered("The user is already registered.");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserEntity userEntity = new UserEntity(dto);
        userRepository.save(userEntity);

        handleEmailVerification(dto.email, dto.username);
    }

    private void handleEmailVerification(String email, String username) throws MessagingException, IOException {

        EmailVerificationEntity newEmailVerificationEntity = new EmailVerificationEntity(email);
        emailVerificationRepository.save(newEmailVerificationEntity);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "email_verification.html");
        htmlTemplate.assign("${name}", username);
        htmlTemplate.assign("${code}", newEmailVerificationEntity.getCode());
        String html = htmlTemplate.build();

        emailSenderService.sendHtmlEmail(email, "Quiz Project Email Verification", html);
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

        emailSenderService.sendHtmlEmail(resetPasswordEntity.getEmail(), "Quiz Project Reset Password", html);
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
