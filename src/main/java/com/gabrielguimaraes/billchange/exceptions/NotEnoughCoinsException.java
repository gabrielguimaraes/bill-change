package com.gabrielguimaraes.billchange.exceptions;

public class NotEnoughCoinsException extends RuntimeException {

    public NotEnoughCoinsException(String message) {
        super(message);
    }
    
}
