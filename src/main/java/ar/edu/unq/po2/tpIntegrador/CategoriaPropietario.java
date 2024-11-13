package ar.edu.unq.po2.tpIntegrador;

public class CategoriaPropietario extends Categoria{

    public CategoriaPropietario(String categoria) {
        super(categoria);
    }

    @Override
    public String getTipoDeCategoria(){
        return "Propietario";
    }
}
