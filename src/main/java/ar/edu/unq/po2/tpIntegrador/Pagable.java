package ar.edu.unq.po2.tpIntegrador;

public interface Pagable {
    void pagar(Precio precio);
    Precio getMontoAPagar();
}
