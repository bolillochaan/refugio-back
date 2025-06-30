package com.empresaanimal.Refugio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vacuna")
public class Vacuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacuna_id")
    private Long vacunaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    @Column(name = "nombre_vacuna", nullable = false, length = 100)
    private String nombreVacuna;
    
    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;
    
    @Column(name = "fecha_proxima_vacuna")
    private LocalDate fechaProximaVacuna;
    
    @Column(name = "lote", length = 50)
    private String lote;
    
    // Constructores
    public Vacuna() {}
    
    public Vacuna(Animal animal, Empleado empleado, String nombreVacuna, LocalDate fechaAplicacion, LocalDate fechaProximaVacuna, String lote) {
        this.animal = animal;
        this.empleado = empleado;
        this.nombreVacuna = nombreVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaProximaVacuna = fechaProximaVacuna;
        this.lote = lote;
    }
    
    // Getters y Setters
    public Long getVacunaId() { return vacunaId; }
    public void setVacunaId(Long vacunaId) { this.vacunaId = vacunaId; }
    
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    
    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String nombreVacuna) { this.nombreVacuna = nombreVacuna; }
    
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
    
    public LocalDate getFechaProximaVacuna() { return fechaProximaVacuna; }
    public void setFechaProximaVacuna(LocalDate fechaProximaVacuna) { this.fechaProximaVacuna = fechaProximaVacuna; }
    
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
    
    @Override
    public String toString() {
        return "Vacuna{" +
                "vacunaId=" + vacunaId +
                ", nombreVacuna='" + nombreVacuna + '\'' +
                ", fechaAplicacion=" + fechaAplicacion +
                ", fechaProximaVacuna=" + fechaProximaVacuna +
                ", lote='" + lote + '\'' +
                '}';
    }
}