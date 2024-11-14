package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public interface Propietario extends Rankeable {
    int cantidadDeVecesQuePublicoInmuebles();
    void agregarPublicacion(Publicacion publicacion);
    List<Publicacion> getInmueblesPublicados();
}
