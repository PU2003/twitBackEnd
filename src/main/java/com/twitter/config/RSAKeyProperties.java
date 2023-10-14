package com.twitter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@AllArgsConstructor
@Data
@ConfigurationProperties(prefix = "rsa")
public class RSAKeyProperties {

    private RSAPublicKey publickey;
    private RSAPrivateKey privateKey;

    public RSAKeyProperties(){

    }

}
