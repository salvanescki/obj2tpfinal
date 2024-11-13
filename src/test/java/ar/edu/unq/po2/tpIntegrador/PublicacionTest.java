package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.po2.tpIntegrador.excepciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


public class PublicacionTest {

    private Publicacion publicacion;
    private SitioWeb sitio;
    private Propietario dummyPropietario;
    private TipoDeInmueble dummyTipoDeInmueble;
    private Foto dummyFoto;
    private FormaDePago dummyFormaDePago;
    private Servicio dummyServicio;
    private Precio dummyPrecio;
    Inquilino inquilino;
    FormaDePago tarjeta;
    LocalDate diaDesde;
    LocalDate diaHasta;
    Periodo mockPeriodo;
    Periodo periodoDeFechas;
    private PoliticaDeCancelacion dummyPoliticaDeCancelacion;

    @BeforeEach
    void setUp() {
        sitio = mock(SitioWeb.class);
        Ranking.setSitio(sitio);
        dummyPropietario = mock(Usuario.class);
        dummyTipoDeInmueble = mock(TipoDeInmueble.class);
        dummyFoto = mock(Foto.class);
        dummyFormaDePago = mock(FormaDePago.class);
        dummyServicio = mock(Servicio.class);
        dummyPrecio = mock(Precio.class);
        when(dummyPrecio.getPrecio()).thenReturn(500.0);
        publicacion = new Publicacion(
                                  dummyPropietario,
                                  dummyTipoDeInmueble,
                                  40,
                                  "Argentina",
                                  "Quilmes",
                                  "Mitre 510",
                                  Arrays.asList(dummyServicio, dummyServicio, dummyServicio, dummyServicio),
                                  3,
                                  Arrays.asList(dummyFoto, dummyFoto, dummyFoto, dummyFoto, dummyFoto),
                                  LocalTime.of(14, 0),
                                  LocalTime.of(12,0),
                                  Arrays.asList(dummyFormaDePago, dummyFormaDePago, dummyFormaDePago, dummyFormaDePago),
                                  dummyPrecio
                                );
        dummyPoliticaDeCancelacion = mock(PoliticaSinCancelacion.class);
        publicacion.definirPoliticaDeCancelacion(dummyPoliticaDeCancelacion);
        inquilino = mock(Usuario.class);
        tarjeta = mock(FormaDePago.class);
        diaDesde = LocalDate.of(2024, 12, 1);
        diaHasta = LocalDate.of(2024, 12, 15);

        periodoDeFechas = crearPeriodoMockDeFechas(diaDesde, diaHasta);

        mockPeriodo = mock(Periodo.class);
        when(mockPeriodo.seSuperponeCon(mockPeriodo)).thenReturn(true);
        for(LocalDate dia = diaDesde; !dia.isAfter(diaHasta); dia = dia.plusDays(1)){
            when(mockPeriodo.estaDentroDelPeriodo(dia)).thenReturn(true);
            when(mockPeriodo.getPrecio()).thenReturn(new Precio(2000));
        }
    }

    private Periodo crearPeriodoMockDeFechas(LocalDate fechaInicio, LocalDate fechaFin){
        Periodo periodo = mock(Periodo.class);
        when(periodo.getFechaDesde()).thenReturn(fechaInicio);
        when(periodo.getFechaHasta()).thenReturn(fechaFin);
        return periodo;
    }

    @Test
    void getPropietarioTest() {
        assertEquals(dummyPropietario, publicacion.getPropietario());
    }

    @Test
    void getTipoDeInmuebleTest() {
        assertEquals(dummyTipoDeInmueble, publicacion.getTipoDeInmueble());
    }

    @Test
    void getSuperficieTest() {
        assertEquals(40, publicacion.getSuperficie());
    }

    @Test
    void getPaisTest() {
        assertEquals("Argentina", publicacion.getPais());
    }

    @Test
    void getCiudadTest() {
        assertEquals("Quilmes", publicacion.getCiudad());
    }

    @Test
    void getDireccionTest() {
        assertEquals("Mitre 510", publicacion.getDireccion());
    }

