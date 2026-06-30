package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.logback.RootLogLevelConfigurator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sec_user_roles")
@Getter
@Setter
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredentials user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    private Instant createdDate;
    private String createdBy;

    public UserRoles() {

    }

    public UserRoles(UserCredentials user, Role role, String modifiedBy) {
        this.user = user;
        this.role = role;
        this.createdBy = modifiedBy;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdDate = now;
    }
}
