package ar.edu.unq.po2.tpIntegrador;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario implements Propietario, Inquilino {

    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaDeCreacion;
    private final List<Reserva> reservas;
    private final List<Publicacion> publicaciones;

    public Usuario(String nombre, String email, String telefono) {
        setNombre(nombre);
        setEmail(email);
        setTelefono(telefono);
        fechaDeCreacion = LocalDate.now();
        reservas = new ArrayList<Reserva>();
        publicaciones = new ArrayList<Publicacion>();
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

    @Override
    public void puntuar(Ranking ranking) {
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
        return List.of();
    }

    @Override
    public int getPuntajeDeUsuarioEnCategoria(Usuario usuario, Categoria categoria) {
        // TODO: Implementar
        return 0;
    }
}
