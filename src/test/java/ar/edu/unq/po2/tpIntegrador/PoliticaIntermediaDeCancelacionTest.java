package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atMost;

public class PoliticaIntermediaDeCancelacionTest {

    private PoliticaDeCancelacion politicaDeCancelacion;
    private Publicacion publicacion;
    private Reserva reserva;
    private Usuario inquilino;
    private Precio precioTotalReserva;
    private Precio mitadPrecioReserva;
    private Periodo periodoReserva;

    @BeforeEach
    void setUp() {
        politicaDeCancelacion = new PoliticaIntermediaDeCancelacion();
        publicacion = mock(Publicacion.class);
        inquilino = mock(Usuario.class);
        reserva = mock(Reserva.class);
        precioTotalReserva = mock(Precio.class);
        mitadPrecioReserva = mock(Precio.class);
        when(precioTotalReserva.decrementarEnPorcentaje(50)).thenReturn(mitadPrecioReserva);
        periodoReserva = mock(Periodo.class);
        when(reserva.getInquilino()).thenReturn(inquilino);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        when(reserva.getPeriodo()).thenReturn(periodoReserva);
        when(precioTotalReserva.getPrecio()).thenReturn(20000.0);
        when(mitadPrecioReserva.getPrecio()).thenReturn(10000.0);
        when(publicacion.getPrecio(reserva.getPeriodo())).thenReturn(precioTotalReserva);
    }

    private void setUpFechaInicialReserva(LocalDate fechaDesde){
        when(reserva.getFechaDesde()).thenReturn(fechaDesde);
        when(periodoReserva.getFechaDesde()).thenReturn(fechaDesde);
    }

    @Test
    void efectuarCancelacionAntesDeVeinteDiasPreviosNoEndeudaAlInquilinoTest() {
        setUpFechaInicialReserva(LocalDate.now().plusDays(25));
        politicaDeCancelacion.efectuarCancelacion(reserva);
        verify(inquilino, never()).agregarPagoPendiente(any());
    }

    @Test
    void efectuarCancelacionVeinteDiasAntesNoEndeudaAlInquilinoTest() {
        setUpFechaInicialReserva(LocalDate.now().plusDays(20));
        politicaDeCancelacion.efectuarCancelacion(reserva);
        verify(inquilino, never()).agregarPagoPendiente(any());
    }

    private Deuda getDeudaCapturada(){
        ArgumentCaptor<Deuda> deudaArgumentCaptor = ArgumentCaptor.forClass(Deuda.class);
        verify(inquilino, atMost(1)).agregarPagoPendiente(deudaArgumentCaptor.capture());

        return deudaArgumentCaptor.getValue();
    }

    @Test
    void efectuarCancelacionDiecinueveDiasAntesEndeudaAlInquilinoConLaMitadDelPrecioTest() {
        LocalDate fechaDesde = LocalDate.now().plusDays(19);
        setUpFechaInicialReserva(fechaDesde);

        politicaDeCancelacion.efectuarCancelacion(reserva);

        assertEquals(mitadPrecioReserva, getDeudaCapturada().getMontoAPagar());
    }

    @Test
    void efectuarCancelacionEntreDiecinueveDiasAntesYDiezDiasAntesEndeudaAlInquilinoConLaMitadDelPrecioTest() {
        LocalDate fechaDesde = LocalDate.now().plusDays(14);
        setUpFechaInicialReserva(fechaDesde);

        politicaDeCancelacion.efectuarCancelacion(reserva);

        assertEquals(mitadPrecioReserva, getDeudaCapturada().getMontoAPagar());
    }

    @Test
    void efectuarCancelacionDiezDiasAntesEndeudaAlInquilinoConLaMitadDelPrecioTest() {
        LocalDate fechaDesde = LocalDate.now().plusDays(10);
        setUpFechaInicialReserva(fechaDesde);

        politicaDeCancelacion.efectuarCancelacion(reserva);

        assertEquals(mitadPrecioReserva, getDeudaCapturada().getMontoAPagar());
    }

    @Test
    void efectuarCancelacionDespuesDeDiezDiasAntesEndeudaAlInquilinoConElTotalDelPrecioTest() {
        LocalDate fechaDesde = LocalDate.now().plusDays(9);
        setUpFechaInicialReserva(fechaDesde);

        politicaDeCancelacion.efectuarCancelacion(reserva);

        assertEquals(precioTotalReserva, getDeudaCapturada().getMontoAPagar());
    }


}
