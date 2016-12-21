package com.ceo.amisaa.validadores;

import com.ceo.amisaa.sessionbeans.UsuarioFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoNombreUsuario")
public class ValidarCampoNombreUsuario implements Validator {

    @EJB
    private UsuarioFacade ejbUsuario;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbUsuario.buscarPorNombreUsuario(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este nombre de usuario pertenece a otro usuario.");
            throw new ValidatorException(msg);
        }

    }
}
