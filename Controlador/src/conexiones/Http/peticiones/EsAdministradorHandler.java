/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
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
public class EsAdministradorHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Administrador");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                this.conexionADATIC(JSONIngreso);
                if (ConexionBase.getInstancia().esAdministrador(
                        Integer.parseInt(JSONIngreso.get("id").toString()))) {
                    he.sendResponseHeaders(200, 0);
                    OutputStream os = he.getResponseBody();
                    os.write(Constantes.Constante_OK.getBytes());
                    os.close();
                }else{
                    he.sendResponseHeaders(404, 0);
                    OutputStream os = he.getResponseBody();
                    os.write(Constantes.Constante_No.getBytes());
                    os.close();
                }
            } catch (ParseException ex) {
                Logger.getLogger(EsAdministradorHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void conexionADATIC(JSONObject pJson) throws IOException {
        FileWriter archibo = new FileWriter(Constantes.Constantes_ArchivoDATIC, true);
        PrintWriter esctritura = new PrintWriter(archibo);
        esctritura.println(pJson.toJSONString());
        esctritura.close();
    }
}
