package com.helix.zibrary.data.security.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "sec_user_roles")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserCredentials getUser() {
        return user;
    }

    public void setUser(UserCredentials user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
