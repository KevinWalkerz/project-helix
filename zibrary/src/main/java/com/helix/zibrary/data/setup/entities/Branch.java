package com.helix.zibrary.data.setup.entities;


import com.helix.zibrary.data.domain.base.UUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "stp_branch")
public class Branch extends UUIDEntity {

    private String name;

    private String address;

    private String description;

    private String phoneNo;

    private String faxNo;

    private String emailAddress;

    private String npwpNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNpwpNo() {
        return npwpNo;
    }

    public void setNpwpNo(String npwpNo) {
        this.npwpNo = npwpNo;
    }
}
