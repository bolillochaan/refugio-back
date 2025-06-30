package com.empresaanimal.Refugio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

/**
 * DTO para la actualización de un animal existente en el refugio
 * Permite actualizaciones parciales de la entidad Animal
 */
public class AnimalUpdateDto {
    
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    @Size(max = 50, message = "La especie no puede exceder 50 caracteres")
    private String especie;
    
    @Size(max = 50, message = "La raza no puede exceder 50 caracteres")
    private String raza;
    
    private LocalDate fechaIngreso;
    
    private EstadoSalud estadoSalud;
    
    // Enum para el estado de salud del animal
    public enum EstadoSalud {
        SALUDABLE("saludable"),
        EN_TRATAMIENTO("en tratamiento"),
        CRITICO("crítico");
        
        private final String valor;
        
        EstadoSalud(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }
    
    // Constructor por defecto
    public AnimalUpdateDto() {}
    
    // Constructor con parámetros opcionales
    public AnimalUpdateDto(String nombre, String especie, String raza, 
                          LocalDate fechaIngreso, EstadoSalud estadoSalud) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaIngreso = fechaIngreso;
        this.estadoSalud = estadoSalud;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public EstadoSalud getEstadoSalud() {
        return estadoSalud;
    }
    
    public void setEstadoSalud(EstadoSalud estadoSalud) {
        this.estadoSalud = estadoSalud;
    }
    
    /**
     * Verifica si al menos un campo está presente para actualizar
     * @return true si hay al menos un campo para actualizar
     */
    public boolean hasUpdates() {
        return nombre != null || especie != null || raza != null || 
               fechaIngreso != null || estadoSalud != null;
    }
    
    @Override
    public String toString() {
        return "AnimalUpdateDto{" +
                "nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", estadoSalud=" + estadoSalud +
                '}';
    }
}