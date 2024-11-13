package ar.edu.unq.po2.tpIntegrador;

public class AppMobile implements Listener{

    private PopUpWindow popUoWindow;

    public AppMobile(PopUpWindow popUoWindow) {
        this.popUoWindow = popUoWindow;
    }

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {
        popUoWindow.popUp(mensaje, "Negro", 24);
    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }


}
