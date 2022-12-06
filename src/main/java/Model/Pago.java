package Model;

/**
 * @author Aylan Miranda
 */
public class Pago {
    
    /** Nombre del usuario. */
    private String nombre;
    
    /** Descripción del pago. */
    private String descripcion;
    
    /** Define si se aplica a la hora de crear la planilla. */
    private boolean automatico;
    
    /** Define el procentaje del monto de las horas trabajadas. */
    private double porcentaje;
    
    /**
     * Constructor con datos
     * @param nombre
     * @param descripcion
     * @param automatico
     * @param porcentaje 
     */
    public Pago(String nombre, String descripcion, boolean automatico, double porcentaje) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.automatico = automatico;
        this.porcentaje = porcentaje;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    // </editor-fold>
    
}
