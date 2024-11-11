package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Publicacion implements Rankeable {

    private Propietario propietario;
    private TipoDeInmueble tipoDeInmueble;
    private int superficie;
    private String pais;
    private String ciudad;
    private String direccion;
    private List<Servicio> servicios = new ArrayList<Servicio>();
    private int capacidad;
    private List<Foto> fotos = new ArrayList<Foto>();
    private LocalTime horarioCheckIn;
    private LocalTime horarioCheckOut;
    private List<FormaDePago> formasDePago = new ArrayList<FormaDePago>();
    private Precio precioBase;
    private PoliticaDeCancelacion politicaDeCancelacion;
    private List<Ranking> rankings = new ArrayList<Ranking>();
    private List<Reserva> reservas = new ArrayList<Reserva>();
    private List<Periodo> periodos = new ArrayList<Periodo>();
    private Listener notificador = new Notificador();

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

    public Precio getPrecio(LocalDate fechaDesde, LocalDate fechaHasta) {
        // TODO: implementar
        return new Precio(0);
    }

    public void definirPoliticaDeCancelacion(PoliticaDeCancelacion politica){
        // TODO: implementar
    }

    public void definirPeriodo(LocalDate fechaDesde, LocalDate fechaHasta, Precio precio){
        // TODO: implementar
    }

    public int getCantidadDeVecesAlquilada() {
        // TODO: implementar
        return 0;
    }

    public boolean estaReservadaEnFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        // TODO: Implementar
        return false;
    }

    public void reservar(Inquilino inquilino, LocalDate fechaDesde, LocalDate fechaHasta, FormaDePago formaDePago) {
        // TODO: Implementar
    }

    public void cancelarReserva(Reserva reserva) {
        // TODO: Implementar
        // politica.efectuarCancelacion
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void suscribirNotificaciones(Listener suscriptor) {
        // TODO: Implementar
    }

    @Override
    public void puntuar(Usuario usuario, int puntaje, String comentario, Categoria categoria) {
        // TODO: Implementar
    }

    @Override
    public double getPuntajePromedioEnCategoria(Categoria categoria) {
        // TODO: Implementar
        return 0;
    }

    @Override
    public double getPuntajePromedioTotal() {
        // TODO: Implementar
        return 0;
    }

    @Override
    public List<String> getComentariosDeInquilinosPrevios() {
        // TODO: Implementar
        return null;
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        // TODO: Implementar
        return 0;
    }
}
