package com.ceo.amisaa.validadores;

//import com.unicauca.gymadmdoc.sessionbeans.MuUsuarioFacade;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoIdPlcMc")
public class ValidarCampoIdPlcMc implements Validator {

    @EJB
    private PlcMcFacade ejbPlcMc;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbPlcMc.buscarPorId(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este id ya pertenece a un PLC_MC.", "Este id ya pertenece a un PLC_MC.");
            throw new ValidatorException(msg);
        }

    }
}
