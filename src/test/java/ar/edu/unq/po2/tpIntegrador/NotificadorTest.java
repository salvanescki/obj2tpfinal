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
        notificador = mock(Notificador.class);
        sitioAlquileres = mock(SitioAlquileres.class);
        app = mock(AppMobile.class);
        publicacion = mock(Publicacion.class);

        notificador.suscribir(sitioAlquileres);
        notificador.suscribir(app);
    }

    @Test
    void sePuedeSuscribirAUnInteresadoTest() {
        assertTrue(notificador.getSuscriptores().contains(sitioAlquileres));
    }

    @Test
    void sePuedeDesuscribirAUnInteresadoTest() {
        assertTrue(notificador.getSuscriptores().contains(sitioAlquileres));
        notificador.desuscribir(sitioAlquileres);
        assertFalse(notificador.getSuscriptores().contains(sitioAlquileres));
    }

    @Test
    void seNotificaUnaBajaDePrecioTest() {
        String mensajeBajaDePrecio = "Tu inmueble de interes bajo de precio.";

        doNothing().when(app).notificarBajaDePrecio(mensajeBajaDePrecio, publicacion);

        notificador.notificarBajaDePrecio(mensajeBajaDePrecio, publicacion);

        verify(sitioAlquileres).notificarBajaDePrecio(mensajeBajaDePrecio, publicacion);
        verify(app, never()).notificarBajaDePrecio(mensajeBajaDePrecio, publicacion);
    }



    @Test
    void seNotificaUnaReservaALosInteresadosTest() {
        String mensajeEsperado = "Tu inmueble de interes ha sido reservado.";

        notificador.notificarReserva(mensajeEsperado, publicacion);

        verify(app).notificarReserva(mensajeEsperado, publicacion);
        verify(sitioAlquileres, never()).notificarReserva(mensajeEsperado, publicacion);
    }

    @Test
    void seNotificaUnaCancelacionALosInteresadosTest() {
        String mensajeEsperado = "En inmueble de interes se ha cancelado una reserva.";

        notificador.notificarCancelacionReserva(mensajeEsperado, publicacion);

        verify(app).notificarCancelacionReserva(mensajeEsperado, publicacion);
        verify(sitioAlquileres, never()).notificarCancelacionReserva(mensajeEsperado, publicacion);
    }
}
