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
public class PlanillaDB {
    
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    protected LinkedList<Empleado> lista = new 
                    LinkedList<Empleado>();
    
    public void CrearPlanilla(Planilla plan) throws SNMPExceptions{
        // Paso 1 - Crear Planilla
        String strSQL = "";
        int cerrado = 0;
        if(plan.isCerrada()){
            cerrado = 1;
        }
        int planillaID = 0;
        try {
            Planilla planillaSQL = plan;
            
            strSQL =  String.format("INSERT INTO Planilla (fechaInicio, fechaPago, fechaFinal, jornada, turno, cerrada) VALUES ( CONVERT(DATE, '%tD', 1), CONVERT(DATE, '%tD', 1), CONVERT(DATE, '%tD', 1)  , '%s', %d, %d);", 
                    planillaSQL.getFechainicio(), planillaSQL.getFechafinal(), planillaSQL.getFechapago(), planillaSQL.getJornada(), planillaSQL.getTurno(), 0);
            
            accesoDatos.ejecutaSQL(strSQL);
        
            strSQL = "SELECT IDENT_CURRENT ('dbo.Planilla') AS IDPlanilla";
            AccesoDatos accesoDatos2 = new AccesoDatos();
            ResultSet rsPA= accesoDatos2.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                planillaID = rsPA.getInt("IDPlanilla");
            }
            rsPA.close();
            AutomaticEmployee(planillaID, planillaSQL.getJornada());
        
        
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
    public void CerrarPlanilla(int IdPlanilla){
        
    }
    
    public void AutomaticEmployee(int planilla, String Jornada) throws SNMPExceptions{
      try{
        EmpleadoDB eDB = new EmpleadoDB();
        TransaccionDB tDB = new TransaccionDB();
        lista = eDB.ObtenerEmpleadosPorJornada(Jornada);
        for (int i = 0; i < lista.size(); i++) {
            String ced = lista.get(i).getCedula();
            tDB.crearTransaccion(ced, planilla);
        }
      } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }

    public LinkedList<Planilla> obtenerPlanillasActivas() {
        LinkedList<Planilla> listaPlanillas = new LinkedList<>();
        String query = "SELECT planillaID ID, fechaInicio Inicio, fechaFinal Final, fechaPago Pago, jornada Jornada, turno Turno FROM Planilla WHERE cerrada = 0";
        try (
                ResultSet rs = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            while (rs.next()) {
                listaPlanillas.add(
                    new Planilla(
                        rs.getString("ID"),
                        rs.getDate("Inicio"),
                        rs.getDate("Final"),
                        rs.getDate("Pago"),
                        rs.getString("Jornada"),
                        rs.getInt("Turno"),
                        false
                    )
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return listaPlanillas;
        }
    }
    

}
