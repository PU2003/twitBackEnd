package com.twitter.controllers;

import com.twitter.exceptions.UnableToResolvePhotoException;
import com.twitter.exceptions.UnableToSavePhotoException;
import com.twitter.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {

    public final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @ExceptionHandler({UnableToSavePhotoException.class,UnableToResolvePhotoException.class})
    public ResponseEntity<String> handlePhotoException(){
        return new ResponseEntity<String>("Unable to process the photo",HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws UnableToResolvePhotoException {
        byte[] imageBytes = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageService.getImageType(fileName)))
                .body(imageBytes);
    }
}
