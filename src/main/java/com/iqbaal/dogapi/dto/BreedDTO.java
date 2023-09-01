package com.iqbaal.dogapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedDTO {

	public BreedDTO(String name, List<SubBreedDTO> subBreedDTOs){
		this.name = name;
		this.subBreedDTOs = subBreedDTOs;
		this.subbreeds = convertSubBreedDTOsToList(subBreedDTOs);
	}
	
	@JsonProperty("breedName")
	private String name;
	
	@JsonProperty("subBreeds")
	private List<String> subbreeds;

	@JsonIgnore
	private List<SubBreedDTO> subBreedDTOs;

	private List<String> convertSubBreedDTOsToList(List<SubBreedDTO> subBreedDTOs){
		List<String> listDTO = new ArrayList<>();
		subBreedDTOs.forEach(subBreedDTO -> {
			listDTO.add(subBreedDTO.getName());
		});
		return listDTO;
	} 
}
