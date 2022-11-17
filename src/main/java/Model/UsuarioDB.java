package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.ResultSet;
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
public class UsuarioDB {

    /** La conexión a la base de datos. */
    private final AccesoDatos accesoDatos = new AccesoDatos();
    
    /** Hoy en 3 meses, valor usado por defecto. */
    private final Date fechaComparacion;
    
    /** Crea la fecha de comparación autónomamente. */
    public UsuarioDB() {
        fechaComparacion = Date
            .from(LocalDateTime.now().plusMonths(3)
            .toInstant(
                ZoneId.systemDefault().getRules().getOffset(Instant.now())
            )
        );
    }
    
    /**
     * Borra un usuario, solo recibe el nombre del mismo.
     * @param nombre El nombre del usuario a borrar/re-activar
     * @param estado El nuevo estado a cambiar
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void borrarUsuario(String nombre, boolean estado) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format("UPDATE Usuario SET activo = %d WHERE nombre = '%s'", estado ? 1 : 0, nombre);
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Actualiza el usuario.
     * No permite actualizar la contraseña.
     * @param usuario Entidad con datos de usuario
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void actualizarUsuario(Usuario usuario) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        actualizarUsuario(usuario, null);
    }
    
    /**
     * Actualiza el usuario y la contraseña.
     * Utilizar {@link #actualizarUsuario(Model.Usuario)}
     * si desea no actualizar la contraseña
     * @param usuario Entidad con datos de usuario
     * @param contrasena Nueva clave secreta, nulo si no desea actualizarla.
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void actualizarUsuario(Usuario usuario, String contrasena) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query;
        if (contrasena != null) {
            query = String.format(
                "UPDATE Usuario SET contrasena = dbo.ENCRIPTAR_PASS('%s'), tipo = %d, vigenciaMaxima = CONVERT(DATE, '%tD', 1), activo = %d WHERE nombre = '%s'",
                contrasena, usuario.getTipo().ordinal(), usuario.getVigenciaM(), usuario.isEstado() ? 1 : 0, usuario.getNombre()
            );
        } else {
            query = String.format(
                "UPDATE Usuario SET tipo = %d, vigenciaMaxima = CONVERT(DATE, '%tD', 1), activo = %d WHERE nombre = '%s'",
                usuario.getTipo().ordinal(), usuario.getVigenciaM(), usuario.isEstado() ? 1 : 0, usuario.getNombre()
            );
        }
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Crea un nuevo usuario y lo actualiza si no contiene campos por defecto.
     * @param usuario Entidad con datos de usuario
     * @param contrasena La contraseña inicial
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void insertarUsuario(Usuario usuario, String contrasena) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format("EXEC dbo.CREATE_USER '%s', '%s', %d", usuario.getNombre(), contrasena, usuario.getTipo().ordinal());
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
            else if (usuario.getVigenciaM().compareTo(fechaComparacion) != 0) {
                (new UsuarioDB()).actualizarUsuario(usuario);
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Consulta todos los usuarios de la base de datos.
     * @return {@link java.util.LinkedList} con todos los {@link Model.Usuario}
     * @throws SNMPExceptions
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NamingException 
     */
    public LinkedList<Usuario> leerUsuarios() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Usuario> listaUsuarios = new LinkedList<>();
        String query = String.format("SELECT nombre Nombre, tipo Tipo, vigenciaMaxima Vigencia, activo Estado FROM Usuario");
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaUsuarios.add(
                    new Usuario(
                        rs.getString("Nombre"),
                        TipoUsuario.values()[rs.getInt("Tipo")],
                        rs.getDate("Vigencia"),
                        rs.getBoolean("Estado")
                    )
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return listaUsuarios;
        }
    }
    
    /**
     * Consulta si el usuario existe para ese nombre y contraseña.
     * Recibe los datos si existe y está activo.
     * @param nombre El nombre de usuario
     * @param contrasena La clave secreta que no será devuelta si es correcta
     * @return Página hacia la cuál se debe redirigir al usuario
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public Usuario login(String nombre, String contrasena) throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        Usuario usuario = null;
        String query = String.format("SELECT * FROM dbo.TRY_LOGIN('%s', '%s')", nombre, contrasena);
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getString("Nombre"),
                    TipoUsuario.values()[rs.getInt("Tipo")],
                    rs.getDate("Vigencia"),
                    rs.getBoolean("Activo")
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return usuario;
        }
    }
    
}
