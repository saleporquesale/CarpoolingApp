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
public class Acompañante extends Usuario{
    
    private boolean _Confirmado;
    
    public Acompañante(int pIdentificacion) {
        super(pIdentificacion);
        this._Confirmado = false;
    }
    
    public boolean estaConfirmado(){
        return this._Confirmado;
    }
    
    public void confirmar(){
        this._Confirmado = !this._Confirmado;
    }
    
}