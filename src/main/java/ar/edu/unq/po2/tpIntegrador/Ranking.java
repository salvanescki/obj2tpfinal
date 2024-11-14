package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.CategoriaInvalidaException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PuntajeInvalidoException;

public class Ranking {

    private static SitioWeb sitio;
    private final Usuario usuario;
    private final int puntaje;
    private final String comentario;
    private final Categoria categoria;

    public Ranking(Usuario usuario, int puntaje, String comentario, Categoria categoria) {
        this.usuario = usuario;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.categoria = categoria;
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

    private static void validarCategoria(Categoria categoria, String tipoRankeable) {
        if(!sitio.esCategoriaValida(categoria, tipoRankeable)){
            throw new CategoriaInvalidaException("La categoría ingresada no es válida");
        }
    }

    public static void validarRanking(Ranking ranking, String tipoRankeable){
        validarPuntaje(ranking.getPuntaje());
        validarCategoria(ranking.getCategoria(), tipoRankeable);
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public int getPuntaje(){
        return puntaje;
    }

    public String getComentario(){
        return comentario;
    }

    public Categoria getCategoria(){
        return categoria;
    }
}
