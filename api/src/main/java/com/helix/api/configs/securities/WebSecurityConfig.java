package com.helix.api.configs.securities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.helix.api.configs.handler.RestAccessDeniedHandler;
import com.helix.api.configs.handler.RestAuthenticationEntryPoint;
import com.helix.api.configs.securities.jwtsecurity.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig {

    private final UserCustomDetailsService userCustomDetailsService;
    private final AuthTokenFilter authenticationJwtTokenFilter;

    public WebSecurityConfig(
            UserCustomDetailsService userCustomDetailsService,
            AuthTokenFilter authenticationJwtTokenFilter
    ) {
        this.userCustomDetailsService = userCustomDetailsService;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userCustomDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
//                .exceptionHandling(
//                        exception -> exception.authenticationEntryPoint(unauthorizedHandler)
//                )
                .exceptionHandling(
                        exception -> exception.accessDeniedHandler(new RestAccessDeniedHandler()).authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/login", "/api/v1/auth/info", "/api/v1/auth/refresh", "api/v1/callback/**", "api/v1/invite/*/verify", "api/v1/invite/*/confirm"
//                                        ,"/v3/api-docs*/**",
//                                        "/configuration/**",
//                                        "/swagger-resources/**",
//                                        "/configuration/security",
//                                        "/swagger-ui.html",
//                                        "/swagger-ui/**",
//                                        "/webjars/**"
                                        ).permitAll()
                                .anyRequest().authenticated()
                );

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        configuration.setAllowedOriginPatterns(List.of(
//                "http://localhost:5173",
//                "http://localhost:8080"
//        ));
        configuration.setAllowCredentials(false);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-IP-ADDRESS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
