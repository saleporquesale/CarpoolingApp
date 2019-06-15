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
    private int _Identificacion;
    private int _Telefono;
    private String _Correo;
    private int _TipoDeUsuario;
    private int _TipoDeCategoria;

    public Usuario(String pNombre, int pIdentificacion,
            int pTelefono, String pCorreo) {
        this._Nombre = pNombre;
        this._Identificacion = pIdentificacion;
        this._Telefono = pTelefono;
        this._Correo = pCorreo;
    }

    public String getNombre() {
        return this._Nombre;
    }

    public int getIdentificacion() {
        return this._Identificacion;
    }

    public int getTelefono() {
        return this._Telefono;
    }

    public String getCorreo() {
        return this._Correo;
    }

    public int getTipoDeUsuario() {
        return this._TipoDeUsuario;
    }

    public int getTipoDeCategoria() {
        return this._TipoDeCategoria;
    }
    
    
    
    
}
