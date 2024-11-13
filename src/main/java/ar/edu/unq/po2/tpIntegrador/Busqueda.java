package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Busqueda {

    private final String ciudad;
    private final Periodo periodo;
    private final List<Predicate<Publicacion>> filtrosOpcionales;

    public Busqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.ciudad = ciudad;
        this.periodo = new Periodo(fechaEntrada, fechaSalida);
        filtrosOpcionales = new ArrayList<>();
    }

    public Busqueda conPrecioMinimo(Precio precio) {
        filtrosOpcionales.add(p -> precio.getPrecio() <= p.getPrecio(periodo).getPrecio());
        return this;
    }

    public Busqueda conPrecioMaximo(Precio precio) {
        filtrosOpcionales.add(p -> precio.getPrecio() >= p.getPrecio(periodo).getPrecio());
        return this;
    }

    public Busqueda conCantidadDeHuespedes(int cantidad) {
        filtrosOpcionales.add(p -> p.getCapacidad() >= cantidad);
        return this;
    }

    public List<Publicacion> efectuarBusqueda(List<Publicacion> publicaciones) {
        Stream<Publicacion> listaFiltrada = publicaciones.stream()
                                                        .filter(p -> p.getCiudad().equals(ciudad))
                                                        .filter(p -> !p.estaReservadaEnFechas(periodo));
        for(Predicate<Publicacion> filtro : filtrosOpcionales){
            listaFiltrada = listaFiltrada.filter(filtro);
        }

        return listaFiltrada.toList();
    }
}
