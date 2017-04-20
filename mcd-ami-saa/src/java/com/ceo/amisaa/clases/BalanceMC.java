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
public class BalanceMC {
    private String Fecha;
    private String Producto;
    private String PLC_MC;
    private String id_medidor;
    private String Cedula;
    private String NombreCliente;

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

    public String getPLC_MC() {
        return PLC_MC;
    }

    public void setPLC_MC(String PLC_MC) {
        this.PLC_MC = PLC_MC;
    }
    
     public String getId_medidor() {
        return id_medidor;
    }

    public void setId_medidor(String id_medidor) {
        this.id_medidor = id_medidor;
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
        tableHeader.add("PLC_MC");
        tableHeader.add("ID MEDIDOR");
        tableHeader.add("CEDULA");
        tableHeader.add("CLIENTE");
 
        return tableHeader;
    }
}
