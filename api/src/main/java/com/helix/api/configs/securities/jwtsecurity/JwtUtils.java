package com.helix.api.configs.securities.jwtsecurity;

import com.helix.zibrary.data.propertiesdata.PropertiesData;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    PropertiesData propertiesData;

    public JwtUtils(@Autowired PropertiesData propertiesData){
        this.propertiesData = propertiesData;
    }

    public String generateJwtToken(Authentication authentication, boolean isWebLogin) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date expiration = Date.from(Instant.now().plus(propertiesData.getJwtExpiration()));
        if(isWebLogin){
            expiration = Date.from(Instant.now().plus(propertiesData.getJwtWebExpiration()));
        }

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken(String emailAddress) {
        return Jwts.builder()
                .setSubject(emailAddress)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(propertiesData.getJwtExpiration())))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(propertiesData.getJwtSecret()));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<String> getUserNameOptionalFromJwtToken(String token) {
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return Optional.ofNullable(username);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String getUserNameFromExpiredJwtToken(String expiredToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(expiredToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            // masih bisa ambil claim meski expired
            return ex.getClaims().getSubject();
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
