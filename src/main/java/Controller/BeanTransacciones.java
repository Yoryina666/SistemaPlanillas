package Controller;

import DAO.SNMPExceptions;
import Model.Deduccion;
import Model.Detalle;
import Model.DetalleDB;
import Model.Empleado;
import Model.Pago;
import Model.Planilla;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;
import javax.naming.NamingException;

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
    String monto;
    LinkedList<Deduccion> listaDeducciones;
    Deduccion deduccion;
    String mensaje;
    /** RegEx para verificar si valor es numérico. */
    private final Pattern regexNumero = Pattern.compile("-?\\d+(\\.\\d+)?");
    
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

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Deduccion getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(Deduccion deduccion) {
        this.deduccion = deduccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    // </editor-fold>
    
    public void planillaCambia() {
    }
    
    public void empleadoCambia() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        listaTransacciones = (new DetalleDB()).leerDetalles(planilla.getPlanillaID(), empleado.getCedula());
    }
    
    public void agregarDetalle() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        if(regexNumero.matcher(monto).matches()){
            this.setMensaje("Campos Obligatorios!");
        } else {
            Detalle detalle = new Detalle("", agregandoPago ? pago.getNombre() : deduccion.getNombre(), Double.parseDouble(monto));
            (new DetalleDB()).insertarDetalle(detalle, planilla.getPlanillaID(), empleado.getCedula());
        }
    }
    
    public void borrarDetalle(Detalle detalle) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        (new DetalleDB()).borrarDetalle(detalle);
        empleadoCambia();
    }
    
}
