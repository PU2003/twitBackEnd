package com.twitter.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public IncorrectVerificationCodeException(){
        super("The code passed didn't matches the user's verification code");
    }
}
