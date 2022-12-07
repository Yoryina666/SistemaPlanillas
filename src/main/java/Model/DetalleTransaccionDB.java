/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Estudiante
 */
public class DetalleTransaccionDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    
    public void InsertarDetalleDeduccion(String pDeduccion, int TransaccionID, double monto) throws SNMPExceptions, SQLException {
        String strSQL = "";
        
        try {
            
            
            strSQL = "INSERT INTO DetalleTransaccion(transaccionID, categoriaPagoID, categoriaDeduccionID, monto) VALUES (" + TransaccionID + ", NULL, '" + pDeduccion + "', " + monto + ")";
            
            accesoDatos.ejecutaSQL(strSQL);
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }

    public void InsertarDetallePago(String pPago, int TransaccionID, double monto) throws SNMPExceptions, SQLException {
        String strSQL = "";
        
        try {
            
            
            strSQL = "INSERT INTO DetalleTransaccion(transaccionID, categoriaPagoID, categoriaDeduccionID, monto) VALUES (" + TransaccionID + ", '" + pPago + "', NULL, " + monto + ")";
            
            accesoDatos.ejecutaSQL(strSQL);
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
}
