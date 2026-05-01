package com.helix.api.dto.general.mapper;

import com.helix.api.dto.general.response.FieldErrorDetail;

public class ErrorMapper {

    public static FieldErrorDetail createFieldErrorDetail(String fieldError, String rejectedValue, String message){
         return new FieldErrorDetail(fieldError, rejectedValue, message);
    }

}
