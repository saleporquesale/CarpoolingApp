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
public class GeneradorCodigos {
    
    private int _Codigo;
    private LocalDate _Fecha;
    private static GeneradorCodigos _Instancia = null;
    
    private GeneradorCodigos(){
        this._Codigo = 0;
        this._Fecha = LocalDate.now();
    }
    
    public static GeneradorCodigos getInstancia(){
        if(_Instancia==null){
            _Instancia = new GeneradorCodigos();
        }
        return _Instancia;
    }
    
    public int generar(){
        int generado = this._Codigo;
        this._Codigo+=102;
        if(this._Codigo>9999){
            this._Codigo-=9999;
        }
        return generado;
    }
}
