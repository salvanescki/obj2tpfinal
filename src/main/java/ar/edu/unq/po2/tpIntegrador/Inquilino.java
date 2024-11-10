package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public interface Inquilino extends Rankeable {
    List<Reserva> getReservas();
    List<Reserva> getReservasFuturas();
    List<Reserva> getReservasEnCiudad(String ciudad);
    List<String> getCiudadesConReservas();
    int getCantidadDeVecesQueAlquilo();
}