package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class Reserva implements EstadoReserva {

    public Reserva(Inquilino inquilino, LocalDate fechaDesde, LocalDate fechaHasta, FormaDePago formaDePago, Publicacion publicacion) {
    }

    @Override
    public void aprobarReserva() {
        // TODO: Implementar
    }

    @Override
    public void cancelarReserva() {
        // TODO: Implementar
    }

    @Override
    public boolean estaAprobada() {
        // TODO: Implementar
        return false;
    }

    @Override
    public boolean fueCancelada() {
        // TODO: Implementar
        return false;
    }

    public boolean seSuperponeConElPeriodo(LocalDate fechaDesde, LocalDate fechaHasta) {
        // TODO: Implementar
        return false;
    }
}
