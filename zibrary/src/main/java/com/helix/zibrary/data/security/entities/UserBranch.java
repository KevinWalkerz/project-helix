package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.data.setup.entities.Branch;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "sec_user_branch")
public class UserBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredentials user;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
