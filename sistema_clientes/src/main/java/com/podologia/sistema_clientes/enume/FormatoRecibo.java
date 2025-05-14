package com.podologia.sistema_clientes.enume;

public enum FormatoRecibo {
    BOLETA("boleta"),FACTURA("com/podologia/sistema_clientes/factura");

    private String nombreFormato;


    FormatoRecibo(String nombreFormato) {
        this.nombreFormato = nombreFormato;
    }

    public String getNombreFormato() {
        return nombreFormato;
    }


}
