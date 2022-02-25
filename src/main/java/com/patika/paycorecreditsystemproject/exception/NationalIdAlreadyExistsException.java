package com.patika.paycorecreditsystemproject.exception;

public class NationalIdAlreadyExistsException extends RuntimeException{
    public NationalIdAlreadyExistsException() {
        super("Sorry customer of this national Id already exists.");
    }
}
