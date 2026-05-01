package com.helix.api.configs.handler.exception;

import com.helix.api.dto.general.response.ApiResponse;
import com.helix.api.dto.general.response.ErrorResponse;
import com.helix.api.dto.general.response.FieldErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new FieldErrorDetail(
                        err.getField(),
                        err.getRejectedValue(),
                        err.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiResponse<?> response = buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String message = String.format("Missing required parameter: %s", ex.getParameterName());
        ApiResponse<?> response = buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        ApiResponse<?> response = buildErrorResponse(HttpStatus.NOT_FOUND, "Endpoint not found", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ApiResponse<?> response = buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String message = "HTTP method not supported: " + ex.getMethod();
        ApiResponse<?> response = buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<FieldErrorDetail> violations = ex.getConstraintViolations().stream()
                .map(cv -> new FieldErrorDetail(
                        cv.getPropertyPath().toString(),
                        null,
                        cv.getMessage()))
                .collect(Collectors.toList());

        ApiResponse<?> response = buildErrorResponse(HttpStatus.BAD_REQUEST, "Constraint violation", request.getRequestURI(), violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ApiResponse<?> response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Malformed or missing JSON body: " + ex.getMostSpecificCause().getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ApiResponse<?>> handleInternalServerErrorException(String errorMessage, Long errorCode, HttpServletRequest request) {
        ApiResponse<?> response = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage + errorCode.toString(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiResponse<?> response = buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static ResponseEntity<ApiResponse<?>> handleCustomException(String message, HttpServletRequest request){
        ApiResponse<?> response = buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<ApiResponse<?>> handleCustomFieldErrorException(String message, List<FieldErrorDetail> fieldError, HttpServletRequest request){
        ApiResponse<?> response = buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI(), fieldError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<ApiResponse<?>> handleSuccessResponse(String successMessage, Object data){
        ApiResponse<?> response = buildSuccessResponse(HttpStatus.OK, successMessage, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Utility method
    public static ApiResponse<?> buildErrorResponse(HttpStatus status, String message, String path) {
        return new ApiResponse<>(status.value(), "Error", new ErrorResponse(OffsetDateTime.now(ZoneOffset.ofHours(0)), status.value(), status.getReasonPhrase(), message, path, null));
    }

    public static ApiResponse<?> buildInternalErrorResponse(HttpStatus status, Long errCode, String message, String path) {
        return new ApiResponse<>(status.value(), "Error", new ErrorResponse(OffsetDateTime.now(ZoneOffset.ofHours(0)), status.value(), status.getReasonPhrase(), message, path, null));
    }

    public static ApiResponse<?> buildErrorResponse(HttpStatus status, String message, String path, List<FieldErrorDetail> details) {
        return new ApiResponse<>(status.value(), "Error", new ErrorResponse(OffsetDateTime.now(ZoneOffset.ofHours(0)), status.value(), status.getReasonPhrase(), message, path, details));
    }

    public static ApiResponse<?> buildSuccessResponse(HttpStatus status, String successMessage, Object data) {
        if(successMessage != null){
            return new ApiResponse<>(status.value(), "Success", successMessage,  data);
        }else{
            return new ApiResponse<>(status.value(), "Success",  data);
        }
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<ApiResponse> handleMissingParams(MissingServletRequestParameterException ex) {
//        ApiResponse<String> apiResponse = new ApiResponse<>(400, "Request Parameter Error", "Missing required parameter " + ex.getParameterName());
//        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<ApiResponse> handleNotFoundException(NoHandlerFoundException ex) {
//        ApiResponse<String> apiResponse = new ApiResponse<>(404, "Error Not Found", "Requested URL Not Found");
//        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
//    }
//
//    // Handle 401 Unauthorized
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
//        ApiResponse<String> apiResponse = new ApiResponse<>(401, "Unauthorized Error", "You don't have permission to access this API");
//        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    // Handle 400 Bad Request
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
//
//        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        ApiResponse<String> apiResponse = new ApiResponse<>(400, "Validation Failed", errorMessage);
//        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//    }
}