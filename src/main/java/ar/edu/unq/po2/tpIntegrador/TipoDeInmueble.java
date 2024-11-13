package ar.edu.unq.po2.tpIntegrador;

public class TipoDeInmueble {

    private String tipoDeInmueble;

    public TipoDeInmueble(String tipoDeInmueble) {
        setTipoDeInmueble(tipoDeInmueble);
    }

    public String getTipoDeInmueble() {
        return tipoDeInmueble;
    }

    public void setTipoDeInmueble(String tipoDeInmueble) {
        this.tipoDeInmueble = TextoNormalizado.normalizarTexto(tipoDeInmueble);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        TipoDeInmueble otro = (TipoDeInmueble) o;
        return this.tipoDeInmueble.equals(otro.getTipoDeInmueble());
    }
}

