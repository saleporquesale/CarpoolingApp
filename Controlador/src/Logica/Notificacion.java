/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author jeja1
 */
public class Notificacion {
    private int _ID;
    private String _Texto;
    private int _Tipo;

    public Notificacion(int pID, String pTexto, int pTipo) {
        this._ID = pID;
        this._Texto = pTexto;
        this._Tipo = pTipo;
    }

    public int getID() {
        return this._ID;
    }

    public String getTexto() {
        return this._Texto;
    }

    public int getTipo() {
        return this._Tipo;
    }
    
    
}
