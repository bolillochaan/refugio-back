package com.empresaanimal.Refugio.repository;

import com.empresaanimal.Refugio.enums.EstadoAdopcion;
import com.empresaanimal.Refugio.model.Adopcion;
import com.empresaanimal.Refugio.model.Adoptante;
import com.empresaanimal.Refugio.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    // Buscar por estado
    List<Adopcion> findByEstado(EstadoAdopcion estado);

    // Buscar por animal
    List<Adopcion> findByAnimal(Animal animal);

    // Buscar por adoptante
    List<Adopcion> findByAdoptante(Adoptante adoptante);

    // Buscar adopción activa de un animal específico
    Optional<Adopcion> findByAnimalAndEstado(Animal animal, EstadoAdopcion estado);

    // Buscar adopciones en un rango de fechas
    List<Adopcion> findByFechaAdopcionBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar adopciones del mes actual
    @Query("SELECT a FROM Adopcion a WHERE MONTH(a.fechaAdopcion) = MONTH(CURRENT_DATE) " +
           "AND YEAR(a.fechaAdopcion) = YEAR(CURRENT_DATE)")
    List<Adopcion> findAdopcionesDelMesActual();

    // Contar adopciones por estado
    @Query("SELECT COUNT(a) FROM Adopcion a WHERE a.estado = :estado")
    Long countByEstado(@Param("estado") EstadoAdopcion estado);

    // Verificar si un animal tiene adopción activa
    @Query("SELECT COUNT(a) > 0 FROM Adopcion a WHERE a.animal = :animal AND a.estado = 'ACTIVA'")
    boolean existeAdopcionActivaParaAnimal(@Param("animal") Animal animal);
}