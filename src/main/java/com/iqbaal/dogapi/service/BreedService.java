package com.iqbaal.dogapi.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import com.iqbaal.dogapi.dto.BreedDTO;
import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.exception.BreedNotFoundException;
import com.iqbaal.dogapi.mapper.Mapper;
import com.iqbaal.dogapi.repository.BreedRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BreedService {
    
    private final BreedRepository breedRepository;

    public List<BreedDTO> getAllBreed(){
        List<BreedDTO> listBreedDTOs = new ArrayList<>();
        for(Breed breed : breedRepository.findAll()){
            listBreedDTOs.add(Mapper.toDto(breed));
        }
        return listBreedDTOs;
    }

    public BreedDTO getBreedByName(String name){
        return Mapper.toDto(breedRepository.findByName(name).orElseThrow(() -> new BreedNotFoundException("Breed with name "+name+" is not found.")));
    }

    @Transactional
    public BreedDTO store(String name){
        if(breedRepository.existsByName(name)){
            throw new ConstraintViolationException("Breed with name: "+name+" is exists.", null, name);
        }
        Breed breed = new Breed();
        breed.setName(name);
        breedRepository.save(breed);
        return Mapper.toDto(breed);
    }

    @Transactional
    public BreedDTO update(String name, String updateName){
        if(breedRepository.existsByName(updateName)){
            throw new ConstraintViolationException("Breed with name: "+name+" is exists.", null, name);
        }
        Breed breed = breedRepository.findByName(name).orElseThrow(() -> new BreedNotFoundException("Breed with name "+name+" is not found."));
        breed.setName(updateName);
        breedRepository.save(breed);
        return Mapper.toDto(breed);
    }

    @Transactional
    public void delete(String name){
        Breed breed = breedRepository.findByName(name).orElseThrow(() -> new BreedNotFoundException("Breed with name "+name+" is not found."));
        breedRepository.delete(breed);
    }
}
