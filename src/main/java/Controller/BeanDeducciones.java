package Controller;

import DAO.SNMPExceptions;
import Model.Deduccion;
import Model.DeduccionDB;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;
import javax.naming.NamingException;

/**
 * @author Aylan Miranda
 */
public class BeanDeducciones {
    
    /** Nombre del usuario. */
    String nombre;
    
    /** Descripción del deduccion. */
    String descripcion;
    
    /** Define si se aplica a la hora de crear la planilla. */
    boolean automatico;
    
    /** Define el monto, pero puede ser considerado fijo o porcentual. */
    String monto;
    
    /** Nombre de la persona que realiza la deducción. */
    String encargado;
    
    /** Define si el monto es usado como un porcentaje. */
    boolean porcentual;
    
    /** Indicador si está editando o creando. */
    boolean modoEdicion;
    
    /** Lista con los usuarios del sistema. */
    LinkedList<Deduccion> listaDeducciones = new LinkedList<>();
    
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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public boolean isPorcentual() {
        return porcentual;
    }

    public void setPorcentual(boolean porcentual) {
        this.porcentual = porcentual;
    }
    
    public LinkedList<Deduccion> getListaDeducciones() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        listaDeducciones = (new DeduccionDB()).leerDeducciones();
        return listaDeducciones;
    }

    public void setListaDeducciones(LinkedList<Deduccion> listaDeducciones) {
        this.listaDeducciones = listaDeducciones;
    }
    
    // </editor-fold>
    
    public void cancelar() {
        setModoEdicion(false);
        nombre = "";
        descripcion = "";
        automatico = false;
        monto = "0.0";
        mensaje = "";
    }
    
    public void editarDeduccion(Deduccion deduccion) {
        setModoEdicion(true);
        nombre = deduccion.getNombre();
        descripcion = deduccion.getDescripcion();
        automatico = deduccion.isAutomatico();
        monto = Double.toString(deduccion.getMonto());
    }
    
    public void crearDeduccion() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        DeduccionDB db = new DeduccionDB();
        Deduccion deduccion = new Deduccion(
                nombre, descripcion, automatico, 0, encargado, porcentual
        );
        if(nombre.equals("") || encargado.equals("") || !regexNumero.matcher(monto).matches()){
            this.setMensaje("Campos Obligatorios!");
        } else {
            deduccion.setMonto(Double.parseDouble(monto));
            if (modoEdicion) db.actualizarDeduccion(deduccion);
            else db.insertarDeduccion(deduccion);
            cancelar();
        }
    }
}
