package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public interface Propietario extends Rankeable {
    int cantidadDeVecesQuePublicoInmuebles();
    List<Publicacion> getInmueblesEnAlquiler();
}
