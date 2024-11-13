package ar.edu.unq.po2.tpIntegrador;

public class FormaDePago {
    private String formaDePago;

    public FormaDePago(String formaDePago) {
        setFormaDePago(formaDePago);
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = TextoNormalizado.normalizarTexto(formaDePago);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        FormaDePago otro = (FormaDePago) o;
        return this.formaDePago.equals(otro.getFormaDePago());
    }
}
