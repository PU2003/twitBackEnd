package com.twitter.exceptions;

public class PostDoesNotExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public PostDoesNotExistException() {
        super("The post requested does not exist");
    }
}
