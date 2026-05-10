package com.breno.PriceRadar_API.exceptions;


public class DuplicateItemException extends PriceRadarException {

    public DuplicateItemException(String message) {
        super(message);
    }

    public DuplicateItemException(String message, Throwable cause) {
        super(message, cause);
    }
}

