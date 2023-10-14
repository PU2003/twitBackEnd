package com.twitter.exceptions;

public class UnableToResolvePhotoException extends Exception{

    public static final long serialVersionUID = 1L;

    public UnableToResolvePhotoException(){
        super("The photo you are looking for cannot be found");
    }
}
