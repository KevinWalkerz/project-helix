package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.data.domain.base.UUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sec_privileges")
public class Privilege extends UUIDEntity {

    private String name;
    private String moduleName;
    private String accessType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}
