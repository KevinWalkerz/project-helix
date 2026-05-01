package com.helix.zibrary.data.propertiesdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Component
@PropertySource("classpath:application.properties")
public class PropertiesData {

    //JWT - from APP Properties
    @Value("${helix.app.jwtSecret}")
    private String jwtSecret;

    @Value("${helix.app.jwtExpiration}")
    private Duration jwtExpiration;

    @Value("${helix.app.jwtWebExpiration}")
    private Duration jwtWebExpiration;

    @Value("${helix.app.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

//    OSS
    @Value("${helix.oss.endpointUrl}")
    private String ossEndpointUrl;

    @Value("${helix.oss.accessKeyId}")
    private String ossAccessKeyId;

    @Value("${helix.oss.accessKeySecret}")
    private String ossAccessKeySecret;


    //Other Component
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getJwtSecret(){
        return jwtSecret;
    }

    public Duration getJwtExpiration(){
        return jwtExpiration;
    }

    public Duration getJwtWebExpiration(){
        return jwtWebExpiration;
    }

    public Duration getRefreshTokenExpiration(){
        return refreshTokenExpiration;
    }


    public DateTimeFormatter getDateTimeFormatter() {return dateTimeFormatter;}

    public DateTimeFormatter getDateFormatter() {return dateFormatter;}

    public String getOssEndpointUrl() {
        return ossEndpointUrl;
    }

    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }

    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }

}
