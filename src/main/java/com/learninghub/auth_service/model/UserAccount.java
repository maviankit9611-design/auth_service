package com.learninghub.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    @Column(unique = true,nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String passwordHash;
    private boolean enabled=true;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_account_roles",
            joinColumns = @JoinColumn(name = "user_account_id")
    )
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    public UserAccount() {

    }

    public Long getId() {
        return id;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NotBlank String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEnabled( boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean isEnabled() {
        return enabled;
    }
}
