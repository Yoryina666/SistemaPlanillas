/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.TipoUsuario;
import Model.Usuario;
import Model.UsuarioDB;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

public class BeanUsuario {
    
    /** Nombre del usuario. */
    String nombre;
    
    /** Clave secreta. */
    String contrasena;
    
    /** Tipo de usuario del enum {@link Model.TipoUsuario}. */
    TipoUsuario tipo;
    
    /** Fecha en la cuál la contraseña debe ser cambiada. */
    Date vigenciaM;
    
    /** Indica si el usuario es permitido en el sistema.  */
    boolean estado;
    
    /** Campo para envío de enumerales {@link Model.TipoUsuario}. */
    TipoUsuario[] tiposUsuario;
   /** Lista que muestra a los usuarios agregados */
    LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>(); 
    // <editor-fold defaultstate="collapsed" desc="Setters y Getters">
    

    public LinkedList<Usuario> getListaUsuario() throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
         LinkedList<Usuario> lista = new LinkedList<Usuario>();
        String nombre="";
        String contrasena="";
        TipoUsuario tipo;
        Date vigencia;
        boolean estado=false;
        
        
        UsuarioDB uDB = new UsuarioDB();
        
        lista = uDB.leerUsuarios();
        
        LinkedList  resultList = new LinkedList();
        
        
        for (Iterator iter= lista.iterator();
                iter.hasNext();) {
        
                Usuario user = (Usuario) iter.next();
                nombre=user.getNombre();
                tipo=user.getTipo();
                vigencia= user.getVigenciaM();
                estado= user.isEstado();
                resultList.add(nombre);
                resultList.add(tipo);
                resultList.add(vigencia);
                resultList.add(estado);
            }         
            return resultList; 
    }

    public void setListaUsuario(LinkedList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return "";
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public Date getVigenciaM() {
        return vigenciaM;
    }

    public TipoUsuario[] getTiposUsuario() {
        tiposUsuario = TipoUsuario.values();
        return tiposUsuario;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
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

    public BeanUsuario() {
        this.estado = true;
        this.vigenciaM = Date
            .from(LocalDateTime.now().plusMonths(3)
            .toInstant(
                ZoneId.systemDefault().getRules().getOffset(Instant.now())
            )
        );
    }
    
    /*
    public LinkedList<Usuario> getListaUsuarios() throws SNMPExceptions, SQLException {
        LinkedList<Usuario> lista = new LinkedList<>();
        UsuarioDB uDB = new UsuarioDB();

        lista = uDB.moTodo();

        LinkedList resultLista = new LinkedList();

        resultLista = lista;
        return resultLista;
    }
    */
}
