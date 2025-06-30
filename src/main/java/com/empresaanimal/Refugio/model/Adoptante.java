package com.empresaanimal.Refugio.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "adoptante")
public class Adoptante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adoptante_id")
    private Long adoptanteId;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;
    
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;
    
    @OneToMany(mappedBy = "adoptante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adopcion> adopciones = new ArrayList<>();
    
    // Constructores
    public Adoptante() {}
    
    public Adoptante(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    // Getters y Setters
    public Long getAdoptanteId() { return adoptanteId; }
    public void setAdoptanteId(Long adoptanteId) { this.adoptanteId = adoptanteId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public List<Adopcion> getAdopciones() { return adopciones; }
    public void setAdopciones(List<Adopcion> adopciones) { this.adopciones = adopciones; }
    
    // Métodos de utilidad
    public void agregarAdopcion(Adopcion adopcion) {
        this.adopciones.add(adopcion);
        adopcion.setAdoptante(this);
    }
    
    @Override
    public String toString() {
        return "Adoptante{" +
                "adoptanteId=" + adoptanteId +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
