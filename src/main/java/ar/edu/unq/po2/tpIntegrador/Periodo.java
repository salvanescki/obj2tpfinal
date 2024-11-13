package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class Periodo {
    public Periodo(LocalDate fechaDesde, LocalDate fechaHasta, Precio precio) {
    }

    public Periodo(LocalDate fechaDesde, LocalDate fechaHasta) {
    }

    public boolean seSuperponeCon(Periodo otroPeriodo) {
        // TODO: Implementar
        return false;
    }

    public int getCantidadDeDias() {
        // TODO: Implementar
        return 0;
    }

    public boolean estaDentroDelPeriodo(LocalDate dia) {
        // TODO: Implementar
        return false;
    }

    public Precio getPrecio() {
        // TODO: Implementar
        return new Precio(0);
    }
}
