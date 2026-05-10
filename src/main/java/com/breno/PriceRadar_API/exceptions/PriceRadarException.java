package com.breno.PriceRadar_API.exceptions;


public abstract class PriceRadarException extends RuntimeException {

    public PriceRadarException(String message) {
        super(message);
    }

    public PriceRadarException(String message, Throwable cause) {
        super(message, cause);
    }
}

