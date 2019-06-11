/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carpoolingteccontrolador;

import conexiones.Base.ConexionBase;
import conexiones.Http.ServidorHTTP;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeja1
 */
public class CarpoolingTECControlador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServidorHTTP servidor = ServidorHTTP.getInstancia();
        servidor.iniciar();
    }
    
}
