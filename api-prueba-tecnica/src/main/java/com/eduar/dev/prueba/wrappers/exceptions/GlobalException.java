package com.eduar.dev.prueba.wrappers.exceptions;


import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final String details;

    public GlobalException(String details) {
        this.details = details;
    }


}
