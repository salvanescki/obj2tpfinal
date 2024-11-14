package ar.edu.unq.po2.tpIntegrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MailTest {

    private Reserva reserva;
    private Mail mail;

    @BeforeEach
    void setUp() {
        reserva = mock(Reserva.class);
        mail = new Mail("remitente@gmail.com", "destinatario@gmail.com", "Asunto", "Mensaje");
    }

    @Test
    void inicializacionMailTest() {
        assertEquals("remitente@gmail.com", mail.getDe());
        assertEquals("destinatario@gmail.com", mail.getA());
        assertEquals("Asunto", mail.getAsunto());
        assertEquals("Mensaje", mail.getMensaje());
    }

    @Test
    void getDeTest() {
        assertEquals("remitente@gmail.com", mail.getDe());
    }

    @Test
    void getATest() {
        assertEquals("destinatario@gmail.com", mail.getA());
    }

    @Test
    void getAsuntoTest() {
        assertEquals("Asunto", mail.getAsunto());
    }

    @Test
    void getMensajeTest() {
        assertEquals("Mensaje", mail.getMensaje());
    }

    @Test
    void setDeTest() {
        mail.setDe("nuevoRemitente@gmail.com");
        assertEquals("nuevoRemitente@gmail.com", mail.getDe());
    }

    @Test
    void setATest() {
        mail.setA("nuevoDestinatario@gmail.com");
        assertEquals("nuevoDestinatario@gmail.com", mail.getA());
    }

    @Test
    void setAsuntoTest() {
        mail.setAsunto("Nuevo asunto");
        assertEquals("Nuevo asunto", mail.getAsunto());
    }

    @Test
    void setMensajeTest() {
        mail.setMensaje("Nuevo mensaje");
        assertEquals("Nuevo mensaje", mail.getMensaje());
    }

    @Test
    void agregarArchivoAdjuntoConReservaTest() {
        mail.agregarArchivoAdjunto(reserva);
        assertEquals(reserva, mail.getAdjunto());
    }

    @Test
    void toStringSinAdjuntoTest() {
        String resultado = mail.toString();

        assertTrue(resultado.contains("Exitoso envio de mail de: remitente@gmail.com"));
        assertTrue(resultado.contains("Para: destinatario@gmail.com"));
        assertTrue(resultado.contains("Asunto: Asunto"));
        assertTrue(resultado.contains("Mensaje: Mensaje"));
        assertFalse(resultado.contains("Archivo adjunto: "));
    }

    @Test
    void toStringConAdjuntoTest() {
        mail.agregarArchivoAdjunto(reserva);

        String resultado = mail.toString();

        assertTrue(resultado.contains("Exitoso envio de mail de: remitente@gmail.com"));
        assertTrue(resultado.contains("Para: destinatario@gmail.com"));
        assertTrue(resultado.contains("Asunto: Asunto"));
        assertTrue(resultado.contains("Mensaje: Mensaje"));
        assertTrue(resultado.contains("Archivo adjunto:"));
    }


    @Test
    void enviarTest() {
        String mensajeEsperado = "Exitoso envio de mail de: remitente@gmail.com. Para: destinatario@gmail.com. Asunto: Asunto. Mensaje: Mensaje.";
        assertEquals(mensajeEsperado, Mail.enviar(mail));
    }
}