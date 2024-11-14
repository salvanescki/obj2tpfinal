package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;

public class ReservaCancelada implements EstadoReserva {

    private final Reserva reserva;

    public ReservaCancelada(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    @Override
    public void aprobarReserva() {
        throw new OperacionInvalidaConEstadoReservaException("No se puede aprobar una reserva ya cancelada.");
    }

    @Override
    public void cancelarReserva() {
        throw new OperacionInvalidaConEstadoReservaException("No se puede cancelar una reserva ya cancelada.");
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
        return true;
    }
}
