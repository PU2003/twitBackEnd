package com.twitter.exceptions;

public class UnableToCreatePostException extends RuntimeException{

    private static final long serialVersionUiD = 1L;

    public UnableToCreatePostException(){
        super("Unable to create a post at this time");
    }
}
