package ar.edu.unq.po2.tpIntegrador;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class RankingUtils {

    private static double getPuntajePromedioDeRankings(Stream<Ranking> str){
        DecimalFormat formato = new DecimalFormat("#.#");
        return Double.parseDouble(formato.format(
                str.mapToInt(Ranking::getPuntaje)
                        .average()
                        .orElseThrow()
        ));
    }

    public static double getPuntajePromedioEnCategoria(List<Ranking> rankings, Categoria categoria) {
        return getPuntajePromedioDeRankings(rankings.stream()
                                                    .filter(ranking -> ranking.getCategoria().equals(categoria)));
    }

    public static double getPuntajePromedioTotal(List<Ranking> rankings) {
        return getPuntajePromedioDeRankings(rankings.stream());
    }

    public static List<String> getComentariosDeInquilinosPrevios(List<Ranking> rankings) {
        return rankings.stream()
                        .map(Ranking::getComentario)
                        .toList();
    }

    public static int getPuntajeDeUsuarioEnCategoria(List<Ranking> rankings, Usuario usuario, Categoria categoria) {
        return rankings.stream()
                        .filter(ranking -> ranking.getUsuario().equals(usuario))
                        .filter(ranking -> ranking.getCategoria().equals(categoria))
                        .mapToInt(Ranking::getPuntaje)
                        .findFirst()
                        .orElseThrow();
    }
}
