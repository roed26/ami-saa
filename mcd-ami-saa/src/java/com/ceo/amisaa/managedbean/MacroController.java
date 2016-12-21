package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.MacroFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@Named("macroController")
@SessionScoped
public class MacroController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.MacroFacade ejbFacade;
    private List<Macro> items = null;
    private Macro macro;
    
    //String
    private String datoBusqueda;

    public MacroController() {
        
    }

    @PostConstruct
    public void init(){
       this.macro= new Macro();
       this.macro.setEstado("A");
    }
    public Macro getMacro() {
        return macro;
    }

    public void setMacro(Macro macro) {
        this.macro = macro;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MacroFacade getFacade() {
        return ejbFacade;
    }

    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public Macro prepareCreate() {
        macro = new Macro();
        initializeEmbeddableKey();
        return macro;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MacroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MacroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MacroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            macro = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Macro> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void registrarMacro() {
        ejbFacade.create(this.macro);
        RequestContext requestContext = RequestContext.getCurrentInstance();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del macromedidor se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
        this.macro = new Macro();
        this.macro.setEstado("A");
        this.items = ejbFacade.findAll();
    }

    public void editarMacro() {
        ejbFacade.edit(macro);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.macro = new Macro();
        this.macro.setEstado("A");
        this.items = ejbFacade.findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del macromedidor se edito con exito."));
        requestContext.execute("PF('MacroEditDialog').hide()");
        requestContext.execute("PF('mensajeRegistroExitoso').show()");

    }

    public void eliminarMacro() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.macro != null) {
            if (this.macro.getIdTrafo()==null) {
                this.ejbFacade.remove(this.macro);
                requestContext.update("MacroListForm");
                requestContext.execute("PF('eliminarMacro').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.macro = new Macro();
                this.macro.setEstado("A");
                this.items = getFacade().findAll();
            } else {
                this.macro = new Macro();
                this.macro.setEstado("A");
                requestContext.execute("PF('eliminarMacro').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }

    public void ventanaEliminar(Macro macro) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.macro = macro;
        requestContext.execute("PF('eliminarMacro').show()");
    }

    public void reiniciarObj() {
        this.macro = new Macro();
        this.macro.setEstado("A");
    }

    public void buscarMacro() {
        this.items = ejbFacade.buscarPorId(this.datoBusqueda.toLowerCase());
        this.datoBusqueda = "";
    }

    public void reiniciarCampo() {
        this.datoBusqueda = "";
        this.items = ejbFacade.findAll();

    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (macro != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(macro);
                } else {
                    getFacade().remove(macro);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Macro getMacro(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Macro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Macro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Macro.class)
    public static class MacroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MacroController controller = (MacroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "macroController");
            return controller.getMacro(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Macro) {
                Macro o = (Macro) object;
                return getStringKey(o.getIdMacro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Macro.class.getName()});
                return null;
            }
        }

    }

}
