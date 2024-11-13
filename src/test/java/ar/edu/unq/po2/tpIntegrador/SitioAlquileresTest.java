package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

public class SitioAlquileresTest {

    private SitioAlquileres sitioAlquileres;
    private HomePagePublisher publisher;
    private Publicacion publicacionMock;

    @BeforeEach
    void setUp() {
        publisher = mock(HomePagePublisher.class);
        sitioAlquileres = new SitioAlquileres(publisher);
        publicacionMock = mock(Publicacion.class);
    }

    @Test
    void notificaBajaDePrecioTest() {

        String mensaje = "";

        sitioAlquileres.notificarBajaDePrecio(mensaje, publicacionMock);
        verify(publisher).publish(mensaje);
    }

    @Test
    void noNotificaCancelacionTest() {

        String mensaje = "";

        sitioAlquileres.notificarCancelacionReserva(mensaje, publicacionMock);
        verify(publisher, never()).publish(anyString());
    }

    @Test
    void noNotificaReservaTest() {

        String mensaje = "";

        sitioAlquileres.notificarReserva(mensaje, publicacionMock);
        verify(publisher, never()).publish(anyString());
    }

}