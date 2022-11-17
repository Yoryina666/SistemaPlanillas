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
    private boolean editable;
    private String mensaje;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    // Method - invoke Update Model -> Load Values Into Create -> Blocks Non-Editable -> Change Button Function.
    public void invokeUpdateModel(String pcedula) throws SNMPExceptions, SQLException {
        setEditable(true);
        EmpleadoDB eDB = new EmpleadoDB();
        Empleado emp = new Empleado();
        emp = eDB.ObtenerEmpleado(pcedula);
        cedula = emp.getCedula();
        nombre = emp.getNombre();
        apellido = emp.getCedula();
        salarioBase = emp.getSalarioBase();
        horas = emp.getHoras();
        jornada = emp.getJornada();
        activo = emp.isActivo();
    }
    // Method - invoke Update Model -> Load Values Into Create -> Blocks Non-Editable -> Change Button Function.
    public void invokeUpdateModelEmpty(){
        setEditable(false);
        Empleado emp = new Empleado();
        cedula = "";
        nombre = "";
        apellido = "";
        salarioBase = 0.0;
        horas = 0;
        jornada = "";
        activo = false;
        mensaje = "";
    }
    
    
    // Public Void - Update Activate/Desactivate Employee.
    public void deleteEmpleado(String cedula, boolean estado) throws SNMPExceptions, SQLException {
        EmpleadoDB eDB = new EmpleadoDB();
        eDB.CambiarEstadoUsuario(cedula, estado);
        invokeUpdateModelEmpty();
    }
    
    //Public Void - Create Employee.
     public void createEmpleado(String pcedula) throws SNMPExceptions, SQLException {
        if(this.nombre.equals("")|| this.cedula.equals("") || this.apellido.equals("") || this.jornada.equals("")){
            
            this.setMensaje("Campos Obligatorios!");
            
        }
        else{
        if(this.editable){
            Empleado emp = new Empleado(pcedula, nombre, apellido, salarioBase, horas, jornada, true);
            EmpleadoDB eDB = new EmpleadoDB();
            eDB.ActualizarEmpleado(emp);  
            invokeUpdateModelEmpty();
        } else { 
            Empleado emp = new Empleado(cedula, nombre, apellido, salarioBase, horas, jornada, true);
            EmpleadoDB eDB = new EmpleadoDB();
            eDB.InsertarEmpleado(emp);  
            invokeUpdateModelEmpty();
        } 
        }
        
    }
    
    
}
