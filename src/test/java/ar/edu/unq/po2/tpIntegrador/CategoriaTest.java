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

    @Test
    void testGetTipoDeCategoriaSinTipo() {
        assertEquals("Sin tipo", new Categoria("ejemplo").getTipoDeCategoria());
    }

    @Test
    void testGetTipoDeCategoriaInquilino() {
        assertEquals("Inquilino", new CategoriaInquilino("ejemplo").getTipoDeCategoria());
    }

    @Test
    void testGetTipoDeCategoriaPropietario() {
        assertEquals("Propietario", new CategoriaPropietario("ejemplo").getTipoDeCategoria());
    }

    @Test
    void testGetTipoDeCategoriaPublicacion() {
        assertEquals("Publicacion", new CategoriaPublicacion("ejemplo").getTipoDeCategoria());
    }

    @Test
    void equalsTest() {
        Categoria categoria = new Categoria(" 4-.24-.-3.4.-e.-.- J --. em-----  P ...l..-   o-..- ");
        Categoria categoriaDos = new Categoria("ejemplo");

        assertEquals(categoria, categoria);
        assertEquals(categoria, categoriaDos);
    }
}