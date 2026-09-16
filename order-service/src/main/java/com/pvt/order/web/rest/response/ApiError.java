package com.pvt.order.web.rest.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String field;
    private String code;
    private String message;
}
