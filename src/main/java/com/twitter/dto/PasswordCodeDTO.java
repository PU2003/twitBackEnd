package com.twitter.dto;

import lombok.Data;

@Data
public class PasswordCodeDTO {

    private Integer code;
    private String email;
    public PasswordCodeDTO(){
        super();
    }

    public PasswordCodeDTO(Integer code, String email) {
        super();
        this.code = code;
        this.email = email;
    }
}
