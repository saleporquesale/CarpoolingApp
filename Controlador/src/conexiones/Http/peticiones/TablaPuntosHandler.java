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
public class TablaPuntosHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Estadistica/Puntaje");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(this.generarTabla(JSONIngreso).getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(TablaPuntosHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String generarTabla(JSONObject pJson){
        JSONObject categoria = ConexionBase.getInstancia().obtenerCategoria(
                Integer.parseInt(pJson.get("id").toString()));
        LinkedList<LinkedList<String>> tabla = new LinkedList<LinkedList<String>>();
        LinkedList<String> headers = new LinkedList<String>();
        headers.add("Categoria");
        headers.add("70%");
        headers.add("80%");
        headers.add("90%");
        headers.add("PuntosObtenidos");
        tabla.add(headers);
        LinkedList<String> valores = new LinkedList<String>();
        valores.add(categoria.get("categoria").toString());
        int total = Integer.parseInt(categoria.get("puntos").toString());
        valores.add(Double.toString(total*0.7));
        valores.add(Double.toString(total*0.8));
        valores.add(Double.toString(total*0.9));
        valores.add(Integer.toString(total));
        tabla.add(valores);
        System.out.println(Utilidades.TablaHTML(tabla));
        return Utilidades.TablaHTML(tabla);
    } 
}
