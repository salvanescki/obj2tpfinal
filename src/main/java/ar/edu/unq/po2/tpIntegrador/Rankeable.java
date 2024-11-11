package ar.edu.unq.po2.tpIntegrador;

import java.util.List;

public interface Rankeable {
    void puntuar(Ranking ranking);
    double getPuntajePromedioEnCategoria(Categoria categoria);
    double getPuntajePromedioTotal();
    List<String> getComentariosDeInquilinosPrevios();
    int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria);
}
