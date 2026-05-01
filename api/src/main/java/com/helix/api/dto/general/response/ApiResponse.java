package com.helix.api.dto.general.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String message;
    private String statusMessage;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int status, String message, String statusMessage, T data){
        this.status = status;
        this.message = message;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}