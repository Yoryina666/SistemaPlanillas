/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.naming.NamingException;

/**
 *
 * @author Estudiante
 */
public class DeduccionDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    
    public LinkedList<Deduccion> ObtenerDeduccionesAutomaticas() throws SNMPExceptions, SQLException{
        String select= "";
        LinkedList<Deduccion> listaAutomatica = new LinkedList<Deduccion>();
        
        try{
            //Se intancia la clase de acceso a datos
            accesoDatos= new AccesoDatos();
            
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
                float monto = rsPA.getFloat("monto");
                boolean esPorcentual= rsPA.getBoolean("esPorcentual");
                Deduccion ded = new Deduccion(nombre, despcripcion, encargado, monto, esPorcentual, true);
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
