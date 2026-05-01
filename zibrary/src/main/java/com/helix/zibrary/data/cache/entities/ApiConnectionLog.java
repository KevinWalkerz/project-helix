package com.helix.zibrary.data.cache.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "cache_api_connection_log")
@Data
public class ApiConnectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String targetServiceUrl;

    @Column(name = "execute_datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime executeDatetime;

    @Column(name = "response_datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime responseDatetime;

    private String requestBodyString;

    private String responseCode;

    private String responseBodyString;

    @PrePersist
    public void prePersist() {
        this.executeDatetime = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void preUpdate() {
        this.responseDatetime = OffsetDateTime.now(ZoneOffset.UTC);
    }

}
