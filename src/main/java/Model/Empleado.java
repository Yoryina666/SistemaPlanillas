/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author Estudiante
 */
public class Empleado {
    private String cedula;
    private String nombre;
    private String apellido;
    private double salarioBase;
    private short horas;
    private String jornada;
    private boolean activo;

    public Empleado() {
    }

    
    
    public Empleado(String cedula, String nombre, String apellido, double salarioBase, short horas, String jornada, boolean activo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioBase = salarioBase;
        this.horas = horas;
        this.jornada = jornada;
        this.activo = activo;
    }

    
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public short getHoras() {
        return horas;
    }

    public void setHoras(short horas) {
        this.horas = horas;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public String getActivoParsed(){
        if(activo){
        return    "Activo";
        }else{
        return    "Inactivo";
        }
    }
     public String getActivoText(){
        if(activo){
        return    "Desactivar";
        }else{
        return    "Reactivar";
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", nombre, apellido, cedula);
    }
     
     
     
}
