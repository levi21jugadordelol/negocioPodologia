package enume;

public enum FormatoRecibo {
    BOLETA("boleta"),FACTURA("factura");

    private String nombreFormato;


    FormatoRecibo(String nombreFormato) {
        this.nombreFormato = nombreFormato;
    }

    public String getNombreFormato() {
        return nombreFormato;
    }


}
