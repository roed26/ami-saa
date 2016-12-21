package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.ClienteFacade;
import com.ceo.amisaa.sessionbeans.MedidorFacade;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
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
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@Named("productoController")
@SessionScoped
public class ProductoController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.ProductoFacade ejbFacade;
    @EJB
    private ClienteFacade ejbCliente;
    @EJB
    private MedidorFacade ejbMedidor;
    @EJB
    private PlcTuFacade ejbPlcTu;
    @EJB
    private PlcMcFacade ejbPlcMc;

    private List<Producto> items = null;
    private ArrayList<Cliente> listaClientes;
    private Producto selected;
    private String idProducto;
    private String dato;
    private boolean trafoSeleccionado;
    private boolean clienteSeleccionado;
    private boolean medidorSeleccionado;
    private Trafo trafo;
    private String idCliente;
    private Cliente cliente;
    private Medidor medidor;
    private String idMedidor;

    public ProductoController() {
    }

    @PostConstruct
    private void init() {
        this.cargarProductos();
    }

    public Producto getSelected() {
        return selected;
    }

    public void setSelected(Producto selected) {
        this.selected = selected;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
    }

    public Trafo getTrafo() {
        return trafo;
    }

    public void setTrafo(Trafo trafo) {
        this.trafo = trafo;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public boolean isMedidorSeleccionado() {
        return medidorSeleccionado;
    }

    public void setMedidorSeleccionado(boolean medidorSeleccionado) {
        this.medidorSeleccionado = medidorSeleccionado;
    }

    public boolean isClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(boolean clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductoFacade getFacade() {
        return ejbFacade;
    }

    public Producto prepareCreate() {
        selected = new Producto();
        initializeEmbeddableKey();
        return selected;
    }

    public void buscarPorId() {
        this.items = getFacade().buscarPorId(idProducto);
        idProducto = "";
    }

    public List<Producto> getListaProductosActivos() {
        List<Producto> lista = new ArrayList<>();
        listaClientes = new ArrayList<>();
        
              
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEstado().equalsIgnoreCase("A") && items.get(i).getCedula() != null && items.get(i).getIdTrafo() != null) {
                if (ejbPlcTu.buscarIdProducto(items.get(i)) == null && items.get(i).getMacPlcMc() == null) {
                    Medidor medidor = ejbMedidor.buscarMedidorDeProducto(items.get(i));
                    if (medidor != null) {
                        lista.add(items.get(i));
                        Cliente cliente = ejbCliente.buscarPorCedula(items.get(i).getCedula().getCedula());
                        if (cliente != null) {
                            listaClientes.add(cliente);
                        }
                    }
                }

            }
        }

        return lista;
    }

    public List<Producto> getListaProductosSinTrafo() {
        List<Producto> lista = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEstado().equalsIgnoreCase("A") && items.get(i).getIdTrafo() == null) {
                lista.add(items.get(i));
            }
        }

        return lista;
    }

    public void cancelarSeleccionProducto() {
        idProducto = "";
        items = ejbFacade.findAll();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarProducto').hide()");
        requestContext.update("plcTuListForm");
        requestContext.update("informacionProducto");
        requestContext.update("panel");
    }

    public List<Producto> getListaProductosSinCliente() {
        List<Producto> lista = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEstado().equalsIgnoreCase("A") && items.get(i).getCedula() == null) {
                lista.add(items.get(i));
            }
        }

        return lista;
    }

    public List<Producto> getListaProductosSinMedidor() {
        List<Producto> lista = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getEstado().equalsIgnoreCase("A") && ejbMedidor.buscarMedidorDeProducto(items.get(i)) == null) {
                lista.add(items.get(i));
            }
        }

        return lista;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Producto> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Producto getProducto(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Producto> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Producto> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private void cargarProductos() {
        this.items = ejbFacade.findAll();

    }

    public void seleccionarTrafo(Trafo trafo) {
        this.trafo = trafo;

        trafoSeleccionado = true;

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionProducto");
    }

    public void seleccionarCliente(Cliente cliente) {
        this.idCliente = cliente.getCedula();

        clienteSeleccionado = true;

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarCliente').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionCliente");
    }

    public void seleccionarMedidor(Medidor medidor) {
        this.idMedidor = medidor.getIdMedidor();

        medidorSeleccionado = true;

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarMedidor').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionMedidor");
    }

    public void cancelarSeleccion() {

        trafoSeleccionado = false;
        this.trafo = new Trafo();

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionProducto");
    }

    public void cancelarSeleccionCliente() {

        clienteSeleccionado = false;
        this.cliente = new Cliente();

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarCliente').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionCliente");
    }

    public void cancelarSeleccionMedidor() {
        medidorSeleccionado = false;
        medidor = new Medidor();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarMedidor').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionMedidor");

    }

    public void vincularProductoATrafo(Producto producto) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        producto.setIdTrafo(trafo);
        ejbFacade.edit(producto);
        this.trafoSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionProducto");
        this.trafo = new Trafo();

    }

    public void vincularProductoACliente(Producto producto) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        this.cliente = ejbCliente.buscarPorCedula(idCliente);
        producto.setCedula(cliente);
        ejbFacade.edit(producto);
        this.clienteSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionCliente");
        this.cliente = new Cliente();

    }

    public void vincularProductoAMedidor(Producto producto) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        this.medidor = ejbMedidor.buscarMedidorPorId(idMedidor);
        medidor.setIdProducto(producto);
        ejbMedidor.edit(medidor);

        this.medidorSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionMedidor");
        this.medidor = new Medidor();

    }

    public void reiniciarVariable() {
        this.trafoSeleccionado = false;
        this.clienteSeleccionado = false;
        this.medidorSeleccionado = false;
    }

    @FacesConverter(forClass = Producto.class)
    public static class ProductoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductoController controller = (ProductoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productoController");
            return controller.getProducto(getKey(value));
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
            if (object instanceof Producto) {
                Producto o = (Producto) object;
                return getStringKey(o.getIdProducto());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Producto.class.getName()});
                return null;
            }
        }

    }

}
