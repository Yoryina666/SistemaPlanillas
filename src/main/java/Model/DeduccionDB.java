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
public class DeduccionDB {

    /** La conexión a la base de datos. */
    private final AccesoDatos accesoDatos = new AccesoDatos();
    
    /**
     * Actualiza la deduccion.
     * @param deduccion Entidad con datos de deduccion
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void actualizarDeduccion(Deduccion deduccion) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query;
        query = String.format(
            "UPDATE CategoriaDeduccion SET descripcion = '%s', automatico = %d, monto = %.4f, encargado = '%s', esPorcentual = %d WHERE nombre = '%s'",
             deduccion.getDescripcion(), deduccion.isAutomatico() ? 1 : 0, deduccion.getMonto(), deduccion.getEncargado(), deduccion.isPorcentual() ? 1 : 0, deduccion.getNombre()
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0)
                throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Crea una nueva deduccion.
     * @param deduccion Entidad con datos de deduccion
     * @throws SNMPExceptions
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NamingException 
     */
    public void insertarDeduccion(Deduccion deduccion) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String query = String.format(
            "INSERT INTO CategoriaDeduccion VALUES ('%s', '%s', '%s', %.4f, %d, %d)",
            deduccion.getNombre(), deduccion.getDescripcion(), deduccion.getEncargado(), deduccion.getMonto(), deduccion.isPorcentual() ? 1 : 0, deduccion.isAutomatico() ? 1 : 0
        );
        try {
            if (accesoDatos.ejecutaSQL(query) == 0) throw new SQLException("La operación falló por motivos desconocidos");
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        }
    }
    
    /**
     * Consulta todas las deducciones de la base de datos.
     * @return {@link java.util.LinkedList} con todos los {@link Model.Deduccion}
     * @throws SNMPExceptions
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NamingException 
     */
    public LinkedList<Deduccion> leerDeducciones() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        LinkedList<Deduccion> listaDeducciones = new LinkedList<>();
        String query = "SELECT nombre Nombre, descripcion, Descripcion, encargado Encargado, monto Monto, esPorcentual Porcentual, automatico Automatico FROM CategoriaDeduccion";
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaDeducciones.add(
                    new Deduccion(
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Automatico"),
                        rs.getDouble("Monto"),
                        rs.getString("Encargado"),
                        rs.getBoolean("Porcentual")
                    )
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return listaDeducciones;
        }
    }
    
}
