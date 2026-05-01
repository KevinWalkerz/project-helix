package com.helix.zibrary.data.cache.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cache_user_refresh_token")
@Data
public class UserRefreshToken {

    @Id
    private UUID userId;

    private String token;

    private OffsetDateTime expiryDate;

}
