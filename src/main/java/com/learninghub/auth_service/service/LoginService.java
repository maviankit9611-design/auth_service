package com.learninghub.auth_service.service;

import com.learninghub.auth_service.dto.LoginRequest;
import com.learninghub.auth_service.exception.InvalidCredentialsException;
import com.learninghub.auth_service.model.UserAccount;
import com.learninghub.auth_service.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserAccountRepository userAccountRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private Logger log = LoggerFactory.getLogger(LoginService.class);
    public LoginService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public String login(LoginRequest loginRequest) {
        log.info("Service: Login Request for user: " + loginRequest.email());
        UserAccount userAccount = userAccountRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new InvalidCredentialsException());
        if(userAccount.isEnabled()&&passwordEncoder.matches(loginRequest.password(), userAccount.getPasswordHash())) {
            return jwtService.generateToken(userAccount);
        }
            throw new InvalidCredentialsException();
    }

}
