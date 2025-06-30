package com.empresaanimal.Refugio.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO para la creación de un nuevo animal en el refugio
 * Basado en la entidad Animal del sistema de gestión de refugio
 */
public class AnimalCreateDto {
    
    @NotBlank(message = "El nombre del animal es requerido")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "La especie es requerida")
    @Size(max = 50, message = "La especie no puede exceder 50 caracteres")
    private String especie;
    
    @Size(max = 50, message = "La raza no puede exceder 50 caracteres")
    private String raza;
    
    @NotNull(message = "La fecha de ingreso es requerida")
    private LocalDate fechaIngreso;
    
    private EstadoSalud estadoSalud = EstadoSalud.SALUDABLE;
    
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
    public AnimalCreateDto() {}
    
    // Constructor con parámetros
    public AnimalCreateDto(String nombre, String especie, String raza, LocalDate fechaIngreso, EstadoSalud estadoSalud) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaIngreso = fechaIngreso;
        this.estadoSalud = estadoSalud != null ? estadoSalud : EstadoSalud.SALUDABLE;
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
    
    @Override
    public String toString() {
        return "AnimalCreateDto{" +
                "nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", estadoSalud=" + estadoSalud +
                '}';
    }
}