package com.podologia.sistema_clientes.enume;

public enum TipoCita {
    SEMANAL("semanal"),QUINCENAL("quincenal"),MENSUAL("mensual");

    private String tipoCita;

    TipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getTipoCita() {
        return tipoCita;
    }
}
