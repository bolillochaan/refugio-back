package com.empresaanimal.Refugio.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    private final EmailService emailService;
    private final AnimalService animalService;

    @Autowired
    public AnimalEmailController(EmailService emailService, AnimalService animalService) {
        this.emailService = emailService;
        this.animalService = animalService;
    }

    @PostMapping("/{id}/enviar-correo-notificacion")
    public void sendNotificationEmail(
            @PathVariable Long id,
            @RequestParam String actionType,
            @RequestParam String recipient) {
        
        // Obtener el animal del servicio
AnimalDto animalDto = animalService.obtenerAnimalPorId(id);
if (animalDto == null) {
    throw new RuntimeException("Animal no encontrado con ID: " + id);
}

// Construir el asunto y cuerpo del mensaje
String subject = "Notificación de " + actionType + " de animal";
String text = "Se ha " + actionType + " el animal " + animalDto.getNombre() + 
             "\n\nDetalles:\n" +
             "Nombre: " + animalDto.getNombre() + "\n" +
             "Especie: " + animalDto.getEspecie() + "\n" +
             "Raza: " + animalDto.getRaza() + "\n" +
             "Estado de salud: " + animalDto.getEstadoSalud() + "\n" +
             "Fecha de ingreso: " + animalDto.getFechaIngreso();

// Enviar la notificación por email
emailService.sendAnimalNotification(recipient, subject, text);

}
}