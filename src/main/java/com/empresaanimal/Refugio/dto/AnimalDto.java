package com.empresaanimal.Refugio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.empresaanimal.Refugio.enums.EstadoSalud;

public class AnimalDto {
    private Long animalId;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edadAproximada;
    private BigDecimal peso;
    private String color;
    private LocalDate fechaIngreso;
    private EstadoSalud estadoSalud;
    private String descripcion;
    private String fotoUrl;
    private Boolean disponibleAdopcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor vacío
    public AnimalDto() {}
    
    // Getters y Setters
    public Long getAnimalId() { return animalId; }
    public void setAnimalId(Long animalId) { this.animalId = animalId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    
    public Integer getEdadAproximada() { return edadAproximada; }
    public void setEdadAproximada(Integer edadAproximada) { this.edadAproximada = edadAproximada; }
    
    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public EstadoSalud getEstadoSalud() { return estadoSalud; }
    public void setEstadoSalud(EstadoSalud estadoSalud) { this.estadoSalud = estadoSalud; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    
    public Boolean getDisponibleAdopcion() { return disponibleAdopcion; }
    public void setDisponibleAdopcion(Boolean disponibleAdopcion) { this.disponibleAdopcion = disponibleAdopcion; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}