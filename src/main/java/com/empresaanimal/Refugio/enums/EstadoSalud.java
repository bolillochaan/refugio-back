package com.empresaanimal.Refugio.enums;
// This file is part of the Refugio project, which manages animal adoption and care.


public enum EstadoSalud {
    SALUDABLE("saludable"),
    EN_TRATAMIENTO("en tratamiento"),
    CRITICO("critico");

    private final String valor;

    EstadoSalud(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
