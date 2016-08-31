package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.ClienteFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.MacroFacade;
import com.ceo.amisaa.sessionbeans.MedidorFacade;
import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

@Named("trafoController")
@SessionScoped
public class TrafoController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.TrafoFacade ejbFacade;
    @EJB
    private ProductoFacade ejbProducto;
    @EJB
    private MedidorFacade ejbMedidor;
    @EJB
    private PlcTuFacade ejbPlcTu;
    @EJB
    private PlcMmsFacade ejbPlcMms;
    @EJB
    private MacroFacade ejbMacro;
    @EJB
    private TrafoFacade ejbTrafo;
    @EJB
    private EventosAmarreFacade ejbEventosAmarre;
    @EJB
    private ClienteFacade ejbCliente;

    private List<Trafo> items = null;
    private Trafo selected;
    private boolean trafoSeleccionado;
    private String idTrafo;
    private Producto producto;
    private Medidor medidor;
    private PlcTu plcTu;
    private PlcMms plcMms;
    private Macro macro;
    private Trafo trafo;
    private Cliente cliente;
    private List<EventosAmarre> eventoAmarre;
    private List<PlcTu> listPlcTu;
    private List<Medidor> listaMedidores;
    private List<Producto> listaProductos;
    private List<Cliente> listaClientes;
    private ArrayList<Boolean> estadosDeAmarre;
    private ArrayList<String> resultado;
    private boolean clientes;
    private String trama;

    public TrafoController() {
    }

    @PostConstruct
    private void init() {
        this.cargarTrafos();
        listaMedidores = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();
        resultado = new ArrayList<>();
        estadosDeAmarre = new ArrayList<>();

    }

    public Trafo getSelected() {
        return selected;
    }

    public void setSelected(Trafo selected) {
        this.selected = selected;
    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TrafoFacade getFacade() {
        return ejbFacade;
    }

    public boolean isClientes() {
        return clientes;
    }

    public void setClientes(boolean clientes) {
        this.clientes = clientes;
    }

    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
    }

    public List<Medidor> getListaMedidores() {
        return listaMedidores;
    }

    public void setListaMedidores(List<Medidor> listaMedidores) {
        this.listaMedidores = listaMedidores;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Trafo prepareCreate() {
        selected = new Trafo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TrafoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TrafoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TrafoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Trafo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public void buscarPorId() {
        this.items = getFacade().buscarPorId(idTrafo);

    }

    public List<Trafo> getListaTrafos() {
        return items;
    }

    private void calcularEstadoAmarre() {
        /*
        for (int i = 0; i < listaMedidores.size(); i++) {
            if (this.ejbEventosAmarre.listaEventos(this.listaMedidores.get(i).getIdPlcTu1()).size() > 0) {
                this.eventoAmarre = this.ejbEventosAmarre.listaEventos(this.listaMedidores.get(i).getIdPlcTu1());
                int tam = eventoAmarre.size();
                int contEstadoActivo = 0;
                for (int j = 0; j < tam; j++) {
                    if (eventoAmarre.get(j).getEstado() == 1) {
                        contEstadoActivo++;
                    }
                }
                if (contEstadoActivo*100/tam >= 70) {
                    estadosDeAmarre.add(true);
                } else {
                    estadosDeAmarre.add(false);
                }
            }
        }
        
         */
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

    public Trafo getTrafo(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Trafo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Trafo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public ArrayList<Boolean> getEstadosDeAmarre() {
        return estadosDeAmarre;
    }

    public void setEstadosDeAmarre(ArrayList<Boolean> estadosDeAmarre) {
        this.estadosDeAmarre = estadosDeAmarre;
    }

    public void seleccionarTrafo(Trafo trafo) {
        reiniciarListas();
        this.selected = trafo;
        this.trafoSeleccionado = true;
        this.clientes = false;
        this.macro = ejbMacro.buscarMacroPorTrafoObj(selected);
        if (macro != null) {
            this.cargarLista();
            this.calcularEstadoAmarre();
            // this.trama="$@A0x5101"+ macro.getIdPlcMms().getIdPlcMms();

        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.update("trafoSeleccionado");
        requestContext.update("tablaListadoClientes");

    }

    public void enviarSolicitud() {
        trama = "Trafo seleccionado: " + selected.getIdTrafo();
        String numeroCelular = "3114760156";

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        try {
            
            String data = "";

            data += "username=" + URLEncoder.encode("roed26", "ISO-8859-1");
            data += "&password=" + URLEncoder.encode("rosario26@", "ISO-8859-1");
            data += "&message=" + URLEncoder.encode(trama, "ISO-8859-1");
            data += "&want_report=1";
            data += "&msisdn=57" + numeroCelular;
           
            URL url = new URL("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
            requestContext.execute("PF('exitoEnvioMensaje').show()");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
            requestContext.execute("PF('errorEnvioMensaje').show()");
        }

    }

    public void cargarLista() {

        this.listaProductos = this.ejbProducto.buscarListaProductosTrafo(idTrafo);
        if (listaProductos.size() > 0) {

            for (int i = 0; i < this.listaProductos.size(); i++) {
                this.cliente = ejbCliente.find(listaProductos.get(i).getCedula().getCedula());

                //this.listPlcTu.add( this.ejbPlcTu.buscarPorProducto(listaProductos.get(i)));
                if (this.cliente != null) {
                    this.listaClientes.add(this.cliente);
                    clientes = true;
                }
            }
        }
        /* if (this.ejbMacro.buscarMacroPorTrafo(selected).size() > 0) {
            this.macro = this.ejbMacro.buscarMacroPorTrafo(selected).get(0);

            if (this.ejbPlcMms.find(this.macro.getIdPlcMms().getIdPlcMms()) != null) {
                this.plcMms = this.ejbPlcMms.find(this.macro.getIdPlcMms().getIdPlcMms());

                this.listPlcTu = this.ejbPlcTu.buscarPorMms(this.plcMms);

                for (int i = 0; i < this.listPlcTu.size(); i++) {
                    if (ejbMedidor.buscarPorPlcTu1(listPlcTu.get(i)).size() > 0) {
                        this.medidor = ejbMedidor.buscarPorPlcTu1(listPlcTu.get(i)).get(0);
                        if (this.medidor != null) {
                            this.listaMedidores.add(this.medidor);
                        }
                    }
                }
                for (int i = 0; i < this.listaMedidores.size(); i++) {
                    if (ejbProducto.find(listaMedidores.get(i).getIdProducto().getId()) != null) {
                        this.producto = ejbProducto.find(listaMedidores.get(i).getIdProducto().getId());
                        if (this.producto != null) {
                            this.listaProductos.add(this.producto);
                        }
                    }
                }
                for (int i = 0; i < this.listaProductos.size(); i++) {
                    if (ejbCliente.find(listaProductos.get(i).getCedula().getCedula()) != null) {
                        this.cliente = ejbCliente.find(listaProductos.get(i).getCedula().getCedula());
                        if (this.cliente != null) {
                            this.listaClientes.add(this.cliente);
                            clientes = true;
                        }
                    }
                }
                
//                System.out.println("com.ceo.amisaa.managedbean.TrafoController.cargarLista()");
            }
        }*/

    }

    public void cargarTrafos() {
        this.items = getFacade().findAll();
    }

    private void reiniciarListas() {
        listaMedidores = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();
        resultado = new ArrayList<>();
        estadosDeAmarre = new ArrayList<>();

    }

    /*private void cargarResultados() {
        for (int i = 0; i < listaClientes.size(); i++) {
            
        }
        
    }*/
    @FacesConverter(forClass = Trafo.class)
    public static class TrafoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TrafoController controller = (TrafoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "trafoController");
            return controller.getTrafo(getKey(value));
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
            if (object instanceof Trafo) {
                Trafo o = (Trafo) object;
                return getStringKey(o.getIdTrafo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Trafo.class.getName()});
                return null;
            }
        }

    }

}
