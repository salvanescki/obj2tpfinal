package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public class Usuario implements Propietario, Inquilino {

    public Usuario(String nombre, String email, String telefono) {
    }

    public String getNombre() {
        // TODO: Implementar
        return "";
    }

    public String getEmail() {
        // TODO: Implementar
        return "";
    }

    public String getTelefono() {
        // TODO: Implementar
        return "";
    }

    public String getTiempoDeAntiguedad() {
        // TODO: Implementar
        return "";
    }

    @Override
    public void agregarReserva(Reserva reserva) {
        // TODO: Implementar

    }

    @Override
    public List<Reserva> getReservas() {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public List<Reserva> getReservasFuturas() {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public List<Reserva> getReservasEnCiudad(String ciudad) {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public List<String> getCiudadesConReservas() {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public int getCantidadDeVecesQueAlquilo() {
        // TODO: Implementar
        return 0;
    }

    @Override
    public int cantidadDeVecesQuePublicoInmuebles() {
        // TODO: Implementar
        return 0;
    }

    @Override
    public void agregarPublicacion(Publicacion publicacion) {
        // TODO: Implementar
    }

    @Override
    public List<Publicacion> getInmueblesPublicados() {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public void puntuar(Ranking ranking) {
        // TODO: Implementar

    }

    @Override
    public double getPuntajePromedioEnCategoria(Categoria categoria) {
        // TODO: Implementar
        return 0;
    }

    @Override
    public double getPuntajePromedioTotal() {
        // TODO: Implementar
        return 0;
    }

    @Override
    public List<String> getComentariosDeInquilinosPrevios() {
        // TODO: Implementar
        return List.of();
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        // TODO: Implementar
        return 0;
    }
}
