package com.empresaanimal.Refugio.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresaanimal.Refugio.enums.EstadoSalud;
import com.empresaanimal.Refugio.enums.PuestoEmpleado;
import com.empresaanimal.Refugio.model.Adopcion;
import com.empresaanimal.Refugio.model.Adoptante;
import com.empresaanimal.Refugio.model.Animal;
import com.empresaanimal.Refugio.model.Empleado;
import com.empresaanimal.Refugio.model.HistorialMedico;
import com.empresaanimal.Refugio.repository.AdopcionRepository;
import com.empresaanimal.Refugio.repository.AdoptanteRepository;
import com.empresaanimal.Refugio.repository.AnimalRepository;
import com.empresaanimal.Refugio.repository.EmpleadoRepository;
import com.empresaanimal.Refugio.repository.HistorialMedicoRepository;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private AdoptanteRepository adoptanteRepository;
    
    @Autowired
    private AdopcionRepository adopcionRepository;
    
    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;
    
    /**
     * Endpoint para verificar que la conexión funciona
     */
    @GetMapping("/connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Contar registros en cada tabla
            long animales = animalRepository.count();
            long empleados = empleadoRepository.count();
            long adoptantes = adoptanteRepository.count();
            long adopciones = adopcionRepository.count();
            long historiales = historialMedicoRepository.count();
            
            response.put("status", "SUCCESS");
            response.put("message", "Conexión exitosa a YugabyteDB");
            response.put("counts", Map.of(
                "animales", animales,
                "empleados", empleados,
                "adoptantes", adoptantes,
                "adopciones", adopciones,
                "historiales_medicos", historiales
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error en la conexión: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Endpoint para insertar datos de prueba
     */
    @PostMapping("/seed-data")
    public ResponseEntity<Map<String, Object>> seedTestData() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Limpiar datos existentes (opcional, solo para pruebas)
            historialMedicoRepository.deleteAll();
            adopcionRepository.deleteAll();
            animalRepository.deleteAll();
            adoptanteRepository.deleteAll();
            empleadoRepository.deleteAll();
            
            // Crear empleados de prueba
            Empleado veterinario = new Empleado("Dr. María González", 
                                              PuestoEmpleado.VETERINARIO, 
                                              LocalDate.of(2023, 1, 15));
            Empleado cuidador = new Empleado("Juan Pérez", 
                                             PuestoEmpleado.CUIDADOR, 
                                           LocalDate.of(2023, 3, 20));
            
            veterinario = empleadoRepository.save(veterinario);
            cuidador = empleadoRepository.save(cuidador);
            
            // Crear animales de prueba
            Animal perro = new Animal("Max", "Perro", "Labrador", LocalDate.now().minusMonths(2));
           perro.setEstadoSalud(EstadoSalud.SALUDABLE);

            Animal gato = new Animal("Luna", "Gato", "Siamés", LocalDate.now().minusMonths(1));
            gato.setEstadoSalud(EstadoSalud.EN_TRATAMIENTO);
            
            
            perro = animalRepository.save(perro);
            gato = animalRepository.save(gato);
            
            // Crear adoptante de prueba
            Adoptante adoptante = new Adoptante("Ana Rodríguez", 
                                              "Calle Principal 123", 
                                              "555-1234");
            adoptante = adoptanteRepository.save(adoptante);
            
            // Crear historial médico de prueba
            HistorialMedico historial1 = new HistorialMedico(
                perro, veterinario, LocalDate.now().minusDays(10),
                "Revisión general - Animal en buen estado",
                "Vitaminas y desparasitante"
            );
            
            HistorialMedico historial2 = new HistorialMedico(
                gato, veterinario, LocalDate.now().minusDays(5),
                "Infección respiratoria leve",
                "Antibióticos por 7 días"
            );
            
            historialMedicoRepository.save(historial1);
            historialMedicoRepository.save(historial2);
            
            // Crear adopción de prueba
            Adopcion adopcion = new Adopcion(perro, adoptante, LocalDate.now().minusDays(3));
            adopcionRepository.save(adopcion);
            
            response.put("status", "SUCCESS");
            response.put("message", "Datos de prueba insertados correctamente");
            response.put("data", Map.of(
                "empleados_creados", 2,
                "animales_creados", 2,
                "adoptantes_creados", 1,
                "historiales_creados", 2,
                "adopciones_creadas", 1
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error al insertar datos: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Endpoint para probar consultas de los repositorios
     */
    @GetMapping("/test-queries")
    public ResponseEntity<Map<String, Object>> testQueries() {
        Map<String, Object> response = new HashMap<>();

        try {
            Pageable pageable = PageRequest.of(0, 10);

            // Buscar perros (paginado)
            Page<Animal> animalesPerroPage = animalRepository.findByEspecie("Perro", pageable);
            List<Animal> animalesPerro = animalesPerroPage.getContent();

            // Buscar animales saludables (paginado)
            Page<Animal> animalesSaludablesPage = animalRepository.findByEstadoSalud(EstadoSalud.SALUDABLE, pageable);
            List<Animal> animalesSaludables = animalesSaludablesPage.getContent();

            List<Empleado> empleadosVeterinarios = empleadoRepository.findByPuesto(PuestoEmpleado.VETERINARIO);
          

            response.put("status", "SUCCESS");
            response.put("message", "Consultas de prueba ejecutadas correctamente");
            response.put("data", Map.of(
                "animales_perro", animalesPerro,
                "animales_saludables", animalesSaludables,
                "empleados_veterinarios", empleadosVeterinarios
                
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Error al ejecutar consultas: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}