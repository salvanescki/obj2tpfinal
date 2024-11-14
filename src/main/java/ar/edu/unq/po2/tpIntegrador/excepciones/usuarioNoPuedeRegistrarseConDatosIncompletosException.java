package ar.edu.unq.po2.tpIntegrador.excepciones;

public class usuarioNoPuedeRegistrarseConDatosIncompletosException extends RuntimeException {
    public usuarioNoPuedeRegistrarseConDatosIncompletosException(String message) {
        super(message);
    }
}
