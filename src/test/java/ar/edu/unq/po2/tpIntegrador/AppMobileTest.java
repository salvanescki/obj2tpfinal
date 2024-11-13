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

        TipoDeInmueble tipoDeInmueble = new TipoDeInmueble("casa");

        when(publicacionMock.getTipoDeInmueble()).thenReturn(tipoDeInmueble);

        String mensaje = "Cancelacion de reserva: El/la casa que te interesa se ha liberado! Corre a reservarlo!";

        app.notificarCancelacionReserva("Cancelacion de reserva", publicacionMock);
        verify(popUpWindowMock).popUp(mensaje, "Rosa",24);
    }

    @Test
    void noNotificaReservaTest() {
        app.notificarReserva("Cancelacion de reserva:", publicacionMock);
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }

    @Test
    void noNotificaBajaDePrecioTest() {
        app.notificarBajaDePrecio("Cancelacion de reserva:", publicacionMock);
        verify(popUpWindowMock, never()).popUp(anyString(), anyString(), anyInt());
    }
}
