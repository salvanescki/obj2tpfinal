package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class PoliticaIntermediaDeCancelacion implements PoliticaDeCancelacion {

    private Precio precioDelTotalDeLaReserva(Reserva reserva){
        return reserva.getPublicacion().getPrecio(reserva.getPeriodo());
    }

    private boolean esCancelacionGratuita(Reserva reserva){
        LocalDate diezDiasAntesDeReserva = reserva.getFechaDesde().minusDays(20);
        return estaEnElMismoDiaOAntes(diezDiasAntesDeReserva);
    }

    private boolean estaEnElMismoDiaOAntes(LocalDate dia){
        return LocalDate.now().isEqual(dia) || LocalDate.now().isBefore(dia);
    }

    private boolean estaEnElMismoDiaODespues(LocalDate dia){
        return LocalDate.now().isEqual(dia) || LocalDate.now().isAfter(dia);
    }

    private boolean debePagarLaMitadDelPrecio(Reserva reserva){
        LocalDate diezDiasAntesDeReserva = reserva.getFechaDesde().minusDays(10);
        LocalDate diecinueveDiasAntesDeReserva = reserva.getFechaDesde().minusDays(19);
        return estaEnElMismoDiaODespues(diecinueveDiasAntesDeReserva) && estaEnElMismoDiaOAntes(diezDiasAntesDeReserva);
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
