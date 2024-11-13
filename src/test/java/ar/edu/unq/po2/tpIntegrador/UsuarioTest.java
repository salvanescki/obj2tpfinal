package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    Usuario usuario;
    Inquilino usuarioInquilino;
    Propietario usuarioPropietario;
    SitioWeb sitio;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Pedro", "pedro_unq@gmail.com", "011-4253-6189");
        usuarioInquilino = new Usuario("Juan", "juan.gomez@gmail.com", "011-5980-4321");
        usuarioPropietario = new Usuario("Lucas", "lucassimon@gmail.com", "011-3435-3637");
        sitio = mock(SitioWeb.class);
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
        // Establezco una fecha específica y fija para hoy
        Instant instant = Instant.parse("2024-04-03T00:00:00Z");
        Clock fixedClock = Clock.fixed(instant, ZoneId.systemDefault());

        Usuario pepe = new Usuario("pepe", "pepe@gmail.com", "011-2345-6789") {
            @Override
            public LocalDate getFechaDeCreacion() {
                return LocalDate.of(2024, 4, 3)
                                .minusYears(years)
                                .minusMonths(months)
                                .withDayOfMonth(1)
                                .minusDays(days - 1);
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
        List<Publicacion> publicaciones = new ArrayList<Publicacion>();
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

    private Reserva reservarPublicacion(Publicacion publicacion, LocalDate fechaDesde, LocalDate fechaHasta, Inquilino inquilino){
        Reserva reserva = mock(Reserva.class);
        doAnswer(invocation -> {
            inquilino.agregarReserva(reserva);
            return null;
        }).when(publicacion).reservar(any(), any(), any(), any());
        when(reserva.getFechaDesde()).thenReturn(fechaDesde);
        when(reserva.getFechaHasta()).thenReturn(fechaHasta);
        when(reserva.estaAprobada()).thenReturn(false);
        when(reserva.getPublicacion()).thenReturn(publicacion);
        publicacion.reservar(inquilino, fechaDesde, fechaHasta, mock(FormaDePago.class));
        return reserva;
    }

    private List<Reserva> mockReservaFactory(int cantidad, Inquilino inquilino){
        List<Reserva> reservas = new ArrayList<Reserva>();
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
        List<Reserva> reservasFuturas = new ArrayList<Reserva>();
        for(int i = 0; i < 4; i++){
            LocalDate fechaFutura = LocalDate.now().plusMonths(i + 1 );
            reservasFuturas.add(reservarPublicacion(mock(Publicacion.class), fechaFutura, fechaFutura.plusDays(15), usuarioInquilino));
        }
        mockReservaFactory(3, usuarioInquilino);
        assertEquals(reservasFuturas, usuarioInquilino.getReservasFuturas());
    }

    private void crearReservasEnDosCiudades(Inquilino inquilino, String ciudadUno, String ciudadDos, List<Reserva> reservasCiudadUno, List<Reserva> reservasCiudadDos){
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
        List<Reserva> reservasEnQuilmes = new ArrayList<Reserva>();
        List<Reserva> reservasEnVarela = new ArrayList<Reserva>();

        crearReservasEnDosCiudades(usuarioInquilino, "Quilmes", "Varela", reservasEnQuilmes, reservasEnVarela);

        assertEquals(reservasEnQuilmes, usuarioInquilino.getReservasEnCiudad("Quilmes"));
        assertEquals(reservasEnVarela, usuarioInquilino.getReservasEnCiudad("Varela"));
    }

    @Test
    void getCiudadesConReservaTest() {
        crearReservasEnDosCiudades(usuarioInquilino, "Berazategui", "Lanus", new ArrayList<Reserva>(), new ArrayList<Reserva>());
        crearReservasEnDosCiudades(usuarioInquilino, "Quilmes", "Lanus", new ArrayList<Reserva>(), new ArrayList<Reserva>());
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

//    private Ranking setUpRanking(Usuario usuario, int puntaje, String comentario, Categoria categoria){
//        Ranking ranking = mock(Ranking.class);
//
//        when(ranking.getUsuario()).thenReturn(usuario);
//        when(ranking.getPuntaje()).thenReturn(puntaje);
//        when(ranking.getComentario()).thenReturn(comentario);
//        when(ranking.getCategoria()).thenReturn(categoria);
//
//        return ranking;
//    }
//
//    private Inquilino setUpCheckOutContext(Inquilino inquilino) {
//        Reserva reserva = mock(Reserva.class);
//        when(reserva.estaAprobada()).thenReturn(true);
//        when(reserva.getInquilino()).thenReturn(inquilino);
//        when(reserva.getPublicacion().fueHechoElCheckOut(inquilino)).thenReturn(true);
//        return inquilino;
//    }
//
//    private Categoria setUpCategoriaValida(Usuario usuario){
//        Categoria pagoEnTermino = mock(Categoria.class);
//        when(sitio.esCategoriaValida(pagoEnTermino, usuario)).thenReturn(true);
//        return pagoEnTermino;
//    }
//
//    @Test
//    void puntuarAInquilinoTest() {
//        setUpCheckOutContext(usuarioInquilino).puntuar(setUpRanking((Usuario) usuarioPropietario, 5, "Muy buen cliente", setUpCategoriaValida((Usuario) usuarioInquilino)));
//    }
//
//    @Test
//    void puntuarSinHaberHechoCheckOutLanzaExcepcionTest() {
//        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->{
//            usuarioInquilino.puntuar(setUpRanking((Usuario) usuarioPropietario, 5, "Muy buen cliente", setUpCategoriaValida((Usuario) usuarioInquilino)));
//        });
//        assertTrue(excepcion.getMessage().contains("No se puede rankear antes de hacer el check-out"));
//    }
//
//    @Test
//    void puntuarConUnPuntajeMayorACincoLanzaExcepcionTest() {
//        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->{
//            setUpCheckOutContext(usuarioInquilino).puntuar(setUpRanking((Usuario) usuarioPropietario, 10, "Muy buen cliente", setUpCategoriaValida((Usuario) usuarioInquilino)));
//        });
//        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
//    }
//
//    @Test
//    void puntuarConUnPuntajeMenorAUnoLanzaExcepcionTest() {
//        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->{
//            setUpCheckOutContext(usuarioInquilino).puntuar(setUpRanking((Usuario) usuarioPropietario, 0, "Muy buen cliente", setUpCategoriaValida((Usuario) usuarioInquilino)));
//        });
//        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
//    }
//
//    @Test
//    void puntuarUnaCategoriaInvalidaLanzaExcepcionTest() {
//        CategoriaInvalidaException excepcion = assertThrows(CategoriaInvalidaException.class, ()->{
//            setUpCheckOutContext(usuarioInquilino).puntuar(setUpRanking((Usuario) usuarioPropietario, 5, "Muy buen cliente", mock(Categoria.class)));
//        });
//        assertTrue(excepcion.getMessage().contains("La categoría ingresada no es válida"));
//    }
//
//    @Test
//    void getPuntajePromedioEnCategoriaEnListaDeRankingsVaciaLanzaExcepcionTest() {
//        assertThrows(NoSuchElementException.class, ()->
//                usuarioInquilino.getPuntajePromedioEnCategoria(setUpCategoriaValida((Usuario) usuarioInquilino))
//        );
//    }
//
//    @Test
//    void getPuntajePromedioEnCategoriaQueNoExisteLanzaExcepcionTest() {
//        puntuarAInquilinoTest();
//        assertThrows(NoSuchElementException.class, ()->
//                usuarioInquilino.getPuntajePromedioEnCategoria(mock(Categoria.class))
//        );
//    }
//
//    @Test
//    void getPuntajePromedioEnCategoriaTest() {
//        Usuario propietario1 = mock(Usuario.class);
//        Usuario propietario2 = mock(Usuario.class);
//        Usuario propietario3 = mock(Usuario.class);
//        Usuario propietario4 = mock(Usuario.class);
//
//        Categoria categoria = setUpCategoriaValida((Usuario) usuarioInquilino);
//        String comentario = "muy buen cliente";
//
//        setUpCheckOutContext(usuarioInquilino);
//
//        usuarioInquilino.puntuar(setUpRanking(propietario1, 5, comentario, categoria));
//        usuarioInquilino.puntuar(setUpRanking(propietario2, 2, comentario, categoria));
//        usuarioInquilino.puntuar(setUpRanking(propietario3, 3, comentario, categoria));
//        usuarioInquilino.puntuar(setUpRanking(propietario4, 1, comentario, setUpCategoriaValida((Usuario) usuarioInquilino)));
//
//        assertEquals(3.3, publicacion.getPuntajePromedioEnCategoria(categoria));
//    }
//
//    @Test
//    void getPuntajePromedioTotalTest() {
//        Usuario inquilino1 = mock(Usuario.class);
//        Usuario inquilino2 = mock(Usuario.class);
//        Usuario inquilino3 = mock(Usuario.class);
//        Usuario inquilino4 = mock(Usuario.class);
//
//        Categoria categoria = setUpCategoriaValida();
//
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, "muy buen wi-fi", categoria));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, "muy buen wi-fi", categoria));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino3), 3, "muy buen wi-fi", categoria));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino4), 1, "muy buen wi-fi", setUpCategoriaValida()));
//
//        assertEquals(2.8, publicacion.getPuntajePromedioTotal());
//    }
//
//    @Test
//    void getComentariosDeInquilinosPreviosRankingsVaciosTest() {
//        assertTrue(publicacion.getComentariosDeInquilinosPrevios().isEmpty());
//    }
//
//    @Test
//    void getComentariosDeInquilinosPreviosTest() {
//        Usuario inquilino1 = mock(Usuario.class);
//        Usuario inquilino2 = mock(Usuario.class);
//
//        Categoria categoria1 = setUpCategoriaValida();
//        Categoria categoria2 = setUpCategoriaValida();
//
//        String comentario1 = "Muy buena la categoria1";
//        String comentario2 = "Malarda la categoria2";
//        String comentario3 = "Bastante mediocre la categoria2";
//        String comentario4 = "Nefasta la categoria1";
//
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, comentario1, categoria1));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, comentario2, categoria2));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 3, comentario3, categoria2));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 1, comentario4, categoria1));
//
//        assertTrue(publicacion.getComentariosDeInquilinosPrevios().containsAll(List.of(comentario1, comentario2, comentario3, comentario4)));
//    }
//
//    @Test
//    void getPuntajeDeUsuarioEnCategoriaTest() {
//        Usuario inquilino1 = mock(Usuario.class);
//        Usuario inquilino2 = mock(Usuario.class);
//
//        Categoria categoria1 = setUpCategoriaValida();
//        Categoria categoria2 = setUpCategoriaValida();
//
//        String comentario1 = "Muy buena la categoria1";
//        String comentario2 = "Malarda la categoria2";
//        String comentario3 = "Bastante mediocre la categoria2";
//        String comentario4 = "Nefasta la categoria1";
//
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, comentario1, categoria1));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, comentario2, categoria2));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 3, comentario3, categoria2));
//        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 1, comentario4, categoria1));
//
//        assertEquals(5, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria1));
//        assertEquals(2, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria2));
//        assertEquals(3, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria2));
//        assertEquals(1, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria1));
//    }
//
//    /*
//    TODO: Habría que separar e/Inquilino y Usuario los Rankings.
//        puntuar()
//        getPuntaje()
//        getPuntajePromedioTotal()
//        getComentariosDeInquilinosPrevios()
//        getPuntajeDeUsuarioEnCategoria()
//
//     */
}
