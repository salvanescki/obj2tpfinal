package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public interface Rankeable {
    void puntuar(Usuario usuario, int puntaje, String comentario, Categoria categoria);
    double getPuntajePromedioEnCategoria(Categoria categoria);
    double getPuntajePromedioTotal();
    List<String> getComentariosDeInquilinosPrevios();
    int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria);
}
