package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.data.domain.base.UUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sec_roles")
public class Role extends UUIDEntity {

    private String name;

    private String description;

    @Column(name = "isactive")
    private boolean isActive = true;

    @Column(name = "not_deletable")
    private boolean notDeletable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotDeletable() {
        return notDeletable;
    }

    public void setNotDeletable(boolean notDeletable) {
        this.notDeletable = notDeletable;
    }
}
