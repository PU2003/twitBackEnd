package com.twitter.exceptions;

public class EmailFailToSendException extends RuntimeException{

    private static final long serialVersionUID = 1L;

      public EmailFailToSendException(){
          super("The email failed to send");
      }
}
