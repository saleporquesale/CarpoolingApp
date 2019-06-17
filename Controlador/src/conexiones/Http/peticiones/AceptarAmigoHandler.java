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
public class AceptarAmigoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Perfil/Amigos/Aceptar");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                this.AceptarUnAmigo(JSONIngreso);
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(Constantes.Constante_OK.getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(AceptarAmigoHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void AceptarUnAmigo(JSONObject pJSON) {
        int idAmigo= ConexionBase.getInstancia().origenNotificacion(
                Integer.parseInt(pJSON.get("idNotificacion").toString()));
        ConexionBase.getInstancia().agregarAmigo(
                Integer.parseInt(pJSON.get("id").toString()),
                idAmigo);
        ConexionBase.getInstancia().agregarAmigo(idAmigo,
                Integer.parseInt(pJSON.get("id").toString()));
        JSONObject JSONUsuario = ConexionBase.getInstancia().perfil(
                Integer.parseInt(pJSON.get("id").toString()));
        String usuario = JSONUsuario.get("nombre").toString();
        String mensaje = usuario + "acepto su solitud de amistad.";
        ConexionBase.getInstancia().notificar(idAmigo,
                Integer.parseInt(pJSON.get("id").toString()),
                mensaje, 6);
    }
}
