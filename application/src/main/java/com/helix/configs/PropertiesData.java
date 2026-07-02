package com.helix.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Component
@PropertySource("classpath:application.properties")
@Getter
public class PropertiesData {

    @Value("${helix.oss.endpointUrl}")
    private String ossEndpointUrl;

    @Value("${helix.oss.accessKeyId}")
    private String ossAccessKeyId;

    @Value("${helix.oss.accessKeySecret}")
    private String ossAccessKeySecret;
}
