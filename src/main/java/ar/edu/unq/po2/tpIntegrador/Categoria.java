package ar.edu.unq.po2.tpIntegrador;

public class Categoria {

    private String categoria;

    public Categoria(String categoria) {
        this.categoria = TextoNormalizado.normalizarTexto(categoria);
    }

    public String getCategoria() {
        return TextoNormalizado.normalizarTexto(categoria);
    }

    public void setCategoria(String categoria) {
        this.categoria = TextoNormalizado.normalizarTexto(categoria);
    }
}
