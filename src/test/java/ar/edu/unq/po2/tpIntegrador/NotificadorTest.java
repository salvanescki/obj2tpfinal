package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificadorTest {

    private Notificador notificador;
    private Listener sitioAlquileres;
    private Listener app;
    private Publicacion publicacion;


    @BeforeEach
    void setUp() {
        notificador = new Notificador();
        sitioAlquileres = mock(SitioAlquileres.class);
        app = mock(AppMobile.class);
        publicacion = mock(Publicacion.class);

        notificador.suscribir(sitioAlquileres);
        notificador.suscribir(app);
    }

    @Test
    void sePuedeSuscribirAUnInteresadoTest() {
        assertTrue(notificador.getSuscriptores().contains(sitioAlquileres));
        assertEquals(2, notificador.getSuscriptores().size());
        assertTrue(notificador.getSuscriptores().contains(sitioAlquileres));
        assertTrue(notificador.getSuscriptores().contains(app));
    }

    @Test
    void sePuedeDesuscribirAUnInteresadoTest() {
        assertTrue(notificador.getSuscriptores().contains(sitioAlquileres));
        notificador.desuscribir(sitioAlquileres);
        assertFalse(notificador.getSuscriptores().contains(sitioAlquileres));
        assertFalse(notificador.getSuscriptores().contains(sitioAlquileres));
        assertTrue(notificador.getSuscriptores().contains(app));
    }

    @Test
    void seNotificaUnaBajaDePrecioTest() {
        notificador.notificarBajaDePrecio("Baja de precios.", publicacion);
        verify(sitioAlquileres, times(1)).notificarBajaDePrecio("Baja de precios.", publicacion);
    }

    @Test
    void seNotificaUnaReservaALosInteresadosTest() {
        notificador.notificarReserva("Reserva.", publicacion);
        verify(app).notificarReserva("Reserva.", publicacion);
    }

    @Test
    void seNotificaUnaCancelacionALosInteresadosTest() {
        notificador.notificarCancelacionReserva("Cancelacion.", publicacion);
        verify(app).notificarCancelacionReserva("Cancelacion.", publicacion);
    }

    @Test
    void noSePuedeNotificarSiNoEstaSuscripto() {
        notificador.desuscribir(app);
        notificador.notificarCancelacionReserva("Cancelacion.", publicacion);
        verify(app, never()).notificarCancelacionReserva("Cancelacion.", publicacion);
        assertEquals(1, notificador.getSuscriptores().size());
    }
}
