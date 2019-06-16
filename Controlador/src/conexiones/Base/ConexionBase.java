/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Base;

import Logica.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author jeja1
 */
public class ConexionBase {

    private static ConexionBase _Instancia;
    private Connection _Conexion;

    private ConexionBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            _Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + "mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&"
                    + "useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "proyecto");
        } catch (SQLException ex) {
            try {
                throw new SQLException(ex);
            } catch (SQLException ex1) {
                Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
        }
    }

    public static ConexionBase getInstancia() {
        if (_Instancia == null) {
            _Instancia = new ConexionBase();
        }
        return _Instancia;
    }

    public void signUp(String pNombre, int pId, String pTelefono, String pCorreo,
            int pTipo) throws SQLException {
        try {
            PreparedStatement consulta;
            //Query del structed procedure para la creacion del usuario
            consulta = this._Conexion.prepareStatement("call Registro(?,?,?,?,?)");
            consulta.setInt(1, pId);
            consulta.setString(2, pNombre);
            consulta.setString(3, pCorreo);
            consulta.setString(4, pTelefono);
            consulta.setInt(5, pTipo);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public List<Usuario> amigos(int pId) {
        List<Usuario> amigos = new LinkedList<Usuario>();
        try {
            PreparedStatement consulta;
            //Query para obtener la lista de los amigos
            consulta = this._Conexion.prepareStatement("call ListaAmigos(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                //amigos.add(new Usuario(respuesta.getString("Nombre"),respuesta.getInt("I"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return amigos;
    }

    public void vehiculos(int pId) {
        try {
            PreparedStatement consulta;
            //Query para la busqueda de la lista de vehiculos
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject perfil(int pId) {
        JSONObject JSONRespuesta = new JSONObject();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerPerfil(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            if(respuesta.next()){
            JSONRespuesta.put("nombre", respuesta.getString("Nombre"));
            JSONRespuesta.put("correo", respuesta.getString("Correo"));
            JSONRespuesta.put("telefono", respuesta.getString("Telefono"));
            }else {
                System.out.println("vacio");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONRespuesta;
    }

    public void guardar(int pId, String pKey, String pValue) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarVehiculo(int pId, String pPlaca, String pMarca,
            String pModelo, int pCantPasajeros) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AgregarVehiculos(?,?,?,?,?)");
            consulta.setString(1, pMarca);
            consulta.setString(2, pModelo);
            consulta.setInt(3, pCantPasajeros);
            consulta.setString(4, pPlaca);
            consulta.setInt(5, pId);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Usuario> posiblesAmigos(String pPosible) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
