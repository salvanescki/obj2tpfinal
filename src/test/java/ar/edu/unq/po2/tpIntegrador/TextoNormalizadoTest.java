package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextoNormalizadoTest {

    @Test
    void formatearTextoNullLanzaExcepcionTest() {
        NullPointerException excepcion = assertThrows(NullPointerException.class, ()-> TextoNormalizado.normalizarTexto(null));

        assertTrue(excepcion.getMessage().contains("No fue ingresado correctamente texto, es null"));
    }

    @Test
    void seEliminanLosEspaciosPuntosComasGuionesGuionesBajoAcentosMinusculasYNumerosTest() {
        String textoSinNormalizar = ".--- ..-_ :H 324 1ól3 4 2a.... _-.m71Ú2n3Do00.-_..-";
        assertEquals("HOLAMUNDO", TextoNormalizado.normalizarTexto(textoSinNormalizar));
    }
}
