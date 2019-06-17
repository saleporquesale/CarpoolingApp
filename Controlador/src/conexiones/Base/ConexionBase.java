/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Base;

import Logica.Notificacion;
import Logica.Usuario;
import Logica.Vehiculo;
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
            consulta = this._Conexion.prepareStatement("call ListaAmigos(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                consulta = this._Conexion.prepareStatement("call VerPerfil(?)");
                consulta.setInt(1, respuesta.getInt("Usuario_idUsuario1"));
                ResultSet resultado = consulta.executeQuery();
                while (resultado.next()) {
                    amigos.add(new Usuario(resultado.getString("Nombre"),
                            respuesta.getInt("Usuario_idUsuario1"),
                            resultado.getString("Telefono"),
                            resultado.getString("Correo")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return amigos;
    }

    public List<Vehiculo> vehiculos(int pId) {
        List<Vehiculo> vehiculos = new LinkedList<Vehiculo>();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ListaVehiculos(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                vehiculos.add(new Vehiculo(respuesta.getString("Modelo"),
                        respuesta.getString("Placa"),
                        respuesta.getInt("Capacidad")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehiculos;
    }

    public JSONObject perfil(int pId) {
        JSONObject JSONRespuesta = new JSONObject();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerPerfil(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            if (respuesta.next()) {
                JSONRespuesta.put("nombre", respuesta.getString("Nombre"));
                JSONRespuesta.put("correo", respuesta.getString("Correo"));
                JSONRespuesta.put("telefono", respuesta.getString("Telefono"));
            } else {
                System.out.println("vacio");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONRespuesta;
    }

    public void guardarPerfil(int pId, String pNombre, String pTelefono) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call GuardarPerfil(?,?,?)");
            consulta.setInt(1, pId);
            consulta.setString(2, pNombre);
            consulta.setString(3, pTelefono);
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

    public List<Usuario> posiblesAmigos(int pId, String pPosible) {
        List<Usuario> usuarios = new LinkedList<Usuario>();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call BuscarAmigo(?,?)");
            consulta.setInt(1, pId);
            consulta.setString(2, pPosible);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                usuarios.add(new Usuario(respuesta.getString("Nombre"),
                        respuesta.getInt("idUsuario"),
                        respuesta.getString("Telefono")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    public void agregarAmigo(int pId, int pIdAmigo) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AgregarAmigos(?,?)");
            consulta.setInt(1, pId);
            consulta.setInt(2, pIdAmigo);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notificar(int pIddueño, int pIdorigen, String pMensaje, int pTipo) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CrearNotificacion(?,?,?,?)");
            consulta.setInt(1, pIddueño);
            consulta.setInt(2, pIdorigen);
            consulta.setString(3, pMensaje);
            consulta.setInt(4, pTipo);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Notificacion> notificaciones(int pId) {
        List<Notificacion> notificaciones = new LinkedList<Notificacion>();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerNotificacion(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                notificaciones.add(new Notificacion(
                        respuesta.getInt("idNotificacion"),
                        respuesta.getString("Notificacion"),
                        respuesta.getInt("Tipo")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notificaciones;
    }

    public int origenNotificacion(int pIdNotificacion) {
        int id = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerOrigenNotificacion(?)");
            consulta.setInt(1, pIdNotificacion);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                id = respuesta.getInt("Origen");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public int capacidadVehiculo(String pPlaca) {
        int capacidad = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerCapacidadVehiculo(?)");
            consulta.setString(1, pPlaca);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                capacidad = respuesta.getInt("Capacidad");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return capacidad;
    }

    public JSONObject obtenerCategoria(int pID) {
        JSONObject categoria = new JSONObject();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerCategoria(?)");
            consulta.setInt(1, pID);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                categoria.put("categoria", respuesta.getString("Categoria"));
                categoria.put("puntos", respuesta.getInt("PuntosTotales"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }

    public int crearPosibleViaje(int pCantidad , String pPlaca) {
        int id = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CrearPosibleViaje(?,?)");
            consulta.setInt(1, pCantidad);
            consulta.setString(2, pPlaca);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                id = respuesta.getInt("max(idPosibleViaje)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void asignarAPosibleViaje(int pIdViaje, int pIdAcompañante) {
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AsignarAcompañante(?,?)");
            consulta.setInt(1, pIdViaje);
            consulta.setInt(2, pIdAcompañante);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void aceptarViaje(int pIdViaje, int pId) {
        try {
            System.out.println(pIdViaje + "   " + pId);
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ConfirAcompañante(?,?)");
            consulta.setInt(1, pIdViaje);
            consulta.setInt(2, pId);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int numeroDeConfirmados(int pIdViaje) {
        int id = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call NumeroConfir(?)");
            consulta.setInt(1, pIdViaje);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                id = respuesta.getInt("count(`acompañantes`.`Usuario_idUsuario`)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public int capacidadDePosibleViaje(int pIdViaje) {
        int capacidad = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerCapacidadPV(?)");
            consulta.setInt(1, pIdViaje);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                capacidad = respuesta.getInt("Capacidad");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return capacidad;
    }
    
    public String  placaDePosibleViaje(int pIdViaje) {
        String placa = "";
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerPlacaPV(?)");
            consulta.setInt(1, pIdViaje);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                placa = respuesta.getString("Placa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return placa;
    }
    
    public void crearViaje(int pParqueo, String pPlaca){
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CrearViaje(?,?)");
            consulta.setInt(1, pParqueo);
            consulta.setString(2, pPlaca);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Usuario> ListaPasajeros(int pIdViaje){
        List<Usuario> pasajeros = new LinkedList<Usuario>();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call HacerViaje(?)");
            consulta.setInt(1, pIdViaje);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                pasajeros.add(new Usuario(respuesta.getInt("Usuario_idUsuario")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pasajeros;
    }
    
    public void asignarCodigo(int pCodigo, int pIdUsuario, int idViaje){
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AsignarCodigo(?,?,?)");
            consulta.setString(1, Integer.toString(pCodigo));
            consulta.setInt(2, pIdUsuario);
            consulta.setInt(3, idViaje);
            consulta.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void asignarPuntosViaje(int pIdViaje, int pPuntos){
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AsignarPuntosViaje(?,?)");
            consulta.setInt(1, pIdViaje);
            consulta.setInt(2, pPuntos);
            consulta.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void asignarViaje(int pIdViaje, int pId){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call AsignarViaje(?,?)");
            consulta.setInt(1, pIdViaje);
            consulta.setInt(2, pId);
            consulta.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int viajesXSemana(int pId, int pSemana, int pMes){
        int cant = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ViajesSemana(?,?,?)");
            consulta.setInt(1, pId);
            consulta.setInt(2, pSemana);
            consulta.setInt(3, pMes);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                cant = respuesta.getInt("count(`usuarioxviaje`.`Viaje_idViaje`)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
    }
    
    public List<Integer> historialDeViajes(int pId){
        List<Integer> viajes = new LinkedList<Integer>();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ListaViajeUsuario(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                viajes.add(respuesta.getInt("usuarioxviaje.Viaje_idViaje"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viajes;
    }
    
    public JSONObject verViaje(int pIdViaje){
        JSONObject viaje = new JSONObject();
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ListaViaje(?)");
            consulta.setInt(1, pIdViaje);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                viaje.put("Fecha", respuesta.getString("Fecha"));
                viaje.put("PuntosObtenidos", respuesta.getInt("PuntosObtenidos"));
                viaje.put("CantUsuarios", respuesta.getInt("count(Usuario_idUsuario)"));
                viaje.put("Parqueo", respuesta.getInt("Parqueo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viaje;
    }
    
    public int choferXSemana(int pId, int pSemana, int pMes){
        int cant = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call Chofer(?,?,?)");
            consulta.setInt(1, pId);
            consulta.setInt(2, pSemana);
            consulta.setInt(3, pMes);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                cant = respuesta.getInt("count(notificacion.Origen)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
    }
    
    public int pasajeroXSemana(int pId, int pSemana, int pMes){
        int cant = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call Pasajero(?,?,?)");
            consulta.setInt(1, pId);
            consulta.setInt(2, pSemana);
            consulta.setInt(3, pMes);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                cant = respuesta.getInt("count(notificacion.Dueño) ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
    }
    
    public int verPuntos(int pId){
        int puntos = 0;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerPuntosUsuario(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                puntos = respuesta.getInt("Puntos");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puntos;
    }
    
    
    public void cambiarPuntos(int pID, int pPuntos){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CambiarPuntosUsuario(?,?)");
            consulta.setInt(1, pID);
            consulta.setInt(2, pPuntos);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean esAdministrador(int pId){
        boolean estatus = false;
        try {
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call esAdministrador(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                estatus = respuesta.getBoolean("Administrador");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estatus;
    }
    
    public void crearNuevoCorreo(String pCorreo){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CrearCorreos(?)");
            consulta.setString(1, pCorreo);
            consulta.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarCorreo(String pCorreo){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call EliminarCorreos(?)");
            consulta.setString(1, pCorreo);
            consulta.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<String> obternerCorreos(){
        List<String> correos = new LinkedList<String>();
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call ObtenerCorreos()");
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                correos.add(respuesta.getString("Correo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correos;
    }
    
    public List<JSONObject> obtenerCategorias(){
        List<JSONObject> categorias = new LinkedList<JSONObject>();
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call VerCategorias()");
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()) {
                JSONObject categoria = new JSONObject();
                categoria.put("categoria", respuesta.getString("Categoria"));
                categoria.put("viajesmin", respuesta.getInt("ViajesMin"));
                categoria.put("minpuntos", respuesta.getInt("PuntosMin"));
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categorias;
    }
    
    public void crearNuevaCategoria(String pCategoria, int pViajesMin, int pPuntosMin, int pPuntosTotales){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call CrearCategoria(?,?,?,?)");
            consulta.setString(1, pCategoria);
            consulta.setInt(2, pViajesMin);
            consulta.setInt(3, pPuntosMin);
            consulta.setInt(4, pPuntosTotales);
            consulta.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarVehiculo(String pPlaca){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call EliminarVehiculo(?)");
            consulta.setString(1, pPlaca);
            consulta.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarPerfil(int pId){
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call EliminarPerfil(?)");
            consulta.setInt(1, pId);
            consulta.executeUpdate();
                    
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean estaVerificado(int pId){
        boolean verificado = false;
        try{
            PreparedStatement consulta;
            consulta = this._Conexion.prepareStatement("call Habilitado(?)");
            consulta.setInt(1, pId);
            ResultSet respuesta = consulta.executeQuery();
            while (respuesta.next()){
                verificado = respuesta.getBoolean("Habilitado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verificado;
    }
}
