package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.po2.tpIntegrador.excepciones.FechasInvalidasException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PeriodoYaDefinidoException;
import ar.edu.unq.po2.tpIntegrador.excepciones.PrecioInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;


public class PublicacionTest {

    private Publicacion publicacion;
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

    @BeforeEach
    void setUp() {
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
        inquilino = mock(Usuario.class);
        tarjeta = mock(FormaDePago.class);
        diaDesde = LocalDate.of(2024, 12, 1);
        diaHasta = LocalDate.of(2024, 12, 15);

        mockPeriodo = mock(Periodo.class);
        when(mockPeriodo.seSuperponeCon(mockPeriodo)).thenReturn(true);
        for(LocalDate dia = diaDesde; !dia.isAfter(diaHasta); dia = dia.plusDays(1)){
            when(mockPeriodo.estaDentroDelPeriodo(dia)).thenReturn(true);
            when(mockPeriodo.getPrecio()).thenReturn(new Precio(2000));
        }
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
    void getPrecioEnElCasoNormalDebeDevolverElPrecioBasePorLaCantidadDeDiasTest() {
        int cantDeDias = 15;
        assertEquals(dummyPrecio.getPrecio() * cantDeDias,
                publicacion.getPrecio(diaDesde, diaHasta).getPrecio());
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
        assertEquals(2000 * cantDeDias, publicacion.getPrecio(diaDesde, diaHasta).getPrecio());
    }

    @Test
    void getPrecioEntreUnPeriodoDefinidoYPeriodoNormalDebeDevolverLaSumaSegunCorrespondeTest() {
        int cantDeDiasEnPeriodo = 11;
        int cantDeDiasFueraDelPeriodo = 7;

        publicacion.definirPeriodo(mockPeriodo);

        assertEquals(2000 * cantDeDiasEnPeriodo + dummyPrecio.getPrecio() * cantDeDiasFueraDelPeriodo,
                publicacion.getPrecio(LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 22)).getPrecio());
    }

    @Test
    void reservarTest() {
        assertEquals(0, publicacion.getReservas().size());
        publicacion.reservar(inquilino, diaDesde, diaHasta, tarjeta);
        verify(inquilino).agregarReserva(any(Reserva.class));
        assertEquals(1, publicacion.getReservas().size());
    }

    @Test
    void reservarConFechaDesdePosteriorAFechaHastaLanzaExcepcionTest() {
        FechasInvalidasException excepcion = assertThrows(FechasInvalidasException.class, ()->{
            publicacion.reservar(inquilino, diaHasta, diaDesde, tarjeta);
        });
        assertTrue(excepcion.getMessage().contains("Las fechas introducidas no son válidas."));
    }

    @Test
    void reservarEnUnPeriodoYaReservadoCreaUnaReservaCondicionalTest() {
        Notificador mockNotificador = mock(Notificador.class);
        publicacion.setNotificador(mockNotificador);

        Reserva reserva = mock(Reserva.class);
        when(reserva.seSuperponeConElPeriodo(diaDesde, diaHasta)).thenReturn(true);
        if(!publicacion.estaReservadaEnFechas(diaDesde,diaHasta)){
            mockNotificador.notificarReserva(anyString(), any());
        }
        publicacion.getReservas().add(reserva);

        publicacion.reservar(inquilino, diaDesde, diaHasta, tarjeta);

        verify(mockNotificador, atMost(1)).notificarReserva(anyString(), any());
    }

    @Test
    void estaReservadaEnFechasTest() {
        assertFalse(publicacion.estaReservadaEnFechas(diaDesde, diaHasta));

        Reserva reserva = mock(Reserva.class);
        when(reserva.seSuperponeConElPeriodo(diaDesde, diaHasta)).thenReturn(true);
        publicacion.getReservas().add(reserva);

        assertTrue(publicacion.estaReservadaEnFechas(diaDesde, diaHasta));
    }

    @Test
    void estaReservadaEnFechasAunqueSoloCoincidaUnDiaTest() {
        assertFalse(publicacion.estaReservadaEnFechas(diaDesde, diaHasta));

        Reserva reserva = mock(Reserva.class);
        when(reserva.seSuperponeConElPeriodo(diaHasta, LocalDate.of(2024, 12, 24))).thenReturn(true);
        publicacion.getReservas().add(reserva);

        assertTrue(publicacion.estaReservadaEnFechas(diaHasta, LocalDate.of(2024, 12, 24)));
    }

    @Test
    void cancelarReservaTest() {
        publicacion.reservar(inquilino, diaDesde, diaHasta, tarjeta);
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

        when(mockReserva.seSuperponeConElPeriodo(diaDesde, diaHasta)).thenReturn(true);
        when(mockReserva.getFechaDesde()).thenReturn(diaDesde);
        when(mockReserva.getFechaHasta()).thenReturn(diaHasta);

        assertFalse(publicacion.estaReservadaEnFechas(diaDesde,diaHasta));

        if(!publicacion.estaReservadaEnFechas(diaDesde,diaHasta)){
            mockNotificador.notificarReserva(anyString(), any());
        }
        publicacion.getReservas().add(mockReserva);

        Reserva mockReservaCondicional = mock(Reserva.class);

        when(mockReservaCondicional.seSuperponeConElPeriodo(diaDesde, diaHasta)).thenReturn(true);
        when(mockReservaCondicional.getFechaDesde()).thenReturn(diaDesde);
        when(mockReservaCondicional.getFechaHasta()).thenReturn(diaHasta);
        when(mockReservaCondicional.estaPendiente()).thenReturn(true);

        assertTrue(publicacion.estaReservadaEnFechas(diaDesde,diaHasta));

        if(!publicacion.estaReservadaEnFechas(diaDesde,diaHasta)){
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

        publicacion.reservar(inquilino, diaDesde, diaHasta, tarjeta);
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
        publicacion.reservar(inquilino, diaDesde, diaHasta, tarjeta);
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

}
