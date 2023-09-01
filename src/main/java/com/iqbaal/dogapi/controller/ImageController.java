package com.iqbaal.dogapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iqbaal.dogapi.dto.ImageDTO;
import com.iqbaal.dogapi.dto.request.ImageRequestDTO;
import com.iqbaal.dogapi.dto.request.RequestDTO;
import com.iqbaal.dogapi.service.ImageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Image API", description = "Group to Create, Read, Update or Delete Dog Image")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/breeds")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(
        path = "/{breedName}/images",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> getImagesByBreed(@PathVariable String breedName){
        return new ResponseEntity<>(imageService.getImages(breedName, null), HttpStatus.OK);
    }

    @GetMapping(
        path = "/{breedName}/{subBreedName}/images",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> getImagesByBreedAndSubBreed(@PathVariable String breedName, @PathVariable String subBreedName){
        return new ResponseEntity<>(imageService.getImages(breedName, subBreedName), HttpStatus.OK);
    }

    @PostMapping(
        path = "/{breedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> storeImageByBreed(@PathVariable String breedName, @RequestBody List<String> imageLinks){
        return new ResponseEntity<>(imageService.storeImages(breedName, null, imageLinks), HttpStatus.OK);
    }

    @PostMapping(
        path = "/{breedName}/{subBreedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> storeImageByBreedAndSubBreed(@PathVariable String breedName, @PathVariable String subBreedName, @RequestBody List<String> imageLinks){
        return new ResponseEntity<>(imageService.storeImages(breedName, subBreedName, imageLinks), HttpStatus.OK);
    }

    @PutMapping(
        path = "/{breedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> updateImageByBreed(@PathVariable String breedName, @RequestBody ImageRequestDTO imageRequestDTO){
        return new ResponseEntity<>(imageService.updateImages(breedName, null, imageRequestDTO.getImagelLinkToReplace(), imageRequestDTO.getNewImageLink()), HttpStatus.OK);
    }

    @PutMapping(
        path = "/{breedName}/{subBreedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ImageDTO> updateImageByBreedAndSubBreed(@PathVariable String breedName, @PathVariable String subBreedName, @RequestBody ImageRequestDTO imageRequestDTO){
        return new ResponseEntity<>(imageService.updateImages(breedName, subBreedName, imageRequestDTO.getImagelLinkToReplace(), imageRequestDTO.getNewImageLink()), HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/{breedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteImageByBreed(@PathVariable String breedName, @RequestBody RequestDTO requestDTO){
        imageService.delete(breedName, null, requestDTO.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/{breedName}/{subBreedName}/images",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteImageByBreedAndSubBreed(@PathVariable String breedName, @PathVariable String subBreedName, @RequestBody RequestDTO requestDTO){
        imageService.delete(breedName, subBreedName, requestDTO.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
