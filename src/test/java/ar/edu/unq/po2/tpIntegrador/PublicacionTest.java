package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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

    @BeforeEach
    void setUp() {
        dummyPropietario = mock(Usuario.class);
        dummyTipoDeInmueble = mock(TipoDeInmueble.class);
        dummyFoto = mock(Foto.class);
        dummyFormaDePago = mock(FormaDePago.class);
        dummyServicio = mock(Servicio.class);
        dummyPrecio = mock(Precio.class);
        when(dummyPrecio.getPrecio()).thenReturn(500.0);
        publicacion = spy(new Publicacion(
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
                                ));
        inquilino = mock(Usuario.class);
        tarjeta = mock(FormaDePago.class);
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
                publicacion.getPrecio(LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15)).getPrecio());
    }

    @Test
    void getPrecioEnUnPeriodoDefinidoDebeDevolverElPrecioDeDichoPeriodoPorLaCantidadDeDiasTest() {
        int cantDeDias = 15;
        publicacion.definirPeriodo(
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 15),
                new Precio(2000)
        );
        assertEquals(2000 * cantDeDias,
                publicacion.getPrecio(LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15)).getPrecio());
    }

    @Test
    void getPrecioEntreUnPeriodoDefinidoYPeriodoNormalDebeDevolverLaSumaSegunCorrespondeTest() {
        int cantDeDiasEnPeriodo = 10;
        int cantDeDiasFueraDelPeriodo = 7;

        LocalDate diaDesde = LocalDate.of(2024, 12, 1);
        LocalDate diaHasta = LocalDate.of(2024, 12, 15);

        Periodo mockPeriodo = mock(Periodo.class);
        for(LocalDate dia = diaDesde; !dia.isAfter(diaHasta); dia = dia.plusDays(1)){
            when(mockPeriodo.estaDentroDelPeriodo(dia)).thenReturn(true);
            when(mockPeriodo.getPrecio()).thenReturn(new Precio(2000));
        }

        doReturn(mockPeriodo).when(publicacion).definirPeriodo(any(LocalDate.class), any(LocalDate.class), any(Precio.class));

        publicacion.definirPeriodo(
                diaDesde,
                diaHasta,
                new Precio(2000)
        );

        assertEquals(2000 * cantDeDiasEnPeriodo + dummyPrecio.getPrecio() * cantDeDiasFueraDelPeriodo,
                publicacion.getPrecio(LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 22)).getPrecio());
    }

    @Test
    void reservarTest() {
        assertEquals(0, publicacion.getReservas().size());
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        verify(inquilino).agregarReserva(any(Reserva.class));
        assertEquals(1, publicacion.getReservas().size());
    }

    @Test
    void estaReservadaEnFechasTest() {
        assertFalse(publicacion.estaReservadaEnFechas(LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15)));
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        assertTrue(publicacion.estaReservadaEnFechas(LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15)));
    }

    @Test
    void estaReservadaEnFechasAunqueSoloCoincidaUnDiaTest() {
        assertFalse(publicacion.estaReservadaEnFechas(LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15)));
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        assertTrue(publicacion.estaReservadaEnFechas(LocalDate.of(2024, 12, 15), LocalDate.of(2024, 12, 24)));
    }

    @Test
    void cancelarReservaTest() {
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(inquilino).agregarReserva(captor.capture());
        Reserva reserva = captor.getValue();
        publicacion.cancelarReserva(reserva);
        assertTrue(publicacion.getReservas().getFirst().fueCancelada());
    }

    @Test
    void getCantidadDeVecesAlquiladaSiNoFueAlquiladaDebeDarCeroTest() {
        assertEquals(0, publicacion.getCantidadDeVecesAlquilada());
    }

    @Test
    void getCantidadDeVecesAlquiladaSiFueAlquiladaTest() {
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        assertEquals(0, publicacion.getCantidadDeVecesAlquilada());
        publicacion.getReservas().getFirst().aprobarReserva();
        assertEquals(1, publicacion.getCantidadDeVecesAlquilada());
    }

    @Test
    void suscribirNotificacionesDeReservaTest() {
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        verify(listener).notificarReserva("El inmueble " + publicacion.getTipoDeInmueble() +
                " que te interesa, ha sido reservado desde el 1/12/24 hasta el 16/12/24.", publicacion); //El mensaje se manda al reservar, por lo que las fechas se encuentran en ese scope
    }

    @Test
    void suscribirNotificacionesDeBajaDePrecioTest() {
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);

        // publicacion.bajarPrecio() o cuando termina un período (pero es más difícil de detectar)
        verify(listener).notificarBajaDePrecio("No te pierdas esta oferta: Un inmueble " + publicacion.getTipoDeInmueble()
                + " a tan sólo " + publicacion.getPrecio(LocalDate.now(), LocalDate.now()) + " pesos", publicacion);
    }

    @Test
    void suscribirNotificacionesDeCancelacionDeReservaTest() {
        publicacion.reservar(inquilino, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 15), tarjeta);
        Listener listener = mock();
        publicacion.suscribirNotificaciones(listener);
        publicacion.cancelarReserva(publicacion.getReservas().getFirst());
        verify(listener).notificarCancelacionReserva("El/la "+ publicacion.getTipoDeInmueble() + " que te interesa se ha liberado! Corre a reservarlo!", publicacion);
    }
}
