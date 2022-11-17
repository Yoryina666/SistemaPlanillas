/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.SNMPExceptions;
import java.sql.SQLException;
import DAO.AccesoDatos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import javax.naming.NamingException;

/**
 *
 * @author Estudiante
 */
public class EmpleadoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    
    private LinkedList<Empleado> listaEmpleado = new LinkedList<Empleado>();
    
    public void InsertarEmpleado(Empleado pEmpleado) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            Empleado empleadoSQL = pEmpleado;
            
            strSQL = "INSERT INTO Empleado (cedula, nombre, apellido, salarioBase, horas, jornada, activo) VALUES " + 
                    "('" + empleadoSQL.getCedula() + "', '" + empleadoSQL.getNombre() + "', '" + empleadoSQL.getApellido() + 
                    "', " + empleadoSQL.getSalarioBase() + ", " + empleadoSQL.getHoras() +  ", '" + empleadoSQL.getJornada() + "', 1)";
            
            accesoDatos.ejecutaSQL(strSQL);
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
     public void ActualizarEmpleado(Empleado pEmpleado) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            Empleado empleadoSQL = pEmpleado;
            
            strSQL = "UPDATE Empleado SET " + 
                    "nombre = '" + empleadoSQL.getNombre() + "', apellido = '" + empleadoSQL.getApellido() + 
                    "', salarioBase = " + empleadoSQL.getSalarioBase() + ", horas = " + empleadoSQL.getHoras() +  ", jornada = '" + empleadoSQL.getJornada() + 
                    "' WHERE cedula = '" + empleadoSQL.getCedula() + "'";
            
            accesoDatos.ejecutaSQL(strSQL);
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
    public void CambiarEstadoUsuario(String cedula, boolean estado) throws SNMPExceptions{
         String strSQL = "";
         int Estado;
         if(estado){
             Estado = 1; //Reactivar Usuario 0 -> 1
         }else {
             Estado = 0; //Desactivar Usuario 1 -> 0
         }
        try {
                        
            strSQL = "UPDATE Empleado SET activo = " + Estado + " WHERE cedula = '"+ cedula +"'";
            
            accesoDatos.ejecutaSQL(strSQL);
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
 
    
    public LinkedList<Empleado> ObtenerTodosEmpleado() throws SNMPExceptions, SQLException{
        String select= "";
        
        try{
            //Se intancia la clase de acceso a datos
            accesoDatos= new AccesoDatos();
            
            //Se vacía el Dataset
            listaEmpleado = new LinkedList<Empleado>();
            
            //Se crea la sentencia de Busqueda
            select=
                    "SELECT [cedula]\n" +
                    "      ,[nombre]\n" +
                    "      ,[apellido]\n" +
                    "      ,[salarioBase]\n" +
                    "      ,[horas]\n" +
                    "      ,[jornada]\n" +
                    "      ,[activo]\n" +
                    "  FROM [ProyectoG5].[dbo].[Empleado]";
            //se ejecuta la sentencia sql
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(select);
            //se llama el array con los Empleados
            while(rsPA.next()){
                
                String cCedula = rsPA.getString("cedula");
                String cNombre = rsPA.getString("nombre");
                String cApellido = rsPA.getString("apellido");
                double cSalario = rsPA.getDouble("salarioBase");
                short cHoras = rsPA.getShort("horas");
                String cJornada = rsPA.getString("jornada");
                boolean cActivo = rsPA.getBoolean("activo");
                
                //se construye el objeto.
                Empleado emp = new Empleado(cCedula, cNombre, cApellido, cSalario, cHoras, cJornada, cActivo);
                
                listaEmpleado.add(emp);
            }
            rsPA.close();//se cierra el ResultSeat.
            
        }catch(SQLException e){
            throw new SNMPExceptions (SNMPExceptions.SQL_EXCEPTION,
                                     e.getMessage(),e.getErrorCode());
        }catch(SNMPExceptions | ClassNotFoundException | NamingException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,e.getMessage());
        }finally{
            
        }
        return listaEmpleado;
    }
    
    public Empleado ObtenerEmpleado(String cedula) throws SNMPExceptions, SQLException{
        String select= "";
        Empleado emp = new Empleado();
        try{
            //Se intancia la clase de acceso a datos
            accesoDatos= new AccesoDatos();
            
            //Se vacía el Dataset
            listaEmpleado = new LinkedList<Empleado>();
            
            //Se crea la sentencia de Busqueda
            select=
                    "SELECT [cedula]\n" +
                    "      ,[nombre]\n" +
                    "      ,[apellido]\n" +
                    "      ,[salarioBase]\n" +
                    "      ,[horas]\n" +
                    "      ,[jornada]\n" +
                    "      ,[activo]\n" +
                    "  FROM [ProyectoG5].[dbo].[Empleado] WHERE [cedula] = " + cedula;
            //se ejecuta la sentencia sql
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(select);
            //se llama el array con los Empleados
            while(rsPA.next()){
                
                String cCedula = rsPA.getString("cedula");
                String cNombre = rsPA.getString("nombre");
                String cApellido = rsPA.getString("apellido");
                double cSalario = rsPA.getDouble("salarioBase");
                short cHoras = rsPA.getShort("horas");
                String cJornada = rsPA.getString("jornada");
                boolean cActivo = rsPA.getBoolean("activo");
                
                //se construye el objeto.
                emp = new Empleado(cCedula, cNombre, cApellido, cSalario, cHoras, cJornada, cActivo);
            }
            rsPA.close();//se cierra el ResultSeat.
            
        }catch(SQLException e){
            throw new SNMPExceptions (SNMPExceptions.SQL_EXCEPTION,
                                     e.getMessage(),e.getErrorCode());
        }catch(SNMPExceptions | ClassNotFoundException | NamingException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,e.getMessage());
        }finally{
            
        }
        return emp;
    }
}
