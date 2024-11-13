package ar.edu.unq.po2.tpIntegrador;

public interface Listener {
    void notificarReserva(String evento, Publicacion publicacion);
    void notificarCancelacionReserva(String evento, Publicacion publicacion);
    void notificarBajaDePrecio(String evento, Publicacion publicacion);
}
