package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Publicacion implements Rankeable {

    private final Propietario propietario;
    private final TipoDeInmueble tipoDeInmueble;
    private final int superficie;
    private final String pais;
    private final String ciudad;
    private final String direccion;
    private final List<Servicio> servicios = new ArrayList<Servicio>();
    private final int capacidad;
    private final List<Foto> fotos = new ArrayList<Foto>();
    private final LocalTime horarioCheckIn;
    private final LocalTime horarioCheckOut;
    private final List<FormaDePago> formasDePago = new ArrayList<FormaDePago>();
    private Precio precioBase;
    private PoliticaDeCancelacion politicaDeCancelacion;
    private final List<Ranking> rankings = new ArrayList<Ranking>();
    private final List<Reserva> reservas = new ArrayList<Reserva>();
    private final List<Periodo> periodos = new ArrayList<Periodo>();
    private Notificador notificador = new Notificador();
    private int cantCheckOuts;
    private final List<Inquilino> inquilinosPrevios = new ArrayList<Inquilino>();

    public Publicacion(Propietario propietario, TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad, String direccion, List<Servicio> servicios, int capacidad, List<Foto> fotos, LocalTime horarioCheckIn, LocalTime horarioCheckOut, List<FormaDePago> formasDePago, Precio precioBase) {
        this.propietario = propietario;
        this.tipoDeInmueble = tipoDeInmueble;
        this.superficie = superficie;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.servicios.addAll(servicios);
        this.capacidad = capacidad;
        this.fotos.addAll(fotos);
        this.horarioCheckIn = horarioCheckIn;
        this.horarioCheckOut = horarioCheckOut;
        this.formasDePago.addAll(formasDePago);
        this.precioBase = precioBase;
        definirPoliticaDeCancelacion(new PoliticaSinCancelacion());
        this.cantCheckOuts = 0;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public TipoDeInmueble getTipoDeInmueble() {
        return tipoDeInmueble;
    }

    public int getSuperficie() {
        return superficie;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public LocalTime getHorarioCheckIn() {
        return horarioCheckIn;
    }

    public LocalTime getHorarioCheckOut() {
        return horarioCheckOut;
    }

    public List<FormaDePago> getFormasDePago() {
        return formasDePago;
    }

    private Precio precioDelDia(LocalDate dia){
        return periodos.stream()
                .filter(periodo -> periodo.estaDentroDelPeriodo(dia))
                .map(Periodo::getPrecio)
                .findFirst()
                .orElse(precioBase);
    }

    public Precio getPrecio(LocalDate fechaDesde, LocalDate fechaHasta) {
        Precio total = new Precio(0);
        for(LocalDate dia = fechaDesde; !dia.isAfter(fechaHasta); dia = dia.plusDays(1)){
            total = total.sumar(precioDelDia(dia));
        }
        return total;
    }

    public void definirPoliticaDeCancelacion(PoliticaDeCancelacion politica){
        this.politicaDeCancelacion = politica;
    }

    private boolean estaPreviamenteDefinidoOSeSuperponeElPeriodo(Periodo periodo){
        return periodos.contains(periodo) || periodos.stream().anyMatch(p -> p.seSuperponeCon(periodo));
    }

    private void validarPeriodo(Periodo periodo){
        if(estaPreviamenteDefinidoOSeSuperponeElPeriodo(periodo)){
            throw new PeriodoYaDefinidoException("El periodo existe o se superpone con otro definido previamente");
        }
    }

    public Periodo definirPeriodo(Periodo periodo){
        validarPeriodo(periodo);
        periodos.add(periodo);
        return periodo;
    }

    public int getCantidadDeVecesAlquilada() {
        // Alquileres pasados (check-outs) + alquileres presentes
        return cantCheckOuts + (int) reservas.stream()
                                            .filter(Reserva::estaAprobada)
                                            .count();
    }

    public boolean estaReservadaEnFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return reservas.stream()
                .anyMatch(reserva -> reserva.seSuperponeConElPeriodo(fechaDesde, fechaHasta));
    }

    private boolean sonFechasInvalidas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return fechaHasta.isBefore(fechaDesde);
    }

    private void validarFechasEnReserva(LocalDate fechaDesde, LocalDate fechaHasta) {
        if(sonFechasInvalidas(fechaDesde, fechaHasta)){
            throw new FechasInvalidasException("Las fechas introducidas no son válidas.");
        }
    }

    private void notificarNuevaReserva(LocalDate fechaDesde, LocalDate fechaHasta){
        notificador.notificarReserva("El inmueble " + tipoDeInmueble + " que te interesa, ha sido reservado desde el " + fechaDesde + " hasta el " + fechaHasta + ".", this);
    }

    public void reservar(Inquilino inquilino, LocalDate fechaDesde, LocalDate fechaHasta, FormaDePago formaDePago) {
        validarFechasEnReserva(fechaDesde, fechaHasta);
        Reserva reserva = new Reserva(inquilino, fechaDesde, fechaHasta, formaDePago, this);
        // propietario.agregarReservaParaAprobar(reserva); o simplemente notificar (lo cual se hace abajo)
        if(!estaReservadaEnFechas(fechaDesde, fechaHasta)) {
            notificarNuevaReserva(fechaDesde, fechaHasta);
        }
        reservas.add(reserva);
        inquilinosPrevios.remove(inquilino);
        inquilino.agregarReserva(reserva);
    }

    private Reserva getFirstReservaCondicional(LocalDate fechaDesde, LocalDate fechaHasta){
        return reservas.stream()
                .filter(reserva -> reserva.seSuperponeConElPeriodo(fechaDesde, fechaHasta) && reserva.estaPendiente())
                .findFirst()
                .orElse(null);
    }

    private void handleReservaCondicional(LocalDate fechaDesde, LocalDate fechaHasta) {
        Reserva reservaCondicional = getFirstReservaCondicional(fechaDesde, fechaHasta);
        if(reservaCondicional != null){
            notificarNuevaReserva(fechaDesde, fechaHasta);
        }
    }

    public void cancelarReserva(Reserva reserva) {
        politicaDeCancelacion.efectuarCancelacion();
        reserva.cancelarReserva();
        notificador.notificarCancelacionReserva("El/la "+ tipoDeInmueble + " que te interesa se ha liberado! Corre a reservarlo!", this);
        handleReservaCondicional(reserva.getFechaDesde(), reserva.getFechaHasta());
    }

    private void validarReservaAprobada(Reserva reserva) {
        if(!reserva.estaAprobada()){
            throw new CheckOutNoRealizadoException("No se puede hacer check out de una reserva previamente aprobada");
        }
    }

    private void validarHorarioCheckOut(LocalTime horaActual) {
        if(horaActual.isAfter(horarioCheckOut)){
            throw new CheckOutNoRealizadoException("El horario de check-out de hoy ya ha pasado, inténtelo mañana");
        }
    }

    public void checkOut(Reserva reserva, LocalTime horaActual){
        // FIXME: Acá habría que agregar una validación de que se haga el checkout luego de la fechaHasta de la reserva
        //  y antes del horarioCheckOut de ese mismo día.
        validarReservaAprobada(reserva);
        validarHorarioCheckOut(horaActual);
        cantCheckOuts++;
        inquilinosPrevios.add(reserva.getInquilino());
        reservas.remove(reserva);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void suscribirNotificaciones(Listener suscriptor) {
        notificador.suscribir(suscriptor);
    }

    public boolean fueHechoElCheckOut(Inquilino inquilino){
        return inquilinosPrevios.contains(inquilino);
    }

    private void validarCheckOut(Inquilino inquilino) {
        if(!fueHechoElCheckOut(inquilino)){
            throw new CheckOutNoRealizadoException("No se puede rankear antes de hacer el check-out");
        }
    }

    private void validarRanking(Ranking ranking) {
        validarCheckOut(ranking.getUsuario());
        Ranking.validarRanking(ranking, this);
    }

    @Override
    public void puntuar(Ranking ranking) {
        validarRanking(ranking);
        rankings.add(ranking);
    }

    @Override
    public double getPuntajePromedioEnCategoria(Categoria categoria) {
        return RankingUtils.getPuntajePromedioEnCategoria(rankings, categoria);
    }

    @Override
    public double getPuntajePromedioTotal() {
        return RankingUtils.getPuntajePromedioTotal(rankings);
    }

    @Override
    public List<String> getComentariosDeInquilinosPrevios() {
        return RankingUtils.getComentariosDeInquilinosPrevios(rankings);
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        return RankingUtils.getPuntajeDeUsuarioEnCategoria(rankings, usuario, categoria);
    }

    public void setNotificador(Notificador notificador){
        this.notificador = notificador;
    }

    public void setPrecioBase(Precio nuevoPrecio){
        if(nuevoPrecio.compareTo(precioBase) < 0) {
            notificador.notificarBajaDePrecio(
                    "No te pierdas esta oferta: Un inmueble " + tipoDeInmueble
                            + " a tan sólo " + nuevoPrecio + " pesos",
                    this
            );
        }
        precioBase = nuevoPrecio;
    }
}
