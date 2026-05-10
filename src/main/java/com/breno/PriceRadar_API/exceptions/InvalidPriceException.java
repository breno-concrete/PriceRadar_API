package com.breno.PriceRadar_API.exceptions;


public class InvalidPriceException extends PriceRadarException {

    public InvalidPriceException(String message) {
        super(message);
    }

    public InvalidPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}

