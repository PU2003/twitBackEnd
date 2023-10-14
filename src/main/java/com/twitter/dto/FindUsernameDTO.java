package com.twitter.dto;

import lombok.Data;

@Data
public class FindUsernameDTO {

    private String email;
    private String phone;
    private String username;

    public FindUsernameDTO(){
        super();
    }
    public FindUsernameDTO(String email, String phone, String username) {
        super();
        this.email = email;
        this.phone = phone;
        this.username = username;
    }
}
