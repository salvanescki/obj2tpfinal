package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FormaDePagoTest {

    @Test
    void testConstructor() {
        String formaDePago = "Tarjeta de Crédito";

        FormaDePago formaDePagoObj = new FormaDePago(formaDePago);

        assertEquals(TextoNormalizado.normalizarTexto(formaDePago), formaDePagoObj.getFormaDePago());
    }

    @Test
    void testSetFormaDePago() {
        FormaDePago formaDePagoObj = new FormaDePago("Efectivo");
        String nuevaFormaDePago = "Transferencia Bancaria";

        formaDePagoObj.setFormaDePago(nuevaFormaDePago);

        assertEquals(TextoNormalizado.normalizarTexto(nuevaFormaDePago), formaDePagoObj.getFormaDePago());
    }

    @Test
    void equalsTest() {
        FormaDePago tarjeta = new FormaDePago(" 4-.24-.-3.4.-t A rg E t  a-..- ");
        FormaDePago tarjetaDos = new FormaDePago("Targeta");

        assertEquals(tarjeta, tarjeta);
        assertEquals(tarjeta, tarjetaDos);
    }
}
