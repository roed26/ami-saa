package com.ceo.amisaa.validadores;

import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoIdTrafo")
public class ValidarCampoIdTrafo implements Validator {

    @EJB
    private TrafoFacade ejbTrafo;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbTrafo.buscarPorIdBool(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este identificador pertenece a otro trafo");
            throw new ValidatorException(msg);
        }

    }
}
