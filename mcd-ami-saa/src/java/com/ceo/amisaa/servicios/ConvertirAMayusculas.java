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
@FacesConverter("toUpperCaseConverter")
    public class ConvertirAMayusculas implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
            return (submittedValue != null) ? submittedValue.toUpperCase() : null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
            return (modelValue != null) ? modelValue.toString() : "";
        }

    }
