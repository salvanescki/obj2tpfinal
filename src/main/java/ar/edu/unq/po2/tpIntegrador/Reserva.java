package ar.edu.unq.po2.tpIntegrador;

public class Reserva implements EstadoReserva {

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
    public boolean fueCancelada() {
        return false;
    }

}
