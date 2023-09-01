package com.iqbaal.dogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.entity.Image;
import com.iqbaal.dogapi.entity.SubBreed;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBreedAndSubBreed(Breed breed, SubBreed subBreed);
    List<Image> findByBreed(Breed breed);
    Optional<Image> findFirstByBreedAndSubBreedAndImageLink(Breed breed, SubBreed subBreed, String imageLink);
    Optional<Image> findFirstByBreedAndImageLink(Breed breed, String imageLink);
    boolean existsByImageLink(String imageLink);
}
