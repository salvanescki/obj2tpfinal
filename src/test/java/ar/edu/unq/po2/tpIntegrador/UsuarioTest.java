package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.CheckOutNoRealizadoException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PuntajeInvalidoException;
import ar.edu.unq.po2.tpIntegrador.excepciones.CategoriaInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    Usuario usuario;
    Usuario usuarioInquilino;
    Usuario usuarioPropietario;
    SitioWeb sitio;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Pedro", "pedro_unq@gmail.com", "011-4253-6189");
        usuarioInquilino = new Usuario("Juan", "juan.gomez@gmail.com", "011-5980-4321");
        usuarioPropietario = new Usuario("Lucas", "lucassimon@gmail.com", "011-3435-3637");
        sitio = mock(SitioWeb.class);
        Ranking.setSitio(sitio);
    }

    private Periodo crearPeriodoMockDeFechas(LocalDate fechaInicio, LocalDate fechaFin){
        Periodo periodo = mock(Periodo.class);
        when(periodo.getFechaDesde()).thenReturn(fechaInicio);
        when(periodo.getFechaHasta()).thenReturn(fechaFin);
        return periodo;
    }

    // ------------------------------------------ Tests Usuario --------------------------------------------------------

    @Test
    void getNombreTest() {
        assertEquals("Pedro", usuario.getNombre());
    }

    @Test
    void getEmailTest() {
        assertEquals("pedro_unq@gmail.com", usuario.getEmail());
    }

    @Test
    void getTelefonoTest() {
        assertEquals("011-4253-6189", usuario.getTelefono());
    }

    private List<Object> setUpUsuarioYClockParaTestsDeTiempo(int days, int months, int years){

        Instant instant = Instant.now();
        Clock fixedClock = Clock.fixed(instant, ZoneId.systemDefault());

        Usuario pepe = new Usuario("pepe", "pepe@gmail.com", "011-2345-6789") {
            @Override
            public LocalDate getFechaDeCreacion() {
                return LocalDate.now().minusYears(years).minusMonths(months).minusDays(days);
            }
        };

        return List.of(pepe, fixedClock);
    }

    @Test
    public void getTiempoDeAntiguedadRecienCreadoTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(0, 0, 0);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("Recien creado", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadDiasTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(3, 0, 0);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("3 dias", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadMesesTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(0, 2, 0);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("2 meses", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadMesesDiasTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(3, 2, 0);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("2 meses, 3 dias", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadAñosTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(0, 0, 4);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("4 años", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadAñosDiasTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(3, 0, 4);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("4 años, 3 dias", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadAñosMesesTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(0, 3, 4);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("4 años, 3 meses", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    @Test
    public void getTiempoDeAntiguedadAñosMesesDiasTest() {
        List<Object> args = setUpUsuarioYClockParaTestsDeTiempo(3, 2, 4);
        Usuario pepe = (Usuario) args.get(0);
        Clock clock = (Clock) args.get(1);
        assertEquals("4 años, 2 meses, 3 dias", pepe.getTiempoDeAntiguedad(LocalDate.now(clock)));
    }

    // ------------------------------------------ Tests Propietario ----------------------------------------------------

    private void agregarPublicacionAlSitio(Propietario propietario, Publicacion publicacion){
        doAnswer(invocation -> {
            propietario.agregarPublicacion(publicacion);
            return null;
        }).when(sitio).darDeAltaInmueble(propietario, publicacion);
        sitio.darDeAltaInmueble(propietario, publicacion);
    }

    private List<Publicacion> mockPublicacionFactory(int cantidad){
        List<Publicacion> publicaciones = new ArrayList<>();
        for(int i = 0; i < cantidad; i++){
            publicaciones.add(mock(Publicacion.class));
        }
        return publicaciones;
    }

    @Test
    void cantidadDeVecesQuePublicoInmueblesTest() {
        assertEquals(0, usuarioPropietario.cantidadDeVecesQuePublicoInmuebles());

        List<Publicacion> publicaciones = mockPublicacionFactory(3);

        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(0));
        assertEquals(1, usuarioPropietario.cantidadDeVecesQuePublicoInmuebles());

        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(1));
        assertEquals(2, usuarioPropietario.cantidadDeVecesQuePublicoInmuebles());

        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(2));
        assertEquals(3, usuarioPropietario.cantidadDeVecesQuePublicoInmuebles());
    }

    @Test
    void getInmueblesPublicadosTest() {
        List<Publicacion> publicaciones = mockPublicacionFactory(3);

        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(0));
        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(1));
        agregarPublicacionAlSitio(usuarioPropietario, publicaciones.get(2));

        assertEquals(publicaciones, usuarioPropietario.getInmueblesPublicados());
    }

    // ------------------------------------------- Tests Inquilino -----------------------------------------------------

    private Reserva reservarPublicacion(Publicacion publicacion, LocalDate fechaDesde, LocalDate fechaHasta, Usuario inquilino){
        Reserva reserva = mock(Reserva.class);
        doAnswer(invocation -> {
            inquilino.agregarReserva(reserva);
            return null;
        }).when(publicacion).reservar(any(), any(), any());
        when(reserva.getFechaDesde()).thenReturn(fechaDesde);
        when(reserva.getFechaHasta()).thenReturn(fechaHasta);
        when(reserva.estaAprobada()).thenReturn(false);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        publicacion.reservar(inquilino, crearPeriodoMockDeFechas(fechaDesde, fechaHasta), mock(FormaDePago.class));
        return reserva;
    }

    private List<Reserva> mockReservaFactory(int cantidad, Usuario inquilino){
        List<Reserva> reservas = new ArrayList<>();
        for(int i = 0; i < cantidad; i++){
            reservas.add(reservarPublicacion(mock(Publicacion.class), LocalDate.now(), LocalDate.now(), inquilino));
        }
        return reservas;
    }

    @Test
    void getReservasTest() {
        List<Reserva> reservas = mockReservaFactory(6, usuarioInquilino);
        LocalDate fechaFutura = LocalDate.now().plusMonths(3);
        Reserva reservaFutura = reservarPublicacion(mock(Publicacion.class), fechaFutura, fechaFutura.plusDays(15), usuarioInquilino);
        reservas.add(reservaFutura);
        assertEquals(reservas, usuarioInquilino.getReservas());
    }

    @Test
    void getReservasFuturasTest() {
        mockReservaFactory(3, usuarioInquilino);
        List<Reserva> reservasFuturas = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            LocalDate fechaFutura = LocalDate.now().plusMonths(i + 1 );
            reservasFuturas.add(reservarPublicacion(mock(Publicacion.class), fechaFutura, fechaFutura.plusDays(15), usuarioInquilino));
        }
        mockReservaFactory(3, usuarioInquilino);
        assertEquals(reservasFuturas, usuarioInquilino.getReservasFuturas());
    }

    private void crearReservasEnDosCiudades(Usuario inquilino, String ciudadUno, String ciudadDos, List<Reserva> reservasCiudadUno, List<Reserva> reservasCiudadDos){
        Publicacion dptoCiudadUno = mock(Publicacion.class);
        when(dptoCiudadUno.getCiudad()).thenReturn(ciudadUno);

        Publicacion dptoCiudadDos = mock(Publicacion.class);
        when(dptoCiudadDos.getCiudad()).thenReturn(ciudadDos);

        Publicacion chaleCiudadUno = mock(Publicacion.class);
        when(chaleCiudadUno.getCiudad()).thenReturn(ciudadUno);

        LocalDate fechaFutura = LocalDate.now().plusMonths(2);

        reservasCiudadUno.add(reservarPublicacion(dptoCiudadUno, LocalDate.now(), LocalDate.now().plusDays(15), inquilino));
        reservasCiudadUno.add(reservarPublicacion(chaleCiudadUno, fechaFutura, fechaFutura.plusDays(15), inquilino));
        reservasCiudadDos.add(reservarPublicacion(dptoCiudadDos, fechaFutura.plusMonths(1), fechaFutura.plusMonths(1).plusDays(15), inquilino));
        reservasCiudadDos.add(reservarPublicacion(dptoCiudadDos, fechaFutura.plusMonths(2), fechaFutura.plusMonths(2).plusDays(15), inquilino));
    }

    @Test
    void getReservasEnCiudadTest() {
        List<Reserva> reservasEnQuilmes = new ArrayList<>();
        List<Reserva> reservasEnVarela = new ArrayList<>();

        crearReservasEnDosCiudades(usuarioInquilino, "Quilmes", "Varela", reservasEnQuilmes, reservasEnVarela);

        assertEquals(reservasEnQuilmes, usuarioInquilino.getReservasEnCiudad("Quilmes"));
        assertEquals(reservasEnVarela, usuarioInquilino.getReservasEnCiudad("Varela"));
    }

    @Test
    void getCiudadesConReservaTest() {
        crearReservasEnDosCiudades(usuarioInquilino, "Berazategui", "Lanus", new ArrayList<>(), new ArrayList<>());
        crearReservasEnDosCiudades(usuarioInquilino, "Quilmes", "Lanus", new ArrayList<>(), new ArrayList<>());
        assertTrue(usuarioInquilino.getCiudadesConReservas().contains("Berazategui"));
        assertTrue(usuarioInquilino.getCiudadesConReservas().contains("Lanus"));
        assertTrue(usuarioInquilino.getCiudadesConReservas().contains("Quilmes"));
        assertFalse(usuarioInquilino.getCiudadesConReservas().contains("Varela"));
    }

    @Test
    void getCantidadDeVecesQueAlquiloTest() {
        assertEquals(0, usuarioInquilino.getCantidadDeVecesQueAlquilo());
        List<Reserva> reservas = mockReservaFactory(2, usuarioInquilino);
        for(Reserva reserva : reservas) {
            when(reserva.estaAprobada()).thenReturn(true);
        }
        reservas.addAll(mockReservaFactory(4, usuarioInquilino));
        assertEquals(2, usuarioInquilino.getCantidadDeVecesQueAlquilo());
    }

    @Test
    void getTipoTest() {
        assertEquals("Usuario", usuario.getTipo());
        assertEquals("Inquilino", usuarioInquilino.getTipoInquilino());
        assertEquals("Propietario", usuarioPropietario.getTipoPropietario());
    }

    @Test
    void getFechaDeCreacionTest() {
        assertEquals(LocalDate.now(), usuarioInquilino.getFechaDeCreacion());
    }

    @Test
    void agregarPagoPendienteTest() {
        Deuda deuda = mock(Deuda.class);
        usuario.agregarPagoPendiente(deuda);
    }

    private Ranking setUpRanking(Usuario usuario, int puntaje, String comentario, Categoria categoria){
        Ranking ranking = mock(Ranking.class);

        when(ranking.getUsuario()).thenReturn(usuario);
        when(ranking.getPuntaje()).thenReturn(puntaje);
        when(ranking.getComentario()).thenReturn(comentario);
        when(ranking.getCategoria()).thenReturn(categoria);

        return ranking;
    }

    private void setUpCheckOutContext(Usuario inquilino, Usuario propietario) {
        Reserva reserva = mock(Reserva.class);
        Publicacion publicacion = mock(Publicacion.class);
        when(reserva.estaAprobada()).thenReturn(true);
        when(reserva.getInquilino()).thenReturn(inquilino);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        when(publicacion.getPropietario()).thenReturn(propietario);
        when(publicacion.fueHechoElCheckOut(inquilino)).thenReturn(true);
        inquilino.getReservas().add(reserva);
    }

    private CategoriaInquilino setUpCategoriaValidaInquilino(){
        CategoriaInquilino pagoEnTermino = mock(CategoriaInquilino.class);
        when(pagoEnTermino.getTipoDeCategoria()).thenReturn("Inquilino");
        when(sitio.esCategoriaValida(pagoEnTermino, "Inquilino")).thenReturn(true);
        return pagoEnTermino;
    }

    private CategoriaPropietario setUpCategoriaValidaPropietario(){
        CategoriaPropietario buenaAtencion = mock(CategoriaPropietario.class);
        when(buenaAtencion.getTipoDeCategoria()).thenReturn("Propietario");
        when(sitio.esCategoriaValida(buenaAtencion, "Propietario")).thenReturn(true);
        return buenaAtencion;
    }

    @Test
    void puntuarInquilinoTest() {
        setUpCheckOutContext(usuarioInquilino, usuarioPropietario);
        usuarioInquilino.puntuar(setUpRanking(usuarioPropietario, 5, "Muy buen cliente", setUpCategoriaValidaInquilino()));
    }

    @Test
    void puntuarInquilinoSinHaberHechoCheckOutLanzaExcepcionTest() {
        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->
                usuarioInquilino.puntuar(setUpRanking(usuarioPropietario, 5, "Muy buen cliente", setUpCategoriaValidaInquilino()))
        );
        assertTrue(excepcion.getMessage().contains("No se puede rankear antes de hacer el check-out"));
    }

    @Test
    void puntuarPropietarioSinHaberHechoCheckOutLanzaExcepcionTest() {
        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->
                usuarioPropietario.puntuar(setUpRanking(usuarioInquilino, 5, "Muy buen propietario", setUpCategoriaValidaInquilino()))
        );
        assertTrue(excepcion.getMessage().contains("No se puede rankear antes de hacer el check-out"));
    }

    @Test
    void puntuarConUnPuntajeMayorACincoLanzaExcepcionTest() {
        setUpCheckOutContext(usuarioInquilino, usuarioPropietario);
        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->
                usuarioInquilino.puntuar(setUpRanking(usuarioPropietario, 10, "Muy buen cliente", setUpCategoriaValidaInquilino()))
        );
        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
    }

    @Test
    void puntuarConUnPuntajeMenorAUnoLanzaExcepcionTest() {
        setUpCheckOutContext(usuarioInquilino, usuarioPropietario);
        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->
                usuarioInquilino.puntuar(setUpRanking(usuarioPropietario, 0, "Muy buen cliente", setUpCategoriaValidaInquilino()))
        );
        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
    }

    @Test
    void puntuarUnaCategoriaInvalidaLanzaExcepcionTest() {
        setUpCheckOutContext(usuarioInquilino, usuarioPropietario);
        CategoriaInvalidaException excepcion = assertThrows(CategoriaInvalidaException.class, ()->
                usuarioInquilino.puntuar(setUpRanking(usuarioPropietario, 5, "Muy buen cliente", mock(Categoria.class)))
        );
        assertTrue(excepcion.getMessage().contains("La categoría ingresada no es válida"));
    }

    @Test
    void getPuntajePromedioEnCategoriaEnListaDeRankingsVaciaLanzaExcepcionTest() {
        assertThrows(NoSuchElementException.class, ()->
                usuarioInquilino.getPuntajePromedioEnCategoria(setUpCategoriaValidaInquilino())
        );
    }

    @Test
    void getPuntajePromedioEnCategoriaQueNoExisteLanzaExcepcionTest() {
        puntuarInquilinoTest();
        assertThrows(NoSuchElementException.class, ()->
                usuarioInquilino.getPuntajePromedioEnCategoria(mock(Categoria.class))
        );
    }

    private void puntuarInquilino(Usuario inquilino, Usuario propietario, String comentario, int puntaje, CategoriaInquilino categoria){
        setUpCheckOutContext(inquilino, propietario);
        inquilino.puntuar(setUpRanking(propietario, puntaje, comentario, categoria));
    }

    private void puntuarPropietario(Usuario propietario, Usuario inquilino, String comentario, int puntaje, CategoriaPropietario categoria){
        setUpCheckOutContext(inquilino, propietario);
        propietario.puntuar(setUpRanking(inquilino, puntaje, comentario, categoria));
    }

    @Test
    void getPuntajePromedioEnCategoriaTest() {
        String comentario = "muy buen cliente";
        CategoriaInquilino categoria = setUpCategoriaValidaInquilino();

        for(int i = 0; i < 3; i++){
            puntuarInquilino(usuarioInquilino, new Usuario("Propietario" + i, "propietario" + i + "@gmail.com", "1234567"), comentario, 5 - i, categoria);
        }
        puntuarInquilino(usuarioInquilino, new Usuario("Propietario 3", "propietario3@gmail.com", "1234567"), comentario, 1, setUpCategoriaValidaInquilino());

        assertEquals(4.0, usuarioInquilino.getPuntajePromedioEnCategoria(categoria));
    }

    @Test
    void getPuntajePromedioTotalTest() {
        String comentario = "muy buen cliente";
        CategoriaInquilino categoria = setUpCategoriaValidaInquilino();

        for(int i = 0; i < 3; i++){
            puntuarInquilino(usuarioInquilino, new Usuario("Propietario" + i, "propietario" + i + "@gmail.com", "1234567"), comentario, 5 - i, categoria);
        }
        puntuarInquilino(usuarioInquilino, new Usuario("Propietario 3", "propietario3@gmail.com", "1234567"), comentario, 1, setUpCategoriaValidaInquilino());

        assertEquals(3.2, usuarioInquilino.getPuntajePromedioTotal());
    }

    @Test
    void getComentariosDeInquilinosPreviosRankingsVaciosTest() {
        assertTrue(usuarioPropietario.getComentariosDeInquilinosPrevios().isEmpty());
    }

    @Test
    void getComentariosDeInquilinosPreviosTest() {
        CategoriaPropietario categoria1 = setUpCategoriaValidaPropietario();
        CategoriaPropietario categoria2 = setUpCategoriaValidaPropietario();

        Usuario inquilino1 = new Usuario("inquilino1", "inquilino1@gmail.com", "011-4224-1452");
        Usuario inquilino2 = new Usuario("inquilino2", "inquilino2@gmail.com", "011-2541-4224");

        String comentario1 = "Muy buena la categoria1";
        String comentario2 = "Malarda la categoria2";
        String comentario3 = "Bastante mediocre la categoria2";
        String comentario4 = "Nefasta la categoria1";

        puntuarPropietario(usuarioPropietario, inquilino1, comentario1, 5, categoria1);
        puntuarPropietario(usuarioPropietario, inquilino2, comentario2, 2, categoria2);
        puntuarPropietario(usuarioPropietario, inquilino1, comentario3, 3, categoria2);
        puntuarPropietario(usuarioPropietario, inquilino2, comentario4, 1, categoria1);

        assertTrue(usuarioPropietario.getComentariosDeInquilinosPrevios().containsAll(List.of(comentario1, comentario2, comentario3, comentario4)));
    }

    @Test
    void getPuntajeDeUsuarioEnCategoriaTest() {
        CategoriaPropietario categoria1 = setUpCategoriaValidaPropietario();
        CategoriaPropietario categoria2 = setUpCategoriaValidaPropietario();

        Usuario inquilino1 = new Usuario("inquilino1", "inquilino1@gmail.com", "011-4224-1452");
        Usuario inquilino2 = new Usuario("inquilino2", "inquilino2@gmail.com", "011-2541-4224");

        String comentario1 = "Muy buena la categoria1";
        String comentario2 = "Malarda la categoria2";
        String comentario3 = "Bastante mediocre la categoria2";
        String comentario4 = "Nefasta la categoria1";

        puntuarPropietario(usuarioPropietario, inquilino1, comentario1, 5, categoria1);
        puntuarPropietario(usuarioPropietario, inquilino2, comentario2, 2, categoria2);
        puntuarPropietario(usuarioPropietario, inquilino1, comentario3, 3, categoria2);
        puntuarPropietario(usuarioPropietario, inquilino2, comentario4, 1, categoria1);

        assertEquals(5, usuarioPropietario.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria1));
        assertEquals(2, usuarioPropietario.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria2));
        assertEquals(3, usuarioPropietario.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria2));
        assertEquals(1, usuarioPropietario.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria1));
    }

}
