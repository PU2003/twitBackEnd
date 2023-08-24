package com.twitter.controllers;

import com.twitter.exceptions.EmailAlreadyTakenException;
import com.twitter.models.ApplicationUser;
import com.twitter.models.RegistrationObject;
import com.twitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }
    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken(){
        return new ResponseEntity<String>("The email you provided is already in use" , HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public  ResponseEntity<String> registerUser(@RequestBody RegistrationObject ro){
        return userService.registerUser(ro);
    }
}
