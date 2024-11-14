package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TipoDeInmuebleTest {

    @Test
    void testConstructor() {

        String nombreTipoDeInmueble = "bungalo";

        TipoDeInmueble tipoDeInmueble = new TipoDeInmueble(nombreTipoDeInmueble);

        assertEquals(TextoNormalizado.normalizarTexto(nombreTipoDeInmueble), tipoDeInmueble.getTipoDeInmueble());
    }

    @Test
    void testSetTipoDeInmueble() {

        TipoDeInmueble tipoDeInmueble = new TipoDeInmueble("Bungalo");
        String nuevoTipoDeInmueble = "Apartamento";

        tipoDeInmueble.setTipoDeInmueble(nuevoTipoDeInmueble);

        assertEquals(TextoNormalizado.normalizarTexto(nuevoTipoDeInmueble), tipoDeInmueble.getTipoDeInmueble());
    }

    @Test
    void equalsTest() {
        TipoDeInmueble tipoDeInmuebleA = new TipoDeInmueble("   ,.,-_323cAsA,.,. ");
        TipoDeInmueble tipoDeInmuebleB = new TipoDeInmueble("Casa");

        assertEquals(tipoDeInmuebleA, tipoDeInmuebleA);
        assertEquals(tipoDeInmuebleA, tipoDeInmuebleB);
    }
}