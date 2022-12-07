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
     * Crea un nuevo detalle.
     * @param detalle Entidad con datos de detalle
     * @param planilla ID de la planilla a la que pertenece
     * @param empleado ID del empleado al cual se le agrega
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void insertarDetalle(Detalle detalle, String planilla, String empleado) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format(
            "INSERT INTO DetalleTransaccion (transaccionID, categoriaPagoID, categoriaDeduccionID, monto) VALUES ((SELECT T.transaccionID FROM Transaccion T INNER JOIN Empleado E ON T.empleadoID = '%s' INNER JOIN Planilla P ON T.planillaID = '%s'), %s, %s, %f)",
            empleado, planilla, detalle.getMonto() > 0 ? String.format("'%s'", detalle.getCategoria()) : "NULL", detalle.getMonto() <= 0 ? String.format("'%s'", detalle.getCategoria()) : "NULL", detalle.getMonto()
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0) throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Consulta todos los detalles de la base de datos.
     * @param planilla ID de la planilla a la que pertenece
     * @param empleado ID del empleado al cual se le agregó
     * @return {@link java.util.LinkedList} con todos los {@link Model.Detalle}
     * @throws SNMPExceptions
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NamingException 
     */
    public LinkedList<Detalle> leerDetalles(String planilla, String empleado) throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Detalle> listaDetalles = new LinkedList<>();
        String query = String.format(
            "SELECT detalleID ID, categoriaPagoID PagoID, categoriaDeduccionID DeduccionID, monto Monto FROM DetalleTransaccion WHERE transaccionID = (SELECT T.transaccionID FROM Transaccion T INNER JOIN Empleado E ON T.empleadoID = '%s' INNER JOIN Planilla P ON T.planillaID = '%s')",
            empleado, planilla
        );
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                String categoria = rs.getString("PagoID");
                if (rs.wasNull()) categoria = rs.getString("deduccionID");
                listaDetalles.add(
                    new Detalle(
                        rs.getString("ID"),
                        categoria,
                        rs.getDouble("Monto")
                    )
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return listaDetalles;
        }
    }
    
    /**
     * Borra un detalle.
     * @param detalle Entidad con datos de detalle
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void borrarDetalle(Detalle detalle) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format(
            "DELETE FROM DetalleTransaccion WHERE detalleID = '%s'", detalle.getIdDetalle()
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0) throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
}
