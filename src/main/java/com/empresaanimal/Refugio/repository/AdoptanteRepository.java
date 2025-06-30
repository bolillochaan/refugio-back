package com.empresaanimal.Refugio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.empresaanimal.Refugio.model.Adoptante;

@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long> {

    // Buscar por teléfono
    Optional<Adoptante> findByTelefono(String telefono);


    // Buscar por nombre (insensible a mayúsculas/minúsculas)
    List<Adoptante> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por dirección que contenga cierto texto
    List<Adoptante> findByDireccionContainingIgnoreCase(String direccion);

    // Verificar si existe adoptante con ese teléfono
    boolean existsByTelefono(String telefono);

    // Buscar adoptantes que tienen adopciones activas
    @Query("SELECT DISTINCT a FROM Adoptante a JOIN a.adopciones ad WHERE ad.estado = 'ACTIVA'")
    List<Adoptante> findAdoptantesConAdopcionesActivas();

    // Buscar adoptantes sin adopciones
    @Query("SELECT a FROM Adoptante a WHERE a.adopciones IS EMPTY")
    List<Adoptante> findAdoptantesSinAdopciones();

   
}
