/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.LinkedList;

/**
 *
 * @author jeja1
 */
public class Usuario {
    private String _Nombre;
    private String _Apellidos;
    private int _Identificacion;
    private int _Telefono;
    private String _Correo;
    private int _TipoDeUsuario;
    private int _TipoDeCategoria;

    public Usuario(String pNombre, String pApellidos, int pIdentificacion,
            int pTelefono, String pCorreo) {
        this._Nombre = pNombre;
        this._Apellidos = pApellidos;
        this._Identificacion = pIdentificacion;
        this._Telefono = pTelefono;
        this._Correo = pCorreo;
    }
    
    
}
