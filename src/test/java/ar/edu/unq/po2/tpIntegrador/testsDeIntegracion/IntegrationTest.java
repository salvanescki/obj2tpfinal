package ar.edu.unq.po2.tpIntegrador.testsDeIntegracion;
import ar.edu.unq.po2.tpIntegrador.*;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("hola chicos", "sigo con el informe", "quiero terminar xd");
    }

    @Test
    void name() {
    }
}
