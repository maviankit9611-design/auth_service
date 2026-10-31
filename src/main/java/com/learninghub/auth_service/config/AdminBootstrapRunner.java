package com.learninghub.auth_service.config;

import com.learninghub.auth_service.model.Role;
import com.learninghub.auth_service.model.UserAccount;
import com.learninghub.auth_service.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminBootstrapRunner implements ApplicationRunner {
    private final String adminEmail;
    private final String adminPassword;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(AdminBootstrapRunner.class);

    public AdminBootstrapRunner(@Value("${admin.email}") String adminEmail, @Value("${admin.password}") String adminPassword, UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(adminEmail.isBlank()||adminPassword.isBlank()){
           throw new IllegalStateException("Admin Email or password must not be blank");
        }
        if(userAccountRepository.existsByEmail(adminEmail)) {
            UserAccount user = userAccountRepository.findByEmail(adminEmail).get();
            Set<Role> roles = user.getRoles();
            if (!roles.contains(Role.ADMIN)) {
                throw new IllegalStateException("configured admin email belongs to a non-admin account and must be resolved manually");
            }
            log.info("Admin account already exists, skipping bootstrap");
        }else {
            UserAccount userAccount = new UserAccount();
            userAccount.setEmail(adminEmail);
            userAccount.setPasswordHash(passwordEncoder.encode(adminPassword));
            userAccount.addRole(Role.ADMIN);
            userAccountRepository.save(userAccount);
            log.info("initial admin account was created");
        }

    }
}
