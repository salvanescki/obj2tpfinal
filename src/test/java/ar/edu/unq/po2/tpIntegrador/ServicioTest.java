package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

    @Test
    void testConstructor() {

        String nombreServicio = "Internet";

        Servicio servicio = new Servicio(nombreServicio);

        assertEquals(TextoNormalizado.normalizarTexto(nombreServicio), servicio.getServicio());
    }

    @Test
    void testSetServicio() {

        Servicio servicio = new Servicio("Telefonía");
        String nuevoServicio = "Cable TV";

        servicio.setServicio(nuevoServicio);

        assertEquals(TextoNormalizado.normalizarTexto(nuevoServicio), servicio.getServicio());
    }

    @Test
    void equalsTest() {
        Servicio internet = new Servicio("Internet");
        assertEquals(internet, internet);
        assertEquals(new Servicio("Telefonía"), new Servicio("TeLefoníA..-.-23"));
    }
}