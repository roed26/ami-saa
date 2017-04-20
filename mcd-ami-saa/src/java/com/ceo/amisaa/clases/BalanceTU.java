/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kathe
 */
public class BalanceTU {
    private String Fecha;
    private String Producto;
    private String PLC_TU;
    private String MacPLC_TU;
    private String Cedula;
    private String NombreCliente;
    
     public String getPLC_TU() {
        return PLC_TU;
    }

    public void setPLC_TU(String PLC_TU) {
        this.PLC_TU = PLC_TU;
    }
    
    

    public String getMacPLC_TU() {
        return MacPLC_TU;
    }

    public void setMacPLC_TU(String MacPLC_TU) {
        this.MacPLC_TU = MacPLC_TU;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String Producto) {
        this.Producto = Producto;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }
   
    public static List<String> getTableHeaders() {
        List<String> tableHeader = new ArrayList<String>();
        tableHeader.add("FECHA");
        tableHeader.add("PRODUCTO");
        tableHeader.add("PLC_TU");
        tableHeader.add(" MAC PLC_TU");
        tableHeader.add("CEDULA");
        tableHeader.add("CLIENTE");
 
        return tableHeader;
    }
}
