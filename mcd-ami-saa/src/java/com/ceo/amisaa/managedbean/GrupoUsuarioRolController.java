package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.GrupoUsuarioRol;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.GrupoUsuarioRolFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("grupoUsuarioRolController")
@SessionScoped
public class GrupoUsuarioRolController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.GrupoUsuarioRolFacade ejbFacade;
    private List<GrupoUsuarioRol> items = null;
    private GrupoUsuarioRol selected;

    public GrupoUsuarioRolController() {
    }

    public GrupoUsuarioRol getSelected() {
        return selected;
    }

    public void setSelected(GrupoUsuarioRol selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getGrupoUsuarioRolPK().setCedula(selected.getUsuario().getCedula());
        selected.getGrupoUsuarioRolPK().setIdRol(selected.getRol().getIdRol());
    }

    protected void initializeEmbeddableKey() {
        selected.setGrupoUsuarioRolPK(new com.ceo.amisaa.entidades.GrupoUsuarioRolPK());
    }

    private GrupoUsuarioRolFacade getFacade() {
        return ejbFacade;
    }

    public GrupoUsuarioRol prepareCreate() {
        selected = new GrupoUsuarioRol();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleRolUsuario").getString("GrupoUsuarioRolCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleRolUsuario").getString("GrupoUsuarioRolUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleRolUsuario").getString("GrupoUsuarioRolDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<GrupoUsuarioRol> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleRolUsuario").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleRolUsuario").getString("PersistenceErrorOccured"));
            }
        }
    }

    public GrupoUsuarioRol getGrupoUsuarioRol(com.ceo.amisaa.entidades.GrupoUsuarioRolPK id) {
        return getFacade().find(id);
    }

    public List<GrupoUsuarioRol> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<GrupoUsuarioRol> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = GrupoUsuarioRol.class)
    public static class GrupoUsuarioRolControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoUsuarioRolController controller = (GrupoUsuarioRolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoUsuarioRolController");
            return controller.getGrupoUsuarioRol(getKey(value));
        }

        com.ceo.amisaa.entidades.GrupoUsuarioRolPK getKey(String value) {
            com.ceo.amisaa.entidades.GrupoUsuarioRolPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.ceo.amisaa.entidades.GrupoUsuarioRolPK();
            key.setIdRol(Integer.parseInt(values[0]));
            key.setCedula(values[1]);
            return key;
        }

        String getStringKey(com.ceo.amisaa.entidades.GrupoUsuarioRolPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdRol());
            sb.append(SEPARATOR);
            sb.append(value.getCedula());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GrupoUsuarioRol) {
                GrupoUsuarioRol o = (GrupoUsuarioRol) object;
                return getStringKey(o.getGrupoUsuarioRolPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GrupoUsuarioRol.class.getName()});
                return null;
            }
        }

    }

}
