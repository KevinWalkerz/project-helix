package com.helix.zibrary.data.cache.entities;

import com.helix.zibrary.data.domain.base.UUIDEntity;
import com.helix.zibrary.data.enumeration.EnumVerificationStatus;
import com.helix.zibrary.data.security.entities.UserCredentials;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "cache_user_verification")
@EqualsAndHashCode(callSuper = true)
public class UserVerification extends UUIDEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserCredentials userCredentials;

    private String hashedInviteId;

    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private EnumVerificationStatus verificationStatus;

    @Column(name = "verified_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime verifiedDate;

    @Column(name = "is_expired")
    private boolean expired;

    @Column(name = "expired_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expiredDate;

    private long expiredDurationMin;

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public String getHashedInviteId() {
        return hashedInviteId;
    }

    public void setHashedInviteId(String hashedInviteId) {
        this.hashedInviteId = hashedInviteId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EnumVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(EnumVerificationStatus verificationStatus) {
        if(verificationStatus == EnumVerificationStatus.VERIFIED){
            this.verifiedDate = OffsetDateTime.now(ZoneOffset.UTC);
        }else{
            this.verifiedDate = null;
        }

        this.verificationStatus = verificationStatus;
    }

    public OffsetDateTime getVerifiedDate() {
        return verifiedDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public OffsetDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(OffsetDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getExpiredDurationMin() {
        return expiredDurationMin;
    }

    public void setExpiredDurationMin(long expiredDurationMin) {
        this.expiredDurationMin = expiredDurationMin;
    }
}
