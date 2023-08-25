package com.twitter.exceptions;

public class UserDoesNotExistException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public UserDoesNotExistException(){
        super("The user you are looking for doesnot exist");
    }
}
