package com.empresaanimal.Refugio.model;

import com.empresaanimal.Refugio.enums.PuestoEmpleado;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "empleado")
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleado_id")
    private Long empleadoId;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "puesto", nullable = false)
    private PuestoEmpleado puesto;
    
    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;
    
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialMedico> historialesMedicos = new ArrayList<>();
    
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacuna> vacunas = new ArrayList<>();
    
    // Constructores
    public Empleado() {}
    
    public Empleado(String nombre, PuestoEmpleado puesto, LocalDate fechaContratacion) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
    }
    
    // Getters y Setters
    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public PuestoEmpleado getPuesto() { return puesto; }
    public void setPuesto(PuestoEmpleado puesto) { this.puesto = puesto; }
    
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    
    public List<HistorialMedico> getHistorialesMedicos() { return historialesMedicos; }
    public void setHistorialesMedicos(List<HistorialMedico> historialesMedicos) { this.historialesMedicos = historialesMedicos; }
    
    public List<Vacuna> getVacunas() { return vacunas; }
    public void setVacunas(List<Vacuna> vacunas) { this.vacunas = vacunas; }
    
    // Métodos de utilidad
    public void agregarHistorialMedico(HistorialMedico historial) {
        this.historialesMedicos.add(historial);
        historial.setEmpleado(this);
    }
    
    public void agregarVacuna(Vacuna vacuna) {
        this.vacunas.add(vacuna);
        vacuna.setEmpleado(this);
    }
    
    @Override
    public String toString() {
        return "Empleado{" +
                "empleadoId=" + empleadoId +
                ", nombre='" + nombre + '\'' +
                ", puesto=" + puesto +
                ", fechaContratacion=" + fechaContratacion +
                '}';
    }
}