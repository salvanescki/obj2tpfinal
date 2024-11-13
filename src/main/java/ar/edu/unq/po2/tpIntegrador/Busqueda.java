package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;
import java.util.List;

public class Busqueda {
    public Busqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // TODO: Implementar
    }

    public List<Publicacion> efectuarBusqueda(List<Publicacion> publicaciones) {
        // TODO: Implementar
        return List.of();
    }

    public Busqueda conPrecioMinimo(Precio precio) {
        // TODO: Implementar
        return this;
    }

    public Busqueda conPrecioMaximo(Precio precio) {
        // TODO: Implementar
        return this;
    }

    public Busqueda conCantidadDeHuespedes(int cantidad) {
        // TODO: Implementar
        return this;
    }
}
