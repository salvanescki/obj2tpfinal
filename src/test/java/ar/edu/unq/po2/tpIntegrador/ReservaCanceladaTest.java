package ar.edu.unq.po2.tpIntegrador;
import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaCanceladaTest {

    private ReservaCancelada cancelada;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = mock(Reserva.class);
        cancelada = new ReservaCancelada(reserva);
    }

    @Test
    void noEsEstadoReservaPendienteTest() {
        assertFalse(cancelada.estaPendiente());
    }

    @Test
    void noEsEstadoReservaAprobadaTest() {
        assertFalse(cancelada.estaAprobada());
    }

    @Test
    void esEstadoReservaCanceladaTest() {
        assertTrue(cancelada.fueCancelada());
    }

    @Test
    void alIntentarAprobarseLanzaExcepcionTest() {
        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            cancelada.aprobarReserva();
        });

        assertTrue(excepcion.getMessage().contains("No se puede aprobar una reserva ya cancelada."));
    }

    @Test
    void alIntentarCancelarseLanzaExcepcionTest() {
        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            cancelada.cancelarReserva();
        });

        assertTrue(excepcion.getMessage().contains("No se puede cancelar una reserva ya cancelada."));

    }
}