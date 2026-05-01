package com.helix.zibrary.data.cache.entities;

import com.helix.zibrary.data.enumeration.EnumVerificationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cache_user_verification_history")
@Data
public class UserVerificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID verificationId;

    @Enumerated(EnumType.STRING)
    private EnumVerificationStatus verificationStatus;

    private String statusDescription;

    @Column(name = "status_changes_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime statusChangesDate;

}
