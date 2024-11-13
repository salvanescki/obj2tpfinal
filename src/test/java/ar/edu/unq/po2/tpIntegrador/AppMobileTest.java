package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AppMobileTest {

    private AppMobile app;
    private PopUpWindow popUpWindowMock;
    private Publicacion publicacionMock;

    @BeforeEach
    void setUp() {
        popUpWindowMock = mock(PopUpWindow.class);
        app = mock(AppMobile.class);
        publicacionMock = mock(Publicacion.class);
        app.setPopUpWindow(popUpWindowMock);
    }

    @Test
    void notificaCancelacionTest() {
    }

    @Test
    void noNotificaReservaTest() {
        app.notificarReserva(anyString(), any(Publicacion.class));
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }

    @Test
    void noNotificaBajaDePrecioTest() {
        app.notificarBajaDePrecio(anyString(), any(Publicacion.class));
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }
}
