package com.pvt.restaurant.web.response;

import lombok.Getter;

@Getter
public class BussinessException extends RuntimeException {
    private final String errorCode;
    public BussinessException(String errorCode){
        super(errorCode);
        this.errorCode = errorCode;
    }
}
