package com.twitter.exceptions;

public class FollowException extends Throwable {

    private static final long serialVersionUID = 1L;

    public FollowException(){
        super("users cannot follow themselves");
    }
}
