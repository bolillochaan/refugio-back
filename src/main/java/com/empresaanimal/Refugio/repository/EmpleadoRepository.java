package com.empresaanimal.Refugio.repository;

import com.empresaanimal.Refugio.model.Empleado;
import com.empresaanimal.Refugio.enums.PuestoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Buscar por puesto
    List<Empleado> findByPuesto(PuestoEmpleado puesto);

    // Buscar solo veterinarios
    @Query("SELECT e FROM Empleado e WHERE e.puesto = 'VETERINARIO'")
    List<Empleado> findVeterinarios();

    // Buscar solo cuidadores
    @Query("SELECT e FROM Empleado e WHERE e.puesto = 'CUIDADOR'")
    List<Empleado> findCuidadores();

    // Buscar empleados contratados en un rango de fechas
    List<Empleado> findByFechaContratacionBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar por nombre (insensible a mayúsculas/minúsculas)
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    // Contar empleados por puesto
    @Query("SELECT COUNT(e) FROM Empleado e WHERE e.puesto = :puesto")
    Long countByPuesto(@Param("puesto") PuestoEmpleado puesto);
}
