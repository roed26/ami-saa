package com.ceo.amisaa.validadores;

import com.ceo.amisaa.sessionbeans.ProductoFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoIdProducto")
public class ValidarCampoIdProducto implements Validator {

    @EJB
    private ProductoFacade ejbProducto;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbProducto.buscarPorIdBool(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este identificador pertenece a otro producto");
            throw new ValidatorException(msg);
        }

    }
}
