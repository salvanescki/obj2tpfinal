package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unq.po2.tpIntegrador.excepciones.PrecioInvalidoException;
import ar.edu.unq.po2.tpIntegrador.excepciones.RestaDePreciosInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrecioTest {

    private Precio precioA;
    private Precio precioB;

    @BeforeEach
    void setUp() {
        precioA = new Precio(12, 15);
        precioB = new Precio(30, 2);
    }

    @Test
    void getPrecioTest() {
        assertEquals(12.15, precioA.getPrecio());
    }

    @Test
    void instanciarPrecioSinDecimalesTest() {
        Precio sinDecimales = new Precio(34);
        assertEquals(34.00, sinDecimales.getPrecio());
    }

    @Test
    void instanciarPrecioConUnDecimalTest() {
        Precio unDecimal = new Precio(34, 1);
        assertEquals(34.10, unDecimal.getPrecio());
    }

    @Test
    void instanciarPrecioConDosDecimalesTest() {
        Precio dosDecimales = new Precio(34, 12);
        assertEquals(34.12, dosDecimales.getPrecio());
    }

    @Test
    void instanciarPrecioConMasDeTresDecimalesLanzaExcepcionTest() {
        PrecioInvalidoException excepcion = assertThrows(PrecioInvalidoException.class, ()->{
                                                            Precio failPrecio = new Precio(145, 309);
                                                        });
        assertTrue(excepcion.getMessage().contains("El precio ingresado no es válido, debe contener menos de 3 decimales"));
    }

    @Test
    void instanciarPrecioNumerosNegativosLanzaExcepcionTest() {
        PrecioInvalidoException excepcion = assertThrows(PrecioInvalidoException.class, ()->{
                                                            Precio failPrecio = new Precio(-145, 30);
                                                        });
        assertTrue(excepcion.getMessage().contains("El precio ingresado no es válido, no se admiten números negativos"));
    }

    @Test
    void toStringTest() {
        assertEquals("12.15", precioA.toString());
    }

    @Test
    void sumarPrecioTest() {
        assertEquals(42.35, precioA.sumar(precioB).getPrecio());
    }

    @Test
    void restarPrecioTest() {
        assertEquals(18.05, precioB.restar(precioA).getPrecio());
    }

    @Test
    void restarUnNumeroMasGrandeLanzaExcepcionTest() {
        RestaDePreciosInvalidaException excepcion = assertThrows(RestaDePreciosInvalidaException.class, ()->{
            Precio failPrecio = precioA.restar(precioB);
        });
        assertTrue(excepcion.getMessage().contains("No se puede restar un número más grande de otro más chico"));
    }

    @Test
    void incrementarEnPorcentajeEnteroTest() {
        assertEquals(14.70, precioA.incremetarEnPorcentaje(21));
    }

    @Test
    void incrementarEnPorcentajeConDecimalesTest() {
        assertEquals(14.76, precioA.incremetarEnPorcentaje(21.5));
    }

    @Test
    void incrementarEnCeroPorcientoDevuelveElMismoNumeroTest() {
        assertEquals(12.15, precioA.incremetarEnPorcentaje(0));
        assertEquals(12.15, precioA.incremetarEnPorcentaje(0.0));
        assertEquals(12.15, precioA.incremetarEnPorcentaje(0.00));
    }

    @Test
    void decrementarEnPorcentajeEnteroTest() {
        assertEquals(3.03, precioA.decremetarEnPorcentaje(75));
    }

    @Test
    void decrementarEnPorcentajeConDecimalesTest() {
        assertEquals(2.97, precioA.decremetarEnPorcentaje(75.5));
    }

    @Test
    void decrementarMasDelCienPorCientoLanzaExcepcionTest() {
        PrecioInvalidoException excepcion = assertThrows(PrecioInvalidoException.class, ()->{
                                                            Precio failPrecio = precioA.decremetarEnPorcentaje(100.1);
                                                        });
        assertTrue(excepcion.getMessage().contains("No se puede decrementar un porcentaje mayor al 100, ya que daría un precio negativo (inválido)"));
    }

    @Test
    void decrementarEnCeroPorcientoDevuelveElMismoNumeroTest() {
        assertEquals(12.15, precioA.decremetarEnPorcentaje(0));
        assertEquals(12.15, precioA.decremetarEnPorcentaje(0.0));
        assertEquals(12.15, precioA.decremetarEnPorcentaje(0.00));
    }

}
