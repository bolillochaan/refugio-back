package com.empresaanimal.Refugio.model;

import java.time.LocalDate;

import com.empresaanimal.Refugio.enums.EstadoAdopcion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adopcion")
public class Adopcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopcion_id")
    private Long adopcionId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false, unique = true)
    private Animal animal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoptante_id", nullable = false)
    private Adoptante adoptante;
    
    @Column(name = "fecha_adopcion", nullable = false)
    private LocalDate fechaAdopcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoAdopcion estado = EstadoAdopcion.ACTIVA;
    
    // Constructores
    public Adopcion() {}
    
public Adopcion(Animal animal, Adoptante adoptante, LocalDate fechaAdopcion) {
    this.animal = animal;
    this.adoptante = adoptante;
    this.fechaAdopcion = (fechaAdopcion != null) ? fechaAdopcion : LocalDate.now();
    this.estado = EstadoAdopcion.ACTIVA;
}

public void setFechaAdopcion(LocalDate fechaAdopcion) {
    this.fechaAdopcion = (fechaAdopcion != null) ? fechaAdopcion : LocalDate.now();
}


    
    // Getters y Setters
    public Long getAdopcionId() { return adopcionId; }
    public void setAdopcionId(Long adopcionId) { this.adopcionId = adopcionId; }
    
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    
    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante adoptante) { this.adoptante = adoptante; }
    
    public LocalDate getFechaAdopcion() { return fechaAdopcion; }
    
 


    
    public EstadoAdopcion getEstado() { return estado; }
    public void setEstado(EstadoAdopcion estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return "Adopcion{" +
                "adopcionId=" + adopcionId +
                ", fechaAdopcion=" + fechaAdopcion +
                ", estado=" + estado +
                '}';
    }
}