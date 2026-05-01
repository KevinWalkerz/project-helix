package com.helix.zibrary.data.security.entities;

import com.helix.zibrary.data.domain.base.UUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sec_user_credentials")
public class UserCredentials extends UUIDEntity {

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String userName;

    private String hashedPassword;

    private String registeredName;

    @Column(name = "isactive")
    private boolean isActive;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
