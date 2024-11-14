package ar.edu.unq.po2.tpIntegrador;

public class AppMobile implements Listener{

    private PopUpWindow popUpWindow;

    public PopUpWindow getPopUoWindow() {
        return popUpWindow;
    }

    public void setPopUpWindow(PopUpWindow popUpWindow) {
        this.popUpWindow = popUpWindow;
    }

    public AppMobile(PopUpWindow setPopUpWindow) {
        this.popUpWindow = setPopUpWindow;
    }

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {
        popUpWindow.popUp(mensaje, "Negro", 24);
    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {
        // Por ahora no se pide esta notificacion.
    }

}
