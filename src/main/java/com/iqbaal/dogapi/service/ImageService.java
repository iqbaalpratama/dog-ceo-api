package com.iqbaal.dogapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import com.iqbaal.dogapi.dto.ImageDTO;
import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.entity.Image;
import com.iqbaal.dogapi.entity.SubBreed;
import com.iqbaal.dogapi.exception.BreedNotFoundException;
import com.iqbaal.dogapi.exception.ImageNotFoundException;
import com.iqbaal.dogapi.exception.SubBreedNotFoundException;
import com.iqbaal.dogapi.repository.BreedRepository;
import com.iqbaal.dogapi.repository.ImageRepository;
import com.iqbaal.dogapi.repository.SubBreedRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final BreedRepository breedRepository;
    private final SubBreedRepository subBreedRepository;
    private final ImageRepository imageRepository;

    public ImageDTO getImages(String breedName, String subBreedName){
        List<Image> imageList = new ArrayList<>();
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        if(subBreedName != null){
            SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
            imageList = imageRepository.findByBreedAndSubBreed(breed, subBreed);
        } else{
            imageList = imageRepository.findByBreed(breed);
        }
        return new ImageDTO(imageList);  
    }

    @Transactional
    public ImageDTO storeImages(String breedName, String subBreedName, List<String> imageLinks){
        List<Image> imageList = new ArrayList<>();
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        if(subBreedName != null){
            SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
            if(!breed.getSubBreed().contains(subBreed)){
                throw new SubBreedNotFoundException("Breed with name: "+breedName+" is not have subbreed with name: "+subBreedName);
            }
            checkImageLink(imageLinks);
            List<Image> imageListStored = new ArrayList<>();
            imageLinks.forEach(imageLink -> {
                Image image = new Image();
                image.setImageLink(imageLink);
                image.setBreed(breed);
                image.setSubBreed(subBreed);
                imageRepository.save(image);
                imageListStored.add(image);
            });
            imageList = imageListStored;
        } else{
            checkImageLink(imageLinks);
            List<Image> imageListStored = new ArrayList<>();
            imageLinks.forEach(imageLink -> {
                Image image = new Image();
                image.setImageLink(imageLink);
                image.setBreed(breed);
                imageRepository.save(image);
                imageListStored.add(image);
            });
            imageList = imageListStored;
        }
        return new ImageDTO(imageList);  
    }

    @Transactional
    public ImageDTO updateImages(String breedName, String subBreedName, String currentImageLink, String newImageLink){
        List<Image> imageList = new ArrayList<>();
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        if(subBreedName != null){
            SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
            if(!breed.getSubBreed().contains(subBreed)){
                throw new SubBreedNotFoundException("Breed with name: "+breedName+" is not have subbreed with name: "+subBreedName);
            }
            Image image = imageRepository.findFirstByBreedAndSubBreedAndImageLink(breed, subBreed, currentImageLink)
                            .orElseThrow(() -> new ImageNotFoundException("Image with current link: "+currentImageLink+" and have breed-subbreed with name: "+breedName+"-"+subBreedName+" is not found"));
            if(!isImageLinkValid(newImageLink)){
                throw new ConstraintViolationException("This image link: "+ newImageLink+" is not valid.", null, newImageLink);
            }
            image.setImageLink(newImageLink);
            imageRepository.save(image);
            imageList.add(image);
        } else{
            Image image = imageRepository.findFirstByBreedAndImageLink(breed, currentImageLink)
                            .orElseThrow(() -> new ImageNotFoundException("Image with current link: "+currentImageLink+" and have breed with name: "+breedName+" is not found"));
            if(!isImageLinkValid(newImageLink)){
                throw new ConstraintViolationException("This image link: "+ newImageLink+" is not valid.", null, newImageLink);
            }
            image.setImageLink(newImageLink);
            imageRepository.save(image);
            imageList.add(image);
        }
        return new ImageDTO(imageList);  
    }

    @Transactional
    public void delete(String breedName, String subBreedName, String imageLink){
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        if(subBreedName != null){
            SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
            if(!breed.getSubBreed().contains(subBreed)){
                throw new SubBreedNotFoundException("Breed with name: "+breedName+" is not have subbreed with name: "+subBreedName);
            }
            Image image = imageRepository.findFirstByBreedAndSubBreedAndImageLink(breed, subBreed, imageLink)
                            .orElseThrow(() -> new ImageNotFoundException("Image with current link: "+imageLink+" and have breed-subbreed with name: "+breedName+"-"+subBreedName+" is not found"));
            imageRepository.delete(image);
        } else{
            Image image = imageRepository.findFirstByBreedAndImageLink(breed, imageLink)
                            .orElseThrow(() -> new ImageNotFoundException("Image with current link: "+imageLink+" and have breed with name: "+breedName+" is not found"));
            imageRepository.delete(image);
        } 
    }

    private boolean isImageLinkValid(String imageLink){
        Pattern pattern = Pattern.compile("https://+[A-Za-z0-9]+.(jpg|png)");
        return pattern.matcher(imageLink).matches();
    }

    private void checkImageLink(List<String> imageLinks){
        imageLinks.forEach(imageLink -> {
            if(!isImageLinkValid(imageLink)){
                throw new ConstraintViolationException("This image link: "+ imageLink+" is not valid.", null, imageLink);
            }
        });
    }
}
