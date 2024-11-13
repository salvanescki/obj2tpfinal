package ar.edu.unq.po2.tpIntegrador;

public class TipoDeInmueble {

    private String tipoDeInmueble;

    public TipoDeInmueble(String tipoDeInmueble) {
        this.tipoDeInmueble = tipoDeInmueble;
    }

    public String getTipoDeInmueble() {
        return (TextoNormalizado.normalizarTexto(tipoDeInmueble));
    }

    public void setTipoDeInmueble(String tipoDeInmueble) {
        this.tipoDeInmueble = TextoNormalizado.normalizarTexto(tipoDeInmueble);
    }
}
