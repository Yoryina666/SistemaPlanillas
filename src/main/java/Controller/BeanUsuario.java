package Controller;

import DAO.SNMPExceptions;
import Model.TipoUsuario;
import Model.Usuario;
import Model.UsuarioDB;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class BeanUsuario {
    
    /** Nombre del usuario. */
    String nombre;
    
    /** Clave secreta. */
    String contrasena;
    
    /** Tipo de usuario del enum {@link Model.TipoUsuario}. */
    TipoUsuario tipo;
    
    /** Fecha en la cuál la contraseña debe ser cambiada. */
    Date vigenciaM;
    
    /** Campo para envío de enumerales {@link Model.TipoUsuario}. */
    TipoUsuario[] tiposUsuario;

    /** Lista con los usuarios del sistema. */
    LinkedList<Usuario> listaUsuarios = new LinkedList<>();

    /** Indicador si está editando o creando. */
    boolean modoEdicion;
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">

    public LinkedList<Usuario> getListaUsuarios()  throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        listaUsuarios = (new UsuarioDB()).leerUsuarios();
        return listaUsuarios;
    }

    public void setListaUsuarios(LinkedList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return "";
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public Date getVigenciaM() {
        return vigenciaM;
    }

    public TipoUsuario[] getTiposUsuario() {
        tiposUsuario = TipoUsuario.values();
        return tiposUsuario;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public void setVigenciaM(Date vigenciaM) {
        this.vigenciaM = vigenciaM;
    }
    
    public boolean isModoEdicion() {
        return modoEdicion;
    }

    // </editor-fold>

    /** Crea el estado por defecto y la vigencia de hoy en 3 meses. */
    public BeanUsuario() {
        this.vigenciaM = Date
            .from(LocalDateTime.now().plusMonths(3)
            .toInstant(
                ZoneId.systemDefault().getRules().getOffset(Instant.now())
            )
        );
    }
    
    public void cancelar() {
        modoEdicion = false;
        nombre = "";
        tipo = TipoUsuario.ADMINISTRADOR;
        vigenciaM = this.vigenciaM = Date
            .from(LocalDateTime.now().plusMonths(3)
            .toInstant(
                ZoneId.systemDefault().getRules().getOffset(Instant.now())
            )
        );
    }
    
    public void editarUsuario(Usuario usuario) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        modoEdicion = true;
        nombre = usuario.getNombre();
        tipo = usuario.getTipo();
        vigenciaM = usuario.getVigenciaM();
    }
    
    public void crearUsuario() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        UsuarioDB db = new UsuarioDB();
        Usuario usuario = new Usuario(
            nombre, tipo, vigenciaM, true
        );
        if (modoEdicion) {
            if (contrasena.isEmpty()) db.actualizarUsuario(usuario);
            else db.actualizarUsuario(usuario, contrasena);
        } else {
            db.insertarUsuario(usuario, contrasena);
        }
        cancelar();
    }
    
    public void borrarUsuario(String nombre, boolean estado) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        (new UsuarioDB()).borrarUsuario(nombre, estado);
    }
    
}
