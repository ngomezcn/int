package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.EmailService;
import com.staxrt.tutorial.dto.AuthDTOS.AuthUserDTO;
import com.staxrt.tutorial.dto.AuthDTOS.LogInDTO;
import com.staxrt.tutorial.dto.AuthDTOS.NewPasswordDTO;
import com.staxrt.tutorial.dto.AuthDTOS.SignUpDTO;
import com.staxrt.tutorial.model.EmailVerification;
import com.staxrt.tutorial.model.ResetPassword;
import com.staxrt.tutorial.model.User;
import com.staxrt.tutorial.repository.EmailVerificationRepository;
import com.staxrt.tutorial.repository.ResetPasswordRepository;
import com.staxrt.tutorial.repository.UserRepository;
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
import java.io.BufferedReader;
import java.io.FileReader;
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

        User user = userRepository.findByEmailAddressAndPasswordHash(authUserDTO.emailAddress, authUserDTO.password);

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


        String aaaa = configPath + "test.html";

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(aaaa.substring(1)));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String htmlTemplate = contentBuilder.toString();

        System.out.println(htmlTemplate);

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", user.getFirstName());
        htmlTemplate = htmlTemplate.replace("${code}", newEmailVerification.getCode());


        senderService.sendEmailFromTemplate(user.getEmailAddress(), "Quiz Project Email Verification", htmlTemplate);
        //senderService.sendEmail("naimgomezcn@gmail.com", "sad", newEmailVerification.getCode());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPassword resetPassword) throws MessagingException, IOException {

        List<ResetPassword> result = resetPasswordRepository.findByEmailAddressAndCode(resetPassword.getEmailAddress(), resetPassword.getCode());



        /*if(result.isEmpty())
        {
            return ResponseEntity.notFound().build();

        } else {

            User user = userRepository.findByEmailAddress(verificationDTO.emailAddress);
            user.setVerified(true);
            final User updatedUser = userRepository.save(user);

            emailVerificationRepository.deleteByEmailAddress(verificationDTO.emailAddress);

            return ResponseEntity.ok().build();
        }*/



        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-new-password")
    public ResponseEntity<Object> createNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) throws MessagingException, IOException {

        List<ResetPassword> result = resetPasswordRepository.findByEmailAddressAndCode(newPasswordDTO.emailAddress, newPasswordDTO.code);



        /*if(result.isEmpty())
        {
            return ResponseEntity.notFound().build();

        } else {

            User user = userRepository.findByEmailAddress(verificationDTO.emailAddress);
            user.setVerified(true);
            final User updatedUser = userRepository.save(user);

            emailVerificationRepository.deleteByEmailAddress(verificationDTO.emailAddress);

            return ResponseEntity.ok().build();
        }*/


        return ResponseEntity.ok().build();
    }
}