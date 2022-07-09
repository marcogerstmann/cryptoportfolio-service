package com.marcogerstmann.cryptoportfolio.service.config;

import static com.marcogerstmann.cryptoportfolio.service.config.SerializationConfigBuilder.buildJackson2ObjectMapperBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean(name = "objectMapper")
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return buildJackson2ObjectMapperBuilder();
    }
}
