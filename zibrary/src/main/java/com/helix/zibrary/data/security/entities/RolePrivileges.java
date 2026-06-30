package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sec_role_privileges")
@Getter
@Setter
public class RolePrivileges{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Privilege privilege;

    private boolean allow;

    private Instant createdDate;
    private String createdBy;

    public RolePrivileges() {

    }

    public RolePrivileges(UUID roleId, UUID privilegeId, boolean allow, String modifiedBy) {
        this.role = new Role(roleId);
        this.privilege = new Privilege(privilegeId);
        this.allow = allow;
        this.createdBy = modifiedBy;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdDate = now;
    }
}
