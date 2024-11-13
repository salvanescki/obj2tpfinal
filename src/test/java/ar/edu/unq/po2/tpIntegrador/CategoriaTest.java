package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void testConstructor() {
        String nombreCategoria = "Electrónica";

        Categoria categoria = new Categoria(nombreCategoria);

        assertEquals(TextoNormalizado.normalizarTexto(nombreCategoria), categoria.getCategoria());
    }

    @Test
    void testSetCategoria() {
        Categoria categoria = new Categoria("Hogar");
        String nuevaCategoria = "Deportes";

        categoria.setCategoria(nuevaCategoria);

        assertEquals(TextoNormalizado.normalizarTexto(nuevaCategoria), categoria.getCategoria());
    }

}