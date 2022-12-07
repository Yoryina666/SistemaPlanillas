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
import java.util.Date;
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
    private LinkedList<Planilla> listaplanilla = new 
                    LinkedList<Planilla>();
    
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
    
    public void CerrarPlanilla(String pPlanillaID) throws SNMPExceptions{
        // Paso 1 - Crear Planilla
        String strSQL = "";
        double SalarioBruto = 0;
        double SalarioNeto = 0;
        String TransaccionID = "";
        try {
            
            strSQL =  "SELECT TransaccionID FROM Transaccion WHERE planillaID = " + pPlanillaID; 
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                TransaccionID = rsPA.getString("TransaccionID");
            }
            rsPA.close();
            // Seleccion de Pagos
            strSQL =  "SELECT SUM(monto) as 'TotalPago' FROM DetalleTransaccion WHERE transaccionID = " + TransaccionID + " AND categoriaDeduccionID IS NULL;";
            rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                SalarioBruto = rsPA.getDouble("TotalPago");
            }
            strSQL =  "SELECT SUM(monto) as 'TotalDeduccion' FROM DetalleTransaccion WHERE transaccionID = " + TransaccionID + " AND categoriaPagoID IS NULL;";
            rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            while(rsPA.next()){
                SalarioNeto = rsPA.getDouble("TotalDeduccion");
            }
            SalarioNeto = SalarioBruto - SalarioNeto;
            TransaccionDB tDB = new TransaccionDB();
            tDB.CerrarTransaccion(TransaccionID, SalarioBruto, SalarioNeto);
            strSQL = "UPDATE Planilla SET cerrada = 1 WHERE planillaID = " + pPlanillaID + ";";
            
            accesoDatos.ejecutaSQL(strSQL);   
        
         } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
    }
    
    public LinkedList<Planilla> ObtenerPlanillas() throws SNMPExceptions, SQLException{
        String select= "";
        
        try{
            //Se intancia la clase de acceso a datos
            accesoDatos= new AccesoDatos();
            
            //Se vacía el Dataset
            listaplanilla = new LinkedList<Planilla>();
            
            //Se crea la sentencia de Busqueda
            select=
                    "SELECT [planillaID]\n" +
                    "      ,[fechaInicio]\n" +
                    "      ,[fechaFinal]\n" +
                    "      ,[fechaPago]\n" +
                    "      ,[jornada]\n" +
                    "      ,[turno]\n" +
                    "      ,[cerrada]\n" +
                    "  FROM [ProyectoG5].[dbo].[Planilla]";
            //se ejecuta la sentencia sql
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(select);
            //se llama el array con los Empleados
            while(rsPA.next()){
                
                String planillaID = rsPA.getString("planillaID");
                Date fechainicio = rsPA.getDate("fechaInicio");
                Date fechafinal = rsPA.getDate("fechaFinal");
                Date fechapago = rsPA.getDate("fechaPago");
                String jornada = rsPA.getString("jornada");
                int turno = rsPA.getInt("turno");
                boolean cerrada = rsPA.getBoolean("cerrada");
                
                //se construye el objeto.
                Planilla plan = new Planilla(planillaID, fechainicio, fechafinal, fechapago, jornada, turno, cerrada);
                
                listaplanilla.add(plan);
            }
            rsPA.close();//se cierra el ResultSeat.
            
        }catch(SQLException e){
            throw new SNMPExceptions (SNMPExceptions.SQL_EXCEPTION,
                                     e.getMessage(),e.getErrorCode());
        }catch(SNMPExceptions | ClassNotFoundException | NamingException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,e.getMessage());
        }finally{
            
        }
        return listaplanilla;
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
