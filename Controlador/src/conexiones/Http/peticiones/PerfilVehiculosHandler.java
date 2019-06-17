/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones.Http.peticiones;

import Auxiliares.Constantes;
import Logica.Vehiculo;
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
public class PerfilVehiculosHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().compareTo("GET") == 0) {
            System.out.println(he.getRequestMethod() + " Perfil/Vehiculos");
        } else if (he.getRequestMethod().compareTo("POST") == 0) {
            try {
                System.out.println(he.getRequestMethod() + " /Perfil/Vehiculos");
                JSONParser jsonParser = new JSONParser();
                JSONObject JSONIngreso = (JSONObject) jsonParser.
                        parse(new InputStreamReader(he.getRequestBody()));
                System.out.println(JSONIngreso.toJSONString());
                he.sendResponseHeaders(200, 0);
                OutputStream os = he.getResponseBody();
                os.write(this.stringRespuesta(JSONIngreso).getBytes());
                os.close();
            } catch (ParseException ex) {
                Logger.getLogger(PerfilVehiculosHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String stringRespuesta(JSONObject pJSON) {
        JSONArray JSONvehiculos = new JSONArray();
        List<Vehiculo> vehiculos = ConexionBase.getInstancia().
                vehiculos(Integer.parseInt(pJSON.get("id").toString()));
        for (int index = 0; index < vehiculos.size(); index++) {
            Vehiculo vehiculo = vehiculos.get(index);
            JSONObject JSONVehiculo = new JSONObject();
            JSONVehiculo.put("modelo",vehiculo.getModelo());
            JSONVehiculo.put("placa", vehiculo.getPlaca());
            JSONVehiculo.put("numero",vehiculo.getCapacidad());
            JSONvehiculos.add(JSONVehiculo);
        }
        System.out.println(JSONvehiculos.toJSONString());
        return JSONvehiculos.toJSONString();
    }
}
