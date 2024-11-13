package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class AppMobileTest {

    private AppMobile app;
    private PopUpWindow popUpWindowMock;
    private Publicacion publicacionMock;

    @BeforeEach
    void setUp() {
        popUpWindowMock = mock(PopUpWindow.class);
        app = new AppMobile(popUpWindowMock);
        publicacionMock = mock(Publicacion.class);
    }

    @Test
    void notificaCancelacionTest() {

        String mensaje = "";

        app.notificarCancelacionReserva(mensaje, publicacionMock);
        verify(popUpWindowMock).popUp(mensaje, "Negro", 24);
    }

    @Test
    void noNotificaReservaTest() {

        String mensaje = "";

        app.notificarReserva(mensaje, publicacionMock);
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }

    @Test
    void noNotificaBajaDePrecioTest() {

        String mensaje = "";

        app.notificarBajaDePrecio(mensaje, publicacionMock);
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }
}
