package com.empresaanimal.Refugio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresaanimal.Refugio.dto.AnimalDto;
import com.empresaanimal.Refugio.service.AnimalService;
import com.empresaanimal.Refugio.service.EmailService;

@RestController
@RequestMapping("/api/animales")
public class AnimalEmailController {
    
    private static final Logger logger = LoggerFactory.getLogger(AnimalEmailController.class);
    
    private final EmailService emailService;
    private final AnimalService animalService;

    @Autowired
    public AnimalEmailController(EmailService emailService, AnimalService animalService) {
        this.emailService = emailService;
        this.animalService = animalService;
    }

    @GetMapping("/test")
    public String test() {
        return "Aplicacion funcionando correctamente";
    }

    @GetMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        try {
            logger.info("Iniciando test de email...");
            emailService.sendAnimalNotification(
                "ivonne.mendezw@gmail.com", 
                "Test desde aplicación", 
                "Este es un email de prueba desde el sistema de refugio animal"
            );
            return ResponseEntity.ok("Email de prueba enviado correctamente");
        } catch (Exception e) {
            logger.error("Error en test de email: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                               .body("Error enviando email de prueba: " + e.getMessage());
        }
    }

    @GetMapping("/test-email-config")
    public ResponseEntity<String> testEmailConfig() {
        try {
            logger.info("Probando configuración de email...");
            emailService.testConnection();
            return ResponseEntity.ok("Configuración de email OK - Se envió un email de prueba");
        } catch (Exception e) {
            logger.error("Error en configuración de email: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                               .body("Error en configuración: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/enviar-correo-notificacion")
    public ResponseEntity<String> sendNotificationEmail(
            @PathVariable Long id,
            @RequestParam String actionType,
            @RequestParam String recipient) {
        
        try {
            logger.info("Enviando notificación para animal ID: {} a: {}", id, recipient);
            
            // Obtener el animal del servicio
            AnimalDto animalDto = animalService.obtenerAnimalPorId(id);
            if (animalDto == null) {
                return ResponseEntity.status(404).body("Animal no encontrado con ID: " + id);
            }

            // Construir el asunto y cuerpo del mensaje
            String subject = "Notificación de " + actionType + " de animal - " + animalDto.getNombre();
            String text = "Se ha " + actionType + " el animal " + animalDto.getNombre() + 
                         "\n\nDetalles:\n" +
                         "Nombre: " + animalDto.getNombre() + "\n" +
                         "Especie: " + animalDto.getEspecie() + "\n" +
                         "Raza: " + animalDto.getRaza() + "\n" +
                         "Estado de salud: " + animalDto.getEstadoSalud() + "\n" +
                         "Fecha de ingreso: " + animalDto.getFechaIngreso() + "\n\n" +
                         "Refugio Animal - Sistema de Gestión";

            // Enviar la notificación por email
            emailService.sendAnimalNotification(recipient, subject, text);
            
            return ResponseEntity.ok("Email enviado correctamente a: " + recipient);
            
        } catch (Exception e) {
            logger.error("Error enviando email de notificación: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                               .body("Error enviando email: " + e.getMessage());
        }
    }
}