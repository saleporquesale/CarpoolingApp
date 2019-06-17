/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
import Logica.Notificacion;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jeja1
 */
public class NotificacionesHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Notificaciones");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(this.stringRespuesta(JSONIngreso).getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(NotificacionesHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String stringRespuesta(JSONObject pJSON) {
        JSONArray JSONamigos = new JSONArray();
        List<Notificacion> Notificaciones = ConexionBase.getInstancia().
                notificaciones(Integer.parseInt(pJSON.get("id").toString()));
        for (int index = 0; index < Notificaciones.size(); index++) {
            Notificacion amigo = Notificaciones.get(index);
            JSONObject JSONAmigo = new JSONObject();
            JSONAmigo.put("id", amigo.getID());
            JSONAmigo.put("mensaje", amigo.getTexto());
            JSONAmigo.put("tipo", amigo.getTipo());
            JSONamigos.add(JSONAmigo);
        }
        System.out.println(JSONamigos.toJSONString());
        return JSONamigos.toJSONString();
    }
}
