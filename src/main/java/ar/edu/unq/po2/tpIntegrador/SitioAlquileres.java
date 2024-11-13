package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class SitioAlquileres implements Listener {

    private HomePagePublisher publisher;

    public SitioAlquileres(HomePagePublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void notificarReserva(String evento, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarCancelacionReserva(String evento, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarBajaDePrecio(String evento, Publicacion publicacion) {

        publisher.publish(evento + ": No te pierdas esta oferta: Un inmueble "
                                 + publicacion.getTipoDeInmueble().getTipoDeInmueble() + " a tan sólo "
                                 + publicacion.getPrecio(LocalDate.now(), LocalDate.now()).getPrecio() + " pesos.");
    }
}
