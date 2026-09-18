package com.pvt.restaurant.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private List<ApiError> errors;
    private String message;
    private T data;
    private final Instant timestamp = Instant.now();
    private PageMeta meta;

    //success
    public static <T> ApiResponse<List<T>> ofPage(Page<T> page){
        ApiResponse<List<T>> res = new ApiResponse<>();
        res.message = "success";
        res.data = page.getContent();
        res.meta = new PageMeta(page.getNumber(), page.getTotalPages(), page.getSize(), page.getTotalElements());
        return res;
    }

    public static <T> ApiResponse<T> of(T data){
        ApiResponse<T> res = new ApiResponse<>();
        res.message = "success";
        res.data = data;
        return res;
    }

    //error
    public static ResponseEntity<ApiResponse<Void>> error(List<ApiError> errors, HttpStatus status, String message){
        ApiResponse<Void> res = new ApiResponse<>();
        res.message = message;
        res.errors = errors;
        return ResponseEntity.status(status).body(res);
    }

    public static ResponseEntity<ApiResponse<Void>> error(HttpStatus status, String message){
        ApiResponse<Void> res = new ApiResponse<>();
        res.message = message;
        return ResponseEntity.status(status).body(res);
    }
}
