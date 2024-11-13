package ar.edu.unq.po2.tpIntegrador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BusquedaTest {

    private List<Publicacion> publicaciones;
    private LocalDate ahora;
    private LocalDate enQuinceDias;
    private Publicacion casaEnQuilmes;
    private Publicacion dptoEnMdp;
    private Publicacion quintaEnQuilmes;

    @BeforeEach
    void setUp() {
        publicaciones = new ArrayList<>();
        ahora = LocalDate.now();
        enQuinceDias = ahora.plusDays(15);

        casaEnQuilmes = mock(Publicacion.class);
        when(casaEnQuilmes.getCiudad()).thenReturn("Quilmes");
        dptoEnMdp = mock(Publicacion.class);
        when(dptoEnMdp.getCiudad()).thenReturn("Mar del Plata");
        quintaEnQuilmes = mock(Publicacion.class);
        when(quintaEnQuilmes.getCiudad()).thenReturn("Quilmes");

        when(casaEnQuilmes.getPrecio(any())).thenReturn(new Precio(10000));
        when(dptoEnMdp.getPrecio(any())).thenReturn(new Precio(30000));
        when(quintaEnQuilmes.getPrecio(any())).thenReturn(new Precio(20000));

        publicaciones.addAll(Arrays.asList(casaEnQuilmes, dptoEnMdp, quintaEnQuilmes));
    }

    private Periodo crearPeriodoMockDeFechas(LocalDate fechaInicio, LocalDate fechaFin){
        Periodo periodo = mock(Periodo.class);
        when(periodo.getFechaDesde()).thenReturn(fechaInicio);
        when(periodo.getFechaHasta()).thenReturn(fechaFin);
        return periodo;
    }

    @Test
    void busquedaPorCiudadPublicacionesDisponiblesTest() {
        assertEquals(List.of(casaEnQuilmes, quintaEnQuilmes), new Busqueda("Quilmes", ahora, enQuinceDias).efectuarBusqueda(publicaciones));
        assertEquals(List.of(dptoEnMdp), new Busqueda("Mar del Plata", ahora, enQuinceDias).efectuarBusqueda(publicaciones));
        assertTrue(new Busqueda("No soy ciudad valida", ahora, enQuinceDias).efectuarBusqueda(publicaciones).isEmpty());
        assertTrue(new Busqueda("", ahora, enQuinceDias).efectuarBusqueda(publicaciones).isEmpty());
    }

    @Test
    void busquedaPorCiudadPublicacionesYaReservadasEnFechasNoAparecenTest() {
        when(dptoEnMdp.estaReservadaEnFechas(any())).thenReturn(true);
        when(casaEnQuilmes.estaReservadaEnFechas(any())).thenReturn(true);

        assertTrue(new Busqueda("Mar del Plata", ahora, enQuinceDias).efectuarBusqueda(publicaciones).isEmpty());
        assertEquals(List.of(quintaEnQuilmes), new Busqueda("Quilmes", ahora, enQuinceDias).efectuarBusqueda(publicaciones));
    }

    @Test
    void conPrecioMinimoIgualACeroNoFiltraPublicacionesTest() {
        assertEquals(new Busqueda("Quilmes", ahora, enQuinceDias).efectuarBusqueda(publicaciones),
                     new Busqueda("Quilmes", ahora, enQuinceDias).conPrecioMinimo(new Precio(0)).efectuarBusqueda(publicaciones));
    }

    @Test
    void conPrecioMinimoFiltraPublicacionesTest() {
        assertEquals(List.of(quintaEnQuilmes),
                     new Busqueda("Quilmes", ahora, enQuinceDias).conPrecioMinimo(new Precio(20000)).efectuarBusqueda(publicaciones));
    }

    @Test
    void conPrecioMaximoFiltraPublicacionesTest() {
        assertEquals(List.of(casaEnQuilmes),
                     new Busqueda("Quilmes", ahora, enQuinceDias).conPrecioMaximo(new Precio(15000)).efectuarBusqueda(publicaciones));
    }

    @Test
    void conPrecioMinimoYMaximoIncluyeLosCasosBordeTest() {
        Precio precioEsperado = new Precio(30000);
        assertEquals(List.of(dptoEnMdp),
                     new Busqueda("Mar del Plata", ahora, enQuinceDias)
                                  .conPrecioMinimo(precioEsperado)
                                  .conPrecioMaximo(precioEsperado)
                                  .efectuarBusqueda(publicaciones));
    }

    @Test
    void conPrecioMinimoYMaximoFiltranPublicacionesTest() {
        assertTrue(new Busqueda("Quilmes", ahora, enQuinceDias)
                        .conPrecioMinimo(new Precio(10001))
                        .conPrecioMaximo(new Precio(19999))
                        .efectuarBusqueda(publicaciones)
                        .isEmpty());
    }

    @Test
    void conCantidadDeHuespedesFiltraPublicacionesTest() {
        when(casaEnQuilmes.getCapacidad()).thenReturn(3);
        when(quintaEnQuilmes.getCapacidad()).thenReturn(10);
        assertEquals(List.of(casaEnQuilmes, quintaEnQuilmes),
                     new Busqueda("Quilmes", ahora, enQuinceDias).conCantidadDeHuespedes(3).efectuarBusqueda(publicaciones));
        assertEquals(List.of(quintaEnQuilmes),
                new Busqueda("Quilmes", ahora, enQuinceDias).conCantidadDeHuespedes(5).efectuarBusqueda(publicaciones));
        assertTrue(new Busqueda("Quilmes", ahora, enQuinceDias).conCantidadDeHuespedes(11).efectuarBusqueda(publicaciones).isEmpty());
    }
}
