package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class PoliticaIntermediaDeCancelacion implements PoliticaDeCancelacion {

    private Precio precioDelTotalDeLaReserva(Reserva reserva){
        return reserva.getPublicacion().getPrecio(reserva.getPeriodo());
    }

    private boolean esCancelacionGratuita(Reserva reserva){
        return LocalDate.now().isBefore(reserva.getFechaDesde().minusDays(20));
    }

    private boolean debePagarLaMitadDelPrecio(Reserva reserva){
        return LocalDate.now().isBefore(reserva.getFechaDesde().minusDays(10))
                && LocalDate.now().isAfter(reserva.getFechaDesde().minusDays(19));
    }

    private Precio getPrecioAPagar(Reserva reserva){
        return debePagarLaMitadDelPrecio(reserva)? precioDelTotalDeLaReserva(reserva).decrementarEnPorcentaje(50) : precioDelTotalDeLaReserva(reserva);
    }

    @Override
    public void efectuarCancelacion(Reserva reserva) {
        if(esCancelacionGratuita(reserva)) return;
        reserva.getInquilino().agregarPagoPendiente(new Deuda(getPrecioAPagar(reserva)));
    }
}
