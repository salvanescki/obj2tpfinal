package ar.edu.unq.po2.tpIntegrador;

public class ReservaPendiente implements EstadoReserva {

    private final Reserva reserva;

    public ReservaPendiente(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
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

    public void enviarMailQueConfirmaLaAprobacion() {
        String mailInquilino = reserva.getInquilino().getEmail();
        String tipoInmueble = reserva.getPublicacion().getTipoDeInmueble().getTipoDeInmueble();
        String mailPublicante = reserva.getPublicacion().getPropietario().getEmail();
        Mail.enviar(new Mail(mailPublicante, mailInquilino, "Reserva Aprobada", "El propietario del inmueble " + tipoInmueble + " ha aprobado tu reserva pendiente"));
    }
}
