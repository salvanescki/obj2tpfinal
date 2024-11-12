package ar.edu.unq.po2.tpIntegrador;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Test
    void getTiempoDeAntiguedadTest() {

        LocalDate fechaCreacion = LocalDate.of(2024, 1, 1);

        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fechaCreacion);
            Usuario usuarioEjemplo = new Usuario("Usuario ejemplo", "usuarioEjemplo@gmail.com", "011-4545-0203");
            assertEquals("Recien creado", usuarioEjemplo.getTiempoDeAntiguedad());

            // Simulo que pasan 5 días
            LocalDate fechaPosterior = fechaCreacion.plusDays(5);
            mockedLocalDate.when(LocalDate::now).thenReturn(fechaPosterior);

            assertEquals("5 días", usuario.getTiempoDeAntiguedad());

            // Simulo que pasan 2 meses
            LocalDate fechaFutura = fechaPosterior.plusMonths(2);
            mockedLocalDate.when(LocalDate::now).thenReturn(fechaFutura);

            assertEquals("2 meses, 5 días", usuario.getTiempoDeAntiguedad());

            // Simulo que pasa 1 año

            LocalDate fechaLejana = fechaFutura.plusYears(1);
            mockedLocalDate.when(LocalDate::now).thenReturn(fechaLejana);

            assertEquals("1 año, 2 meses, 5 días", usuario.getTiempoDeAntiguedad());
        }
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
        }).when(publicacion).reservar(inquilino, fechaDesde, fechaHasta, any());
        when(reserva.getFechaDesde()).thenReturn(fechaDesde);
        when(reserva.getFechaHasta()).thenReturn(fechaHasta);
        when(reserva.estaAprobada()).thenReturn(false);
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
        when(dptoCiudadUno.getCiudad()).thenReturn(ciudadUno);

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
}
