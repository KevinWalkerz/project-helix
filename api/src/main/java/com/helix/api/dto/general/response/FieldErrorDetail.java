package com.helix.api.dto.general.response;

import lombok.Data;

@Data
public class FieldErrorDetail {
    private String field;
    private Object rejectedValue;
    private String message;

    public FieldErrorDetail(String field, Object rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

}