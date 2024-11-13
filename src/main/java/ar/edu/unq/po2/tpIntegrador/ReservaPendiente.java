package ar.edu.unq.po2.tpIntegrador;

public class ReservaPendiente implements EstadoReserva {

    private final Reserva reserva;

    public ReservaPendiente(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public void aprobarReserva() {
        reserva.setEstado(new ReservaAprobada(reserva));
        enviarMailQueConfirmaLaAprobacion();
    }

    @Override
    public void cancelarReserva() {
        reserva.setEstado(new ReservaCancelada(reserva));
    }

    @Override
    public boolean estaAprobada() {
        return false;
    }

    @Override
    public boolean estaPendiente() {
        return true;
    }

    @Override
    public boolean fueCancelada() {
        return false;
    }

    public String enviarMailQueConfirmaLaAprobacion() {
        return "Se envio con exito la aprobacion de la reserva al inquilino.";
    }
}
