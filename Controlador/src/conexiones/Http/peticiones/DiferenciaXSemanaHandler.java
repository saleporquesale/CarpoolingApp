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
public class DiferenciaXSemanaHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Estadistica/Diferencia/Semana");
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
                Logger.getLogger(DiferenciaXSemanaHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private JSONObject generarEstadistica(JSONObject pJson){
        int Chofer1 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                1,Calendar.getInstance().get(Calendar.MONTH));
        int Chofer2 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                2,Calendar.getInstance().get(Calendar.MONTH));
        int Chofer3 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                3,Calendar.getInstance().get(Calendar.MONTH));
        int Chofer4 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                4,Calendar.getInstance().get(Calendar.MONTH));
        JSONObject estadistica = new JSONObject();
        estadistica.put("Chofer1", Chofer1);
        estadistica.put("Chofer2", Chofer2);
        estadistica.put("Chofer3", Chofer3);
        estadistica.put("Chofer4", Chofer4);
        int Pasajero1 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                1,Calendar.getInstance().get(Calendar.MONTH));
        int Pasajero2 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                2,Calendar.getInstance().get(Calendar.MONTH));
        int Pasajero3 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                3,Calendar.getInstance().get(Calendar.MONTH));
        int Pasajero4 = ConexionBase.getInstancia().choferXSemana(
                Integer.parseInt(pJson.get("id").toString()), 
                4,Calendar.getInstance().get(Calendar.MONTH));
        estadistica.put("Pasajero1", Pasajero1);
        estadistica.put("Pasajero2", Pasajero2);
        estadistica.put("Pasajero3", Pasajero3);
        estadistica.put("Pasajero4", Pasajero4);
        return estadistica;
    }
}
