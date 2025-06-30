package com.empresaanimal.Refugio.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresaanimal.Refugio.dto.AnimalCreateDto;
import com.empresaanimal.Refugio.dto.AnimalDto;
import com.empresaanimal.Refugio.dto.AnimalUpdateDto;
import com.empresaanimal.Refugio.model.Animal;
import com.empresaanimal.Refugio.service.AnimalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/animales")
@CrossOrigin(origins = "http://localhost:3000")
public class AnimalController {
    
    @Autowired
    private AnimalService animalService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    // Obtener todos los animales disponibles (con logs de parámetros)
    @GetMapping
    public ResponseEntity<Page<AnimalDto>> obtenerAnimales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaIngreso") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        System.out.println("Parámetros recibidos:");
        System.out.println("page: " + page);
        System.out.println("size: " + size);
        System.out.println("sortBy: " + sortBy);
        System.out.println("sortDir: " + sortDir);

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AnimalDto> animales = animalService.obtenerAnimalesDisponibles(pageable);

        return ResponseEntity.ok(animales);
    }
    
    // Obtener animal por ID
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> obtenerAnimalPorId(@PathVariable Long id) {
        AnimalDto animal = animalService.obtenerAnimalPorId(id);
        return ResponseEntity.ok(animal);
    }
    
    // Crear nuevo animal
    @PostMapping
    public ResponseEntity<AnimalDto> crearAnimal(@Valid @RequestBody AnimalCreateDto animalCreateDto) {
        AnimalDto animalCreado = animalService.crearAnimal(animalCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalCreado);
    }

  @GetMapping("/{id}/foto")
public ResponseEntity<Map<String, String>> obtenerFotoUrlAnimal(@PathVariable Long id) {
    Optional<Animal> animalOpt = animalService.obtenerAnimalEntidadPorId(id);
    if (animalOpt.isPresent() && animalOpt.get().getFotoUrl() != null) {
        Map<String, String> response = Map.of("fotoUrl", animalOpt.get().getFotoUrl());
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.notFound().build();
    }
}
    // Actualizar animal
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> actualizarAnimal(
            @PathVariable Long id, 
            @Valid @RequestBody AnimalUpdateDto animalUpdateDto) {
        AnimalDto animalActualizado = animalService.actualizarAnimal(id, animalUpdateDto);
        return ResponseEntity.ok(animalActualizado);
    }
    
    // Eliminar animal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable Long id) {
        animalService.eliminarAnimal(id);
        return ResponseEntity.noContent().build();
    }
    
    // Buscar por especie
    @GetMapping("/especie/{especie}")
    public ResponseEntity<Page<AnimalDto>> buscarPorEspecie(
            @PathVariable String especie,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<AnimalDto> animales = animalService.buscarPorEspecie(especie, pageable);
        return ResponseEntity.ok(animales);
    }
    
    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<Page<AnimalDto>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<AnimalDto> animales = animalService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(animales);
    }
    
    // Obtener estadísticas
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> estadisticas = animalService.obtenerEstadisticasPorEspecie();
        return ResponseEntity.ok(estadisticas);
    }
    
    // Animales que requieren atención
    @GetMapping("/atencion-requerida")
    public ResponseEntity<List<AnimalDto>> obtenerAnimalesQueRequierenAtencion() {
        List<AnimalDto> animales = animalService.obtenerAnimalesQueRequierenAtencion();
        return ResponseEntity.ok(animales);
    }
    
    // Enviar correo de adopción
    @PostMapping("/enviar-correo-adopcion")
    public ResponseEntity<?> enviarCorreoAdopcion(@RequestBody AnimalDto animal) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("refugioanimalweb@gmail.com");
        message.setTo("refugioanimalweb@gmail.com"); // Cambia por el correo real del refugio
        message.setSubject("Animal adoptado: " + animal.getNombre());
        message.setText("Detalles del animal adoptado:\n" +
                "Nombre: " + animal.getNombre() + "\n" +
                "Especie: " + animal.getEspecie() + "\n" +
                "Raza: " + animal.getRaza() + "\n" +
                "Edad: " + animal.getEdadAproximada() + "\n"
                // ...otros detalles...
        );
        mailSender.send(message);
        return ResponseEntity.ok().build();
    }
}