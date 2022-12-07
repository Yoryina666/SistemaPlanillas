/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Estudiante
 */
public class Planilla {
    private String planillaID;
    private Date fechainicio;
    private Date fechafinal;
    private Date fechapago;
    private String jornada;
    private int turno;
    private boolean cerrada;

    public Planilla(String planillaID, Date fechainicio, Date fechafinal, Date fechapago, String jornada, int turno, boolean cerrada) {
        this.planillaID = planillaID;
        this.fechainicio = fechainicio;
        this.fechafinal = fechafinal;
        this.fechapago = fechapago;
        this.jornada = jornada;
        this.turno = turno;
        this.cerrada = cerrada;
    }

    public String fechaFormateada(Date fecha) {
        return String.format("%1$tA %1$te, %1$tb %1$tY", fecha);
    }
    
    public String getPlanillaID() {
        return planillaID;
    }

    public void setPlanillaID(String planillaID) {
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
    
}
