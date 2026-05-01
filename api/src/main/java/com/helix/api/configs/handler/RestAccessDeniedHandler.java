package com.helix.api.configs.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.helix.api.dto.general.response.ApiResponse;
import com.helix.api.dto.general.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;

public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");

        // Create the error response payload
        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Access denied",
                request.getRequestURI(),
                null
        );

        ApiResponse<ErrorResponse> apiResponse = new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                "Error",
                error
        );

        // Use ObjectMapper with support for OffsetDateTime
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Write the serialized JSON to the response
        try (PrintWriter writer = response.getWriter()) {
            writer.write(mapper.writeValueAsString(apiResponse));
            writer.flush();
        }
    }
}