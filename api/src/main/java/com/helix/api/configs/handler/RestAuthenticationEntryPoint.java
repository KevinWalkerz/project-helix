package com.helix.api.configs.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.helix.api.dto.general.response.ApiResponse;
import com.helix.api.dto.general.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("WWW-Authenticate", ""); // optional: suppress browser popup

        // Build response object
        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Unauthorized access",
                request.getRequestURI(),
                null
        );

        ApiResponse<ErrorResponse> apiResponse = new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Error",
                error
        );

        // ✅ Manually configure ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // enable OffsetDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // use ISO format

        // Write to response
        try (PrintWriter writer = response.getWriter()) {
            writer.write(mapper.writeValueAsString(apiResponse));
            writer.flush();
        }
    }
}