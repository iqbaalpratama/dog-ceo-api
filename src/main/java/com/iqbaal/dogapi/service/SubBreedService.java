package com.iqbaal.dogapi.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.iqbaal.dogapi.dto.BreedDTO;
import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.entity.SubBreed;
import com.iqbaal.dogapi.exception.BreedNotFoundException;
import com.iqbaal.dogapi.exception.SubBreedNotFoundException;
import com.iqbaal.dogapi.mapper.Mapper;
import com.iqbaal.dogapi.repository.BreedRepository;
import com.iqbaal.dogapi.repository.SubBreedRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubBreedService {

    private final BreedRepository breedRepository;
    private final SubBreedRepository subBreedRepository;
    
    @Transactional
    public BreedDTO storeSubBreed(String breedName, List<String> subBreedNames){
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        subBreedNames.forEach(subBreedName -> {
            if(!subBreedRepository.existsByName(subBreedName)){
                SubBreed subBreed = new SubBreed(breedName);
                subBreedRepository.save(subBreed);
                Set<SubBreed> subBreeds = breed.getSubBreed();
                subBreeds.add(subBreed);
                breed.setSubBreed(subBreeds);
                breedRepository.save(breed);
            }else{
                SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
                if(!breed.getSubBreed().contains(subBreed)){
                    Set<SubBreed> subBreeds = breed.getSubBreed();
                    subBreeds.add(subBreed);
                    breed.setSubBreed(subBreeds);
                    breedRepository.save(breed);
                }
            }
        });
        return Mapper.toDto(breed);
    }

    @Transactional
    public BreedDTO updateSubBreed(String breedName, String subBreedName, String updateSubBreedName){
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
        if(!breed.getSubBreed().contains(subBreed)){
            throw new SubBreedNotFoundException("Breed with name: "+breedName+" is not have subbreed with name: "+subBreedName);
        }
        Set<SubBreed> subBreeds = breed.getSubBreed();
        subBreeds.remove(subBreed);
        if(!subBreedRepository.existsByName(updateSubBreedName)){
            SubBreed updateSubBreed = new SubBreed(updateSubBreedName);
            subBreedRepository.save(updateSubBreed);
            subBreeds.add(updateSubBreed);
            breed.setSubBreed(subBreeds);
            breedRepository.save(breed);
        }else{
            SubBreed updateSubBreed = subBreedRepository.findByName(updateSubBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
            subBreeds.add(updateSubBreed);
            breed.setSubBreed(subBreeds);
            breedRepository.save(breed);
        }
        return Mapper.toDto(breed);
    }

    @Transactional
    public BreedDTO delete(String breedName, String subBreedName){
        Breed breed = breedRepository.findByName(breedName).orElseThrow(() -> new BreedNotFoundException("Breed with name "+breedName+" is not found."));
        SubBreed subBreed = subBreedRepository.findByName(subBreedName).orElseThrow(() -> new SubBreedNotFoundException("Sub-breed with name "+subBreedName+" is not found."));
        if(!breed.getSubBreed().contains(subBreed)){
            throw new SubBreedNotFoundException("Breed with name: "+breedName+" is not have subbreed with name: "+subBreedName);
        }
        Set<SubBreed> subBreeds = breed.getSubBreed();
        subBreeds.remove(subBreed);
        breed.setSubBreed(subBreeds);
        breedRepository.save(breed);
        return Mapper.toDto(breed);
    }
}
