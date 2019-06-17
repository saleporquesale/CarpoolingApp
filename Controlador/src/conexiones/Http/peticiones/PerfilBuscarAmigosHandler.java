/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
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
public class PerfilBuscarAmigosHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Perfil/Amigos/Buscar");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(this.stringRespuesta(JSONIngreso).getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(PerfilBuscarAmigosHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String stringRespuesta(JSONObject pJSON) {
        JSONArray JSONusuarios = new JSONArray();
        int idUsuario = Integer.parseInt(pJSON.get("id").toString());
        List<Usuario> usuarios = ConexionBase.getInstancia().
                posiblesAmigos(idUsuario,
                        pJSON.get("nombre").toString());
        for (int index = 0; index < usuarios.size(); index++) {
            Usuario usuario = usuarios.get(index);
            if (idUsuario != usuario.getIdentificacion()) {
                JSONObject JSONAmigo = new JSONObject();
                JSONAmigo.put("nombre", usuario.getNombre());
                JSONAmigo.put("id", usuario.getIdentificacion());
                JSONAmigo.put("telefono", usuario.getTelefono());
                JSONusuarios.add(JSONAmigo);
            }
        }
        System.out.println(JSONusuarios.toJSONString());
        return JSONusuarios.toJSONString();
    }
}
