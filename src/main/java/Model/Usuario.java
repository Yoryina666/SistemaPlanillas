/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Juan Gabriel
 */
public class Usuario {

    private String nombre;
    private TipoUsuario tipo;
    private Date vigenciaM;
    private boolean estado;

    public Usuario(String nombre, TipoUsuario tipo, Date vigenciaM, boolean estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vigenciaM = vigenciaM;
        this.estado = estado;
    }

    public Usuario() {
    }

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

}
