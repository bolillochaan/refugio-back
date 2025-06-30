package com.empresaanimal.Refugio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // <-- Permite todos los endpoints<-- Permite todos los endpoints
                        .allowedOrigins("*") // <-- Permite cualquier origen temporalmentequier origen temporalmente
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(false); // <-- Debe ser false si usas '*'; // <-- Debe ser false si usas '*'
            }
        };
    }
}
