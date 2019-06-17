/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
import Auxiliares.GeneradorCodigos;
import Auxiliares.GeneradorParqueos;
import Logica.Usuario;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import conexiones.Base.ConexionBase;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jeja1
 */
public class IniciarViajeHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Viaje/Iniciar");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                this.IniciarUnViaje(JSONIngreso);
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(Constantes.Constante_OK.getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(IniciarViajeHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void IniciarUnViaje(JSONObject pJSON) {

        String placa = ConexionBase.getInstancia().placaDePosibleViaje(
                Integer.parseInt(pJSON.get("idViaje").toString()));

        ConexionBase.getInstancia().crearViaje(
                GeneradorParqueos.getInstancia().generar(),
                placa);

        int idChofer = Integer.parseInt(pJSON.get("id").toString());

        int parqueo = GeneradorParqueos.getInstancia().generar();

        int codigo = GeneradorCodigos.getInstancia().generar();

        String mensaje = "Se ha iniciado el viaje, su parqueo se encuentra en el"
                + " espacio numero " + parqueo + ". El codigo de acceso es: "
                + codigo;
        ConexionBase.getInstancia().asignarCodigo(codigo, idChofer,
                Integer.parseInt(pJSON.get("idViaje").toString()));
        ConexionBase.getInstancia().notificar(idChofer, idChofer,
                mensaje, 9);
        List<Usuario> pasajeros = ConexionBase.getInstancia().ListaPasajeros(
                Integer.parseInt(pJSON.get("idViaje").toString()));
        for (int index = 0; index < pasajeros.size(); index++) {
            Usuario pasajero = pasajeros.get(index);
            codigo = GeneradorCodigos.getInstancia().generar();
            mensaje = "Se ha iniciado el viaje, su parqueo se encuentra en el"
                    + " espacio numero " + parqueo + ". El codigo de acceso es: "
                    + codigo;
            ConexionBase.getInstancia().asignarViaje(Integer.
                    parseInt(pJSON.get("idViaje").toString()),
                    pasajero.getIdentificacion());
            ConexionBase.getInstancia().asignarCodigo(codigo, pasajero.getIdentificacion(),
                    Integer.parseInt(pJSON.get("idViaje").toString()));
            ConexionBase.getInstancia().notificar(pasajero.getIdentificacion(), 
                    idChofer, mensaje, 9);
            
        }
        ConexionBase.getInstancia().asignarPuntosViaje(Integer.
                parseInt(pJSON.get("idViaje").toString()), Constantes.Constante_Puntos);
    }
}
