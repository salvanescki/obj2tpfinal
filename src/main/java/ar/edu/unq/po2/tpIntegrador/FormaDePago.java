package ar.edu.unq.po2.tpIntegrador;

public class FormaDePago {
    private String formaDePago;

    public FormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getFormaDePago() {
        return TextoNormalizado.normalizarTexto(formaDePago);
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = TextoNormalizado.normalizarTexto(formaDePago);
    }
}
