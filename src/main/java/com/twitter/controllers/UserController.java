package com.twitter.controllers;

import com.google.common.net.HttpHeaders;
import com.twitter.exceptions.FollowException;
import com.twitter.exceptions.UnableToSavePhotoException;
import com.twitter.models.ApplicationUser;
import com.twitter.services.ImageService;
import com.twitter.services.TokenService;
import com.twitter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final ImageService imageService;

    @Autowired
    public UserController(UserService userService,TokenService tokenService,ImageService imageService){
        this.userService = userService;
        this.tokenService = tokenService;
        this.imageService = imageService;
    }

    @GetMapping("/verify")
    public ApplicationUser verifyIdentity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String username = tokenService.getUsernameFromToken(token);

        return userService.getUserByUsername(username);
    }
    @PostMapping("/pfp")
    public ApplicationUser uploadProfilePicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestParam("image")MultipartFile file) throws UnableToSavePhotoException {

        String username = tokenService.getUsernameFromToken(token);
        return userService.setProfileOrBannerPicture(username,file,"pfp");
    }

    @PostMapping("/banner")
    public ApplicationUser uploadBannerPicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestParam("image")MultipartFile file) throws UnableToSavePhotoException{
        String username = tokenService.getUsernameFromToken(token);
        return userService.setProfileOrBannerPicture(username,file,"bnr");
    }
    @PutMapping("/")
    public ApplicationUser updateUser(@RequestBody ApplicationUser u){
        return userService.updateUser(u);
    }

    @ExceptionHandler({FollowException.class})
    public ResponseEntity<String> handleFollowException(){
        return new ResponseEntity<String>("Users cannot follow themselves",HttpStatus.FORBIDDEN);
    }

    @PutMapping("/follow")
    public Set<ApplicationUser> followUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody LinkedHashMap<String,String> body) throws FollowException {
        String loggedInUser = tokenService.getUsernameFromToken(token);
        String followedUser = body.get("followedUser");

        return userService.followUser(loggedInUser,followedUser);
    }

    @GetMapping("/following/{username}")
    public Set<ApplicationUser> getFollowingList(@PathVariable("username") String username){
        return userService.retrieveFollowingList(username);
    }

    @GetMapping("/followers/{username}")
    public Set<ApplicationUser> getFollowersList(@PathVariable("username") String username){
        return userService.retrieveFollowersList(username);
    }
}
