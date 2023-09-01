package com.iqbaal.dogapi.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDTO {
    
    @NotBlank
    @Size(max = 100)
    private String imagelLinkToReplace;

    @NotBlank
    @Size(max = 100)
    private String newImageLink;
}
