package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.enumeration.security.AccessType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sec_privileges")
@Getter
@Setter
public class Privilege{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String menuName;
    private String subMenuName;
    private String pageName;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    private Instant createdDate;
    private Instant updatedDate;

    public Privilege() {

    }

    public Privilege(UUID id) {
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
