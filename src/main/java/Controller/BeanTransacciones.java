package Controller;

import Model.Detalle;
import Model.Empleado;
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
    
}
