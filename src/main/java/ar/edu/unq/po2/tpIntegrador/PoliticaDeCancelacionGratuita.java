package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class PoliticaDeCancelacionGratuita implements PoliticaDeCancelacion {

    private boolean esCancelacionGratuita(Reserva reserva){
        LocalDate ahora = LocalDate.now();
        LocalDate diezDiasAntesDeReserva = reserva.getFechaDesde().minusDays(10);
        return ahora.isBefore(diezDiasAntesDeReserva) || ahora.isEqual(diezDiasAntesDeReserva);
    }

    private Precio precioEquivalenteADosDiasDeReserva(Reserva reserva){
        Periodo dosDiasDeReserva = new Periodo(reserva.getFechaDesde(), reserva.getFechaDesde().plusDays(2));
        return reserva.getPublicacion().getPrecio(dosDiasDeReserva);
    }

    @Override
    public void efectuarCancelacion(Reserva reserva) {
        if(esCancelacionGratuita(reserva)) return;
        reserva.getInquilino().agregarPagoPendiente(new Deuda(precioEquivalenteADosDiasDeReserva(reserva)));
    }
}
