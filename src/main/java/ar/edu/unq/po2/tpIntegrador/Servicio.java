package ar.edu.unq.po2.tpIntegrador;

public class Servicio {

    private String servicio;

    public Servicio(String servicio) {
        setServicio(servicio);
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = TextoNormalizado.normalizarTexto(servicio);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        Servicio otro = (Servicio) o;
        return this.servicio.equals(otro.getServicio());
    }
}