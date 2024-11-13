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

        TipoDeInmueble tipoDeInmueble = new TipoDeInmueble("casa");
        Precio precio = new Precio(170000);

        when(publicacionMock.getTipoDeInmueble()).thenReturn(tipoDeInmueble);
        when(publicacionMock.getPrecio(any(LocalDate.class), any(LocalDate.class))).thenReturn(precio);

        String mensaje = "Baja de precio: No te pierdas esta oferta: Un inmueble casa a tan sólo 170000.0 pesos.";

        sitioAlquileres.notificarBajaDePrecio("Baja de precio", publicacionMock);
        verify(publisher).publish(mensaje);
    }

    @Test
    void noNotificaCancelacionTest() {
        sitioAlquileres.notificarCancelacionReserva("Cancelacion de reserva:", publicacionMock);
        verify(publisher, never()).publish(anyString());
    }

    @Test
    void noNotificaReservaTest() {
        sitioAlquileres.notificarReserva("Reserva:", publicacionMock);
        verify(publisher, never()).publish(anyString());
    }

}