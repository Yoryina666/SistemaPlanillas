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

/**
 *
 * @author Estudiante
 */
public class TransaccionDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    protected LinkedList<Deduccion> lista = new 
                    LinkedList<Deduccion>();
    protected LinkedList<Pago> listaP = new 
                    LinkedList<Pago>();
    //Transaccion DB existe y Transaccion sola no por que Transaccion es una tabla intermedia QUE NO SE EDITA
    //Se Crea y no se edita, solo se edita el salarioNeto 
    
    public void crearTransaccion(String empleadoID, int planillaID) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            int TransaccionID = 0;
            
            strSQL = "INSERT INTO Transaccion (empleadoID, planillaID, salarioBruto, salarioNeto) "
                    + "VALUES ('" + empleadoID + "', '" + planillaID + "', " + 0 + ", " + 0 + ");";
            
            accesoDatos.ejecutaSQL(strSQL);
            strSQL = "SELECT IDENT_CURRENT ('dbo.Transaccion') AS TransaccionID";
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                TransaccionID = rsPA.getInt("TransaccionID");
            }
            rsPA.close();
            AutomaticCrearPago(TransaccionID);
            AutomaticCrearDeducciones(TransaccionID);
            
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        
    }
    
    public void AutomaticCrearDeducciones(int pTransaccionID) throws SNMPExceptions{
      try{

        DeduccionDB dDB = new DeduccionDB();
        lista = dDB.ObtenerDeduccionesAutomaticas();
        DetalleTransaccionDB dtDB = new DetalleTransaccionDB();
        
        for (int i = 0; i < lista.size(); i++) {
            String nombre = lista.get(i).getNombre();
            dtDB.InsertarDetalleDeduccion(nombre, pTransaccionID);
        }
      }
       catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
    public void AutomaticCrearPago(int pTransaccionID) throws SNMPExceptions{
      try{

        PagoDB pDB = new PagoDB();
        listaP = pDB.leerPagosAutomaticos();
        DetalleTransaccionDB dtDB = new DetalleTransaccionDB();
        
        for (int i = 0; i < listaP.size(); i++) {
            String nombre = listaP.get(i).getNombre();
            dtDB.InsertarDetallePago(nombre, pTransaccionID);
        }
      }
       catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }

}
