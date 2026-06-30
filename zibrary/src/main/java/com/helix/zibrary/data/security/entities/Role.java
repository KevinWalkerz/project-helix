package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "sec_roles")
@Getter
@Setter
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private boolean active;

    private Instant createdDate;
    private Instant updatedDate;

    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "role")
    private Collection<UserRoles> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    private Collection<RolePrivileges> rolePrivileges = new ArrayList<>();

    public Collection<Privilege> getPrivileges() {
        return rolePrivileges.stream()
                .filter(RolePrivileges::isAllow)
                .map(RolePrivileges::getPrivilege)
                .collect(Collectors.toList());
    }

    public Role() {

    }

    public Role(UUID id) {
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
