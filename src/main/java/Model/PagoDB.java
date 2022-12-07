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
public class PagoDB {

    /** La conexión a la base de datos. */
    private final AccesoDatos accesoDatos = new AccesoDatos();
    
    /**
     * Actualiza el pago.
     * @param pago Entidad con datos de pago
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void actualizarPago(Pago pago) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query;
        query = String.format(
            "UPDATE CategoriaPago SET descripcion = '%s', automatico = %d, porcentaje = %.4f WHERE nombre = '%s'",
             pago.getDescripcion(), pago.isAutomatico() ? 1 : 0, pago.getPorcentaje(), pago.getNombre()
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Crea un nuevo pago.
     * @param pago Entidad con datos de pago
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void insertarPago(Pago pago) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format(
            "INSERT INTO CategoriaPago VALUES ('%s', '%s', %.4f, %d)",
            pago.getNombre(), pago.getDescripcion(), pago.getPorcentaje(), pago.isAutomatico() ? 1 : 0
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0) throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Consulta todos los pagos de la base de datos.
     * @return {@link java.util.LinkedList} con todos los {@link Model.Pago}
     * @throws SNMPExceptions
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NamingException 
     */
    public LinkedList<Pago> leerPagos() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Pago> listaPagos = new LinkedList<>();
        String query = "SELECT nombre Nombre, descripcion, Descripcion, porcentaje Porcentaje, automatico Automatico FROM CategoriaPago";
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaPagos.add(
                    new Pago(
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
            return listaPagos;
        }
    }
     public LinkedList<Pago> leerPagosAutomaticos() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Pago> listaPagos = new LinkedList<>();
        String query = "SELECT nombre Nombre, descripcion, Descripcion, porcentaje Porcentaje, automatico Automatico FROM CategoriaPago WHERE Automatico = 1";
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaPagos.add(
                    new Pago(
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
            return listaPagos;
        }
    }
    
}
