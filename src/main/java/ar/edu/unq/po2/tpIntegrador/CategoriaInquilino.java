package ar.edu.unq.po2.tpIntegrador;

public class CategoriaInquilino extends Categoria{

    public CategoriaInquilino(String categoria) {
        super(categoria);
    }

    @Override
    public String getTipoDeCategoria(){
        return "Inquilino";
    }
}
