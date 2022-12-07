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
     public LinkedList<Deduccion> ObtenerDeduccionesAutomaticas() throws SNMPExceptions, SQLException{
        String select= "";
        LinkedList<Deduccion> listaAutomatica = new LinkedList<Deduccion>();
        
        try{
            //Se intancia la clase de acceso a datos
           
            
            //Se vacía el Dataset
            listaAutomatica = new LinkedList<Deduccion>();
            
            //Se crea la sentencia de Busqueda
            select= "SELECT [nombre]\n" +
                    "      ,[descripcion]\n" +
                    "      ,[encargado]\n" +
                    "      ,[monto]\n" +
                    "      ,[esPorcentual]\n" +
                    "      ,[automatico]\n" +
                    "  FROM [ProyectoG5].[dbo].[CategoriaDeduccion] WHERE [automatico] = 1;";
            //se ejecuta la sentencia sql
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(select);
            //se llama el array con los Empleados
            while(rsPA.next()){
                String nombre = rsPA.getString("nombre");
                String despcripcion = rsPA.getString("descripcion");
                String encargado = rsPA.getString("encargado");
                double monto = rsPA.getDouble("monto");
                boolean esPorcentual= rsPA.getBoolean("esPorcentual");
                Deduccion ded = new Deduccion(nombre, despcripcion, true, monto , encargado, esPorcentual);
                listaAutomatica.add(ded);
            }
            rsPA.close();//se cierra el ResultSeat.
            
        }catch(SQLException e){
            throw new SNMPExceptions (SNMPExceptions.SQL_EXCEPTION,
                                     e.getMessage(),e.getErrorCode());
        }catch(SNMPExceptions | ClassNotFoundException | NamingException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,e.getMessage());
        }finally{
            
        }
        return listaAutomatica;
    }
}
