package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.PeriodoSinPrecioDefinidoException;

import java.time.LocalDate;

public class Periodo {

    private final LocalDate fechaDesde;
    private final LocalDate fechaHasta;
    private Precio precio;

    public Periodo(LocalDate fechaDesde, LocalDate fechaHasta) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public Periodo(LocalDate fechaDesde, LocalDate fechaHasta, Precio precio) {
        this(fechaDesde, fechaHasta);
        setPrecio(precio);
    }

    public boolean seSuperponeCon(Periodo otroPeriodo) {
        return (fechaDesde.isBefore(otroPeriodo.getFechaHasta()) || fechaDesde.isEqual(otroPeriodo.getFechaHasta())) &&
                (fechaHasta.isAfter(otroPeriodo.getFechaDesde()) || fechaHasta.isEqual(otroPeriodo.getFechaDesde()));
    }

    public LocalDate getFechaDesde(){
        return fechaDesde;
    }

    public LocalDate getFechaHasta(){
        return fechaHasta;
    }

    public boolean estaDentroDelPeriodo(LocalDate dia) {
        return (dia.isAfter(fechaDesde) || dia.isEqual(fechaDesde))&&
                (dia.isBefore(fechaHasta) || dia.isEqual(fechaHasta));
    }

    private void validarPrecio() {
        if(precio == null){
            throw new PeriodoSinPrecioDefinidoException("El periodo no tiene definido un precio");
        }
    }

    public Precio getPrecio() {
        validarPrecio();
        return precio;
    }

    public void setPrecio(Precio precio){
        this.precio = precio;
    }
}
