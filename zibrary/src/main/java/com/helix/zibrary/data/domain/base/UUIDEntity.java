package com.helix.zibrary.data.domain.base;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@MappedSuperclass
public class UUIDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Version
    private int version;

    private String createdBy;
    @Column(name = "created_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate;

    private String updatedBy;
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(OffsetDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = OffsetDateTime.now(ZoneOffset.UTC);
    }

}
