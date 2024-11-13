package ar.edu.unq.po2.tpIntegrador;

public class AppMobile implements Listener{

    private PopUpWindow popUoWindow;

    public AppMobile(PopUpWindow popUoWindow) {
        this.popUoWindow = popUoWindow;
    }

    @Override
    public void notificarReserva(String evento, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarCancelacionReserva(String evento, Publicacion publicacion) {
        popUoWindow.popUp(evento + ": El/la "
                                          + publicacion.getTipoDeInmueble().getTipoDeInmueble()
                                          + " que te interesa se ha liberado! Corre a reservarlo!", "Rosa", 24);
    }

    @Override
    public void notificarBajaDePrecio(String evento, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }
}
