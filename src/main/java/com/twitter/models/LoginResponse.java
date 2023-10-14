package com.twitter.models;

import lombok.Data;

@Data
public class LoginResponse {
    private ApplicationUser user;
    private String token;

    public LoginResponse(){
        super();
    }
    public LoginResponse(ApplicationUser user,String token){
        super();
        this.user = user;
        this.token = token;
    }
}
