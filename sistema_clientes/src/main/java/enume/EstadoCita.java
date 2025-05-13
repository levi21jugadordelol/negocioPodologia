package enume;

public enum EstadoCita {
    PROGRAMADA("programada"),ATENDIDA("atendida"),CANCELADA("cancelada");

    private String estadoCita;

    EstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }
}
