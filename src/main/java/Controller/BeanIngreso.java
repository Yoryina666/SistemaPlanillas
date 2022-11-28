package Controller;

import DAO.SNMPExceptions;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class BeanIngreso implements Serializable {
    
    /** Nombre de usuario. */
    String nombre;
    
    /** Contraseña secreta. */
    String contrasena;
    
    /** Campo para mostrar mensaje de información. */
    String mensaje;
    
    /** Usuario logeado en el sistema. */
    Usuario usuario;
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    
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

    public String getMensaje() {
        return mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // </editor-fold>
    
    /**
     * Define si se le permite ir al Mantenimiento de Empleados.
     * @return Booleano indicando el permiso
     */
    public boolean empleadosAllowed() {
        if (usuario == null) return true; //Esto solo sucede si estamos en el IDE
        switch (usuario.getTipo()) {
            case ADMINISTRADOR:
            case RECURSOS_HUMANOS:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Define si se le permite ir al Mantenimiento de Usuarios.
     * @return Booleano indicando el permiso
     */
    public boolean usuariosAllowed() {
        if (usuario == null) return true; //Esto solo sucede si estamos en el IDE
        switch (usuario.getTipo()) {
            case ADMINISTRADOR:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Define si se le permite ir al Sistema de Planillas.
     * @return Booleano indicando el permiso
     */
    public boolean planillasAllowed() {
        if (usuario == null) return true; //Esto solo sucede si estamos en el IDE
        switch (usuario.getTipo()) {
            case ADMINISTRADOR:
            case PLANILLERO:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Inicia el proceso de ingreso con las credenciales.Se ejecuta una vez que los datos estén listos.
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     * @throws java.io.IOException 
     */
    public void login() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException, IOException {
        usuario = (new UsuarioDB()).login(nombre, contrasena);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getApplicationMap().put("usuario", usuario);
        mensaje = usuario == null ? "Credenciales incorrectas, usuario inexistente o usuario bloqueado" : "";
        context.redirect(usuario == null ? "index.xhtml" : "Bienvenida.xhtml");
        context.responseFlushBuffer();
    }
    
}
