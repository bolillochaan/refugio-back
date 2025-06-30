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
@Table(name = "historial_medico")
public class HistorialMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_id")
    private Long historialId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;
    
    @Column(name = "diagnostico", nullable = false, columnDefinition = "TEXT")
    private String diagnostico;
    
    @Column(name = "tratamiento", columnDefinition = "TEXT")
    private String tratamiento;
    
    // Constructores
    public HistorialMedico() {}
    
    public HistorialMedico(Animal animal, Empleado empleado, LocalDate fechaConsulta, String diagnostico, String tratamiento) {
        this.animal = animal;
        this.empleado = empleado;
        this.fechaConsulta = fechaConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }
    
    // Getters y Setters
    public Long getHistorialId() { return historialId; }
    public void setHistorialId(Long historialId) { this.historialId = historialId; }
    
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    
    public LocalDate getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDate fechaConsulta) { this.fechaConsulta = fechaConsulta; }
    
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    
    @Override
    public String toString() {
        return "HistorialMedico{" +
                "historialId=" + historialId +
                ", fechaConsulta=" + fechaConsulta +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                '}';
    }
}