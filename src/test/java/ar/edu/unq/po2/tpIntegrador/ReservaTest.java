package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaTest {

    private Reserva reserva;
    private EstadoReserva estadoAprobadaMock;
    private EstadoReserva estadoPendienteMock;
    private EstadoReserva estadoCanceladaMock;
    private Publicacion dummyPublicacion;
    private Usuario dummyInquilino;
    private FormaDePago dummyFormaDePago;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @BeforeEach
    void setUp() {
        estadoPendienteMock = mock(ReservaPendiente.class);
        estadoCanceladaMock = mock(ReservaCancelada.class);
        estadoAprobadaMock = mock(ReservaAprobada.class);
        dummyPublicacion = mock(Publicacion.class);
        dummyInquilino = mock(Usuario.class);
        dummyFormaDePago = mock(FormaDePago.class);
        fechaInicio = LocalDate.now();
        fechaFin = fechaInicio.plusDays(10);
        reserva = spy(new Reserva(dummyInquilino, fechaInicio, fechaFin, dummyFormaDePago, dummyPublicacion));

        when(estadoPendienteMock.estaPendiente()).thenReturn(true);
        reserva.setEstado(estadoPendienteMock);
    }

    @Test
    void estadoInicialReservaPendienteTest() {

        assertTrue(reserva.estaPendiente());
    }

    @Test
    void getInquilinoTest() {
        assertEquals(dummyInquilino, reserva.getInquilino());
    }

    @Test
    void getFechaInicioTest() {
        assertEquals(fechaInicio, reserva.getFechaDesde());
    }

    @Test
    void getFechaFinTest() {
        assertEquals(fechaFin, reserva.getFechaHasta());
    }

    @Test
    void reservaPendientePuedeSerAceptadaTest() {

        doAnswer(invocation -> {
            reserva.setEstado(estadoAprobadaMock);
            return null;
        }).when(reserva).aprobarReserva();

        reserva.aprobarReserva();

        verify(estadoPendienteMock).aprobarReserva();
        when(estadoAprobadaMock.estaAprobada()).thenReturn(true);

        assertTrue(reserva.estaAprobada());
        //        TODO: MANDAR MAIL A INQUILINO Y (CONSOLIDAR EN EL SISTEMA)
    }

    @Test
    void reservaPendientePuedeSerCanceladaTest() {

        doAnswer(invocation -> {
            reserva.setEstado(estadoCanceladaMock);
            return null;
        }).when(reserva).cancelarReserva();

        reserva.cancelarReserva();

        verify(estadoPendienteMock).cancelarReserva();
        when(estadoCanceladaMock.fueCancelada()).thenReturn(true);

        assertTrue(reserva.fueCancelada());
    }

    @Test
    void aprobarReservaAprobadaLanzaExcepcionTest() {
        reserva.setEstado(estadoAprobadaMock);

        doThrow(new OperacionInvalidaConEstadoReservaException("No se puede aprobar una reserva ya aprobada."))
                .when(estadoAprobadaMock).aprobarReserva();

        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            reserva.aprobarReserva();
        });
        assertTrue(excepcion.getMessage().contains("No se puede aprobar una reserva ya aprobada."));

        verify(estadoAprobadaMock, times(1)).aprobarReserva();
    }
}
