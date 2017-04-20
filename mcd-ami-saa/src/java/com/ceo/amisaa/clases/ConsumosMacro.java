/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.clases;

/**
 *
 * @author Natic_000
 */
public class ConsumosMacro {
    private String idMacro;    
    private float consumo;    

    public ConsumosMacro() {
    }
    
    public ConsumosMacro(String idMacro, float consumo) {
        this.idMacro = idMacro;
        this.consumo = consumo;
    }

    public String getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(String idMacro) {
        this.idMacro = idMacro;
    }
    
    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }
    
    
}
