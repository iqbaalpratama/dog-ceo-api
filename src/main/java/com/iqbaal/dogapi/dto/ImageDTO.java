package com.iqbaal.dogapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iqbaal.dogapi.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    public ImageDTO(List<Image> imageList){
        this.imageList = imageList;
        this.images = convertImageListToStrings(imageList);
    }
    
    @JsonProperty("images")
	private List<String> images;

	@JsonIgnore
	private List<Image> imageList;

	private List<String> convertImageListToStrings(List<Image> imageList){
		List<String> listDTO = new ArrayList<>();
		imageList.forEach(image -> {
			listDTO.add(image.getImageLink());
		});
		return listDTO;
	} 
}
