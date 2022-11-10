/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;


@SessionScoped
public class BeanUsuario implements Serializable{
    private String nombre;
    private String contrasena;
    private String tipo;
    private String vigenciaM;
    private boolean estado;
    private String mensaje;
    private LinkedList<Usuario> listaU= new LinkedList<Usuario>();
//    private AccesoDatos accesoDatos = new AccesoDatos();
//    private Connection conn;

    public BeanUsuario() {
    
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVigenciaM() {
        return vigenciaM;
    }

    public void setVigenciaM(String vigenciaM) {
        this.vigenciaM = vigenciaM;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LinkedList<Usuario> getListaU() throws SNMPExceptions, SQLException {
      LinkedList<Usuario> lista = new 
                    LinkedList<Usuario>();
        UsuarioDB uDB = new UsuarioDB();
        
        lista = uDB.moTodo();
        
        LinkedList resultLista = new LinkedList();
           
        resultLista=lista;       
        return resultLista; 
    }

    public void setListaU(LinkedList<Usuario> listaU) {
        this.listaU = listaU;
    }
    
    public void Login() throws SNMPExceptions{
     String pagina = "";
        try{
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos= new AccesoDatos();
            
            //Se crea la sentencia de Busqueda
            String query = "SELECT * FROM USUARIO WHERE NOMBRE = '"+nombre+"'and contrasena= '"+contrasena+"'";

            //se ejecuta la sentencia sql
            ResultSet rsPA= accesoDatos.ejecutaSQLRetornaRS(query);
            //se llama el array con los proyectos
            while(rsPA.next()){

            String nom= rsPA.getString("nombre");
            String cont=rsPA.getString("contrasena");
            
                
                
                
                
            }
            
            rsPA.close();//se cierra el ResultSeat.
            
        }catch(SQLException e){
            throw new SNMPExceptions (SNMPExceptions.SQL_EXCEPTION,
                                     e.getMessage(),e.getErrorCode());
        }catch(SNMPExceptions | ClassNotFoundException | NamingException e){
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,e.getMessage());
        }finally{
            
        }
    }
    
}
