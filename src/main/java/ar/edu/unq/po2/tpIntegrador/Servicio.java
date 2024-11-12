package ar.edu.unq.po2.tpIntegrador;

public class Servicio {
    private String servicio;

    public Servicio(String servicio) {
        this.servicio = servicio;
    }

    public String getServicio() {
        return (TextoNormalizado.normalizarTexto(servicio));
    }

    public void setServicio(String servicio) {
        this.servicio = TextoNormalizado.normalizarTexto(servicio);
    }
}