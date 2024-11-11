package ar.edu.unq.po2.tpIntegrador;

public class ReservaCancelada implements EstadoReserva {

    @Override
    public void aprobarReserva() {

    }

    @Override
    public void cancelarReserva() {

    }

    @Override
    public boolean estaAprobada() {
        return false;
    }

    @Override
    public boolean estaPendiente() {
        return false;
    }

    @Override
    public boolean fueCancelada() {
        return false;
    }
}
