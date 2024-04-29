package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.util.EmailService;
import com.staxrt.tutorial.dto.AuthDTOS.*;
import com.staxrt.tutorial.model.EmailVerification;
import com.staxrt.tutorial.model.ResetPassword;
import com.staxrt.tutorial.model.User;
import com.staxrt.tutorial.repository.EmailVerificationRepository;
import com.staxrt.tutorial.repository.ResetPasswordRepository;
import com.staxrt.tutorial.repository.UserRepository;
import com.staxrt.tutorial.util.HtmlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

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

    @Transactional
    @PostMapping("/log-in")
    public ResponseEntity<Object> logIn(@Valid @RequestBody LogInDTO authUserDTO) throws MessagingException, IOException {

        User user = userRepository.findByEmailAddressAndPassword(authUserDTO.emailAddress, authUserDTO.password);

        // Temporal check only development
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(user.getVerified())
        {
            // good
            return ResponseEntity.status(HttpStatus.OK).build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Transactional
    @PostMapping("/email-verification")
    public ResponseEntity<Object> emailVerification(@Valid @RequestBody SignUpDTO verificationDTO) throws MessagingException, IOException {

        List<EmailVerification> result = emailVerificationRepository.findByEmailAddressAndCode(verificationDTO.emailAddress, verificationDTO.code);

        if(result.isEmpty())
        {
            return ResponseEntity.notFound().build();

        } else {

            User user = userRepository.findByEmailAddress(verificationDTO.emailAddress);
            user.setVerified(true);
            final User updatedUser = userRepository.save(user);

            emailVerificationRepository.deleteByEmailAddress(verificationDTO.emailAddress);

            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser(@Valid @RequestBody AuthUserDTO newUser) throws MessagingException, IOException {

        // Is the user already registered?
        if (userRepository.findByEmailAddress(newUser.emailAddress) != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = new User(newUser);
        userRepository.save(user);

        EmailVerification newEmailVerification = new EmailVerification(user.getEmailAddress());
        emailVerificationRepository.save(newEmailVerification);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "email_verification.html"); // TODO: Improve the way to handle the html path
        htmlTemplate.assign("${name}", user.getUsername());
        htmlTemplate.assign("${code}", newEmailVerification.getCode());
        String html = htmlTemplate.build();

        senderService.sendEmailFromTemplate(user.getEmailAddress(), "Quiz Project Email Verification", html);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws MessagingException, IOException {

        User user = userRepository.findByEmailAddress(resetPasswordDTO.emailAddress);
        if(user == null)
        {
            return ResponseEntity.ok().build(); // If the user does not exist we are still returning 200 as a security reason
        }

        ResetPassword resetPassword = new ResetPassword(resetPasswordDTO.emailAddress);
        resetPasswordRepository.save(resetPassword);

        HtmlTemplate htmlTemplate = new HtmlTemplate(configPath + "reset_password.html"); // TODO: Improve the way to handle the html path
        htmlTemplate.assign("${code}", resetPassword.getCode());
        String html = htmlTemplate.build();

        senderService.sendEmailFromTemplate(resetPassword.getEmailAddress(), "Quiz Project Reset Password", html);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/create-new-password")
    public ResponseEntity<Object> createNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) throws MessagingException, IOException {

        List<ResetPassword> result = resetPasswordRepository.findByEmailAddressAndCode(newPasswordDTO.emailAddress, newPasswordDTO.code);

        if(result.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        } else {

            User user = userRepository.findByEmailAddress(newPasswordDTO.emailAddress);
            user.setPassword(newPasswordDTO.password);
            final User updatedUser = userRepository.save(user);

            resetPasswordRepository.deleteByEmailAddress(newPasswordDTO.emailAddress);

            return ResponseEntity.ok().build();
        }
    }
}