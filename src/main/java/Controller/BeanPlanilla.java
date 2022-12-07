/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Planilla;
import Model.PlanillaDB;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class BeanPlanilla {
    private int planillaID;
    private Date fechainicio;
    private Date fechafinal;
    private Date fechapago;
    private String jornada;
    private int turno;
    private boolean cerrada;

    public int getPlanillaID() {
        return planillaID;
    }

    public void setPlanillaID(int planillaID) {
        this.planillaID = planillaID;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public boolean isCerrada() {
        return cerrada;
    }

    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }
    
    //
    public void AutoPlanilla () throws SNMPExceptions, SQLException {
            Planilla plan = new Planilla(0, this.fechainicio, this.fechafinal, this.fechapago, this.jornada, this.turno, false);
            
            PlanillaDB pDB = new PlanillaDB();
            pDB.CrearPlanilla(plan);
    }    
    
        //TO-DO
        //Search Employees based upon TipoJornada (on Empleado DB)
        //Create 1 Transaction Header per Employee and Planilla (Mantenimiento Planillas)
        //salarioButo gets fill in salarioBaseXHoras
        //SalarioNeto gets fill in SalarioBruto
        //Call in Automatic Detalle - CCSS
        //Function UpdateNeto() it's called after every mod to Detalle
        
    
}
