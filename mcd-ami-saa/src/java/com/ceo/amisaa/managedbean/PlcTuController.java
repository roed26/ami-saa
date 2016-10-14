package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;

import java.io.Serializable;
import java.sql.Time;
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

@Named("plcTuController")
@SessionScoped
public class PlcTuController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.PlcTuFacade ejbPlcTu;

    private List<PlcTu> items = null;
    private List<PlcTu> listaPlcTuSinVinculo;
    private PlcTu objPlcTu;
    private String dato;
    private Producto producto;
    private boolean productoSeleccionado;

    public PlcTuController() {
        this.objPlcTu = new PlcTu();
        this.listaPlcTuSinVinculo = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        productoSeleccionado = false;
    }

    public PlcTu getSelected() {
        return objPlcTu;
    }

    public void setSelected(PlcTu selected) {
        this.objPlcTu = selected;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PlcTuFacade getFacade() {
        return ejbPlcTu;
    }

    public boolean isProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(boolean productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<PlcTu> getListaPlcTuSinVinculo() {
        this.getItems();
        listaPlcTuSinVinculo = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getIdProducto() == null) {
                listaPlcTuSinVinculo.add(items.get(i));
            }
        }
        return listaPlcTuSinVinculo;
    }

    public void setListaPlcTuSinVinculo(List<PlcTu> listaPlcTuSinVinculo) {
        this.listaPlcTuSinVinculo = listaPlcTuSinVinculo;
    }

    public void ventanaEliminar(PlcTu selected) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.objPlcTu = selected;
        requestContext.execute("PF('eliminarPlcTu').show()");
    }

    public void registrarPlcTu() {

        this.ejbPlcTu.create(objPlcTu);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        this.objPlcTu = new PlcTu();
        this.items = getFacade().findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El PLC_TU se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void newObj() {
        this.objPlcTu = new PlcTu();
    }
    
    public void cancelarEditar(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('PlcTuEditDialog').hide()");
        this.objPlcTu = new PlcTu();
    
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void editarInfoPlcTu() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcTu != null) {
            this.ejbPlcTu.edit(this.objPlcTu);
            requestContext.execute("PF('PlcTuEditDialog').hide()");
            requestContext.execute("PF('edicionCorrecta').show()");
            this.objPlcTu = new PlcTu();
        }
    }

    public void eliminarPlcTu() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcTu != null) {
            if (true) {
                this.ejbPlcTu.remove(this.objPlcTu);
                requestContext.update("PlcTuListForm");
                requestContext.execute("PF('eliminarPlcTu').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.objPlcTu = new PlcTu();
                this.items = getFacade().findAll();
            } else {
                this.objPlcTu = new PlcTu();
                requestContext.execute("PF('eliminarPlcTu').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }

    public List<PlcTu> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void vincularProducto(PlcTu plcTu) {
        this.objPlcTu = plcTu;
        this.objPlcTu.setIdProducto(producto);
        this.ejbPlcTu.edit(this.objPlcTu);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        productoSeleccionado = false;
        requestContext.execute("PF('mensajeVinculo').show()");

    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (objPlcTu != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(objPlcTu);
                } else {
                    getFacade().remove(objPlcTu);
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

    public PlcTu getPlcTu(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<PlcTu> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PlcTu> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void buscarDato() {
        this.items = ejbPlcTu.buscarPorDato(this.dato.toLowerCase());
    }

    public void seleccionarProducto(Producto producto) {
       
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        
        requestContext.execute("PF('seleccionarProducto').hide()"); 
        
        this.producto = producto;
        this.productoSeleccionado = true;
        requestContext.update("plcTuListForm");
        requestContext.update("informacionProducto");
        requestContext.update("panel");
        

    }

    public void reiniciarVariables() {
        productoSeleccionado = false;

    }

    

    @FacesConverter(forClass = PlcTu.class)
    public static class PlcTuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlcTuController controller = (PlcTuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "plcTuController");
            return controller.getPlcTu(getKey(value));
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
            if (object instanceof PlcTu) {
                PlcTu o = (PlcTu) object;
                return getStringKey(o.getIdPlcTu());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PlcTu.class.getName()});
                return null;
            }
        }

    }

}
