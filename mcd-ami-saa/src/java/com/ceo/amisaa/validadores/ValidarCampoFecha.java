/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.validadores;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author geovanny
 */
@FacesValidator(value = "ValidarCampoFecha")
public class ValidarCampoFecha implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       
        Date fechaFin= (Date)component.getAttributes().get("fechaFin"); 
        
        if (value != null) {
            Date fecha = (Date) value;
            if (!validarFechaNacimiento(fecha)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha no puede ser mayor a la fecha actual.");
                throw new ValidatorException(msg);
            }
        }

    }

    public boolean validarFechaNacimiento(Date fecha) {

        Date fechaActual = new Date();
        return fecha.compareTo(fechaActual) <= 0;

    }

}
