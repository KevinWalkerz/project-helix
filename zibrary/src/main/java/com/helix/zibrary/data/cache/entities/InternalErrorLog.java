package com.helix.zibrary.data.cache.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name  = "cache_internal_error_log")
@Data
public class InternalErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    private OffsetDateTime errorDate;

    private String requestedBy;

    private String exceptionType;

    private String errorPath;

    private String errorMessage;

    @Column(name = "is_fixed")
    private boolean fixed;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String errorStacktrace;

    @PrePersist
    public void prePersist() {
        this.errorDate = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
