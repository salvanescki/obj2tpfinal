package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    void setUp() {
        dummyPropietario = mock(Usuario.class);
        dummyTipoDeInmueble = mock(TipoDeInmueble.class);
        dummyFoto = mock(Foto.class);
        dummyFormaDePago = mock(FormaDePago.class);
        dummyServicio = mock(Servicio.class);
        dummyPrecio = mock(Precio.class);
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
/*
    @Test
    void getPrecioEnElCasoNormalDebeDevolverElPrecioBaseTest() {
        Periodo periodoNormal = mock();
        when(periodoNormal.)
        assertEquals(dummyPrecio, publicacion.getPrecio());
    }

 */
}
