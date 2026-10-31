package com.learninghub.auth_service.controller;

import com.learninghub.auth_service.dto.AuthResponse;
import com.learninghub.auth_service.dto.LoginRequest;
import com.learninghub.auth_service.dto.RegisterRequest;
import com.learninghub.auth_service.service.LoginService;
import com.learninghub.auth_service.service.RegistrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private RegistrationService registrationService;
    private LoginService loginService;
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(RegistrationService registrationService,
            LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest user){
            log.info("Controller: registerUser with email :"+user.email());
            registrationService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody LoginRequest user){
        log.info("Controller: loginUser with email :"+user.email());
       return new AuthResponse(loginService.login(user));
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication){
        log.info("Controller: getMe for userId: {}", authentication.getPrincipal());
        Map<String,Object> map = new HashMap<>();
        map.put("userId",authentication.getPrincipal());
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        map.put("roles",roles);
        return  ResponseEntity.status(HttpStatus.OK).body(map);
    }

}
