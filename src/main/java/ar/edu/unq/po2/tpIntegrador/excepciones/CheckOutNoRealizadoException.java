package ar.edu.unq.po2.tpIntegrador.excepciones;

public class CheckOutNoRealizadoException extends RuntimeException {
    public CheckOutNoRealizadoException(String message) {
        super(message);
    }
}
