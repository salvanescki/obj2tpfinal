package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.MontoIngresadoMayorADeudaException;
import ar.edu.unq.po2.tpIntegrador.excepciones.MontoIngresadoNoValidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeudaTest {
    
    private Pagable deuda;
    private Precio montoDeuda;
    private Precio mitadDeLaDeuda;
    private Precio montoCero;

    @BeforeEach
    void setUp() {
        montoDeuda = mock(Precio.class);
        when(montoDeuda.getPrecio()).thenReturn(2000.0);
        mitadDeLaDeuda = mock(Precio.class);
        when(mitadDeLaDeuda.getPrecio()).thenReturn(1000.0);
        when(montoDeuda.decrementarEnPorcentaje(50)).thenReturn(mitadDeLaDeuda);
        when(montoDeuda.restar(mitadDeLaDeuda)).thenReturn(mitadDeLaDeuda);
        montoCero = mock(Precio.class);
        when(montoCero.getPrecio()).thenReturn(0.0);
        when(montoDeuda.restar(montoDeuda)).thenReturn(montoCero);
        deuda = new Deuda(montoDeuda);
    }

    @Test
    void getMontoAPagarTest() {
        assertEquals(montoDeuda, deuda.getMontoAPagar());
    }

    @Test
    void pagarUnMontoMayorALaDeudaLanzaExcepcionTest() {
        Precio montoMayorALaDeuda = mock(Precio.class);
        when(montoMayorALaDeuda.compareTo(montoDeuda)).thenReturn(1);

        MontoIngresadoMayorADeudaException excepcion = assertThrows(MontoIngresadoMayorADeudaException.class, ()-> deuda.pagar(montoMayorALaDeuda));
        assertTrue(excepcion.getMessage().contains("El monto que estás queriendo pagar es mayor a la deuda total"));
    }

    @Test
    void pagarUnMontoCeroONegativoLanzaExcepcionTest() {
        when(montoCero.compareTo(montoDeuda)).thenReturn(-1);
        /*
            El caso del monto negativo no debería ser posible porque Precio lanza excepción antes,
            pero como Precio podría cambiar a futuro, es mejor manejar la excepción también en Deuda,
            asi Deuda no depende de la implementación de Precio.
        */
        Precio montoNegativo = mock(Precio.class);
        when(montoNegativo.getPrecio()).thenReturn(-1.0);
        when(montoNegativo.compareTo(montoDeuda)).thenReturn(-1);

        MontoIngresadoNoValidoException excepcion = assertThrows(MontoIngresadoNoValidoException.class, ()-> deuda.pagar(montoCero));
        assertTrue(excepcion.getMessage().contains("El monto ingresado no puede ser menor o igual a cero"));

        excepcion = assertThrows(MontoIngresadoNoValidoException.class, ()-> deuda.pagar(montoNegativo));
        assertTrue(excepcion.getMessage().contains("El monto ingresado no puede ser menor o igual a cero"));
    }

    @Test
    void pagarLaTotalidadDeLaDeudaTest() {
        deuda.pagar(deuda.getMontoAPagar());
        assertEquals(0.0, deuda.getMontoAPagar().getPrecio());
    }

    @Test
    void pagarUnaParteDeLaDeudaTest() {
        when(mitadDeLaDeuda.compareTo(montoDeuda)).thenReturn(-1);
        deuda.pagar(deuda.getMontoAPagar().decrementarEnPorcentaje(50));
        assertEquals(mitadDeLaDeuda, deuda.getMontoAPagar());
        assertEquals(1000.0, deuda.getMontoAPagar().getPrecio());
    }
}
