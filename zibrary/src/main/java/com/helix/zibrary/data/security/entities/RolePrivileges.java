package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "sec_role_privileges")
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

    @Column(name = "allowed")
    private boolean isAllowed;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }
}
