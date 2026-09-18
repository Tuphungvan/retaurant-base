package com.pvt.restaurant.web.Advice;

import com.pvt.restaurant.web.response.ApiError;
import com.pvt.restaurant.web.response.ApiResponse;
import com.pvt.restaurant.web.response.BussinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ApiError> errors = ex.getFieldErrors().stream()
                .map(error -> new ApiError(error.getField(), error.getCode(), error.getDefaultMessage()))
                .toList();
        return ApiResponse.error(errors, HttpStatus.BAD_REQUEST, "Validation error");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(Exception ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "Failed JSON request or invalid parameter type");
    }

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBussinessException(BussinessException ex){
        ApiError error = new ApiError(null, ex.getErrorCode(), null);
        return ApiResponse.error(List.of(error), HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Internal server error: ", ex);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error. Please try again later.");    }
}
