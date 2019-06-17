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
public class ViajeXSemanaHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Estadistica/Viajes/Semana");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                System.out.println(this.generarEstadistica(JSONIngreso).toJSONString());
                os.write(this.generarEstadistica(JSONIngreso).toJSONString().getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(ViajeXSemanaHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private JSONObject generarEstadistica(JSONObject pJson){
        int primersemana = ConexionBase.getInstancia().viajesXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                1,Calendar.getInstance().get(Calendar.MONTH));
        int segundasemana = ConexionBase.getInstancia().viajesXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                2,Calendar.getInstance().get(Calendar.MONTH));
        int tercerasemana = ConexionBase.getInstancia().viajesXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                3,Calendar.getInstance().get(Calendar.MONTH));
        int cuartasemana = ConexionBase.getInstancia().viajesXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                4,Calendar.getInstance().get(Calendar.MONTH));
        JSONObject estadistica = new JSONObject();
        estadistica.put("Primera", primersemana);
        estadistica.put("Segunda", segundasemana);
        estadistica.put("Tercera", tercerasemana);
        estadistica.put("Cuarta", cuartasemana);
        return estadistica;
    }
}