    @Test
    void getServiciosTest() {
        assertEquals(Arrays.asList(dummyServicio, dummyServicio, dummyServicio, dummyServicio), publicacion.getServicios());
    }

    @Test
    void getCapacidadTest() {
        assertEquals(3, publicacion.getCapacidad());
    }

    @Test
    void getFotosTest() {
        assertEquals(Arrays.asList(dummyFoto, dummyFoto, dummyFoto, dummyFoto, dummyFoto), publicacion.getFotos());
    }

    @Test
    void getHorarioCheckInTest() {
        assertEquals(LocalTime.of(14, 0), publicacion.getHorarioCheckIn());
    }

    @Test
    void getHorarioCheckOutTest() {
        assertEquals(LocalTime.of(12, 0), publicacion.getHorarioCheckOut());
    }

    @Test
    void getFormasDePagoTest() {
        assertEquals(Arrays.asList(dummyFormaDePago, dummyFormaDePago, dummyFormaDePago, dummyFormaDePago), publicacion.getFormasDePago());
    }

    @Test
    void getTipoTest() {
        assertEquals("Publicacion", publicacion.getTipo());
    }

    @Test
    void getPrecioEnElCasoNormalDebeDevolverElPrecioBasePorLaCantidadDeDiasTest() {
        int cantDeDias = 15;
        assertEquals(dummyPrecio.getPrecio() * cantDeDias,
                publicacion.getPrecio(periodoDeFechas).getPrecio());
    }

    @Test
    void definirPeriodoDosOMasVecesLanzaExcepcion() {
        publicacion.definirPeriodo(mockPeriodo);

        PeriodoYaDefinidoException excepcion = assertThrows(PeriodoYaDefinidoException.class, ()->{
            publicacion.definirPeriodo(mockPeriodo);
        });

        assertTrue(excepcion.getMessage().contains("El periodo existe o se superpone con otro definido previamente"));
    }

    @Test
    void definirPeriodoQueSeSuperponeAOtroYaDefinidoPreviamenteLanzaExcepcion() {
        publicacion.definirPeriodo(mockPeriodo);

        Periodo mockPeriodoSuperpuesto = mock(Periodo.class);
        when(mockPeriodo.seSuperponeCon(mockPeriodoSuperpuesto)).thenReturn(true);

        PeriodoYaDefinidoException excepcion = assertThrows(PeriodoYaDefinidoException.class, ()->{
            publicacion.definirPeriodo(mockPeriodoSuperpuesto);
        });

        assertTrue(excepcion.getMessage().contains("El periodo existe o se superpone con otro definido previamente"));
    }

    @Test
    void getPrecioEnUnPeriodoDefinidoDebeDevolverElPrecioDeDichoPeriodoPorLaCantidadDeDiasTest() {
        int cantDeDias = 15;
        publicacion.definirPeriodo(mockPeriodo);
        assertEquals(2000 * cantDeDias, publicacion.getPrecio(periodoDeFechas).getPrecio());
    }

    @Test
    void getPrecioEntreUnPeriodoDefinidoYPeriodoNormalDebeDevolverLaSumaSegunCorrespondeTest() {
        int cantDeDiasEnPeriodo = 11;
        int cantDeDiasFueraDelPeriodo = 7;

        publicacion.definirPeriodo(mockPeriodo);

        Periodo periodo = crearPeriodoMockDeFechas(LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 22));

