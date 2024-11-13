package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;

public class PoliticaDeCancelacionGratuita implements PoliticaDeCancelacion {

    private boolean esCancelacionGratuita(Reserva reserva){
        return LocalDate.now().isBefore(reserva.getFechaDesde().minusDays(10));
    }

    private Precio precioEquivalenteADosDiasDeReserva(Reserva reserva){
        Periodo dosDiasDeReserva = new Periodo(reserva.getFechaDesde(), reserva.getFechaDesde().plusDays(2));
        return reserva.getPublicacion().getPrecio(dosDiasDeReserva);
    }

    @Override
    public void efectuarCancelacion(Reserva reserva) {
        if(!esCancelacionGratuita(reserva)){
            reserva.getInquilino().agregarPagoPendiente(new Deuda(precioEquivalenteADosDiasDeReserva(reserva)));
        }
    }
}
