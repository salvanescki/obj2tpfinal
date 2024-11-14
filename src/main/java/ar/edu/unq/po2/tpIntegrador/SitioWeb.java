package ar.edu.unq.po2.tpIntegrador;

import ar.edu.unq.po2.tpIntegrador.excepciones.UsuarioYaRegistradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SitioWeb {

    private final List<Usuario> usuariosRegistrados;

    private final List<Servicio> servicios;
    private final List<TipoDeInmueble> tiposDeInmueble;
    private final List<Categoria> categorias;

    public SitioWeb(){
        usuariosRegistrados = new ArrayList<>();
        tiposDeInmueble = new ArrayList<>();
        servicios = new ArrayList<>();
        categorias = new ArrayList<>();
    }

    private void validarUsuario(Usuario usuario) {
        // Acá podría agregarse lógica de validación respecto al nombre, email y/o teléfono del usuario.
        if(estaRegistrado(usuario)){
            throw new UsuarioYaRegistradoException("El usuario: " + usuario.getNombre() + " ya fue registrado previamente");
        }
    }

    public void registrar(Usuario usuario){
        validarUsuario(usuario);
        usuariosRegistrados.add(usuario);
    }

    public boolean estaRegistrado(Usuario usuario){
        return usuariosRegistrados.contains(usuario);
    }

    public List<TipoDeInmueble> getListaDeTiposDeInmueble() {
        return tiposDeInmueble;
    }

    public List<Servicio> getListaDeServicios() {
        return servicios;
    }

    public List<Categoria> getListaDeCategorias(String tipoRankeable) {
        return categorias.stream()
                         .filter(categoria -> categoria.getTipoDeCategoria().equals(tipoRankeable))
                         .toList();
    }

    public void agregarTipoDeInmueble(TipoDeInmueble tipoDeInmueble){
        tiposDeInmueble.add(tipoDeInmueble);
    }

    public void agregarServicio(Servicio servicio){
        servicios.add(servicio);
    }

    public void agregarCategoria(Categoria categoria){
        categorias.add(categoria);
    }

    public boolean esCategoriaValida(Categoria categoria, String tipoRankeable){
        return getListaDeCategorias(tipoRankeable).contains(categoria);
    }

    private void validarPublicacion(Publicacion publicacion){
        // Acá podría agregarse lógica de validación respecto a la publicación y sus parámetros de construcción
    }

    public void darDeAltaInmueble(Propietario propietario, Publicacion publicacion) {
        validarPublicacion(publicacion);
        propietario.agregarPublicacion(publicacion);
    }

    private List<Publicacion> getListaDePublicaciones(){
        return usuariosRegistrados.stream()
                                  .flatMap(u -> u.getInmueblesPublicados().stream())
                                  .collect(Collectors.toList());
    }

    public List<Publicacion> buscar(Busqueda busqueda){
        return busqueda.efectuarBusqueda(getListaDePublicaciones());
    }
}
