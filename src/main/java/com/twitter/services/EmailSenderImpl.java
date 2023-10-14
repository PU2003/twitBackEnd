package com.twitter.services;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.twitter.exceptions.EmailFailToSendException;
import com.twitter.services.interfaces.EmailSender;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class EmailSenderImpl implements EmailSender {

    private final Gmail gmail;

    @Autowired
    public EmailSenderImpl(Gmail gmail){
      this.gmail = gmail;
    }

    @Override
    public void sendEmail(String toAddress,String subject,String content) throws EmailFailToSendException{

        Properties props = new Properties();
        Session session = Session.getInstance(props,null);
        MimeMessage email = new MimeMessage(session);

        try{
            email.setFrom(new InternetAddress("praani2003@gmail.com"));
            email.addRecipient(javax.mail.Message.RecipientType.TO,new InternetAddress(toAddress));
            email.setSubject(subject);
            email.setText(content);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            email.writeTo(buffer);

            byte[] rawMessageBytes = buffer.toByteArray();

            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

            Message message = new Message();
            message.setRaw(encodedEmail);

            message = gmail.users().messages().send("me",message).execute();
        } catch (Exception e){
           throw new EmailFailToSendException();
        }
    }
}
