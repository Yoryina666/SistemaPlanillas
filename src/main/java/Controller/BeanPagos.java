package Controller;

import DAO.SNMPExceptions;
import Model.Pago;
import Model.PagoDB;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class BeanPagos {
    
    /** Nombre del usuario. */
    String nombre;
    
    /** Descripción del pago. */
    String descripcion;
    
    /** Define si se aplica a la hora de crear la planilla. */
    boolean automatico;
    
    /** Define el procentaje del monto de las horas trabajadas. */
    String porcentaje;
    
    /** Indicador si está editando o creando. */
    boolean modoEdicion;
    
    /** Lista con los usuarios del sistema. */
    LinkedList<Pago> listaPagos = new LinkedList<>();
    
    /** Despliega mensaje de validación. */
    String mensaje;
    
    /** RegEx para verificar si valor es numérico. */
    private final Pattern regexNumero = Pattern.compile("-?\\d+(\\.\\d+)?");
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LinkedList<Pago> getListaPagos() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        listaPagos = (new PagoDB()).leerPagos();
        return listaPagos;
    }

    public void setListaPagos(LinkedList<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }
    
    // </editor-fold>
    
    public void cancelar() {
        setModoEdicion(false);
        nombre = "";
        descripcion = "";
        automatico = false;
        porcentaje = "1.0";
        mensaje = "";
    }
    
    public void editarPago(Pago pago) {
        setModoEdicion(true);
        System.out.println("1 FUCK");
        nombre = pago.getNombre();
        descripcion = pago.getDescripcion();
        automatico = pago.isAutomatico();
        porcentaje = Double.toString(pago.getPorcentaje());
    }
    
    public void crearPago() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        PagoDB db = new PagoDB();
        Pago pago = new Pago(
            nombre, descripcion, automatico, 1
        );
        if(nombre.equals("") || !regexNumero.matcher(porcentaje).matches()){
            this.setMensaje("Campos Obligatorios!");
        } else {
            pago.setPorcentaje(Double.parseDouble(porcentaje));
            db.insertarPago(pago);
            cancelar();
        }
        
    }
    
    public void actualizarPago() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        PagoDB db = new PagoDB();
        Pago pago = new Pago(
            nombre, descripcion, automatico, 1
        );
        if(nombre.equals("") || !regexNumero.matcher(porcentaje).matches()){
            this.setMensaje("Campos Obligatorios!");
        } else {
            pago.setPorcentaje(Double.parseDouble(porcentaje));
            db.actualizarPago(pago);
            cancelar();
        }
    }
    
}
