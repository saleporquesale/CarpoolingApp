/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http;

import Auxiliares.Constantes;
import com.sun.net.httpserver.HttpServer;
import conexiones.Http.peticiones.LoginHandler;
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
    
    private ServidorHTTP(){
        try {
            InetAddress direcionIP = InetAddress.getByName(Constantes.Constante_IP);
            _Servidor = HttpServer.create(new InetSocketAddress(direcionIP,Constantes.Constante_Puerto),0);
            System.out.println("server started at " + Constantes.Constante_Puerto+ " at " + 
                    _Servidor.getAddress().getHostString());
            _Servidor.createContext("/", new LoginHandler());
            _Servidor.setExecutor(null);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ServidorHTTP getInstancia(){
        if(_Instancia==null){
            _Instancia = new ServidorHTTP();
        }
        return _Instancia;
    }
    
    public void iniciar(){
        _Servidor.start();
    }
}