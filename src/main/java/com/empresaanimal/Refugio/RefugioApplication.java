package com.empresaanimal.Refugio;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class RefugioApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RefugioApplication.class);
        // Opcional: desactivar el banner de Spring Boot al iniciar
        // app.setBannerMode(Banner.Mode.OFF);

        app.run(args);
    }

    // Método que se ejecuta justo después de que Spring termine de iniciar
    @PostConstruct
    public void init() {
        System.out.println("🚀 RefugioApplication ha iniciado correctamente");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
