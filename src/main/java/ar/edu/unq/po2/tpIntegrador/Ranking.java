package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.CategoriaInvalidaException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PuntajeInvalidoException;

public class Ranking {

    private static SitioWeb sitio;

    public Ranking(Usuario usuario, int puntaje, String comentario, Categoria categoria) {
    }

    public static void setSitio(SitioWeb sitio) {
        Ranking.sitio = sitio;
    }

    private static boolean puntajeInvalido(int puntaje) {
        return puntaje < 1 || puntaje > 5;
    }

    private static void validarPuntaje(int puntaje) {
        if(puntajeInvalido(puntaje)){
            throw new PuntajeInvalidoException("El puntaje debe ser en una escala del 1 al 5");
        }
    }

    private static void validarCategoria(Categoria categoria, Rankeable rankeable) {
        if(!sitio.esCategoriaValida(categoria, rankeable)){
            throw new CategoriaInvalidaException("La categoría ingresada no es válida");
        }
    }

    public static void validarRanking(Ranking ranking, Rankeable rankeable){
        validarPuntaje(ranking.getPuntaje());
        validarCategoria(ranking.getCategoria(), rankeable);
    }

    public Usuario getUsuario(){
        // TODO: Implementar
        return null;
    }

    public int getPuntaje(){
        // TODO: Implementar
        return 0;
    }

    public String getComentario(){
        // TODO: Implementar
        return "";
    }

    public Categoria getCategoria(){
        // TODO: Implementar
        return null;
    }
}
