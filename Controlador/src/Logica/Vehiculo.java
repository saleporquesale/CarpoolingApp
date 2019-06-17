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
public class Vehiculo {
    
    private String _Modelo;
    private String _Placa;
    private int _Capacidad;
    

    public Vehiculo(String pModelo, String pPlaca,int pCapacidad) {
        this._Modelo = pModelo;
        this._Placa = pPlaca;
        this._Capacidad = pCapacidad;
    }

    public String getModelo() {
        return this._Modelo;
    }

    public int getCapacidad() {
        return this._Capacidad;
    }

    public String getPlaca() {
        return this._Placa;
    }
    
    
}
