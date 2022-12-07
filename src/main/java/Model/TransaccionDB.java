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
    
    public void CerrarTransaccion(String pTransaccionID, double SalarioBruto, double SalarioNeto) throws SNMPExceptions, SQLException {
       String strSQL = "";
        try {
            strSQL = "UPDATE dbo.Transaccion SET salarioBruto = " + SalarioBruto + ", salarioNeto = " + SalarioNeto + " WHERE transaccionID = "+ pTransaccionID + ";";
            
            accesoDatos.ejecutaSQL(strSQL);          
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
    
    
    public void crearTransaccion(String empleadoID, int planillaID) throws SNMPExceptions, SQLException {
        String strSQL = "";
        try {
            int TransaccionID = 0;
            double Salario = 0;
            int horas = 0;
            
            strSQL = "INSERT INTO Transaccion (empleadoID, planillaID, salarioBruto, salarioNeto) "
                    + "VALUES ('" + empleadoID + "', '" + planillaID + "', " + 0 + ", " + 0 + ");";
            
            accesoDatos.ejecutaSQL(strSQL);
            strSQL = "SELECT IDENT_CURRENT ('dbo.Transaccion') AS TransaccionID";
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                TransaccionID = rsPA.getInt("TransaccionID");
            }
            strSQL = "SELECT salarioBase, horas FROM Empleado WHERE cedula = '" + empleadoID + "'";
            rsPA= accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                Salario = rsPA.getDouble("salarioBase");
                horas = rsPA.getInt("horas");
            }
            Salario = Salario * horas;
            rsPA.close();
            double salarioBruto = AutomaticCrearPago(TransaccionID, Salario);
            AutomaticCrearDeducciones(TransaccionID, salarioBruto);
            
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        
    }
    
    public void AutomaticCrearDeducciones(int pTransaccionID, double psalarioBruto) throws SNMPExceptions{
      try{

        DeduccionDB dDB = new DeduccionDB();
        lista = dDB.ObtenerDeduccionesAutomaticas();
        DetalleTransaccionDB dtDB = new DetalleTransaccionDB();
        double monto = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            String nombre = lista.get(i).getNombre();
            monto = lista.get(i).getMonto();
            if(lista.get(i).isPorcentual()){
                monto = psalarioBruto * (monto / 100);
            }
            dtDB.InsertarDetalleDeduccion(nombre, pTransaccionID, monto);
        }
      }
       catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }

    
    public double AutomaticCrearPago(int pTransaccionID, double pSalario) throws SNMPExceptions{
      try{

        PagoDB pDB = new PagoDB();
        listaP = pDB.leerPagosAutomaticos();
        DetalleTransaccionDB dtDB = new DetalleTransaccionDB();
        double total = 0;
        
        for (int i = 0; i < listaP.size(); i++) {
            String nombre = listaP.get(i).getNombre();
            double monto = listaP.get(i).getPorcentaje();
            monto = monto/ 100;
            monto = monto * pSalario; 
            total += monto;
            dtDB.InsertarDetallePago(nombre, pTransaccionID, monto);
        }
        return total;
      }
       catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }

}
