package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.MontoIngresadoMayorADeudaException;
import ar.edu.unq.po2.tpIntegrador.excepciones.MontoIngresadoNoValidoException;

public class Deuda implements Pagable {

    private Precio montoAPagar;

    public Deuda(Precio montoAPagar){
        this.montoAPagar = montoAPagar;
    }

    private void validarMonto(Precio monto){
        if(monto.compareTo(montoAPagar) > 0){
            throw new MontoIngresadoMayorADeudaException("El monto que estás queriendo pagar es mayor a la deuda total");
        }
        if(monto.getPrecio() <= 0){
            throw new MontoIngresadoNoValidoException("El monto ingresado no puede ser menor o igual a cero");
        }
    }

    @Override
    public void pagar(Precio monto) {
        validarMonto(monto);
        montoAPagar = montoAPagar.restar(monto);
    }

    @Override
    public Precio getMontoAPagar() {
        return montoAPagar;
    }

}
