package com.learninghub.auth_service.service;

import com.learninghub.auth_service.dto.RegisterRequest;
import com.learninghub.auth_service.exception.EmailAlreadyRegisteredException;
import com.learninghub.auth_service.model.Role;
import com.learninghub.auth_service.model.UserAccount;
import com.learninghub.auth_service.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private Logger log= LoggerFactory.getLogger(RegistrationService.class);
    private UserAccountRepository userAccountRepository;
    private PasswordEncoder passwordEncoder;
    public RegistrationService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {
        if(userAccountRepository.existsByEmail(registerRequest.email())){
            throw new EmailAlreadyRegisteredException();
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(registerRequest.email());
        userAccount.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        userAccount.addRole(Role.CUSTOMER);
        userAccountRepository.save(userAccount);
        log.info("Service: registration successful");

    }
}
