/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.servicios;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ROED26
 */
@FacesConverter("agregarCeroVersion")
    public class AgregarCeroVersion implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String version) {
            
            if(version.length()>0){
                String texto = String.valueOf(version);
            
                if(texto.charAt(texto.length()-1)=='.'){
                    version=version+"0";
                }
            }            
            return version;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
            return (modelValue != null) ? modelValue.toString() : "";
        }

    }
