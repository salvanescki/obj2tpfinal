package ar.edu.unq.po2.tpIntegrador;

public class AppMobile implements Listener{

    private PopUpWindow popUoWindow;

    @Override
    public void notificarReserva(String mensaje, Publicacion publicacion) {

    }

    @Override
    public void notificarCancelacionReserva(String mensaje, Publicacion publicacion) {

    }

    @Override
    public void notificarBajaDePrecio(String mensaje, Publicacion publicacion) {

    }

    public void setPopUpWindow(PopUpWindow popUpWindowMock) {
        this.popUoWindow = popUpWindowMock;
    }
}
