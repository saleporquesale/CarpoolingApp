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
public class SignUpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " /SignUp");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /SignUp");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                
                int retornoDATIC = this.ConexionADATIC(JSONIngreso);
                if (retornoDATIC != -1) {
                    this.comunicacionBase(JSONIngreso,retornoDATIC);
                    he.sendResponseHeaders(200, 0);
                    OutputStream os = he.getResponseBody();
                    os.write(Constantes.Constante_OK.getBytes());
                    os.close();
                } else {
                    he.sendResponseHeaders(404, 0);
                    OutputStream os = he.getResponseBody();
                    os.write(Constantes.Constante_NoExiste.getBytes());
                    os.close();
                }
            } catch (ParseException ex) {
                Logger.getLogger(SignUpHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SignUpHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int ConexionADATIC(JSONObject pJson) throws IOException {
        JSONObject JSONdatic = new JSONObject();
        JSONdatic.put("id", pJson.get("id"));
        FileWriter archibo = new FileWriter(Constantes.Constantes_ArchivoDATIC, true);
        PrintWriter esctritura = new PrintWriter(archibo);
        esctritura.println(JSONdatic.toJSONString());
        esctritura.close();
        return this.definirTipo(Integer.parseInt(pJson.get("id").toString()));
    }

    /**
     * Metodo existente al no tener conexion con DATIC Define el tipo de usuario
     * segun la longitud del id 10 caracteres Cedula 9 caracteres Carnet
     *
     * @param pId
     * @return
     */
    private int definirTipo(int pId) {
        if (Integer.toString(pId).length() == 10) {
            return 0;//Cedula
        } else if (Integer.toString(pId).length() == 9) {
            return 1;//Carnet
        }
        return -1;
    }

    private void comunicacionBase(JSONObject pJson, int tipo) throws SQLException {
        ConexionBase.getInstancia().signUp(pJson.get("nombre").toString(),
                Integer.parseInt(pJson.get("id").toString()),
                pJson.get("telefono").toString(),
                pJson.get("correo").toString(),
                tipo);
    }

}
