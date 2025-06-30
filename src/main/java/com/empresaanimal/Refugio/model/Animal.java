package com.empresaanimal.Refugio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.empresaanimal.Refugio.enums.EstadoAdopcion;
import com.empresaanimal.Refugio.enums.EstadoSalud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "animal")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long animalId;
    
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "especie", nullable = false, length = 50)
    private String especie;
    
    @Column(name = "raza", length = 50)
    private String raza;
    
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_salud", nullable = false)
    private EstadoSalud estadoSalud = EstadoSalud.SALUDABLE;
    
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialMedico> historialesMedicos = new ArrayList<>();
    
    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Adopcion adopcion;
    
        @Column(name = "disponible_adopcion", nullable = false)
    private boolean disponibleAdopcion = true; // Valor por defecto: disponible
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacuna> vacunas = new ArrayList<>();
    
    @Column(name = "foto_url", length = 255)
    private String fotoUrl;
    
    // Constructores
    public Animal() {}
    
    public Animal(String nombre, String especie, String raza, LocalDate fechaIngreso) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaIngreso = fechaIngreso;
        this.estadoSalud = EstadoSalud.SALUDABLE;
    }
    
    // Getters y Setters
    public Long getAnimalId() { return animalId; }
    public void setAnimalId(Long animalId) { this.animalId = animalId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public EstadoSalud getEstadoSalud() { return estadoSalud; }
    public void setEstadoSalud(EstadoSalud estadoSalud) { this.estadoSalud = estadoSalud; }
    
    public List<HistorialMedico> getHistorialesMedicos() { return historialesMedicos; }
    public void setHistorialesMedicos(List<HistorialMedico> historialesMedicos) { this.historialesMedicos = historialesMedicos; }
    
    public Adopcion getAdopcion() { return adopcion; }
    public void setAdopcion(Adopcion adopcion) { this.adopcion = adopcion; }
    
    public List<Vacuna> getVacunas() { return vacunas; }
    public void setVacunas(List<Vacuna> vacunas) { this.vacunas = vacunas; }

        public boolean isDisponibleAdopcion() {
        return disponibleAdopcion;
    }

    public void setDisponibleAdopcion(boolean disponibleAdopcion) {
        this.disponibleAdopcion = disponibleAdopcion;
    }
    
    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    // Métodos de utilidad


    
    public void agregarHistorialMedico(HistorialMedico historial) {
        this.historialesMedicos.add(historial);
        historial.setAnimal(this);
    }
    
    public void agregarVacuna(Vacuna vacuna) {
        this.vacunas.add(vacuna);
        vacuna.setAnimal(this);
    }
    
    public boolean estaAdoptado() {
        return adopcion != null && adopcion.getEstado() == EstadoAdopcion.ACTIVA;
    }
    
    public boolean estaDisponibleParaAdopcion() {
        return adopcion == null || adopcion.getEstado() != EstadoAdopcion.ACTIVA;
    }
    
    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", estadoSalud=" + estadoSalud +
                '}';
    }
}