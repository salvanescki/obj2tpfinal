package ar.edu.unq.po2.tpIntegrador;

public class CategoriaPublicacion extends Categoria{

    public CategoriaPublicacion(String categoria) {
        super(categoria);
    }

    @Override
    public String getTipoDeCategoria(){
        return "Publicacion";
    }
}
