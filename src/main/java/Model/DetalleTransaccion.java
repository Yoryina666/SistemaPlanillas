/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Estudiante
 */
public class DetalleTransaccion {
    private int detalleID;
    private int transaccionID;
    private String categoriaPago;
    private String categoriaDeduccion;
    private double monto;

    public DetalleTransaccion(int detalleID, int transaccionID, String categoriaPago, String categoriaDeduccion, double monto) {
        this.detalleID = detalleID;
        this.transaccionID = transaccionID;
        this.categoriaPago = categoriaPago;
        this.categoriaDeduccion = categoriaDeduccion;
        this.monto = monto;
    }

    public int getDetalleID() {
        return detalleID;
    }

    public void setDetalleID(int detalleID) {
        this.detalleID = detalleID;
    }

    public int getTransaccionID() {
        return transaccionID;
    }

    public void setTransaccionID(int transaccionID) {
        this.transaccionID = transaccionID;
    }

    public String getCategoriaPago() {
        return categoriaPago;
    }

    public void setCategoriaPago(String categoriaPago) {
        this.categoriaPago = categoriaPago;
    }

    public String getCategoriaDeduccion() {
        return categoriaDeduccion;
    }

    public void setCategoriaDeduccion(String categoriaDeduccion) {
        this.categoriaDeduccion = categoriaDeduccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
}
