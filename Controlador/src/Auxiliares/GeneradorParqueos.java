/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliares;

import java.time.LocalDate;

/**
 *
 * @author jeja1
 */
public class GeneradorParqueos {
    
    private int _Numero;
    private static GeneradorParqueos _Instancia = null;
    
    private GeneradorParqueos(){
        this._Numero = 0;
    }
    
    public static GeneradorParqueos getInstancia(){
        if(_Instancia==null){
            _Instancia = new GeneradorParqueos();
        }
        return _Instancia;
    }
    
    public int generar(){
        int generado = this._Numero;
        this._Numero+=12;
        if(this._Numero>200){
            this._Numero-=200;
        }
        return generado;
    }
}
