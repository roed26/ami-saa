
package com.ceo.amisaa.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="ValidarCampoSoloNumerosYPuntos")
public class ValidarCampoSoloNumerosYpuntos implements Validator 
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        Pattern patron = Pattern.compile("[^0-9.]");
        Matcher encaja = patron.matcher(texto);        
        if(encaja.find())
        {
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo solo números y puntos.","Campo solo números y puntos.");
            throw new ValidatorException(msg);
        }
    }
    
}