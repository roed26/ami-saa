/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.servicios;

import com.ceo.amisaa.entidades.PlcTu;

/**
 *
 * @author ROED26
 */
public class ResultadosEstadistica {

    private PlcTu plcTu;
    private int amarre;

    public ResultadosEstadistica() {
        this.amarre = 0;
    }

    public PlcTu getPlcTu() {
        return plcTu;
    }

    public void setPlcTu(PlcTu plcTu) {
        this.plcTu = plcTu;
    }

    public int getAmarre() {
        return amarre;
    }

    public void setAmarre(int amarre) {
        this.amarre = amarre;
    }

}
