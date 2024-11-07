package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.PrecioInvalidoException;
import ar.edu.unq.po2.tpIntegrador.excepciones.RestaDePreciosInvalidaException;

public class Precio {

    private long precio;

    public Precio(int pesos) {
        setPrecio(pesos);
    }

    public Precio(double monto) {
        validarCantidadDeDecimales(monto);
        setPrecio(monto);
    }

    private void setPrecio(double monto){
        validarPrecioPositivo(monto);
        precio = Math.round(monto * 100.00);
    }

    private void validarPrecioPositivo(double precio) {
        if (precio < 0){
            throw new PrecioInvalidoException("El precio ingresado no es válido, no se admiten números negativos");
        }
    }

    private int cantidadDeDecimales(double monto){
        return String.valueOf(monto).split("\\.")[1].length();
    }

    private void validarCantidadDeDecimales(double monto) {
        if(cantidadDeDecimales(monto) > 2){
            throw new PrecioInvalidoException("El precio ingresado no es válido, debe contener menos de 3 decimales");
        }
    }

    public double getPrecio() {
        return precioToDouble(precio);
    }

    private double precioToDouble(long precio){
        return precio / 100.00;
    }

    private Precio sumarRestarPrecio(int signo, Precio unPrecio){
        return new Precio(precioToDouble(Math.round(precio + signo * unPrecio.getPrecio() * 100.00)));
    }

    public Precio sumar(Precio unPrecio) {
        return sumarRestarPrecio(1, unPrecio);
    }

    private void validarResta(Precio unPrecio){
        if (getPrecio() < unPrecio.getPrecio()){
            throw new RestaDePreciosInvalidaException("No se puede restar un número más grande de otro más chico");
        }
    }

    public Precio restar(Precio unPrecio) {
        validarResta(unPrecio);
        return sumarRestarPrecio(-1, unPrecio);
    }

    public Precio incremetarEnPorcentaje(int porcentaje) {
        return incremetarEnPorcentaje((double) porcentaje);
    }

    public Precio incremetarEnPorcentaje(double porcentaje) {
        long nuevoPrecio = (long) (precio * (100 + porcentaje)) / 100;
        return new Precio(precioToDouble(nuevoPrecio));
    }

    private void validarPorcentajeADecrementar(double porcentaje){
        if(porcentaje > 100){
            throw new PrecioInvalidoException("No se puede decrementar un porcentaje mayor al 100, ya que daría un precio negativo (inválido)");
        }
    }

    public Precio decrementarEnPorcentaje(int porcentaje) {
        return decrementarEnPorcentaje((double) porcentaje);
    }

    public Precio decrementarEnPorcentaje(double porcentaje) {
        validarPorcentajeADecrementar(porcentaje);
        return incremetarEnPorcentaje(porcentaje * -1);
    }

    @Override
    public String toString(){
        return String.format("$%,.2f", (double) precio / 100);
    }
}
