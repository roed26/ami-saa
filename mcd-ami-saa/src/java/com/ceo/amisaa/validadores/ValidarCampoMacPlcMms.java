package com.ceo.amisaa.validadores;

import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoMacPlcMms")
public class ValidarCampoMacPlcMms implements Validator {

    @EJB
    private PlcMmsFacade ejbPlcMms;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbPlcMms.buscarPorMac(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta MAC ya pertenece a un PLC_MMS.", "Esta MAC ya pertenece a un PLC_MMS.");
            throw new ValidatorException(msg);
        }

    }
}
