package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.PeriodoSinPrecioDefinidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodoTest {

    Periodo periodoSinPrecio;
    Periodo periodoConPrecio;
    LocalDate ahora;
    LocalDate enUnMes;
    LocalDate enDosMeses;
    LocalDate enTresMeses;
    LocalDate enCuatroMeses;

    @BeforeEach
    void setUp() {
        ahora = LocalDate.now();
        enUnMes = ahora.plusMonths(1);
        enDosMeses = ahora.plusMonths(2);
        enTresMeses = ahora.plusMonths(3);
        enCuatroMeses = ahora.plusMonths(4);
        periodoSinPrecio = new Periodo(ahora, enUnMes);
        periodoConPrecio = new Periodo(enDosMeses, enCuatroMeses, new Precio(25));
    }

    @Test
    void getFechaDesdeTest() {
        assertEquals(ahora, periodoSinPrecio.getFechaDesde());
        assertEquals(enDosMeses, periodoConPrecio.getFechaDesde());
    }

    @Test
    void getFechaHastaTest() {
        assertEquals(enUnMes, periodoSinPrecio.getFechaHasta());
        assertEquals(enCuatroMeses, periodoConPrecio.getFechaHasta());
    }

    @Test
    void estaDentroDelPeriodoTest() {
        assertFalse(periodoSinPrecio.estaDentroDelPeriodo(ahora.minusDays(1)));
        assertTrue(periodoSinPrecio.estaDentroDelPeriodo(ahora));
        assertTrue(periodoSinPrecio.estaDentroDelPeriodo(ahora.plusDays(15)));
        assertTrue(periodoSinPrecio.estaDentroDelPeriodo(enUnMes));
        assertFalse(periodoSinPrecio.estaDentroDelPeriodo(enUnMes.plusDays(1)));
    }

    @Test
    void getPrecioDeUnPeriodoSinPrecioLanzaExcepcionTest() {
        PeriodoSinPrecioDefinidoException excepcion = assertThrows(PeriodoSinPrecioDefinidoException.class, ()->
                periodoSinPrecio.getPrecio()
        );
        assertTrue(excepcion.getMessage().contains("El periodo no tiene definido un precio"));
    }

    @Test
    void getPrecioTest() {
        assertEquals(new Precio(25), periodoConPrecio.getPrecio());
    }

    @Test
    void periodoNoSeSuperponeConOtroPosteriorEnElTiempoTest() {
        assertFalse(periodoSinPrecio.seSuperponeCon(periodoConPrecio));
    }

    @Test
    void periodoNoSeSuperponeConOtroAnteriorEnElTiempoTest() {
        assertFalse(periodoConPrecio.seSuperponeCon(periodoSinPrecio));
    }

    @Test
    void periodoSeSuperponeConOtroIncluidoEnElMismoTest() {
        assertTrue(periodoConPrecio.seSuperponeCon(new Periodo(enTresMeses, enTresMeses.plusDays(15))));
    }

    @Test
    void periodoSeSuperponeConOtroIncluidoEnElPeroQueDuraMasTest() {
        assertTrue(periodoConPrecio.seSuperponeCon(new Periodo(enTresMeses, enCuatroMeses.plusMonths(2))));
    }

    @Test
    void periodoSeSuperponeConOtroIncluidoEnElPeroQueEmpezoAntesTest() {
        assertTrue(periodoConPrecio.seSuperponeCon(new Periodo(enUnMes, enTresMeses)));
    }

    @Test
    void periodoSeSuperponeConOtroAunqueCompartaSoloUnDiaTest() {
        assertTrue(periodoConPrecio.seSuperponeCon(new Periodo(enCuatroMeses, enCuatroMeses.plusMonths(4))));
        assertTrue(periodoConPrecio.seSuperponeCon(new Periodo(ahora, enDosMeses)));
    }

    @Test
    void periodoSonIgualesSiCompartenAmbasFechasTest() {
        assertEquals(periodoConPrecio, new Periodo(enDosMeses, enCuatroMeses));
    }
}
