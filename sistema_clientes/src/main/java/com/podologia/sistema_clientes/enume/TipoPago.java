package com.podologia.sistema_clientes.enume;

public enum TipoPago {
    EFECTIVO("efectivo"),TARJETA("tarjeta"),YAPE("yape"),PLIN("plin");

    private String formaPago;

    TipoPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getFormaPago() {
        return formaPago;
    }
}
