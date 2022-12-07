package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class DetalleDB {

    /** La conexión a la base de datos. */
    private final AccesoDatos accesoDatos = new AccesoDatos();
    
    /**
     * Actualiza el detalle.
     * @param detalle Entidad con datos de detalle
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void actualizarDetalle(Detalle detalle) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query;
        query = String.format(
            "UPDATE CategoriaDetalle SET descripcion = '%s', automatico = %d, porcentaje = %.4f WHERE nombre = '%s'",
             detalle.getDescripcion(), detalle.isAutomatico() ? 1 : 0, detalle.getPorcentaje(), detalle.getNombre()
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Crea un nuevo detalle.
     * @param detalle Entidad con datos de detalle
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void insertarDetalle(Detalle detalle) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format(
            "INSERT INTO CategoriaDetalle VALUES ('%s', '%s', %.4f, %d)",
            detalle.getNombre(), detalle.getDescripcion(), detalle.getPorcentaje(), detalle.isAutomatico() ? 1 : 0
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0) throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Consulta todos los detalles de la base de datos.
     * @return {@link java.util.LinkedList} con todos los {@link Model.Detalle}
     * @throws SNMPExceptions
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NamingException 
     */
    public LinkedList<Detalle> leerDetalles() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Detalle> listaDetalles = new LinkedList<>();
        String query = "SELECT nombre Nombre, descripcion, Descripcion, porcentaje Porcentaje, automatico Automatico FROM CategoriaDetalle";
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaDetalles.add(
                    new Detalle(
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Automatico"),
                        rs.getDouble("Porcentaje")
                    )
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return listaDetalles;
        }
    }
    
}
