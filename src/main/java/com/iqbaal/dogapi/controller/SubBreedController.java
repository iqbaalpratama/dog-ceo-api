package com.iqbaal.dogapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iqbaal.dogapi.dto.BreedDTO;
import com.iqbaal.dogapi.dto.request.RequestDTO;
import com.iqbaal.dogapi.service.SubBreedService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Sub-breed API", description = "Group to Create, Read, Update or Delete Dog Sub-breed")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/breeds")
public class SubBreedController {

    private final SubBreedService subBreedService;

    @PostMapping(
        path = "/{breedName}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> storeSubBreed(@PathVariable String breedName, @RequestBody List<String> subBreedNames){
        return new ResponseEntity<>(subBreedService.storeSubBreed(breedName, subBreedNames), HttpStatus.OK);
    }

    @PutMapping(
        path = "/{breedName}/{subBreedName}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> updateSubBreed(@PathVariable String breedName, @PathVariable String subBreedName, @RequestBody RequestDTO subBreedRequestDTO){
        return new ResponseEntity<>(subBreedService.updateSubBreed(breedName, subBreedName, subBreedRequestDTO.getName()), HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/{breedName}/{subBreedName}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BreedDTO> deleteSubBreed(@PathVariable String breedName, @PathVariable String subBreedName){
        return new ResponseEntity<>(subBreedService.delete(breedName, subBreedName),HttpStatus.OK);
    }
}
