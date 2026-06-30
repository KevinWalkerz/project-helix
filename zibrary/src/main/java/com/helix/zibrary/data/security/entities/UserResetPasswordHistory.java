package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sec_user_reset_password_history")
@Getter
@Setter
public class UserResetPasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredentials user;

    Instant resetDate;

    public UserResetPasswordHistory() {

    }

    public UserResetPasswordHistory(UserCredentials user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        this.resetDate = Instant.now();
    }
}
