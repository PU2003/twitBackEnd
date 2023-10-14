package com.twitter.services.interfaces;

public interface EmailSender {

    public void sendEmail(String toAddress,String subject,String content)throws Exception;
}
