package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class Reserva implements EstadoReserva {

    private EstadoReserva estado;
    private Inquilino inquilino;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private FormaDePago formaDePago;
    private Publicacion publicacion;

    public Reserva(Inquilino inquilino, LocalDate fechaDesde, LocalDate fechaHasta, FormaDePago formaDePago, Publicacion publicacion) {
        this.inquilino = inquilino;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.formaDePago = formaDePago;
        this.publicacion = publicacion;
        this.estado = new ReservaPendiente();
    }

    public LocalDate getFechaDesde(){
        return fechaDesde;
    }

    public LocalDate getFechaHasta(){
        return fechaHasta;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    @Override
    public boolean estaAprobada() {
        return estado.estaAprobada();
    }

    @Override
    public boolean estaPendiente() {
        return estado.estaPendiente();
    }

    @Override
    public boolean fueCancelada() {
        return estado.fueCancelada();
    }

    @Override
    public void aprobarReserva() {
        estado.aprobarReserva();
    }

    @Override
    public void cancelarReserva() {
        estado.cancelarReserva();
    }

    public boolean seSuperponeConElPeriodo(LocalDate fechaDesde, LocalDate fechaHasta) {
        return !fechaDesde.isAfter(this.getFechaHasta()) && !fechaHasta.isBefore(this.getFechaDesde());
    }
}
