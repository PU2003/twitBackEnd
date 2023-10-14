package com.twitter.exceptions;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException(){
        super("Username or password doesnot exist");
    }
}
