/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http;

import Auxiliares.Constantes;
import com.sun.net.httpserver.HttpServer;
import conexiones.Http.peticiones.AceptarAmigoHandler;
import conexiones.Http.peticiones.AceptarViajeHandler;
import conexiones.Http.peticiones.AgregarAmigoHandler;
import conexiones.Http.peticiones.CategoriaAgregarHandler;
import conexiones.Http.peticiones.CategoriasHandler;
import conexiones.Http.peticiones.CorreoAgregarHandler;
import conexiones.Http.peticiones.CorreoEliminarHandler;
import conexiones.Http.peticiones.CorreoHandler;
import conexiones.Http.peticiones.CrearViajeHandler;
import conexiones.Http.peticiones.DiferenciaXSemanaHandler;
import conexiones.Http.peticiones.EsAdministradorHandler;
import conexiones.Http.peticiones.GuardarPerfilHandler;
import conexiones.Http.peticiones.HistorialDeViajesHandler;
import conexiones.Http.peticiones.IniciarViajeHandler;
import conexiones.Http.peticiones.LoginHandler;
import conexiones.Http.peticiones.NotificacionesHandler;
import conexiones.Http.peticiones.PerfilAgregarVehiculosHandler;
import conexiones.Http.peticiones.PerfilAmigosHandler;
import conexiones.Http.peticiones.PerfilBuscarAmigosHandler;
import conexiones.Http.peticiones.PerfilHandler;
import conexiones.Http.peticiones.PerfilVehiculosHandler;
import conexiones.Http.peticiones.PuntosHandler;
import conexiones.Http.peticiones.RechazarViajeHandler;
import conexiones.Http.peticiones.SignUpHandler;
import conexiones.Http.peticiones.TablaPuntosHandler;
import conexiones.Http.peticiones.VehiculoEliminarHandler;
import conexiones.Http.peticiones.ViajeXSemanaHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeja1
 */
public class ServidorHTTP {

    private static ServidorHTTP _Instancia;
    private HttpServer _Servidor;

    private ServidorHTTP() {
        try {
            InetAddress direcionIP = InetAddress.getByName(Constantes.Constante_IP);
            this._Servidor = HttpServer.create(new InetSocketAddress(direcionIP, Constantes.Constante_Puerto), 0);
            System.out.println("server started at " + Constantes.Constante_Puerto + " at "
                    + this._Servidor.getAddress().getHostString());
            this.difineHandlers();
            this._Servidor.setExecutor(null);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ServidorHTTP getInstancia() {
        if (_Instancia == null) {
            _Instancia = new ServidorHTTP();
        }
        return _Instancia;
    }

    public void iniciar() {
        _Servidor.start();
    }

    private void difineHandlers() {
        this._Servidor.createContext("/", new LoginHandler());
        this._Servidor.createContext("/SignUp", new SignUpHandler());
        this._Servidor.createContext("/Perfil/Amigos", new PerfilAmigosHandler());
        this._Servidor.createContext("/Perfil/Vehiculos", new PerfilVehiculosHandler());
        this._Servidor.createContext("/Perfil", new PerfilHandler());
        this._Servidor.createContext("/Perfil/Guardar", new GuardarPerfilHandler());
        this._Servidor.createContext("/Perfil/Vehiculos/Agregar", new PerfilAgregarVehiculosHandler());
        this._Servidor.createContext("/Perfil/Amigos/Buscar", new PerfilBuscarAmigosHandler());
        this._Servidor.createContext("/Perfil/Amigos/Buscar/Agregar", new AgregarAmigoHandler());
        this._Servidor.createContext("/Notificaciones", new NotificacionesHandler());
        this._Servidor.createContext("/Perfil/Amigos/Aceptar", new AceptarAmigoHandler());
        this._Servidor.createContext("/Viaje/Crear", new CrearViajeHandler());
        this._Servidor.createContext("/Estadistica/Puntaje", new TablaPuntosHandler());
        this._Servidor.createContext("/Viaje/Aceptar", new AceptarViajeHandler());
        this._Servidor.createContext("/Viaje/Iniciar", new IniciarViajeHandler());
        this._Servidor.createContext("/Estadistica/Viajes/Semana", new  ViajeXSemanaHandler());
        this._Servidor.createContext("/Estadistica/Historial", new HistorialDeViajesHandler());
        this._Servidor.createContext("/Estadistica/Diferencia/Semana", new DiferenciaXSemanaHandler());
        this._Servidor.createContext("/Perfil/Puntos", new PuntosHandler());
        this._Servidor.createContext("/Administrador", new EsAdministradorHandler());
        this._Servidor.createContext("/Administrador/Correo", new CorreoHandler());
        this._Servidor.createContext("/Administrador/Correo/Agregar", new CorreoAgregarHandler());
        this._Servidor.createContext("/Administrador/Correo/Eliminar", new CorreoEliminarHandler());
        this._Servidor.createContext("/Administrador/Categorias", new CategoriasHandler());
        this._Servidor.createContext("/Administrador/Categoria/Agregar", new CategoriaAgregarHandler());
        this._Servidor.createContext("/Viaje/Rechazar", new RechazarViajeHandler());
        this._Servidor.createContext("/Perfil/Vehiculos/Eliminar", new VehiculoEliminarHandler());
    }
}
