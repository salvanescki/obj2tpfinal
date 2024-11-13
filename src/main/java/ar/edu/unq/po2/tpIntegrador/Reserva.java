package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class Reserva implements EstadoReserva {

    private EstadoReserva estado;
    private final Inquilino inquilino;
    private final Periodo periodo;
    private final FormaDePago formaDePago;
    private final Publicacion publicacion;

    public Reserva(Inquilino inquilino, Periodo periodo, FormaDePago formaDePago, Publicacion publicacion) {
        this.inquilino = inquilino;
        this.periodo = periodo;
        this.formaDePago = formaDePago;
        this.publicacion = publicacion;
        this.estado = new ReservaPendiente(this);
    }

    public LocalDate getFechaDesde(){
        return periodo.getFechaDesde();
    }

    public LocalDate getFechaHasta(){
        return periodo.getFechaHasta();
    }

    public Periodo getPeriodo(){
        return periodo;
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

    public boolean seSuperponeConElPeriodo(Periodo periodo) {
        return this.periodo.seSuperponeCon(periodo);
    }
}
