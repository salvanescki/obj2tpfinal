package ar.edu.unq.po2.tpIntegrador;

public class SitioAlquileres implements Listener {

    private HomePagePublisher publisher;

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {

    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {

    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {
        publisher.publish("No te pierdas esta oferta: Un inmueble " + publicacion.getTipoDeInmueble() + " a tan sólo " + "???" + " pesos");
    }
}
