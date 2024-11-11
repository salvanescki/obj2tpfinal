package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public class Usuario implements Propietario, Inquilino {

    @Override
    public void agregarReserva(Reserva reserva) {

    }

    @Override
    public List<Reserva> getReservas() {
        return List.of();
    }

    @Override
    public List<Reserva> getReservasFuturas() {
        return List.of();
    }

    @Override
    public List<Reserva> getReservasEnCiudad(String ciudad) {
        return List.of();
    }

    @Override
    public List<String> getCiudadesConReservas() {
        return List.of();
    }

    @Override
    public int getCantidadDeVecesQueAlquilo() {
        return 0;
    }

    @Override
    public int cantidadDeVecesQuePublicoInmuebles() {
        return 0;
    }

    @Override
    public List<Publicacion> getInmueblesEnAlquiler() {
        return List.of();
    }

    @Override
    public void puntuar(Usuario usuario, int puntaje, String comentario, Categoria categoria) {

    }

    @Override
    public double getPuntajePromedioEnCategoria(Categoria categoria) {
        return 0;
    }

    @Override
    public double getPuntajePromedioTotal() {
        return 0;
    }

    @Override
    public List<String> getComentariosDeInquilinosPrevios() {
        return List.of();
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        return 0;
    }
}
