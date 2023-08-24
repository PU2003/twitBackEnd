package com.twitter.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    private static final Long serialVersionUID = 1L;
    public EmailAlreadyTakenException(){
        super("The email provided is already taken");
    }
}
