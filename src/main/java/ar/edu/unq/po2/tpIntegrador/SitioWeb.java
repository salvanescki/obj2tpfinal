package ar.edu.unq.po2.tpIntegrador;

import java.util.ArrayList;
import java.util.List;

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

    }

    public void darDeAltaInmueble(Propietario propietario, Publicacion publicacion) {
        validarPublicacion(publicacion);
        propietario.agregarPublicacion(publicacion);
    }
}
