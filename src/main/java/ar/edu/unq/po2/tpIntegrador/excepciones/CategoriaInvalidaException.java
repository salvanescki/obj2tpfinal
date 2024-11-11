package ar.edu.unq.po2.tpIntegrador.excepciones;

public class CategoriaInvalidaException extends RuntimeException {
    public CategoriaInvalidaException(String message) {
        super(message);
    }
}
