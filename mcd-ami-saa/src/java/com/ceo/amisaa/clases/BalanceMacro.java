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
public class BalanceMacro {
    private String fecha;
    private String id_macro;
   // private String id_notificacion;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_macro() {
        return id_macro;
    }

    public void setId_macro(String id_macro) {
        this.id_macro = id_macro;
    }

    

    
    public static List<String> getTableHeaders() {
        List<String> tableHeader = new ArrayList<String>();
        tableHeader.add("FECHA");
        tableHeader.add("MACROMEDIDOR");
       // tableHeader.add("ID NOTIFICACIÓN");
        
 
        return tableHeader;
    }
    
}
