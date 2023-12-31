package com.iqbaal.dogapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI(){
        Info info = new Info()
        .title("Dog Ceo REST API")
        .version("1.0")
        .description("This API consumes and process data from https://dog.ceo/dog-api/");

        return new OpenAPI().info(info);
    }
}
