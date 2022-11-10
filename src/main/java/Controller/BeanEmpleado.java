/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Empleado;
import Model.EmpleadoDB;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Estudiante
 */
public class BeanEmpleado implements Serializable{
    private String cedula;
    private String nombre;
    private String apellido;
    private double salarioBase;
    private short horas;
    private String jornada;
    private boolean activo;
    private LinkedList<Empleado> listaEmp = new LinkedList<Empleado>();

    public BeanEmpleado (){
        
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

    public LinkedList<Empleado> getListaEmp() throws SNMPExceptions, SQLException {
      LinkedList<Empleado> lista = new 
                    LinkedList<Empleado>();
        EmpleadoDB eDB = new EmpleadoDB();
        
        lista = eDB.ObtenerTodosEmpleado();
        
        LinkedList resultLista = new LinkedList();
           
        resultLista=lista;       
        return resultLista; 
    }

    public void setListaEmp(LinkedList<Empleado> listaEmp) {
        this.listaEmp = listaEmp;
    }
    
    
    
}
