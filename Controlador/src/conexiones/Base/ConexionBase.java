/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void signUp(String pNombre, int pId, String pTelefono, String pCorreo) throws SQLException {
        try {
            PreparedStatement consulta;
            //Query del structed procedure para la creacion del usuario
            consulta = this._Conexion.prepareStatement("INSERT INTO prueba (Mensaje) VALUES(?)");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    
    public void amigos(int pId){
        try {
            PreparedStatement consulta;
            //Query para obtener la lista de los amigos
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void vehiculos(int pId){
        try {
            PreparedStatement consulta;
            //Query para la busqueda de la lista de vehiculos
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void perfil(int pId){
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardar(int pId, String pKey, String pValue){
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
