/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.enterprise.context.SessionScoped;
import javax.naming.NamingException;

@SessionScoped
public class BeanUsuario implements Serializable {

    private String nombre;
    private String contrasena;
    private String tipo;
    private String vigenciaM;
    private boolean estado;
    private String mensaje;
    private LinkedList<Usuario> listaU = new LinkedList<Usuario>();
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
        LinkedList<Usuario> lista = new LinkedList<Usuario>();
        UsuarioDB uDB = new UsuarioDB();

        lista = uDB.moTodo();

        LinkedList resultLista = new LinkedList();

        resultLista = lista;
        return resultLista;
    }

    public void setListaU(LinkedList<Usuario> listaU) {
        this.listaU = listaU;
    }

    public String login() throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String pagina = "index.xhtml";
        UsuarioDB uDB = new UsuarioDB();
        Usuario usuario = uDB.login(nombre, contrasena);
        if (usuario == null) {
            mensaje = "Credenciales incorrectas, usuario inexistente o usuario bloqueado";
        } else {
            switch (usuario.getTipo()) {
                case ADMINISTRADOR:
                    pagina = "MantenimientoEmpleado.xhtml";
                    break;
                case PLANILLERO:
                    pagina = "MantenimientoEmpleado.xhtml";
                    break;
                case RECURSOS_HUMANOS:
                    pagina = "MantenimientoEmpleado.xhtml";
                    break;
                default:
                    throw new AssertionError("Se obtuvo un entero de tipo inexistente como enumeral.");
            }
        }
        return pagina;
    }

}
