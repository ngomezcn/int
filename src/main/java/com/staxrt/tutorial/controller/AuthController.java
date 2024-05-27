package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.JwtValidationDTO;
import com.staxrt.tutorial.services.AuthService;
import com.staxrt.tutorial.dto.authDTOS.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Transactional
    @PostMapping("/jwt-validation")
    public ResponseEntity<Object> jwtValidation(@Valid @RequestBody JwtValidationDTO jwtValidationDTO) {
        if (authService.validateJwtToken(jwtValidationDTO.getToken())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Transactional
    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDTO> logIn(@Valid @RequestBody LogInDTO authUserDTO) {
        HttpHeaders headers = new HttpHeaders();
        LoginResponseDTO response;


            response = authService.authenticateUser(authUserDTO);
            headers.add(HttpHeaders.AUTHORIZATION, response.getAuthorization());
            return ResponseEntity.ok().headers(headers).body(response);

    }

    @Transactional
    @PostMapping("/email-verification")
    public ResponseEntity<LoginResponseDTO> emailVerification(@Valid @RequestBody SignUpDTO verificationDTO) throws MessagingException, IOException {
        LoginResponseDTO response = authService.verifyEmail(verificationDTO);
        HttpHeaders headers = new HttpHeaders();

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        headers.add(HttpHeaders.AUTHORIZATION, response.getAuthorization());
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser(@Valid @RequestBody AuthUserDTO newUser) {
        try {
            authService.registerUser(newUser);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | MessagingException | IOException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws MessagingException, IOException {
        authService.initiatePasswordReset(resetPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/create-new-password")
    public ResponseEntity<Object> createNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            authService.updatePassword(newPasswordDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}

/*@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${upload.dir}")
    String uploadDir;

    AvatarService avatarService = new AvatarService();

    static private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static private final String configPath = rootPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private EmailService senderService;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional
    @PostMapping("/jwt-validation")
    public ResponseEntity<Object> jwtValidation(@Valid @RequestBody JwtValidationDTO jwtValidationDTO) throws MessagingException, IOException {

         if(jwtValidationDTO.token != null && jwtTokenUtil.validateToken(jwtValidationDTO.token))
        {

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @Transactional
    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDTO> logIn(@Valid @RequestBody LogInDTO authUserDTO) throws MessagingException, IOException {

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authUserDTO.email, authUserDTO.password
                            )
                    );

            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();

            LoginResponseDTO response = new LoginResponseDTO();
            response.displayName = userEntity.getUsername();
            response.email = userEntity.getEmail();
            response.authorization = jwtTokenUtil.generateToken(userEntity);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(userEntity)); // Reemplaza "tuTokenDeAutorización" con el token real

            return ResponseEntity.ok().headers(headers).body(response);
                   // /*.header(
                   //         HttpHeaders.AUTHORIZATION,
                  //          jwtTokenUtil.generateToken(user)
                   // ) //.build();//.body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Transactional
    @PostMapping("/email-verification")
    public ResponseEntity<Object> emailVerification(@Valid @RequestBody SignUpDTO verificationDTO) throws MessagingException, IOException {

        List<EmailVerificationEntity> result = emailVerificationRepository.findByEmailAndCode(verificationDTO.email, verificationDTO.code);

        if(result.isEmpty())
        {
            return ResponseEntity.notFound().build();

        } else {

            UserEntity userEntity = userRepository.getByEmail(verificationDTO.email);
            userEntity.setEnabled(true);
            final UserEntity updatedUserEntity = userRepository.save(userEntity);

            emailVerificationRepository.deleteByEmail(verificationDTO.email);

            LoginResponseDTO response = new LoginResponseDTO();
            response.displayName = userEntity.getUsername();
            response.email = userEntity.getEmail();
            response.authorization = jwtTokenUtil.generateToken(userEntity);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(userEntity)); // Reemplaza "tuTokenDeAutorización" con el token real

            avatarService.createDefaultAvatar(userEntity.getUsername(), uploadDir);

            return ResponseEntity.ok().headers(headers).body(response);
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser(@Valid @RequestBody AuthUserDTO newUser) throws MessagingException, IOException {

        // Is the user already registered?
        if (userRepository.getByEmail(newUser.email) != null || userRepository.getByUsername(newUser.email) != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        newUser.password = passwordEncoder.encode(newUser.password);
        UserEntity userEntity = new UserEntity(newUser);
        userRepository.save(userEntity);

        EmailVerificationEntity newEmailVerificationEntity = new EmailVerificationEntity(userEntity.getEmail());
        emailVerificationRepository.save(newEmailVerificationEntity);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "email_verification.html"); // TODO: Improve the way to handle the html path
        htmlTemplate.assign("${name}", userEntity.getUsername());
        htmlTemplate.assign("${code}", newEmailVerificationEntity.getCode());
        String html = htmlTemplate.build();

        senderService.sendHtmlEmail(userEntity.getEmail(), "Quiz Project Email Verification", html);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws MessagingException, IOException {

        UserEntity userEntity = userRepository.getByEmail(resetPasswordDTO.email);
        if(userEntity == null)
        {
            return ResponseEntity.ok().build(); // If the user does not exist we are still returning 200 as a security reason
        }

        ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity(resetPasswordDTO.email);
        resetPasswordRepository.save(resetPasswordEntity);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "reset_password.html"); // TODO: Improve the way to handle the html path
        htmlTemplate.assign("${code}", resetPasswordEntity.getCode());
        String html = htmlTemplate.build();

        senderService.sendHtmlEmail(resetPasswordEntity.getemail(), "Quiz Project Reset Password", html);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/create-new-password")
    public ResponseEntity<Object> createNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) throws MessagingException, IOException {

        List<ResetPasswordEntity> result = resetPasswordRepository.findByEmailAndCode(newPasswordDTO.email, newPasswordDTO.code);

        if(result.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        } else {

            UserEntity userEntity = userRepository.getByEmail(newPasswordDTO.email);
            userEntity.setPassword(newPasswordDTO.password);
            final UserEntity updatedUserEntity = userRepository.save(userEntity);

            resetPasswordRepository.deleteByEmail(newPasswordDTO.email);

            return ResponseEntity.ok().build();
        }
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}*/