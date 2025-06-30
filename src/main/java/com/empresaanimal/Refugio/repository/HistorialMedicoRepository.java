package com.empresaanimal.Refugio.repository;

import com.empresaanimal.Refugio.model.HistorialMedico;
import com.empresaanimal.Refugio.model.Animal;
import com.empresaanimal.Refugio.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    
    // Buscar historial por animal
    List<HistorialMedico> findByAnimal(Animal animal);
    
    // Buscar historial por animal ordenado por fecha (más reciente primero)
    List<HistorialMedico> findByAnimalOrderByFechaConsultaDesc(Animal animal);
    
    // Buscar historial por empleado (veterinario)
    List<HistorialMedico> findByEmpleado(Empleado empleado);
    
    // Buscar consultas en un rango de fechas
    List<HistorialMedico> findByFechaConsultaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar última consulta de un animal (devuelve la lista ordenada, toma el primero en el servicio)
    @Query("SELECT h FROM HistorialMedico h WHERE h.animal = :animal ORDER BY h.fechaConsulta DESC")
    List<HistorialMedico> findUltimaConsultaByAnimal(@Param("animal") Animal animal);
    
    // Buscar por diagnóstico que contenga cierto texto
    List<HistorialMedico> findByDiagnosticoContainingIgnoreCase(String diagnostico);
    
    // Buscar consultas del día actual
    @Query("SELECT h FROM HistorialMedico h WHERE h.fechaConsulta = CURRENT_DATE")
    List<HistorialMedico> findConsultasDelDia();
    
    // Contar consultas por veterinario
    @Query("SELECT COUNT(h) FROM HistorialMedico h WHERE h.empleado = :veterinario")
    Long countConsultasByVeterinario(@Param("veterinario") Empleado veterinario);
    
    // Buscar animales que necesitan seguimiento (última consulta hace más de X días)
    @Query("SELECT DISTINCT h.animal FROM HistorialMedico h WHERE h.fechaConsulta = " +
           "(SELECT MAX(h2.fechaConsulta) FROM HistorialMedico h2 WHERE h2.animal = h.animal) " +
           "AND h.fechaConsulta < :fechaLimite")
    List<Animal> findAnimalesNecesitanSeguimiento(@Param("fechaLimite") LocalDate fechaLimite);
}