        assertEquals(2000 * cantDeDiasEnPeriodo + dummyPrecio.getPrecio() * cantDeDiasFueraDelPeriodo,
                publicacion.getPrecio(periodo).getPrecio());
    }

    @Test
    void reservarTest() {
        assertEquals(0, publicacion.getReservas().size());
        publicacion.reservar(inquilino, periodoDeFechas, tarjeta);
        verify(inquilino).agregarReserva(any(Reserva.class));
        assertEquals(1, publicacion.getReservas().size());
    }

    @Test
    void reservarConFechaDesdePosteriorAFechaHastaLanzaExcepcionTest() {
        Periodo periodoInvalido = crearPeriodoMockDeFechas(diaHasta, diaDesde);
        FechasInvalidasException excepcion = assertThrows(FechasInvalidasException.class, ()->{
            publicacion.reservar(inquilino, periodoInvalido, tarjeta);
        });
        assertTrue(excepcion.getMessage().contains("Las fechas introducidas no son válidas."));
    }

    @Test
    void reservarEnUnPeriodoYaReservadoCreaUnaReservaCondicionalTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);

        Reserva reserva = mock(Reserva.class);
        when(reserva.seSuperponeConElPeriodo(periodoDeFechas)).thenReturn(true);
        if(!publicacion.estaReservadaEnFechas(periodoDeFechas)){
            mockNotificador.notificarReserva(anyString(), any());
        }
        publicacion.getReservas().add(reserva);

        publicacion.reservar(inquilino, periodoDeFechas, tarjeta);

        verify(mockNotificador, atMost(1)).notificarReserva(anyString(), any());
    }

    @Test
    void estaReservadaEnFechasTest() {
        assertFalse(publicacion.estaReservadaEnFechas(periodoDeFechas));

        Reserva reserva = mock(Reserva.class);
        when(reserva.seSuperponeConElPeriodo(periodoDeFechas)).thenReturn(true);
        publicacion.getReservas().add(reserva);

        assertTrue(publicacion.estaReservadaEnFechas(periodoDeFechas));
    }

    @Test
    void estaReservadaEnFechasAunqueSoloCoincidaUnDiaTest() {
        assertFalse(publicacion.estaReservadaEnFechas(periodoDeFechas));

        Reserva reserva = mock(Reserva.class);
        Periodo periodo = crearPeriodoMockDeFechas(diaHasta, LocalDate.of(2024, 12, 24));
        when(reserva.seSuperponeConElPeriodo(periodo)).thenReturn(true);
        publicacion.getReservas().add(reserva);

        assertTrue(publicacion.estaReservadaEnFechas(periodo));
    }

    @Test
    void cancelarReservaTest() {
        publicacion.reservar(inquilino, periodoDeFechas, tarjeta);
        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(inquilino).agregarReserva(captor.capture());
        Reserva reserva = spy(captor.getValue());
        publicacion.cancelarReserva(reserva);
        verify(reserva).cancelarReserva();
    }

    @Test
    void cancelarReservaConReservasCondicionalesEjecutaLaPrimeraDeEllasTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);

        Reserva mockReserva = mock(Reserva.class);

        when(mockReserva.seSuperponeConElPeriodo(periodoDeFechas)).thenReturn(true);
        when(mockReserva.getFechaDesde()).thenReturn(diaDesde);
        when(mockReserva.getFechaHasta()).thenReturn(diaHasta);
        when(mockReserva.getPeriodo()).thenReturn(periodoDeFechas);

        assertFalse(publicacion.estaReservadaEnFechas(periodoDeFechas));

        if(!publicacion.estaReservadaEnFechas(periodoDeFechas)){
            mockNotificador.notificarReserva(anyString(), any());
        }
        publicacion.getReservas().add(mockReserva);

        Reserva mockReservaCondicional = mock(Reserva.class);

        when(mockReservaCondicional.seSuperponeConElPeriodo(periodoDeFechas)).thenReturn(true);
        when(mockReservaCondicional.getFechaDesde()).thenReturn(diaDesde);
        when(mockReservaCondicional.getFechaHasta()).thenReturn(diaHasta);
        when(mockReservaCondicional.getPeriodo()).thenReturn(periodoDeFechas);
        when(mockReservaCondicional.estaPendiente()).thenReturn(true);

        assertTrue(publicacion.estaReservadaEnFechas(periodoDeFechas));

        if(!publicacion.estaReservadaEnFechas(periodoDeFechas)){
            mockNotificador.notificarReserva(anyString(), any());
        }

        publicacion.getReservas().add(mockReservaCondicional);

        //doNothing().when(mockReserva).cancelarReserva();
        when(mockReserva.fueCancelada()).thenReturn(true);
        when(mockReserva.estaPendiente()).thenReturn(false);

        publicacion.cancelarReserva(mockReserva);

        verify(mockNotificador, atLeast(2)).notificarReserva(anyString(), any());
    }

    @Test
    void getCantidadDeVecesAlquiladaSiNoFueAlquiladaDebeDarCeroTest() {
        assertEquals(0, publicacion.getCantidadDeVecesAlquilada());
    }

    @Test
    void getCantidadDeVecesAlquiladaSiFueAlquiladaTest() {
        Reserva reserva = mock(Reserva.class);
        publicacion.getReservas().add(reserva);
        assertEquals(0, publicacion.getCantidadDeVecesAlquilada());
        when(reserva.estaAprobada()).thenReturn(true);
        assertEquals(1, publicacion.getCantidadDeVecesAlquilada());
    }

    @Test
    void suscribirNotificacionesDeReservaTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        doAnswer(invocation ->{
            String msg = invocation.getArgument(0);
            Publicacion publicacion = invocation.getArgument(1);
            listener.notificarReserva(msg, publicacion);
            return null;
        }).when(mockNotificador).notificarReserva(anyString(), any());

        publicacion.reservar(inquilino, periodoDeFechas, tarjeta);
        verify(listener).notificarReserva("El inmueble " + publicacion.getTipoDeInmueble() +
                " que te interesa, ha sido reservado desde el 2024-12-01 hasta el 2024-12-15.", publicacion); //El mensaje se manda al reservar, por lo que las fechas se encuentran en ese scope
    }

    @Test
    void suscribirNotificacionesDeBajaDePrecioTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        doAnswer(invocation ->{
            String msg = invocation.getArgument(0);
            Publicacion publicacion = invocation.getArgument(1);
            listener.notificarBajaDePrecio(msg, publicacion);
            return null;
        }).when(mockNotificador).notificarBajaDePrecio(anyString(), any());

        Precio nuevoPrecio = mock(Precio.class);
        when(nuevoPrecio.compareTo(any())).thenReturn(-1);
        when(nuevoPrecio.toString()).thenReturn("$250.00");
        publicacion.setPrecioBase(nuevoPrecio);

        verify(listener).notificarBajaDePrecio("No te pierdas esta oferta: Un inmueble " + publicacion.getTipoDeInmueble()
                + " a tan sólo " + nuevoPrecio.toString() + " pesos", publicacion);
    }

    @Test
    void subaDePrecioNoNotificaBajaDePrecioTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        doAnswer(invocation ->{
            String msg = invocation.getArgument(0);
            Publicacion publicacion = invocation.getArgument(1);
            listener.notificarBajaDePrecio(msg, publicacion);
            return null;
        }).when(mockNotificador).notificarBajaDePrecio(anyString(), any());

        Precio nuevoPrecio = mock(Precio.class);
        when(nuevoPrecio.compareTo(any())).thenReturn(1);
        when(nuevoPrecio.toString()).thenReturn("$750.00");
        publicacion.setPrecioBase(nuevoPrecio);

        verify(listener, never()).notificarBajaDePrecio("No te pierdas esta oferta: Un inmueble " + publicacion.getTipoDeInmueble()
                + " a tan sólo " + nuevoPrecio.toString() + " pesos", publicacion);
    }

    @Test
    void suscribirNotificacionesDeCancelacionDeReservaTest() {
        publicacion.reservar(inquilino, periodoDeFechas, tarjeta);
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        doAnswer(invocation ->{
            String msg = invocation.getArgument(0);
            Publicacion publicacion = invocation.getArgument(1);
            listener.notificarCancelacionReserva(msg, publicacion);
            return null;
        }).when(mockNotificador).notificarCancelacionReserva(anyString(), any());

        publicacion.cancelarReserva(publicacion.getReservas().getFirst());
        verify(listener).notificarCancelacionReserva("El/la "+ publicacion.getTipoDeInmueble() + " que te interesa se ha liberado! Corre a reservarlo!", publicacion);
    }

    @Test
    void checkOutTest() {
        Usuario inquilino = mock(Usuario.class);
        Reserva reserva = mock(Reserva.class);
        when(reserva.estaAprobada()).thenReturn(true);
        publicacion.getReservas().add(reserva);
        when(reserva.getInquilino()).thenReturn(inquilino);

        assertEquals(1, publicacion.getReservas().size());

        publicacion.checkOut(reserva, publicacion.getHorarioCheckOut());

        assertEquals(0, publicacion.getReservas().size());
    }

    @Test
    void checkOutSiLaReservaNoFueAprobadaPreviamenteLanzaExcepcion() {
        Usuario inquilino = mock(Usuario.class);
        Reserva reserva = mock(Reserva.class);
        when(reserva.estaAprobada()).thenReturn(false);
        publicacion.getReservas().add(reserva);
        when(reserva.getInquilino()).thenReturn(inquilino);

        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->{
            publicacion.checkOut(reserva, publicacion.getHorarioCheckOut());
        });
        assertTrue(excepcion.getMessage().contains("No se puede hacer check out de una reserva previamente aprobada"));
    }

    @Test
    void checkOutSiElHorarioDeCheckOutYaPasoLanzaExcepcion() {
        Usuario inquilino = mock(Usuario.class);
        Reserva reserva = mock(Reserva.class);
        when(reserva.estaAprobada()).thenReturn(true);
        publicacion.getReservas().add(reserva);
        when(reserva.getInquilino()).thenReturn(inquilino);

        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->{
            publicacion.checkOut(reserva, publicacion.getHorarioCheckOut().plusHours(1));
        });
        assertTrue(excepcion.getMessage().contains("El horario de check-out de hoy ya ha pasado, inténtelo mañana"));
    }

    private CategoriaPublicacion setUpCategoriaValida(){
        CategoriaPublicacion servicios = mock(CategoriaPublicacion.class);
        when(sitio.esCategoriaValida(servicios, publicacion)).thenReturn(true);
        return servicios;
    }

    private Ranking setUpRanking(Usuario inquilino, int puntaje, String comentario, Categoria categoria){
        Ranking ranking = mock(Ranking.class);

        when(ranking.getUsuario()).thenReturn(inquilino);
        when(ranking.getPuntaje()).thenReturn(puntaje);
        when(ranking.getComentario()).thenReturn(comentario);
        when(ranking.getCategoria()).thenReturn(categoria);

        return ranking;
    }

    private Usuario setUpCheckOutContext(Usuario inquilino) {
        Reserva reserva = mock(Reserva.class);
        when(reserva.estaAprobada()).thenReturn(true);
        when(reserva.getInquilino()).thenReturn(inquilino);
        publicacion.checkOut(reserva, publicacion.getHorarioCheckOut());
        return inquilino;
    }

    @Test
    void puntuarTest(){
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(mock(Usuario.class)), 5, "muy buen wi-fi", setUpCategoriaValida()));
    }

    @Test
    void puntuarSinHaberHechoCheckOutLanzaExcepcionTest() {
        Usuario inquilino = mock(Usuario.class);

        CheckOutNoRealizadoException excepcion = assertThrows(CheckOutNoRealizadoException.class, ()->{
            publicacion.puntuar(setUpRanking(inquilino, 5, "muy buen wi-fi", setUpCategoriaValida()));
        });
        assertTrue(excepcion.getMessage().contains("No se puede rankear antes de hacer el check-out"));
    }

    @Test
    void puntuarConUnPuntajeMayorACincoLanzaExcepcionTest() {
        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->{
            publicacion.puntuar(setUpRanking(setUpCheckOutContext(mock(Usuario.class)), 10, "muy buen wi-fi", setUpCategoriaValida()));
        });
        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
    }

    @Test
    void puntuarConUnPuntajeMenorAUnoLanzaExcepcionTest() {
        PuntajeInvalidoException excepcion = assertThrows(PuntajeInvalidoException.class, ()->{
            publicacion.puntuar(setUpRanking(setUpCheckOutContext(mock(Usuario.class)), 0, "muy buen wi-fi", setUpCategoriaValida()));
        });
        assertTrue(excepcion.getMessage().contains("El puntaje debe ser en una escala del 1 al 5"));
    }

    @Test
    void puntuarUnaCategoriaInvalidaLanzaExcepcionTest() {
        CategoriaInvalidaException excepcion = assertThrows(CategoriaInvalidaException.class, ()->{
            publicacion.puntuar(setUpRanking(setUpCheckOutContext(mock(Usuario.class)), 4, "muy buen wi-fi", mock(Categoria.class)));
        });
        assertTrue(excepcion.getMessage().contains("La categoría ingresada no es válida"));
    }

    @Test
    void getPuntajePromedioEnCategoriaEnListaDeRankingsVaciaLanzaExcepcionTest() {
        assertThrows(NoSuchElementException.class, ()->
            publicacion.getPuntajePromedioEnCategoria(setUpCategoriaValida())
        );
    }

    @Test
    void getPuntajePromedioEnCategoriaQueNoExisteLanzaExcepcionTest() {
        puntuarTest();
        assertThrows(NoSuchElementException.class, ()->
            publicacion.getPuntajePromedioEnCategoria(mock(Categoria.class))
        );
    }

    @Test
    void getPuntajePromedioEnCategoriaTest() {
        Usuario inquilino1 = mock(Usuario.class);
        Usuario inquilino2 = mock(Usuario.class);
        Usuario inquilino3 = mock(Usuario.class);
        Usuario inquilino4 = mock(Usuario.class);

        Categoria categoria = setUpCategoriaValida();

        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino3), 3, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino4), 1, "muy buen wi-fi", setUpCategoriaValida()));

        assertEquals(3.3, publicacion.getPuntajePromedioEnCategoria(categoria));
    }

    @Test
    void getPuntajePromedioTotalTest() {
        Usuario inquilino1 = mock(Usuario.class);
        Usuario inquilino2 = mock(Usuario.class);
        Usuario inquilino3 = mock(Usuario.class);
        Usuario inquilino4 = mock(Usuario.class);

        Categoria categoria = setUpCategoriaValida();

        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino3), 3, "muy buen wi-fi", categoria));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino4), 1, "muy buen wi-fi", setUpCategoriaValida()));

        assertEquals(2.8, publicacion.getPuntajePromedioTotal());
    }

    @Test
    void getComentariosDeInquilinosPreviosRankingsVaciosTest() {
        assertTrue(publicacion.getComentariosDeInquilinosPrevios().isEmpty());
    }

    @Test
    void getComentariosDeInquilinosPreviosTest() {
        Usuario inquilino1 = mock(Usuario.class);
        Usuario inquilino2 = mock(Usuario.class);

        Categoria categoria1 = setUpCategoriaValida();
        Categoria categoria2 = setUpCategoriaValida();

        String comentario1 = "Muy buena la categoria1";
        String comentario2 = "Malarda la categoria2";
        String comentario3 = "Bastante mediocre la categoria2";
        String comentario4 = "Nefasta la categoria1";

        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, comentario1, categoria1));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, comentario2, categoria2));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 3, comentario3, categoria2));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 1, comentario4, categoria1));

        assertTrue(publicacion.getComentariosDeInquilinosPrevios().containsAll(List.of(comentario1, comentario2, comentario3, comentario4)));
    }

    @Test
    void getPuntajeDeUsuarioEnCategoriaTest() {
        Usuario inquilino1 = mock(Usuario.class);
        Usuario inquilino2 = mock(Usuario.class);

        Categoria categoria1 = setUpCategoriaValida();
        Categoria categoria2 = setUpCategoriaValida();

        String comentario1 = "Muy buena la categoria1";
        String comentario2 = "Malarda la categoria2";
        String comentario3 = "Bastante mediocre la categoria2";
        String comentario4 = "Nefasta la categoria1";

        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 5, comentario1, categoria1));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 2, comentario2, categoria2));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino1), 3, comentario3, categoria2));
        publicacion.puntuar(setUpRanking(setUpCheckOutContext(inquilino2), 1, comentario4, categoria1));

        assertEquals(5, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria1));
        assertEquals(2, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria2));
        assertEquals(3, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino1, categoria2));
        assertEquals(1, publicacion.getPuntajeDeUsuarioEnCategoria(inquilino2, categoria1));
    }
}
