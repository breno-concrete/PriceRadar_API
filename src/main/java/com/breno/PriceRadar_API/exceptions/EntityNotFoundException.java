package com.breno.PriceRadar_API.exceptions;


public class EntityNotFoundException extends PriceRadarException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

