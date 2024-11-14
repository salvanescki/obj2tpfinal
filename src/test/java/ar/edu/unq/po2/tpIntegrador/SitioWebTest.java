package ar.edu.unq.po2.tpIntegrador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.po2.tpIntegrador.excepciones.UsuarioYaRegistradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;


public class SitioWebTest {

    private SitioWeb sitio;
    private Usuario usuario;
    private Usuario usuarioDos;

    @BeforeEach
    void setUp() {
        sitio = new SitioWeb();
        usuario = mock(Usuario.class);
        when(usuario.getNombre()).thenReturn("Usuario");
        usuarioDos = mock(Usuario.class);
        when(usuarioDos.getNombre()).thenReturn("Usuario 2");
    }

    @Test
    void estaRegistradoTest() {
        sitio.registrar(usuario);

        assertTrue(sitio.estaRegistrado(usuario));
    }

    @Test
    void registrarUnUsuarioYaRegistradoLanzaExcepcionTest() {
        sitio.registrar(usuario);

        UsuarioYaRegistradoException excepcion = assertThrows(UsuarioYaRegistradoException.class, ()->
            sitio.registrar(usuario)
        );

        assertTrue(excepcion.getMessage().contains("El usuario: " + usuario.getNombre() + " ya fue registrado previamente"));
    }

    @Test
    void getListaDeTiposDeInmueblesTest() {
        assertTrue(sitio.getListaDeTiposDeInmueble().isEmpty());
        TipoDeInmueble tipo1 = mock(TipoDeInmueble.class);
        sitio.agregarTipoDeInmueble(tipo1);
        assertEquals(List.of(tipo1), sitio.getListaDeTiposDeInmueble());

        TipoDeInmueble tipo2 = mock(TipoDeInmueble.class);
        sitio.agregarTipoDeInmueble(tipo2);
        assertEquals(List.of(tipo1, tipo2), sitio.getListaDeTiposDeInmueble());
    }

    @Test
    void getListaDeServiciosTest() {
        assertTrue(sitio.getListaDeServicios().isEmpty());
        Servicio servicio1 = mock(Servicio.class);
        sitio.agregarServicio(servicio1);
        assertEquals(List.of(servicio1), sitio.getListaDeServicios());

        Servicio servicio2 = mock(Servicio.class);
        sitio.agregarServicio(servicio2);
        assertEquals(List.of(servicio1, servicio2), sitio.getListaDeServicios());
    }

    private List<Categoria> agregarUnaCategoriaDeCadaTipo(){
        CategoriaInquilino categoriaInquilino = mock(CategoriaInquilino.class);
        CategoriaPropietario categoriaPropietario = mock(CategoriaPropietario.class);
        CategoriaPublicacion categoriaPublicacion = mock(CategoriaPublicacion.class);

        when(categoriaInquilino.getTipoDeCategoria()).thenReturn("Inquilino");
        when(categoriaPropietario.getTipoDeCategoria()).thenReturn("Propietario");
        when(categoriaPublicacion.getTipoDeCategoria()).thenReturn("Publicacion");

        sitio.agregarCategoria(categoriaInquilino);
        sitio.agregarCategoria(categoriaPropietario);
        sitio.agregarCategoria(categoriaPublicacion);

        return List.of(categoriaInquilino, categoriaPropietario, categoriaPublicacion);
    }

    @Test
    void getListaDeCategoriasTest() {
        assertTrue(sitio.getListaDeServicios().isEmpty());

        List<Categoria> categorias = agregarUnaCategoriaDeCadaTipo();

        assertEquals(List.of(categorias.get(0)), sitio.getListaDeCategorias("Inquilino"));
        assertEquals(List.of(categorias.get(1)), sitio.getListaDeCategorias("Propietario"));
        assertEquals(List.of(categorias.get(2)), sitio.getListaDeCategorias("Publicacion"));
    }

    @Test
    void esCategoriaValidaTest() {
        CategoriaInquilino categoriaInquilino = mock(CategoriaInquilino.class);
        when(categoriaInquilino.getTipoDeCategoria()).thenReturn("Inquilino");

        // Tipo inválido
        assertFalse(sitio.esCategoriaValida(categoriaInquilino, "Tipo inválido"));

        // Categoría Inválida
        assertFalse(sitio.esCategoriaValida(mock(Categoria.class), "Inquilino"));

        List<Categoria> categorias = agregarUnaCategoriaDeCadaTipo();

        // Casos normales
        assertTrue(sitio.esCategoriaValida(categorias.get(0),"Inquilino"));
        assertTrue(sitio.esCategoriaValida(categorias.get(1),"Propietario"));
        assertTrue(sitio.esCategoriaValida(categorias.get(2),"Publicacion"));

        // Casos donde no coincide el tipo con la categoria
        assertFalse(sitio.esCategoriaValida(categorias.get(1),"Inquilino"));
        assertFalse(sitio.esCategoriaValida(categorias.get(2),"Propietario"));
        assertFalse(sitio.esCategoriaValida(categorias.get(0),"Publicacion"));
    }

    @Test
    void darDeAltaInmuebleTest() {
        Publicacion mockPublicacion = mock(Publicacion.class);
        sitio.darDeAltaInmueble(usuario, mockPublicacion);
        verify(usuario, times(1)).agregarPublicacion(mockPublicacion);
    }

    @Test
    void buscarTest() {
        // Acá no mockeeamos la Busueda porque no tiene mucho sentido, ya que hace el trabajo entero
        // Sitio solo le brinda la lista de publicaciones totales

        sitio.registrar(usuario);
        sitio.registrar(usuarioDos);

        LocalDate ahora = LocalDate.now();
        LocalDate enQuinceDias = ahora.plusDays(15);

        Publicacion casaEnQuilmes = mock(Publicacion.class);
        when(casaEnQuilmes.getCiudad()).thenReturn("Quilmes");
        Publicacion dptoEnMdp = mock(Publicacion.class);
        when(dptoEnMdp.getCiudad()).thenReturn("Mar del Plata");
        Publicacion quintaEnQuilmes = mock(Publicacion.class);
        when(quintaEnQuilmes.getCiudad()).thenReturn("Quilmes");

        when(usuario.getInmueblesPublicados()).thenReturn(List.of(casaEnQuilmes));
        when(usuarioDos.getInmueblesPublicados()).thenReturn(List.of(dptoEnMdp, quintaEnQuilmes));

        assertEquals(List.of(casaEnQuilmes, quintaEnQuilmes),sitio.buscar(new Busqueda("Quilmes", ahora, enQuinceDias)));
        assertEquals(List.of(dptoEnMdp),sitio.buscar(new Busqueda("Mar del Plata", ahora, enQuinceDias)));
    }
}
