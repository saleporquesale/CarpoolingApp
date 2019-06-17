/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
import Auxiliares.Utilidades;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import conexiones.Base.ConexionBase;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
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
public class PuntosHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Perfil/Puntos");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(this.buscarPuntaje(JSONIngreso).toJSONString().getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(PuntosHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private JSONObject buscarPuntaje(JSONObject pJson){
        JSONObject pJsonSalida = new JSONObject();
        int puntos = ConexionBase.getInstancia().verPuntos(
                Integer.parseInt(pJson.get("id").toString()));
        pJsonSalida.put("Puntaje", puntos);
        System.out.println(pJsonSalida.toJSONString());
        return pJsonSalida;
    }
}
