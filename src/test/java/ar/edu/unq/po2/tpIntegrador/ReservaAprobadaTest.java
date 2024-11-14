package ar.edu.unq.po2.tpIntegrador;
import static org.mockito.Mockito.*;
import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.*;


public class ReservaAprobadaTest {

    private ReservaAprobada aprobada;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = mock(Reserva.class);
        aprobada = new ReservaAprobada(reserva);
    }

    @Test
    void inicializacionReservaPendiente() {
        assertEquals(reserva, aprobada.getReserva());
    }

    @Test
    void getReservaTest() {
        assertEquals(reserva, aprobada.getReserva());
    }

    @Test
    void noEsEstadoReservaPendienteTest() {
        assertFalse(aprobada.estaPendiente());
    }

    @Test
    void esEstadoReservaAprobadaTest() {
        assertTrue(aprobada.estaAprobada());
    }

    @Test
    void noEsEstadoReservaCanceladaTest() {
        assertFalse(aprobada.fueCancelada());
    }

    @Test
    void alAprobarseLanzaExcepcion() {
        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            aprobada.aprobarReserva();
        });

        assertTrue(excepcion.getMessage().contains("No se puede aprobar una reserva ya aprobada."));
    }

    @Test
    void alCancelarseCambiaDeEstadoACanceladaYEnviaUnMailAlPropietarioTest() {
        Usuario inquilinoMock = mock(Usuario.class);
        Usuario propietarioMock = mock(Usuario.class);
        Publicacion publicacionMock = mock(Publicacion.class);

        when(inquilinoMock.getEmail()).thenReturn("inquilino@gmail.com");
        when(inquilinoMock.getNombre()).thenReturn("Inquilino");
        when(propietarioMock.getEmail()).thenReturn("propietario@gmail.com");
        when(publicacionMock.getPropietario()).thenReturn(propietarioMock);
        when(reserva.getInquilino()).thenReturn(inquilinoMock);
        when(reserva.getPublicacion()).thenReturn(publicacionMock);

        try (MockedStatic<Mail> mockedMail = mockStatic(Mail.class)) {
            when(Mail.enviar(any(Mail.class))).thenReturn("true");

            when(reserva.fueCancelada()).thenReturn(true);

            aprobada.cancelarReserva();

            assertTrue(reserva.fueCancelada());
            mockedMail.verify(() -> Mail.enviar(any(Mail.class)), times(1));
    }
}
}
