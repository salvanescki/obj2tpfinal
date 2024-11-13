package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ReservaPendienteTest {

    private ReservaPendiente pendiente;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = mock(Reserva.class);
        pendiente = new ReservaPendiente(reserva);
    }

    @Test
    void esEstadoReservaPendienteTest() {
        assertTrue(pendiente.estaPendiente());
    }

    @Test
    void noEsEstadoReservaAprobadaTest() {
        assertFalse(pendiente.estaAprobada());
    }

    @Test
    void noEsEstadoReservaCanceladaTest() {
        assertFalse(pendiente.fueCancelada());
    }

    @Test
    void alAprobarseCambiaDeEstadoAAprobadaYEnviaUnMailAlInquilinoTest() {
        when(reserva.estaAprobada()).thenReturn(true);
        ReservaPendiente spyPendiente = spy(pendiente);
        verify(spyPendiente, times(0)).enviarMailQueConfirmaLaAprobacion();
        spyPendiente.aprobarReserva();
        verify(spyPendiente, times(1)).enviarMailQueConfirmaLaAprobacion();
        assertTrue(reserva.estaAprobada());
        assertEquals("Se envio con exito la aprobacion de la reserva al inquilino.", spyPendiente.enviarMailQueConfirmaLaAprobacion());
    }

    @Test
    void alCancelarseCambiaDeEstadoACanceladaTest() {
        pendiente.cancelarReserva();
        when(reserva.fueCancelada()).thenReturn(true);
        assertTrue(reserva.fueCancelada());
    }
}
