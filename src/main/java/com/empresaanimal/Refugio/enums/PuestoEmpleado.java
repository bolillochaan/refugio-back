package com.empresaanimal.Refugio.enums;


// Enum para puesto de empleado
public enum PuestoEmpleado {
    VETERINARIO("veterinario"),
    CUIDADOR("cuidador"),
    ADMINISTRATIVO("administrativo");
    
    private final String valor;
    
    PuestoEmpleado(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
}
