package ar.edu.unq.po2.tpIntegrador;

public class Categoria {

    private String categoria;

    public Categoria(String categoria) {
        setCategoria(categoria);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = TextoNormalizado.normalizarTexto(categoria);
    }

    public String getTipoDeCategoria(){
        return "Sin tipo";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        Categoria otro = (Categoria) o;
        return this.categoria.equals(otro.getCategoria());
    }
}
