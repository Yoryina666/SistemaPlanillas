/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.LinkedList;
import javax.naming.NamingException;

public class UsuarioDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    private LinkedList<Usuario> listaUser = new LinkedList<Usuario>();

    public UsuarioDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public UsuarioDB() {
        super();
    }

    //insertar usuarios en la base de datos.
    public void InsertarUsuario(Usuario user)
            throws SNMPExceptions, SQLException, ClassNotFoundException, NamingException {
        String strSQL = "";

        try {
            //Se obtienen los valores del objeto Usuario

            strSQL
                    = "INSERT INTO Usuario(nombre,contrasena,tipo,vigenciaMaxima,"
                    + "activo) VALUES "
                    + "(" + "'" + user.getNombre() + "'" + ","
                    + "'" + user.getTipo() + "'" + ","
                    + "'" + user.getVigenciaM() + "'" + ","
                    + "'" + user.isEstado() + "'" + ",";

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {

        }
    }

    public Usuario login(String nombre, String contrasena) throws SQLException, SNMPExceptions, ClassNotFoundException, NamingException {
        Usuario usuario = null;
        String query = String.format("SELECT * FROM dbo.TRY_LOGIN('%s', '%s')", nombre, contrasena);
        try (
                ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(query)
            ) {
            if (rsPA.next()) {
                usuario = new Usuario(
                    rsPA.getString("Nombre"),
                    TipoUsuario.values()[rsPA.getInt("Tipo")],
                    rsPA.getDate("Vigencia"),
                    rsPA.getBoolean("Activo")
                );
            }
        } catch (SQLException | SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw e;
        } finally {
            return usuario;
        }
    }

    public LinkedList<Usuario> moTodo() throws SNMPExceptions, SQLException {
        String select = "";
        LinkedList<Usuario> listaUser = new LinkedList<Usuario>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //Se crea la sentencia de Busqueda
            select
                    = "Select nombre, tipo, vigenciaMaxima, activo from Usuario";
            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);
            //se llama el array con los proyectos
            while (rsPA.next()) {

                String nomUser = rsPA.getString("nombre");
                TipoUsuario tipo = TipoUsuario.values()[rsPA.getInt("Tipo")];
                Date vigenciaM = rsPA.getDate("vigenciaMaxima");
                boolean estado = rsPA.getBoolean("activo");

                //se construye el objeto.
                Usuario perUsuario = new Usuario(nomUser, tipo, vigenciaM, estado);

                listaUser.add(perUsuario);
            }
            rsPA.close();//se cierra el ResultSeat.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (SNMPExceptions | ClassNotFoundException | NamingException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return listaUser;
    }
}
