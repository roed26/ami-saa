package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Ciudad;
import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.entidades.PlcTu;
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
import javax.faces.application.FacesMessage;
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
    private com.ceo.amisaa.sessionbeans.ProductoFacade ejbProducto;
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
    private Producto producto;
    private String idProducto;
    private String dato;
    private boolean trafoSeleccionado;
    private boolean clienteSeleccionado;
    private boolean medidorSeleccionado;
    private boolean plcTuSeleccionado;
    private Trafo trafo;
    private PlcTu plcTu;
    private String idCliente;
    private Cliente cliente;
    private Medidor medidor;
    private String idMedidor;

    public ProductoController() {
        this.producto = new Producto();
        this.producto.setEstado("A");
    }

    @PostConstruct
    private void init() {
        this.producto = new Producto();
        this.producto.setEstado("A");
        this.cargarProductos();
    }

    public Producto getSelected() {
        return producto;
    }

    public void setSelected(Producto selected) {
        this.producto = selected;
    }

    public PlcTu getPlcTu() {
        return plcTu;
    }

    public void setPlcTu(PlcTu plcTu) {
        this.plcTu = plcTu;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Medidor getMedidor() {
        return medidor;
    }

    public void setMedidor(Medidor medidor) {
        this.medidor = medidor;
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

    public boolean isPlcTuSeleccionado() {
        return plcTuSeleccionado;
    }

    public void setPlcTuSeleccionado(boolean plcTuSeleccionado) {
        this.plcTuSeleccionado = plcTuSeleccionado;
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
        return ejbProducto;
    }

    public Producto prepareCreate() {
        producto = new Producto();
        initializeEmbeddableKey();
        return producto;
    }

    public void buscarPorId() {
        this.items = getFacade().buscarPorId(idProducto);
        idProducto = "";
    }

    public void reiniciarCampoBusqueda() {
        this.idProducto = "";
        this.items = ejbProducto.findAll();

    }

    public List<Producto> getListaProductosActivos() {
        items = ejbProducto.findAll();
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

    public List<Producto> listaProductosDelPlcMc(PlcMc plcMc) {

        List<Producto> lista =  new ArrayList<>();
        listaClientes = new ArrayList<>();
        if (items.size() == 0) {
            items = ejbProducto.findAll();
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getMacPlcMc() != null) {
                if (items.get(i).getMacPlcMc().getMacPlcMc().equals(plcMc.getMacPlcMc())) {
                    lista.add(items.get(i));
                    Cliente cliente = ejbCliente.buscarPorCedula(items.get(i).getCedula().getCedula());
                    listaClientes.add(cliente);
                }
            }
        }

        return lista;
    }

    public List<Producto> getListaProductosEnElPlcTu() {

        List<Producto> listaF = new ArrayList<>();
        listaF.add(plcTu.getIdProducto());
        return listaF;
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

    public List<Producto> getListaProductosEnElTrafo() {
        List<Producto> lista = new ArrayList<>();
        lista = ejbProducto.buscarListaProductosTrafo(trafo);

        return lista;
    }

    public void ventanaEliminar(Producto producto) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.producto = producto;
        requestContext.execute("PF('eliminarProducto').show()");
    }

    public void eliminarProducto() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.producto != null) {
            if (this.producto.getCedula() == null) {
                this.ejbProducto.remove(this.producto);
                requestContext.update("ProductoListForm");
                requestContext.execute("PF('eliminarProducto').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.producto = new Producto();
                this.producto.setEstado("A");

            } else {
                this.producto = new Producto();
                this.producto.setEstado("A");
                requestContext.execute("PF('eliminarProducto').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
                requestContext.update("ProductoListForm");
            }
        }
        reiniciarCampoBusqueda();
    }

    public void reiniciarObj() {
        this.producto = new Producto();
        this.producto.setEstado("A");
    }

    public void cancelarSeleccionProducto() {
        idProducto = "";
        items = ejbProducto.findAll();
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

    public void registrarProducto() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        ejbProducto.create(this.producto);
        items = ejbProducto.findAll();
        this.producto = new Producto();
        this.producto.setEstado("A");

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del producto se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");

    }

    public void editarInfoProducto() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.producto != null) {
            this.ejbProducto.edit(this.producto);
            requestContext.execute("PF('ProductoEditDialog').hide()");
            requestContext.execute("PF('edicionCorrecta').show()");
            this.producto = new Producto();
            this.producto.setEstado("A");

        }
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

    public List<Producto> getListaProductosDeCliente() {
        List<Producto> lista = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCedula() != null) {
                if (items.get(i).getCedula().getCedula().equals(cliente.getCedula())) {
                    lista.add(items.get(i));
                }
            }

        }

        return lista;
    }

    public List<Producto> getListaProductosConMedidor() {
        List<Producto> lista = new ArrayList<>();
        lista.add(medidor.getIdProducto());
        return lista;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public List<Producto> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (producto != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(producto);
                } else {
                    getFacade().remove(producto);
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
        this.items = ejbProducto.findAll();

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
        this.cliente = cliente;
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

    public void seleccionarMedidorDesvincular(Medidor medidor) {
        this.medidor = medidor;

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
        ejbProducto.edit(producto);
        this.trafoSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionProducto");
        this.trafo = new Trafo();

    }

    public void desvincularProductoDeTrafo(Producto producto) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        producto.setIdTrafo(null);
        ejbProducto.edit(producto);
        this.trafoSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionProducto");
        this.trafo = new Trafo();
        this.items = ejbProducto.findAll();

    }

    public void desvincularProductoDePlcTu() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        plcTu.setIdProducto(null);
        ejbPlcTu.edit(plcTu);
        this.plcTuSeleccionado = false;

        requestContext.execute("PF('mensajeDesvinculo').show()");
        requestContext.update("productoListForm");
        this.plcTu = new PlcTu();
        this.items = ejbProducto.findAll();

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
        ejbProducto.edit(producto);
        this.clienteSeleccionado = false;

        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        requestContext.update("informacionCliente");
        this.cliente = new Cliente();

    }

    public void desvincularProductoDeCliente(Producto producto) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        producto.setCedula(null);
        ejbProducto.edit(producto);
        this.clienteSeleccionado = false;
        requestContext.execute("PF('mensajeVinculo').show()");
        requestContext.update("productoListForm");
        this.cliente = new Cliente();
        this.items = ejbProducto.findAll();

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

    public void desvincularProductoDeMedidor() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.medidor.setIdProducto(null);
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
        this.plcTuSeleccionado = false;
    }

    public void seleccionarPlcTu(PlcTu plcTu) {
        this.plcTu = plcTu;
        plcTuSeleccionado = true;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarPlcTu').hide()");
        requestContext.update("productoListForm");
        requestContext.update("informacionCliente");

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
