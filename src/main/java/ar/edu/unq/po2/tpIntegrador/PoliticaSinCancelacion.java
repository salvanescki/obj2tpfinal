package ar.edu.unq.po2.tpIntegrador;

public class PoliticaSinCancelacion implements PoliticaDeCancelacion {

    private Precio precioDelTotalDeLaReserva(Reserva reserva){
        return reserva.getPublicacion().getPrecio(reserva.getPeriodo());
    }

    @Override
    public void efectuarCancelacion(Reserva reserva) {
        reserva.getInquilino().agregarPagoPendiente(new Deuda(precioDelTotalDeLaReserva(reserva)));
    }
}
