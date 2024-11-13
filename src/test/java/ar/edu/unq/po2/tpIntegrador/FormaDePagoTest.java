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
}
