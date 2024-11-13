package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class SitioAlquileres implements Listener {

    private HomePagePublisher publisher;

    public SitioAlquileres(HomePagePublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {
        publisher.publish(mensaje);
    }
}
