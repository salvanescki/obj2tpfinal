package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
    void inicializacionReservaPendiente() {
        assertEquals(reserva, pendiente.getReserva());
    }

    @Test
    void getReservaTest() {
        assertEquals(reserva, pendiente.getReserva());
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
        Usuario inquilinoMock = mock(Usuario.class);
        Usuario propietarioMock = mock(Usuario.class);
        Publicacion publicacionMock = mock(Publicacion.class);

        when(mock(TipoDeInmueble.class).getTipoDeInmueble()).thenReturn("casa");
        when(inquilinoMock.getEmail()).thenReturn("inquilino@gmail.com");
        when(publicacionMock.getTipoDeInmueble()).thenReturn(mock(TipoDeInmueble.class));
        when(propietarioMock.getEmail()).thenReturn("propietario@gmail.com");
        when(publicacionMock.getPropietario()).thenReturn(propietarioMock);
        when(reserva.getInquilino()).thenReturn(inquilinoMock);
        when(reserva.getPublicacion()).thenReturn(publicacionMock);

        try (MockedStatic<Mail> mockedMail = mockStatic(Mail.class)) {
            when(Mail.enviar(any(Mail.class))).thenReturn("true");

            when(reserva.estaAprobada()).thenReturn(true);

            pendiente.aprobarReserva();

            assertTrue(reserva.estaAprobada());
            mockedMail.verify(() -> Mail.enviar(any(Mail.class)), times(1));
        }
    }


        @Test
        void alCancelarseCambiaDeEstadoACanceladaTest() {
            pendiente.cancelarReserva();
            when(reserva.fueCancelada()).thenReturn(true);
            assertTrue(reserva.fueCancelada());
        }
    }

