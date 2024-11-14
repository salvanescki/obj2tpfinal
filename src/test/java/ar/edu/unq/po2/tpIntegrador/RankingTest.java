package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ar.edu.unq.po2.tpIntegrador.excepciones.CategoriaInvalidaException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PuntajeInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class RankingTest {

    private SitioWeb sitioMock;
    private Usuario usuario;
    private Categoria categoria;
    private Rankeable rankeable;

    @BeforeEach
    void setUp() {
        sitioMock = mock(SitioWeb.class);
        usuario = mock(Usuario.class);
        categoria = mock(Categoria.class);
        rankeable = mock(Rankeable.class);
        Ranking.setSitio(sitioMock);
        when(rankeable.getTipo()).thenReturn("Rankeable");
    }

    @Test
    void testCrearRankingConDatosValidos() {
        Ranking ranking = new Ranking(usuario, 4, "Buen servicio", categoria);

        assertEquals(usuario, ranking.getUsuario());
        assertEquals(4, ranking.getPuntaje());
        assertEquals("Buen servicio", ranking.getComentario());
        assertEquals(categoria, ranking.getCategoria());
    }

    @Test
    void testValidarPuntajeLanzaExcepcionCuandoEsMenorA1() {
        Ranking ranking = new Ranking(usuario, 0, "Comentario", categoria);

        assertThrows(PuntajeInvalidoException.class, () ->
            Ranking.validarRanking(ranking, rankeable.getTipo()));
    }

    @Test
    void testValidarPuntajeLanzaExcepcionCuandoEsMayorA5() {
        Ranking ranking = new Ranking(usuario, 6, "Comentario", categoria);

        assertThrows(PuntajeInvalidoException.class, () ->
            Ranking.validarRanking(ranking, rankeable.getTipo()));
    }

    @Test
    void testValidarPuntajeNoLanzaExcepcionParaPuntajeValido() {
        when(sitioMock.esCategoriaValida(categoria, rankeable.getTipo())).thenReturn(true);
        Ranking ranking = new Ranking(usuario, 3, "Comentario", categoria);

        assertDoesNotThrow(() ->
            Ranking.validarRanking(ranking, rankeable.getTipo()));
    }

    @Test
    void testValidarCategoriaLanzaExcepcionCuandoEsInvalida() {
        when(sitioMock.esCategoriaValida(categoria, rankeable.getTipo())).thenReturn(false);
        Ranking ranking = new Ranking(usuario, 3, "Comentario", categoria);

        assertThrows(CategoriaInvalidaException.class, () ->
            Ranking.validarRanking(ranking, rankeable.getTipo()));
    }

    @Test
    void testValidarCategoriaNoLanzaExcepcionCuandoEsValida() {
        when(sitioMock.esCategoriaValida(categoria, rankeable.getTipo())).thenReturn(true);
        Ranking ranking = new Ranking(usuario, 3, "Comentario", categoria);

        assertDoesNotThrow(() ->
            Ranking.validarRanking(ranking, rankeable.getTipo()));
    }

}