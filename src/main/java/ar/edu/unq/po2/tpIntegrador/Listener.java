package ar.edu.unq.po2.tpIntegrador;

public interface Listener {
    void notificarReserva(String mensaje, Publicacion publicacion);
    void notificarCancelacionReserva(String mensaje, Publicacion publicacion);
    void notificarBajaDePrecio(String mensaje, Publicacion publicacion);
}
