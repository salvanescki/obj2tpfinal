package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Busqueda {

    private String ciudad;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private List<Predicate<Publicacion>> filtrosOpcionales;

    public Busqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.ciudad = ciudad;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        filtrosOpcionales = new ArrayList<>();
    }

    public Busqueda conPrecioMinimo(Precio precio) {
        filtrosOpcionales.add(p -> precio.getPrecio() <= p.getPrecio(fechaEntrada, fechaSalida).getPrecio());
        return this;
    }

    public Busqueda conPrecioMaximo(Precio precio) {
        filtrosOpcionales.add(p -> precio.getPrecio() >= p.getPrecio(fechaEntrada, fechaSalida).getPrecio());
        return this;
    }

    public Busqueda conCantidadDeHuespedes(int cantidad) {
        filtrosOpcionales.add(p -> p.getCapacidad() >= cantidad);
        return this;
    }

    public List<Publicacion> efectuarBusqueda(List<Publicacion> publicaciones) {
        Stream<Publicacion> listaFiltrada = publicaciones.stream()
                                                        .filter(p -> p.getCiudad().equals(ciudad))
                                                        .filter(p -> !p.estaReservadaEnFechas(fechaEntrada, fechaSalida));
        for(Predicate<Publicacion> filtro : filtrosOpcionales){
            listaFiltrada = listaFiltrada.filter(filtro);
        }

        return listaFiltrada.toList();
    }
}
