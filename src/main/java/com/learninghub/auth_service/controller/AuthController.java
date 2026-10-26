package com.learninghub.auth_service.controller;

import com.learninghub.auth_service.dto.RegisterRequest;
import com.learninghub.auth_service.service.RegistrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private RegistrationService registrationService;
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest user){
            log.info("Controller: registerUser with email :"+user.email());
            registrationService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
