package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaAprobadaTest {

    private ReservaAprobada aprobada;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = mock(Reserva.class);
        aprobada = new ReservaAprobada(reserva);
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
        when(reserva.fueCancelada()).thenReturn(true);
        ReservaAprobada spyAprobada = spy(aprobada);
        verify(spyAprobada, times(0)).enviarMailQueAvisaLaCancelacion();
        spyAprobada.cancelarReserva();
        verify(spyAprobada, times(1)).enviarMailQueAvisaLaCancelacion();
        assertTrue(reserva.fueCancelada());
        assertEquals("Se envio con exito el aviso de cancelacion al propietario de la publicacion.", spyAprobada.enviarMailQueAvisaLaCancelacion());
    }
}
