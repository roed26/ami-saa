package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;

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

@Named("plcMcController")
@SessionScoped
public class PlcMcController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.PlcMcFacade ejbPlcMc;
    @EJB
    private com.ceo.amisaa.sessionbeans.ProductoFacade ejbProducto;

    private List<PlcMc> items = null;
    private PlcMc objPlcMc;
    private String dato;
    private boolean estadoActivo;
    private boolean plcMcSeleccionado;
    private List<Producto> productos;
    private String idPlcMc;
    private int numero=0;

    public PlcMcController() {
        this.objPlcMc = new PlcMc();

    }

    @PostConstruct
    private void init() {

    }

    public PlcMc getSelected() {
        return this.objPlcMc;
    }

    public void setSelected(PlcMc selected) {
        this.objPlcMc = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getIdPlcMc() {
        return idPlcMc;
    }

    public void setIdPlcMc(String idPlcMc) {
        this.idPlcMc = idPlcMc;
    }

    public boolean isPlcMcSeleccionado() {
        return plcMcSeleccionado;
    }
    
    public void setPlcMcSeleccionado(boolean plcMcSeleccionado) {
        this.plcMcSeleccionado = plcMcSeleccionado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    private PlcMcFacade getFacade() {
        return ejbPlcMc;
    }

    public PlcMc prepareCreate() {
        objPlcMc = new PlcMc();
        initializeEmbeddableKey();
        return objPlcMc;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PlcMcCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void editarInfoPlcMc() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcMc != null) {
            this.ejbPlcMc.edit(this.objPlcMc);
            requestContext.execute("PF('PlcMcEditDialog').hide()");
            requestContext.execute("PF('edicionCorrecta').show()");
            this.objPlcMc = new PlcMc();
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PlcMcDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            objPlcMc = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PlcMc> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public boolean isEstadoActivo() {
        if (objPlcMc.getEstado().equalsIgnoreCase("A")) {
            estadoActivo = true;
        } else {
            estadoActivo = false;
        }

        return estadoActivo;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (objPlcMc != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(objPlcMc);
                } else {
                    getFacade().remove(objPlcMc);
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

    public PlcMc getPlcMc(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<PlcMc> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PlcMc> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PlcMc.class)
    public static class PlcMcControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlcMcController controller = (PlcMcController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "plcMcController");
            return controller.getPlcMc(getKey(value));
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
            if (object instanceof PlcMc) {
                PlcMc o = (PlcMc) object;
                return getStringKey(o.getIdPlcMc());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PlcMc.class.getName()});
                return null;
            }
        }

    }

    public void buscarDato() {
        this.items = ejbPlcMc.buscarPorDato(this.dato.toLowerCase());
        this.dato = "";
    }

    public void reiniciarCampo() {
        this.dato = "";
        this.items = ejbPlcMc.findAll();

    }

    public void ventanaEliminar(PlcMc selected) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.objPlcMc = selected;
        requestContext.execute("PF('eliminarPlcMc').show()");
    }

    public void eliminarPlcMc() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcMc != null) {
            if (true) {
                this.ejbPlcMc.remove(this.objPlcMc);
                requestContext.update("PlcMcListForm");
                requestContext.execute("PF('eliminarPlcMc').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.objPlcMc = new PlcMc();
            } else {
                requestContext.execute("PF('eliminarPlcMc').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }

    public void cancelarSeleccion() {
        plcMcSeleccionado = false;
        this.objPlcMc = new PlcMc();
        dato="";
        items=ejbPlcMc.findAll();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarPlcMc').hide()");
        requestContext.update("ProductosListForm");
        requestContext.update("informacionPlcMc");
    }

    public void newObj() {
        this.objPlcMc = new PlcMc();
    }

    public void seleccionarPlcMc(PlcMc plcMc) {
        idPlcMc = plcMc.getIdPlcMc();
        plcMcSeleccionado = true;
        dato = "";
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("ProductosListForm");
        requestContext.update("informacionPlcMc");
        requestContext.execute("PF('seleccionarPlcMc').hide()");

    }

    public void vincularProductos() {
        this.objPlcMc = ejbPlcMc.buscarPorId(idPlcMc);
        if (this.productos.size() > 0) {
            for (int i = 0; i < this.productos.size(); i++) {
                Producto producto = this.productos.get(i);
                producto.setMacPlcMc(objPlcMc);
                this.ejbProducto.edit(producto);
            }
            this.plcMcSeleccionado = false;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("informacionPlcMc");
            requestContext.update("ProductosListForm");
            requestContext.execute("PF('mensajeVinculo').show()");
            this.objPlcMc = new PlcMc();
        }
    }

    public void registrarPlcMc() {
        this.ejbPlcMc.create(objPlcMc);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        this.objPlcMc = new PlcMc();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El PLC_MC se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void reiniciarVariables() {
        this.plcMcSeleccionado = false;

    }

}
