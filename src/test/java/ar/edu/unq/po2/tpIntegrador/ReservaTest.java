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
    void getPublicacionTest() {
        assertEquals(dummyPublicacion, reserva.getPublicacion());
    }

    @Test
    void getFormaDePagoTest() {
        assertEquals(dummyFormaDePago, reserva.getFormaDePago());
    }

    @Test
    void inicializacionDeReservaTest() {
        assertEquals(fechaInicio, reserva.getFechaDesde());
        assertEquals(fechaFin, reserva.getFechaHasta());
        assertEquals(dummyInquilino, reserva.getInquilino());
        assertEquals(dummyFormaDePago, reserva.getFormaDePago());
        assertEquals(dummyPublicacion, reserva.getPublicacion());
    }

    @Test
    void estadoInicialReservaPendienteTest() {
        assertTrue(reserva.estaPendiente());
    }

    @Test
    void setEstadoCambiaEstadoAAprobadaTest() {
        reserva.setEstado(estadoAprobadaMock);
        when(estadoAprobadaMock.estaPendiente()).thenReturn(true);
        assertTrue(reserva.estaAprobada());
    }

    @Test
    void setEstadoCambiaEstadoAPendienteTest() {
        setEstadoCambiaEstadoAAprobadaTest();

        reserva.setEstado(estadoPendienteMock);
        when(estadoPendienteMock.estaPendiente()).thenReturn(true);
        assertTrue(reserva.estaPendiente());
    }

    @Test
    void setEstadoCambiaEstadoACanceladaTest() {
        reserva.setEstado(estadoCanceladaMock);
        when(estadoCanceladaMock.estaPendiente()).thenReturn(true);
        assertTrue(reserva.fueCancelada());
    }

    @Test
    void reservaPendientePuedeSerAprobadaTest() {

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
    void reservaAprobadaPuedeSerCanceladaTest() {

        doAnswer(invocation -> {
            reserva.setEstado(estadoCanceladaMock);
            return null;
        }).when(reserva).cancelarReserva();

        reserva.cancelarReserva();

        verify(estadoAprobadaMock).cancelarReserva();
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

    @Test
    void cancelarReservaCanceladaLanzaExcepcionTest() {
        reserva.setEstado(estadoCanceladaMock);

        doThrow(new OperacionInvalidaConEstadoReservaException("No se puede cancelar una reserva ya cancelada."))
                .when(estadoAprobadaMock).aprobarReserva();

        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            reserva.cancelarReserva();
        });
        assertTrue(excepcion.getMessage().contains("No se puede cancelar una reserva ya cancelada."));

        verify(estadoCanceladaMock, times(1)).aprobarReserva();
    }

    @Test
    void aprobarReservaCanceladaLanzaExcepcionTest() {
        reserva.setEstado(estadoCanceladaMock);

        doThrow(new OperacionInvalidaConEstadoReservaException("No se puede aprobar una reserva que ya se cancelo."))
                .when(estadoCanceladaMock).aprobarReserva();

        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            reserva.aprobarReserva();
        });
        assertTrue(excepcion.getMessage().contains("No se puede aprobar una reserva que ya se cancelo."));

        verify(estadoCanceladaMock, times(1)).aprobarReserva();
    }

    @Test
    void validacionDeFechasTest() {
        assertTrue(fechaFin.isAfter(fechaInicio));
        assertEquals(LocalDate.now(), fechaInicio);
        assertEquals(fechaInicio.plusDays(10), fechaFin);
    }

    @Test
    void reservaSeSuperponenConUnPeriodoYaReservadoTest() {
        LocalDate nuevaFechaInicio = fechaInicio.plusDays(5);
        LocalDate nuevaFechaFin = fechaFin;

        assertTrue(reserva.seSuperponeConElPeriodo(nuevaFechaInicio, nuevaFechaFin));
    }

    @Test
    void reservaNoSeSuperponenConUnPeriodoYaReservadoTest() {
        LocalDate nuevaFechaInicio = fechaInicio.plusDays(30);
        LocalDate nuevaFechaFin = fechaFin.plusDays(30);

        assertTrue(reserva.seSuperponeConElPeriodo(nuevaFechaInicio, nuevaFechaFin));
    }

    @Test
    void reservaSeSuperponenConUnPeriodoYaReservadoPorSoloUnDiaTest() {
        LocalDate nuevaFechaInicio = fechaInicio.plusDays(9);
        LocalDate nuevaFechaFin = fechaFin.plusDays(9);

        assertTrue(reserva.seSuperponeConElPeriodo(nuevaFechaInicio, nuevaFechaFin));
    }

    @Test
    void reservarEnFechaSuperpuestaLanzaExcepcion() {

        LocalDate nuevaFechaInicio = fechaInicio.plusDays(5);
        LocalDate nuevaFechaFin = fechaFin.plusDays(5);

        OperacionInvalidaConEstadoReservaException excepcion = assertThrows(OperacionInvalidaConEstadoReservaException.class, () -> {
            reserva.seSuperponeConElPeriodo(nuevaFechaInicio, nuevaFechaFin);
        });

        assertTrue(excepcion.getMessage().contains("Ya existe una reserva en las fechas seleccionadas."));
    }
}


