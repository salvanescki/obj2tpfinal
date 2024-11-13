package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PoliticaDeCancelacionGratuitaTest {

    private PoliticaDeCancelacion politicaDeCancelacion;
    private Publicacion publicacion;
    private Reserva reserva;
    private Inquilino inquilino;
    private Precio precioTotalReserva;
    private Periodo periodoReserva;

    @BeforeEach
    void setUp() {
        politicaDeCancelacion = new PoliticaDeCancelacionGratuita();
        publicacion = mock(Publicacion.class);
        inquilino = mock(Usuario.class);
        reserva = mock(Reserva.class);
        precioTotalReserva = mock(Precio.class);
        periodoReserva = mock(Periodo.class);
        when(reserva.getInquilino()).thenReturn(inquilino);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        when(reserva.getPeriodo()).thenReturn(periodoReserva);
        when(precioTotalReserva.getPrecio()).thenReturn(20000.0);
        when(publicacion.getPrecio(reserva.getPeriodo())).thenReturn(precioTotalReserva);
    }

    private void setUpFechaInicialReserva(LocalDate fechaDesde){
        when(reserva.getFechaDesde()).thenReturn(fechaDesde);
        when(periodoReserva.getFechaDesde()).thenReturn(fechaDesde);
    }

    @Test
    void efectuarCancelacionAntesDeDiezDiasPreviosNoEndeudaAlInquilinoTest() {
        setUpFechaInicialReserva(LocalDate.now().plusDays(12));
        politicaDeCancelacion.efectuarCancelacion(reserva);
        verify(inquilino, never()).agregarPagoPendiente(any());
    }

    @Test
    void efectuarCancelacionDiezDiasAntesNoEndeudaAlInquilinoTest() {
        setUpFechaInicialReserva(LocalDate.now().plusDays(10));
        politicaDeCancelacion.efectuarCancelacion(reserva);
        verify(inquilino, never()).agregarPagoPendiente(any());
    }

    @Test
    void efectuarCancelacionLuegoDeDiezDiasAntesEndeudaAlInquilinoConDosDiasDeReservaTest() {
        LocalDate fechaDesde = LocalDate.now().plusDays(7);
        setUpFechaInicialReserva(fechaDesde);

        Periodo primerosDosDiasDeReserva = mock(Periodo.class);
        when(primerosDosDiasDeReserva.getFechaDesde()).thenReturn(fechaDesde);
        when(primerosDosDiasDeReserva.getFechaHasta()).thenReturn(fechaDesde.plusDays(2));

        Precio precioDosDiasDeReserva = mock(Precio.class);
        when(precioDosDiasDeReserva.getPrecio()).thenReturn(2000.0);
        when(publicacion.getPrecio(any())).thenReturn(precioDosDiasDeReserva);

        politicaDeCancelacion.efectuarCancelacion(reserva);

        ArgumentCaptor<Deuda> deudaArgumentCaptor = ArgumentCaptor.forClass(Deuda.class);
        verify(inquilino, atMost(1)).agregarPagoPendiente(deudaArgumentCaptor.capture());

        Deuda deudaCapturada = deudaArgumentCaptor.getValue();
        assertEquals(precioDosDiasDeReserva, deudaCapturada.getMontoAPagar());
    }

}
