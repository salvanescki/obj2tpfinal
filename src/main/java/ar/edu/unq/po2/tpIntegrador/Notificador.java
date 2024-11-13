package ar.edu.unq.po2.tpIntegrador;
import java.util.ArrayList;
import java.util.List;

public class Notificador implements Listener {

    private final List<Listener> suscriptores = new ArrayList<>();

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {
        for (Listener suscriptor : suscriptores) {
            suscriptor.notificarReserva(mensaje, publicacion);
        }
    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {
        for (Listener suscriptor : suscriptores) {
            suscriptor.notificarCancelacionReserva(mensaje, publicacion);
        }
    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {
        for (Listener suscriptor : suscriptores) {
            suscriptor.notificarBajaDePrecio(mensaje, publicacion);
        }
    }

    public void suscribir(Listener suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void desuscribir(Listener suscriptor) {
        suscriptores.remove(suscriptor);
    }

    public List<Listener> getSuscriptores() {
        return suscriptores;
    }
}
