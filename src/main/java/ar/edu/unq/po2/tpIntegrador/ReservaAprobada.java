package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.OperacionInvalidaConEstadoReservaException;

public class ReservaAprobada implements EstadoReserva {

    private Reserva reserva;

    public ReservaAprobada(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public void aprobarReserva() {
        throw new OperacionInvalidaConEstadoReservaException("No se puede aprobar una reserva ya aprobada.");
    }

    @Override
    public void cancelarReserva() {
        reserva.setEstado(new ReservaCancelada(reserva));
        enviarMailQueAvisaLaCancelacion();
    }

    @Override
    public boolean estaAprobada() {
        return true;
    }

    @Override
    public boolean estaPendiente() {
        return false;
    }

    @Override
    public boolean fueCancelada() {
        return false;
    }

    public String enviarMailQueAvisaLaCancelacion() {
        return "Se envio con exito el aviso de cancelacion al propietario de la publicacion.";
    };
}

