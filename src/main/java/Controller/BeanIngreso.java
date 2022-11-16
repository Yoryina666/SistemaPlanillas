package Controller;

import DAO.SNMPExceptions;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.Serializable;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class BeanIngreso implements Serializable {
    
    /** Nombre de usuario */
    String nombre;
    
    /** Contraseña secreta */
    String contrasena;
    
    /** Campo para mostrar mensaje de información */
    String mensaje;
    
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
    
    // </editor-fold>
    
    /**
     * Se ejecuta una vez que los datos estén listos. Inicia el proceso de
     * ingreso con las credenciales.
     * @return Página hacia la cuál se debe redirigir al usuario
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public String login() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String pagina = "index.xhtml";
        UsuarioDB uDB = new UsuarioDB();
        Usuario usuario = uDB.login(nombre, contrasena);
        if (usuario == null) {
            mensaje = "Credenciales incorrectas, usuario inexistente o usuario bloqueado";
        } else {
            switch (usuario.getTipo()) { 
                case ADMINISTRADOR:
                    pagina = "MantenimientoUsuario.xhtml";
                    break;
                case PLANILLERO:
                    pagina = "MantenimientoUsuario.xhtml";
                    break;
                case RECURSOS_HUMANOS:
                    pagina = "MantenimientoEmpleado.xhtml";
                    break;
                default:
                    throw new AssertionError("Se obtuvo un entero de tipo inexistente como enumeral.");
            }
            mensaje = "";
        }
        return pagina;
    }
    
}
