/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
import Logica.Memoria;
import Logica.Viaje;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import conexiones.Base.ConexionBase;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jeja1
 */
public class CrearViajeHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Viaje/Crear");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                this.crearViaje(JSONIngreso);
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(Constantes.Constante_OK.getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(CrearViajeHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void crearViaje(JSONObject pJson) {
        int capacidad = ConexionBase.getInstancia().capacidadVehiculo(
                pJson.get("placa").toString());
        int numeroDeAcompañantes = Integer.parseInt(pJson.get("numero").
                toString());
        int idViaje = ConexionBase.getInstancia().crearPosibleViaje(capacidad,
                pJson.get("placa").toString());
        for (int index = 0; index < numeroDeAcompañantes; index++) {
            System.out.println(idViaje+"    "+Integer.parseInt(pJson.
                    get(("idAmigo"+index)).toString()));
            ConexionBase.getInstancia().asignarAPosibleViaje(idViaje, 
                    Integer.parseInt(pJson.
                    get(("idAmigo"+index)).toString()));
            JSONObject JSONUsuario = ConexionBase.getInstancia().perfil(Integer.parseInt(
                    pJson.get("id").toString()));
            String mensaje = JSONUsuario.get("nombre").toString() +
                    " te ha invitado a un viaje. Toca la notificaion para "
                    + "confirmar#" + idViaje;
            System.out.println(mensaje);
            ConexionBase.getInstancia().notificar(
                    Integer.parseInt(pJson.get(("idAmigo" + index)).toString()), 
                    Integer.parseInt(pJson.get("id").toString()), 
                    mensaje, 2);
        }
    }
}