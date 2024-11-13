package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

        @Test
        void testConstructorYGetters() {
            Usuario usuario = new Usuario();
            Categoria categoria = new Categoria("Tecnología");
            int puntaje = 4;
            String comentario = "Muy buen producto";

            Ranking ranking = new Ranking(usuario, puntaje, comentario, categoria);

            assertEquals(usuario, ranking.getUsuario());
            assertEquals(puntaje, ranking.getPuntaje());
            assertEquals(comentario, ranking.getComentario());
            assertEquals(categoria, ranking.getCategoria());
        }

        @Test
        void testComentarioConNormalizacion() {
            Usuario usuario = new Usuario();
            Categoria categoria = new Categoria("Servicios");
            String comentario = "  EXCELENTE CALIDAD!  ";

            Ranking ranking = new Ranking(usuario, 5, comentario, categoria);

            assertEquals("excelente calidad!", TextoNormalizado.normalizarTexto(ranking.getComentario()));
        }

        @Test
        void testPuntajeEnLimitesValidos() {
            Usuario usuario = new Usuario();
            Categoria categoria = new Categoria("Productos");

            // Testeamos con puntaje mínimo (0)
            Ranking rankingMin = new Ranking(usuario, 0, "Malo", categoria);
            assertEquals(0, rankingMin.getPuntaje());

            // Testeamos con puntaje máximo (5)
            Ranking rankingMax = new Ranking(usuario, 5, "Excelente", categoria);
            assertEquals(5, rankingMax.getPuntaje());
        }
}
