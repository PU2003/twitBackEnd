package com.twitter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_name",unique = true)
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_path")
    @JsonIgnore
    private String imagePath;

    @Column(name = "image_url")
    private String imageURL;

    public Image(){
        super();
    }

    public Image(Long imageId, String imageName, String imageType, String imagePath, String imageURL) {
        super();
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageURL = imageURL;
    }

    public Image(String imageName, String imageType, String imagePath, String imageURL) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
