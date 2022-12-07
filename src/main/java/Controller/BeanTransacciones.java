package Controller;

import Model.Deduccion;
import Model.Detalle;
import Model.Empleado;
import Model.Pago;
import Model.Planilla;
import java.util.LinkedList;

/**
 *
 * @author Aylan Miranda
 */
public class BeanTransacciones {
    
    LinkedList<Planilla> listaPlanillas;
    Planilla planilla;
    LinkedList<Empleado> listaEmpleados;
    Empleado empleado;
    boolean agregandoPago;
    double montoHora;
    double montoBruto;
    double montoNeto;
    LinkedList<Detalle> listaTransacciones;
    LinkedList<Pago> listaPagos;
    Pago pago;
    LinkedList<Deduccion> listaDeducciones;
    Deduccion deduccion;
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    
    public LinkedList<Planilla> getListaPlanillas() {
        return listaPlanillas;
    }

    public Planilla getPlanilla() {
        return planilla;
    }

    public LinkedList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isAgregandoPago() {
        return agregandoPago;
    }

    public void setAgregandoPago(boolean agregandoPago) {
        this.agregandoPago = agregandoPago;
    }

    public double getMontoHora() {
        return montoHora;
    }

    public void setMontoHora(double montoHora) {
        this.montoHora = montoHora;
    }

    public double getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(double montoBruto) {
        this.montoBruto = montoBruto;
    }

    public double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public LinkedList<Detalle> getListaTransacciones() {
        return listaTransacciones;
    }

    public LinkedList<Pago> getListaPagos() {
        return listaPagos;
    }

    public LinkedList<Deduccion> getListaDeducciones() {
        return listaDeducciones;
    }
    
    
    
    // </editor-fold>
    
    public void planillaCambia() {
        
    }
    
    public void empleadoCambia() {
        
    }
    
    public void agregarDetalle(Detalle detalle) {
        
    }
    
    public void borrarDetalle(Detalle detalle) {
        
    }
    
}
