package com.twitter.services;

import com.twitter.exceptions.UnableToResolvePhotoException;
import com.twitter.exceptions.UnableToSavePhotoException;
import com.twitter.models.Image;
import com.twitter.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.font.MultipleMaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private static final File DIRECTORY = new File("/home/user2/Desktop/TwitterBackend/twitter-backend/img");
    private static final String URL = "http:://localhost::8082/images/";

    @Autowired
    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }
    public Image uploadImage(MultipartFile file, String prefix) throws UnableToSavePhotoException {
        try{
           String extension = "." + file.getContentType().split("/")[1];

           File img = File.createTempFile(prefix,extension,DIRECTORY);
           file.transferTo(img);

           String imageURL = URL + img.getName();

           Image i = new Image(img.getName(),file.getContentType(),img.getPath(),imageURL);
           Image saved = imageRepository.save(i);
           return saved;

        } catch (IOException e){
            throw new UnableToSavePhotoException();
        }
    }

    public byte[] downloadImage(String filename) throws UnableToResolvePhotoException {
        try{
            Image image = imageRepository.findByImageName(filename).get();

            String filepath = image.getImagePath();
            byte[] imageBytes = Files.readAllBytes(new File(filepath).toPath());

            return imageBytes;
        } catch(IOException e){
            throw new UnableToResolvePhotoException();
        }
    }

    public String getImageType(String filename){
        Image image = imageRepository.findByImageName(filename).get();

        return image.getImageType();
    }
}
