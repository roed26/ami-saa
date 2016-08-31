package com.ceo.amisaa.validadores;

//import com.unicauca.gymadmdoc.sessionbeans.MuUsuarioFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoIdPlcTu")
public class ValidarCampoIdPlcTu implements Validator {

    @EJB
    private PlcTuFacade ejbPlcTu;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbPlcTu.buscarPorId(texto))
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este id ya pertenece a un PLC_TU.", "Este id ya pertenece a un PLC_TU.");
            throw new ValidatorException(msg);
        }

    }
}
