package com.helix.api.dto.general.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int errCode;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorDetail> errors;

    public ErrorResponse(OffsetDateTime timestamp, int errCode, String error, String message, String path, List<FieldErrorDetail> errors) {
        this.timestamp = timestamp;
        this.errCode = errCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }
}

