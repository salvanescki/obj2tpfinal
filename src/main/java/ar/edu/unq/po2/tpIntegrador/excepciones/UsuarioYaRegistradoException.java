package ar.edu.unq.po2.tpIntegrador.excepciones;

public class UsuarioYaRegistradoException extends RuntimeException {
    public UsuarioYaRegistradoException(String message) {
        super(message);
    }
}
