/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jeja1
 */
public class Memoria {
    
    private static Memoria _Instancia = null;
    private List<Viaje> _Viajes;
    
    private Memoria(){
        this._Viajes = new LinkedList<Viaje>();
    }
    
    public static Memoria getInstancia(){
        if(_Instancia==null){
            _Instancia = new Memoria();
        }
        return _Instancia;
    }
    
    public void addViaje(int pCapacidad){
        this._Viajes.add(new Viaje(pCapacidad));
    }
    
    public void addViaje(Viaje pViaje){
        this._Viajes.add(pViaje);
    }
}
