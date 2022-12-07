package Model;

/**
 *
 * @author Aylan Miranda
 */
public class Detalle {
    
    /** ID autogenerada, existe solo para poder borrar el detalle. */
    private String idDetalle;
    
    /** Nombre de la categoria, usada para buscar la categoria correspondiente. */
    private String categoria;
    
    /** Monto de registro, dependiente de la categoria y elementos agregados. */
    private double monto;
    
    /**
     * Constructor con datos.
     * @param idDetalle
     * @param categoria
     * @param monto 
     */
    public Detalle(String idDetalle, String categoria, double monto) {
        this.idDetalle = idDetalle;
        this.categoria = categoria;
        this.monto = monto;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    
    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    // </editor-fold>
    
}
