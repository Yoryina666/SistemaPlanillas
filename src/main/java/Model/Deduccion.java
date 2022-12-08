package Model;

/**
 *
 * @author Aylan Miranda
 */
public class Deduccion {
    
    /** Nombre del usuario. */
    private String nombre;
    
    /** Descripción del deduccion. */
    private String descripcion;
    
    /** Define si se aplica a la hora de crear la planilla. */
    private boolean automatico;
    
    /** Define el monto, pero puede ser considerado fijo o porcentual. */
    private double monto;
    
    /** Nombre de la persona que realiza la deducción. */
    private String encargado;
    
    /** Define si el monto es usado como un porcentaje. */
    private boolean porcentual;
    
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
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
    
    // </editor-fold>
    
    /**
     * Constructor con datos.
     * @param nombre
     * @param descripcion
     * @param automatico
     * @param monto
     * @param encargado
     * @param porcentual 
     */
    public Deduccion(String nombre, String descripcion, boolean automatico, double monto, String encargado, boolean porcentual) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.automatico = automatico;
        this.monto = monto;
        this.encargado = encargado;
        this.porcentual = porcentual;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
