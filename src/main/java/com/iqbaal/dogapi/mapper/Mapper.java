package com.iqbaal.dogapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.iqbaal.dogapi.dto.BreedDTO;
import com.iqbaal.dogapi.dto.SubBreedDTO;
import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.entity.SubBreed;

public class Mapper {

    public static BreedDTO toDto(Breed breed) {
		String name = breed.getName();
		List<SubBreedDTO> subBreedDTOs = (breed.getSubBreed() != null) ?
										  breed.getSubBreed()
										  	   .stream()
										  	   .map(sb -> toDto(sb))
										  	   .collect(Collectors.toList()) : null;
		
		return new BreedDTO(name, subBreedDTOs);
	}

	
	public static SubBreedDTO toDto(SubBreed subBreed) {
		String name = subBreed.getName();
		return new SubBreedDTO(name);
	}

    public static Breed toEntity(BreedDTO breedDto) {
		String name = breedDto.getName();
		return new Breed(name);
	}
	
	public static SubBreed toEntity(SubBreedDTO subBreedDto) {
		String name = subBreedDto.getName();
		return new SubBreed(name);
	}
	
}
