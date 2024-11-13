package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atMost;

public class PoliticaSinCancelacionTest {

    private PoliticaDeCancelacion politicaDeCancelacion;
    private Publicacion publicacion;
    private Reserva reserva;
    private Inquilino inquilino;
    private Precio precioTotalReserva;
    private Periodo periodoReserva;

    @BeforeEach
    void setUp() {
        politicaDeCancelacion = new PoliticaSinCancelacion();
        publicacion = mock(Publicacion.class);
        inquilino = mock(Usuario.class);
        reserva = mock(Reserva.class);
        precioTotalReserva = mock(Precio.class);
        periodoReserva = mock(Periodo.class);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        when(reserva.getInquilino()).thenReturn(inquilino);
        when(reserva.getPeriodo()).thenReturn(periodoReserva);
        when(precioTotalReserva.getPrecio()).thenReturn(20000.0);
        when(publicacion.getPrecio(reserva.getPeriodo())).thenReturn(precioTotalReserva);
    }

    @Test
    void efectuarCancelacionTest() {
        politicaDeCancelacion.efectuarCancelacion(reserva);

        ArgumentCaptor<Deuda> deudaArgumentCaptor = ArgumentCaptor.forClass(Deuda.class);
        verify(inquilino, atMost(1)).agregarPagoPendiente(deudaArgumentCaptor.capture());

        Deuda deudaCapturada = deudaArgumentCaptor.getValue();
        assertEquals(precioTotalReserva, deudaCapturada.getMontoAPagar());
    }
}
