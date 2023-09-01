package com.iqbaal.dogapi.seeder;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbaal.dogapi.entity.Breed;
import com.iqbaal.dogapi.entity.Image;
import com.iqbaal.dogapi.entity.SubBreed;
import com.iqbaal.dogapi.repository.BreedRepository;
import com.iqbaal.dogapi.repository.ImageRepository;
import com.iqbaal.dogapi.repository.SubBreedRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final BreedRepository breedRepository;
    private final SubBreedRepository subBreedRepository;
    private final ImageRepository imageRepository;

    @Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
        String breedsUrl = "https://dog.ceo/api/breeds/list/all";
		Duration timeoutDuration = Duration.ofMillis(5000);
		
		String messageBody = getRestAPIResources(breedsUrl, timeoutDuration);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String[]> result = null;
		try {
			JsonNode breedsJson = objectMapper.readTree(messageBody).get("message");
			result = objectMapper.convertValue(breedsJson, new TypeReference<Map<String, String[]>>(){});
            result.forEach((k,v) -> {

				Breed breed = new Breed();
				breed.setName(k);
				breedRepository.save(breed);
				
				if(v.length == 0){
					fetchImages(breed, null);
				} else{
					Set<SubBreed> subBreeds = new HashSet<>();
					for(String subBreedName : v){
						SubBreed subBreed;
						if(!subBreedRepository.existsByName(subBreedName)){
							subBreed = new SubBreed();
							subBreed.setName(subBreedName);
							subBreedRepository.save(subBreed);
						} else{
							subBreed = subBreedRepository.findByName(subBreedName)
							.orElseThrow(() -> new RuntimeException("Error: Subbreed is not found."));
						}
						subBreeds.add(subBreed);
						fetchImages(breed, subBreed);
					}
					breed.setSubBreed(subBreeds);
				}
				
				breedRepository.save(breed);
            }); 
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			result = null;
		}
    }
    
    @Transactional
	private void fetchImages(Breed breed, SubBreed subBreed){
		String breedsUrl = "";
		if(subBreed == null){
			breedsUrl = "https://dog.ceo/api/breed/"+breed.getName()+"/images";
		}else{
			breedsUrl = "https://dog.ceo/api/breed/"+breed.getName()+"/"+subBreed.getName()+"/images";
		}
		Duration timeoutDuration = Duration.ofMillis(5000);
		
		String messageBody = getRestAPIResources(breedsUrl, timeoutDuration);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String[] result = null;
		try {
			JsonNode breedsJson = objectMapper.readTree(messageBody).get("message");
			result = objectMapper.convertValue(breedsJson, new TypeReference<String[]>(){});
            Set<Image> images = new HashSet<>();
			for(String res : result){
				Image image = new Image();
				image.setImageLink(res);
				image.setBreed(breed);
				if(subBreed != null){
					image.setSubBreed(subBreed);
				}
				imageRepository.save(image);
                images.add(image);
			}
            breed.setImages(images);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			result = null;
		}
	}

    private String getRestAPIResources(String url, Duration timeoutDuration) {
		WebClient client = WebClient.create();

		ResponseSpec responseSpec = client.get()
		    .uri(url)
		    .retrieve();

		String responseBody = responseSpec.bodyToMono(String.class)
							.timeout(timeoutDuration)
							.onErrorResume(throwable -> timeoutFallBackMethod(throwable))
							.block();
		

		if (responseBody.isEmpty()) {
			return null;
		}else {
			return responseBody;
		}
	}

	private Mono<? extends String> timeoutFallBackMethod(Throwable throwable) {
		return Mono.just("");
	}
}
