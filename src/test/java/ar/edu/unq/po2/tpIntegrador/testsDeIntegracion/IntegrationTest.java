package ar.edu.unq.po2.tpIntegrador.testsDeIntegracion;
import ar.edu.unq.po2.tpIntegrador.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Cristian", "Cristian@gmial.com", "1165543383");
    }

    @Test
    void usuarioSeRegistraComoPropietario() {
        assertEquals("Propietario", usuario.getTipoPropietario());
    }

    @Test
    void usuarioSeRegistraComoInquilino(){
        assertEquals("Inquilino", usuario.getTipoInquilino());
    }
/*
    @Test
    void usuarioNoPuedeRegistrarseConNombreNulo() {
        usuarioNoPuedeRegistrarseConDatosIncompletosException excepcion = assertThrows(
                usuarioNoPuedeRegistrarseConDatosIncompletosException.class,
                () -> new Usuario(null, "test@example.com", "123456789")
        );
        assertTrue(excepcion.getMessage().contains("El nombre no puede ser nulo o vacío"));
    }

    @Test
    void usuarioNoPuedeRegistrarseConEmailNulo() {
        usuarioNoPuedeRegistrarseConDatosIncompletosException excepcion = assertThrows(
                usuarioNoPuedeRegistrarseConDatosIncompletosException.class,
                () -> new Usuario("Juan", null, "123456789")
        );
        assertTrue(excepcion.getMessage().contains("El email no puede ser nulo o vacío"));
    }

    @Test
    void usuarioNoPuedeRegistrarseConTelefonoNulo() {
        usuarioNoPuedeRegistrarseConDatosIncompletosException excepcion = assertThrows(
                usuarioNoPuedeRegistrarseConDatosIncompletosException.class,
                () -> new Usuario("Juan", "test@example.com", null)
        );
        assertTrue(excepcion.getMessage().contains("El teléfono no puede ser nulo o vacío"));
    }

 */
}

