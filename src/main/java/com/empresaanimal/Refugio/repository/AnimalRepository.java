package com.empresaanimal.Refugio.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empresaanimal.Refugio.enums.EstadoAdopcion;
import com.empresaanimal.Refugio.enums.EstadoSalud;
import com.empresaanimal.Refugio.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // Animales disponibles para adopción (sin adopción o con adopción no activa)
    @Query("SELECT a FROM Animal a LEFT JOIN a.adopcion ad WHERE ad IS NULL OR ad.estado <> :estadoActivo")
    Page<Animal> findAnimalesDisponibles(@Param("estadoActivo") EstadoAdopcion estadoActivo, Pageable pageable);

    // Animales adoptados (con adopción activa)
    @Query("SELECT a FROM Animal a JOIN a.adopcion ad WHERE ad.estado = :estadoActivo")
    Page<Animal> findAnimalesAdoptados(@Param("estadoActivo") EstadoAdopcion estadoActivo, Pageable pageable);

    // Buscar por especie (sin filtrar adopción)
    Page<Animal> findByEspecie(String especie, Pageable pageable);

    // Buscar por estado de salud (sin filtrar adopción)
    Page<Animal> findByEstadoSalud(EstadoSalud estadoSalud, Pageable pageable);

    // Buscar por nombre (contiene) sin filtrar adopción
    Page<Animal> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Buscar por nombre disponible para adopción
    @Query("SELECT a FROM Animal a LEFT JOIN a.adopcion ad WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND (ad IS NULL OR ad.estado <> :estadoActivo)")
    Page<Animal> findDisponiblesByNombre(@Param("nombre") String nombre, @Param("estadoActivo") EstadoAdopcion estadoActivo, Pageable pageable);

    // Animales ingresados en un rango de fechas
    List<Animal> findByFechaIngresoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Contar animales por especie (solo disponibles para adopción)
    @Query("SELECT a.especie, COUNT(a) FROM Animal a LEFT JOIN a.adopcion ad WHERE ad IS NULL OR ad.estado <> :estadoActivo GROUP BY a.especie")
    List<Object[]> countDisponiblesByEspecie(@Param("estadoActivo") EstadoAdopcion estadoActivo);

    // Animales que requieren atención médica
    @Query("SELECT a FROM Animal a WHERE a.estadoSalud IN ('EN_TRATAMIENTO', 'CRITICO')")
    List<Animal> findAnimalesQueRequierenAtencion();

    // Buscar animal con su historial médico completo
    @Query("SELECT DISTINCT a FROM Animal a LEFT JOIN FETCH a.historialesMedicos WHERE a.animalId = :id")
    Optional<Animal> findByIdWithHistorialMedico(@Param("id") Long id);

    // Buscar animales disponibles para adopción (campo disponibleAdopcion = true)
    Page<Animal> findByDisponibleAdopcionTrue(Pageable pageable);

    // Buscar por especie y disponibles para adopción
    Page<Animal> findByEspecieAndDisponibleAdopcionTrue(String especie, Pageable pageable);

    // Buscar por nombre (contiene) y disponibles para adopción
    Page<Animal> findByNombreContainingIgnoreCaseAndDisponibleAdopcionTrue(String nombre, Pageable pageable);

    

    // Contar animales disponibles agrupados por especie
    @Query("SELECT a.especie, COUNT(a) FROM Animal a WHERE a.disponibleAdopcion = true GROUP BY a.especie")
    List<Object[]> countByEspecie();

    // Buscar animales que no tienen adopción asociada (disponibles)
    @Query("SELECT a FROM Animal a LEFT JOIN a.adopcion ad WHERE ad IS NULL")
    List<Animal> findAnimalesSinAdopcion();
}
