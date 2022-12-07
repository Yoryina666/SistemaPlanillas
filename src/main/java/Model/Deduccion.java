/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Estudiante
 */
public class Deduccion {
    private String nombre;
    private String despcripcion;
    private String encargado;
    private float monto;
    private boolean esPorcentual;
    private boolean automatic;

    public Deduccion(String nombre, String despcripcion, String encargado, float monto, boolean esPorcentual, boolean automatic) {
        this.nombre = nombre;
        this.despcripcion = despcripcion;
        this.encargado = encargado;
        this.monto = monto;
        this.esPorcentual = esPorcentual;
        this.automatic = automatic;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDespcripcion() {
        return despcripcion;
    }

    public void setDespcripcion(String despcripcion) {
        this.despcripcion = despcripcion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public boolean isEsPorcentual() {
        return esPorcentual;
    }

    public void setEsPorcentual(boolean esPorcentual) {
        this.esPorcentual = esPorcentual;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
    
}
