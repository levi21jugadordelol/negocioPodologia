package com.podologia.sistema_clientes.enume;

public enum Status {
    ADMIN("admin"), NO_ADMIN("no_admin");
    private String roles;

    Status(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }
}
