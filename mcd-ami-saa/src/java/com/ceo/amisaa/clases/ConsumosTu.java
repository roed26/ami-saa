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
public class ConsumosTu {
    
    private String macTu;    
    private float consumo;    

    public ConsumosTu() {
    }           

    public ConsumosTu(String macTu, float consumo) {
        this.macTu = macTu;
        this.consumo = consumo;
    }

    public String getMacTu() {
        return macTu;
    }

    public void setMacTu(String macTu) {
        this.macTu = macTu;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }
    
    
    
}
