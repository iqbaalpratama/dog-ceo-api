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

import com.iqbaal.dogapi.dto.BreedDTO;
import com.iqbaal.dogapi.dto.request.RequestDTO;
import com.iqbaal.dogapi.service.BreedService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Breed API", description = "Group to Create, Read, Update or Delete Dog Breed")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/breeds")
public class BreedController {

    private final BreedService breedService;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<BreedDTO>> getAllBreed(){
        return new ResponseEntity<>(breedService.getAllBreed(), HttpStatus.OK);
    }

    @GetMapping(
        path = "/{breedName}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> getBreed(@PathVariable String breedName){
        return new ResponseEntity<>(breedService.getBreedByName(breedName), HttpStatus.OK);
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> storeBreed(@RequestBody RequestDTO breedRequestDTO){
        return new ResponseEntity<>(breedService.store(breedRequestDTO.getName()), HttpStatus.OK);
    }

    @PutMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> updateBreed(@RequestBody RequestDTO breedRequestDTO){
        return new ResponseEntity<>(breedService.store(breedRequestDTO.getName()), HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/{breedName}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteBreed(@PathVariable String breedName){
        breedService.delete(breedName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
