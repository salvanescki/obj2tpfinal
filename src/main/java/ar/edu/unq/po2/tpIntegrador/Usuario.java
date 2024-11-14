package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.CheckOutNoRealizadoException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario implements Propietario, Inquilino {

    private String nombre;
    private String email;
    private String telefono;
    private final LocalDate fechaDeCreacion;
    private final List<Reserva> reservas;
    private final List<Publicacion> publicaciones;
    private final List<Ranking> rankings;
    private final List<Pagable> pagosPendientes;

    public Usuario(String nombre, String email, String telefono) {
        setNombre(nombre);
        setEmail(email);
        setTelefono(telefono);
        fechaDeCreacion = LocalDate.now();
        reservas = new ArrayList<>();
        publicaciones = new ArrayList<>();
        rankings = new ArrayList<>();
        pagosPendientes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    //La razón de los setters es que un usuario podría querer cambiar sus datos personales
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getTiempoDeAntiguedad(LocalDate fechaActual) {
        long añosAntiguedad = ChronoUnit.YEARS.between(getFechaDeCreacion(), fechaActual);

        LocalDate fechaRegistroMasAños = getFechaDeCreacion().plusYears(añosAntiguedad);
        long mesesAntiguedad = ChronoUnit.MONTHS.between(fechaRegistroMasAños, fechaActual);

        LocalDate fechaRegistroMasMeses = fechaRegistroMasAños.plusMonths(mesesAntiguedad);
        long diasAntiguedad = ChronoUnit.DAYS.between(fechaRegistroMasMeses, fechaActual);

        List<String> strs = List.of(
                añosAntiguedad > 0? añosAntiguedad + " años" : "",
                mesesAntiguedad > 0? mesesAntiguedad + " meses" : "",
                diasAntiguedad > 0? diasAntiguedad + " dias" : ""
        );

        String mensaje = strs.stream()
                             .filter(s -> !s.isEmpty())
                             .collect(Collectors.joining(", "));

        return mensaje.isEmpty()? "Recien creado" : mensaje;
    }

    @Override
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public List<Reserva> getReservas() {
        return reservas;
    }

    @Override
    public List<Reserva> getReservasFuturas() {
        return reservas.stream()
                        .filter(r -> r.getFechaDesde().isAfter(LocalDate.now()))
                        .toList();
    }

    @Override
    public List<Reserva> getReservasEnCiudad(String ciudad) {
        return reservas.stream()
                        .filter(r -> r.getPublicacion().getCiudad().equals(ciudad))
                        .toList();
    }

    @Override
    public List<String> getCiudadesConReservas() {
        return reservas.stream()
                        .map(r -> r.getPublicacion().getCiudad())
                        .distinct()
                        .toList();
    }

    @Override
    public int getCantidadDeVecesQueAlquilo() {
        return (int) reservas.stream()
                             .filter(Reserva::estaAprobada)
                             .count();
    }

    @Override
    public void agregarPagoPendiente(Pagable pagable) {
        pagosPendientes.add(pagable);
    }

    @Override
    public int cantidadDeVecesQuePublicoInmuebles() {
        return publicaciones.size();
    }

    @Override
    public void agregarPublicacion(Publicacion publicacion) {
        publicaciones.add(publicacion);
    }

    @Override
    public List<Publicacion> getInmueblesPublicados() {
        return publicaciones;
    }

    private boolean fueHechoCheckOutConPropietario(Propietario propietario){
        return reservas.stream()
                        .anyMatch(reserva -> reserva.getPublicacion().getPropietario().equals(propietario)
                                            && reserva.getPublicacion().fueHechoElCheckOut(this));
    }

    private boolean fueHechoCheckOutConInquilino(Inquilino inquilino){
        return inquilino.getReservas().stream()
                                      .anyMatch(reserva -> reserva.getPublicacion().getPropietario().equals(this)
                                              && reserva.getPublicacion().fueHechoElCheckOut(inquilino));
    }

    private void validarCheckOut(Ranking ranking) {
        Usuario ranker = ranking.getUsuario();
        if(!(fueHechoCheckOutConPropietario(ranker) || fueHechoCheckOutConInquilino(ranker))){
            throw new CheckOutNoRealizadoException("No se puede rankear antes de hacer el check-out");
        }
    }

    @Override
    public void puntuar(Ranking ranking) {
        validarCheckOut(ranking);
        Ranking.validarRanking(ranking, ranking.getCategoria().getTipoDeCategoria());
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

    private List<Ranking> getRankingsPropietario(){
        return rankings.stream()
                       .filter(r -> r.getCategoria().getTipoDeCategoria().equals("Propietario"))
                       .toList();
    }

    @Override
    public List<String> getComentariosDeInquilinosPrevios() {
        return RankingUtils.getComentariosDeInquilinosPrevios(getRankingsPropietario());
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        return RankingUtils.getPuntajeDeUsuarioEnCategoria(rankings, usuario, categoria);
    }

    @Override
    public String getTipo() {
        return "Usuario";
    }

    public String getTipoInquilino() {
        return "Inquilino";
    }

    public String getTipoPropietario() {
        return "Propietario";
    }
}
