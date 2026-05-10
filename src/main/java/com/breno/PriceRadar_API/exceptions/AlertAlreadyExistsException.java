package com.breno.PriceRadar_API.exceptions;

public class AlertAlreadyExistsException extends PriceRadarException {

    public AlertAlreadyExistsException(String message) {
        super(message);
    }

    public AlertAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

