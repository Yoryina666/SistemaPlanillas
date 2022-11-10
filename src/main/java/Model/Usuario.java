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
    private String contrasena;
    private int tipo;
    private Date vigenciaM;
    private boolean estado;
    
     public Usuario(String nombre,String contrasena, int tipo,Date vigenciaM, boolean estado){
        this.setNombre(nombre);
        this.setContrasena(contrasena);
        this.setTipo(tipo);
        this.setVigenciaM(vigenciaM);
        this.setEstado(estado);
    }
public Usuario(){
        
    }
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
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
