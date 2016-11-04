package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.MedidorFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("medidorController")
@SessionScoped
public class MedidorController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.MedidorFacade ejbFacade;
    private List<Medidor> items = null;
    private Medidor selected;
    private String idMedidor;

    public MedidorController() {
    }

    @PostConstruct
    private void init() {
        this.cargarMedidores();
    }

    public Medidor getSelected() {
        return selected;
    }

    public void setSelected(Medidor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MedidorFacade getFacade() {
        return ejbFacade;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public Medidor prepareCreate() {
        selected = new Medidor();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MedidorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MedidorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MedidorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Medidor> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void buscarPorId() {
        this.items = getFacade().buscarPorId(idMedidor);

    }

    public List<Medidor> getListaMedidoresActivos() {
        List<Medidor> lista = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIdProducto() == null && items.get(i).getEstado().equalsIgnoreCase("FA")) {
                lista.add(items.get(i));
            }
        }
        return lista;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Medidor getMedidor(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Medidor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Medidor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private void cargarMedidores() {
        this.items = ejbFacade.findAll();
    }

    @FacesConverter(forClass = Medidor.class)
    public static class MedidorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MedidorController controller = (MedidorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "medidorController");
            return controller.getMedidor(getKey(value));
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
            if (object instanceof Medidor) {
                Medidor o = (Medidor) object;
                return getStringKey(o.getIdMedidor());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Medidor.class.getName()});
                return null;
            }
        }

    }

}
