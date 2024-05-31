package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.*;
import com.staxrt.tutorial.services.AuthenticationService;
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
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> createUser(@Valid @RequestBody AuthUserDTO newUser) throws MessagingException, IOException {
        authService.registerUser(newUser);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/email-verification")
    public ResponseEntity<AuthUserReactDTO> emailVerification(@Valid @RequestBody SignUpDTO verificationDTO) throws MessagingException, IOException {
        AuthUserReactDTO response = authService.verifyEmail(verificationDTO);
        HttpHeaders headers = new HttpHeaders();

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        headers.add(HttpHeaders.AUTHORIZATION, response.getAuthorization());
        return ResponseEntity.ok()
                .headers(headers).body(response);
    }

    @PostMapping("/jwt-validation")
    public ResponseEntity<Object> jwtValidation(@Valid @RequestBody JwtValidationDTO jwtValidationDTO) {
        if (authService.validateJwtToken(jwtValidationDTO.getToken())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(
                HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthUserReactDTO> logIn(@Valid @RequestBody LogInDTO authUserDTO) throws MessagingException, IOException {
        HttpHeaders headers = new HttpHeaders();
        AuthUserReactDTO response;

        response = authService.authenticateUser(authUserDTO);
        headers.add(HttpHeaders.AUTHORIZATION, response.getAuthorization());
        return ResponseEntity.ok()
                .headers(headers).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws MessagingException, IOException {
        authService.initiatePasswordReset(resetPasswordDTO);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/create-new-password")
    public ResponseEntity<Object> createNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            authService.updatePassword(newPasswordDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(
                    HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
