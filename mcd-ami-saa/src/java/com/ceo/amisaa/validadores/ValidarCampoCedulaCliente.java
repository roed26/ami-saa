package com.ceo.amisaa.validadores;

//import com.unicauca.gymadmdoc.sessionbeans.MuUsuarioFacade;
import com.ceo.amisaa.sessionbeans.ClienteFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import com.ceo.amisaa.sessionbeans.UsuarioFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "ValidarCampoCedulaCliente")
public class ValidarCampoCedulaCliente implements Validator {

    @EJB
    private ClienteFacade ejbCliente;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);

        if(ejbCliente.buscarPorCedula(texto)!=null)
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este número de cédula pertenece a otro cliente");
            throw new ValidatorException(msg);
        }

    }
}
