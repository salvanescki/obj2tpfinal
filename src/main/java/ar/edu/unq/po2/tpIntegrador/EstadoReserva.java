package ar.edu.unq.po2.tpIntegrador;

public interface EstadoReserva {
    void aprobarReserva();
    void cancelarReserva();
    boolean estaAprobada();
    boolean estaPendiente();
    boolean fueCancelada();
}
