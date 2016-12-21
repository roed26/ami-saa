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
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@Named("medidorController")
@SessionScoped
public class MedidorController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.MedidorFacade ejbFacade;
    private List<Medidor> items = null;
    private Medidor medidor;
    //String
    private String idMedidor;
    private String datoBusqueda;

    public MedidorController() {
        this.medidor = new Medidor();
    }

    @PostConstruct
    private void init() {
        this.cargarMedidores();
        

    }

    public Medidor getSelected() {
        return medidor;
    }

    public void setSelected(Medidor selected) {
        this.medidor = selected;
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

    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public Medidor prepareCreate() {
        medidor = new Medidor();
        initializeEmbeddableKey();
        return medidor;
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
        if (medidor != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(medidor);
                } else {
                    getFacade().remove(medidor);
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

    public void cancelarSeleccionMedidor() {
        idMedidor = "";
        items = ejbFacade.findAll();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarMedidor').hide()");
    }

    public void registrarMedidor() {
        ejbFacade.create(medidor);
        RequestContext requestContext = RequestContext.getCurrentInstance();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del medidor se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
        this.medidor = new Medidor();
        this.items = ejbFacade.findAll();
    }

    public void editarMedidor() {
        ejbFacade.edit(medidor);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.medidor = new Medidor();
        this.items = ejbFacade.findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del medidor se edito con exito."));
        requestContext.execute("PF('MedidorEditDialog').hide()");
        requestContext.execute("PF('mensajeRegistroExitoso').show()");

    }

    public void eliminarMedidor() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.medidor != null) {
            if (this.medidor.getIdProducto()==null) {
                this.ejbFacade.remove(this.medidor);
                requestContext.update("MedidorListForm");
                requestContext.execute("PF('eliminarMedidor').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.medidor = new Medidor();
                this.items = getFacade().findAll();
            } else {
                this.medidor = new Medidor();
                requestContext.execute("PF('eliminarMedidor').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }

    public void ventanaEliminar(Medidor medidor) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.medidor = medidor;
        requestContext.execute("PF('eliminarMedidor').show()");
    }

    public void reiniciarObj() {
        this.medidor = new Medidor();
    }

    public void buscarMedidor() {
        this.items = ejbFacade.buscarPorId(this.datoBusqueda.toLowerCase());
        this.datoBusqueda = "";
    }

    public void reiniciarCampo() {
        this.datoBusqueda = "";
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
