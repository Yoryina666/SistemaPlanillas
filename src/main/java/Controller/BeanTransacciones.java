package Controller;

import DAO.SNMPExceptions;
import Model.Deduccion;
import Model.DeduccionDB;
import Model.Detalle;
import Model.DetalleDB;
import Model.Empleado;
import Model.EmpleadoDB;
import Model.Pago;
import Model.PagoDB;
import Model.Planilla;
import Model.PlanillaDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
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
    
    public LinkedList<Planilla> getListaPlanillas() throws IOException, SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        listaPlanillas = (new PlanillaDB()).obtenerPlanillasActivas();
        if (listaPlanillas.isEmpty()) mensaje = "No existen planillas, por favor crea una antes de proceder";
        else if (planilla == null) setPlanilla(listaPlanillas.get(0));
        return listaPlanillas;
    }

    public Planilla getPlanilla() {
        return (Planilla) planilla;
    }

    public void setPlanilla(Planilla planilla) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        this.planilla = planilla;
        planillaCambia();
    }

    public LinkedList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        this.empleado = empleado;
        empleadoCambia();
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
    
    public LinkedList<Pago> getListaPagos() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        listaPagos = (new PagoDB()).leerPagos();
        return listaPagos;
    }

    public LinkedList<Deduccion> getListaDeducciones() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        listaDeducciones = (new DeduccionDB()).leerDeducciones();
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
    
    public void planillaCambiaEvento(ValueChangeEvent e)throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
	planillaCambia();
    } 
    
    public void empleadoCambiaEvento(ValueChangeEvent e)throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
	empleadoCambia();
    }
    
    public void planillaCambia() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        if (planilla == null) return;
        listaEmpleados = (new EmpleadoDB()).ObtenerTodosEmpleado(planilla.getPlanillaID());
        if (listaEmpleados.isEmpty()) mensaje = "No hay empleados, por favor crea alguno o pidele a un administrador o recursos humanos";
        else if (empleado == null) setEmpleado(listaEmpleados.get(0));
    }
    
    public void empleadoCambia() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        if (empleado == null) return;
        listaTransacciones = (new DetalleDB()).leerDetalles(planilla.getPlanillaID(), empleado.getCedula());
        montoHora = empleado.getSalarioBase() / empleado.getHoras();
        float montoDeducciones = 0;
        for (Detalle detalle : listaTransacciones) {
            if (detalle.getMonto() > 0) montoBruto += detalle.getMonto();
            else montoDeducciones += detalle.getMonto();
        }
        montoNeto = montoBruto + montoDeducciones;
    }
    
    public void agregarDetalle() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        if(regexNumero.matcher(monto).matches()){
            this.setMensaje("Campos Obligatorios!");
        } else {
            Detalle detalle = new Detalle("", agregandoPago ? pago.getNombre() : deduccion.getNombre(), 0);
            if (agregandoPago) detalle.setMonto(Double.parseDouble(monto) * montoHora * pago.getPorcentaje());
            else detalle.setMonto(-(deduccion.isPorcentual() ? montoBruto * (deduccion.getMonto() / 100) : deduccion.getMonto()));
            (new DetalleDB()).insertarDetalle(detalle, planilla.getPlanillaID(), empleado.getCedula());
            empleadoCambia();
        }
    }
    
    public void borrarDetalle(Detalle detalle) throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        (new DetalleDB()).borrarDetalle(detalle);
        empleadoCambia();
    }
    
}
