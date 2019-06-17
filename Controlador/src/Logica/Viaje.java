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
public class Viaje {
    
    private static int _ID = 0;
    private List<Acompañante> _Acompañantes;
    private int _Capacidad;
    
    public Viaje(int pCapacidad){
        _ID++;
        this._Capacidad = pCapacidad;
        this._Acompañantes = new LinkedList<Acompañante>();
    }
    
    public void addAcompañante(int pID){
        this._Acompañantes.add(new Acompañante(pID));
    }
    
    public void confirmarAcompañante(int pId){
        for (int index = 0; index < this._Acompañantes.size(); index++) {
            if(pId == this._Acompañantes.get(index).getIdentificacion()){
                this._Acompañantes.get(index).confirmar();
            }
        }
    }
    
    public boolean mayorA70Porciento(){
        int confirmados = 0;
        for (int index = 0; index < this._Acompañantes.size(); index++) {
            if(this._Acompañantes.get(index).estaConfirmado()){
                confirmados++;
            }
        }
        if(((confirmados/this._Capacidad)*100)>=70){
            return true;
        }
        return false;
    }
}
