package com.ceo.amisaa.validadores;

//import com.unicauca.gymadmdoc.sessionbeans.MuUsuarioFacade;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoMacPlcMc")
public class ValidarCampoMacPlcMc implements Validator {

    @EJB
    private PlcMcFacade ejbPlcMc;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbPlcMc.buscarPorMac(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Esta MAC ya pertenece a un PLC_MC.", "Esta MAC ya pertenece a un PLC_MC.");
            throw new ValidatorException(msg);
        }

    }
}
