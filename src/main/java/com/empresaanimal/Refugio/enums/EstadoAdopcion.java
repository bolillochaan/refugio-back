package com.empresaanimal.Refugio.enums;



public enum EstadoAdopcion {
    ACTIVA("activa"),
    FINALIZADA("finalizada"),
    CANCELADA("cancelada");
    
    private final String valor;
    
    EstadoAdopcion(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
}
