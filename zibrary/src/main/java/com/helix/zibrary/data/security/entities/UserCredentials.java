package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.data.image.entities.ImageFile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "sec_user_credentials")
@Getter
@Setter
public class UserCredentials{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String hashedPassword;

    private String registeredName;

    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private ImageFile profilePicture;

    private boolean active;

    private Instant createdDate;
    private Instant updatedDate;

    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "user")
    private Collection<UserRoles> userRoles = new ArrayList<>();

    public UserCredentials() {

    }

    public UserCredentials(UUID id, String username, String email, String hashedPassword, String registeredName, boolean active, String modifiedBy) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.registeredName = registeredName;
        this.active = active;
        this.createdBy = modifiedBy;
        this.updatedBy = modifiedBy;
    }

    public UserCredentials(UUID id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = Instant.now();
    }

}
