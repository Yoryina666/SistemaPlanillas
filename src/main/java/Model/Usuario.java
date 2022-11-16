package Model;

import java.util.Date;

/**
 * Clase entidad para usuarios del sistema
 * @author Juan Gabriel
 * @author Aylan Miranda
 */
public class Usuario {

    /** Nombre del usuario. */
    private String nombre;
    
    /** Tipo de usuario del enum {@link Model.TipoUsuario}. */
    private TipoUsuario tipo;
    
    /** Fecha en la cuál la contraseña debe ser cambiada. */
    private Date vigenciaM;
    
    /** Indica si el usuario es permitido en el sistema.  */
    private boolean estado;

    /**
     * Constructor con datos
     * @param nombre Nombre
     * @param tipo Tipo de usuario
     * @param vigenciaM Vigencia máxima
     * @param estado Estado
     */
    public Usuario(String nombre, TipoUsuario tipo, Date vigenciaM, boolean estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vigenciaM = vigenciaM;
        this.estado = estado;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Date getVigenciaM() {
        return vigenciaM;
    }

    public void setVigenciaM(Date vigenciaM) {
        this.vigenciaM = vigenciaM;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    // </editor-fold>
    
}
