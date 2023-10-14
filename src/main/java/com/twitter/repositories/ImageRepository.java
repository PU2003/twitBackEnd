package com.twitter.repositories;

import com.twitter.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByImageName(String imageName);
}
