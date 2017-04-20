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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.faces.event.ValueChangeEvent;
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
    private Trafo trafo;
    private boolean trafoSeleccionado;
    private String idTrafo;
    private Producto producto;
    private Medidor medidor;
    private PlcTu plcTu;
    private PlcMms plcMms;
    private Macro macro;
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
    private Date fechaInicio;
    private Date fechaFin;
    private String rangoFecha;
    private boolean rangoFechaCambiado1;
    private boolean rangoFechaCambiado2;
    private boolean rangoFechaCambiado3;
    private SimpleDateFormat formatoFecha;
    private String mensajeError;

    public TrafoController() {
        this.trafo = new Trafo();
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostConstruct
    private void init() {
        this.cargarTrafos();
        listaMedidores = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();
        listPlcTu = new ArrayList<>();
        resultado = new ArrayList<>();
        estadosDeAmarre = new ArrayList<>();
        this.clientes = true;
        this.trafo = new Trafo();

    }

    public Trafo getSelected() {
        return trafo;
    }

    public void setSelected(Trafo selected) {
        this.trafo = selected;
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

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getRangoFecha() {
        return rangoFecha;
    }

    public void setRangoFecha(String rangoFecha) {
        this.rangoFecha = rangoFecha;
    }

    public boolean isRangoFechaCambiado1() {
        return rangoFechaCambiado1;
    }

    public void setRangoFechaCambiado1(boolean rangoFechaCambiado1) {
        this.rangoFechaCambiado1 = rangoFechaCambiado1;
    }

    public boolean isRangoFechaCambiado2() {
        return rangoFechaCambiado2;
    }

    public void setRangoFechaCambiado2(boolean rangoFechaCambiado2) {
        this.rangoFechaCambiado2 = rangoFechaCambiado2;
    }

    public boolean isRangoFechaCambiado3() {
        return rangoFechaCambiado3;
    }

    public void setRangoFechaCambiado3(boolean rangoFechaCambiado3) {
        this.rangoFechaCambiado3 = rangoFechaCambiado3;
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
        trafo = new Trafo();
        initializeEmbeddableKey();
        return trafo;
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
            trafo = null; // Remove selection
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
        this.idTrafo = "";
    }

    public void reiniciarCampoBusqueda() {
        this.idTrafo = "";
        this.items = ejbTrafo.findAll();

    }

    public List<Trafo> getListaTrafos() {
        return items;
    }

    public List<Trafo> getListaTrafosDisponiblesEstadistica() {
        List<Trafo> trafosDisponibles = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPlcMmsCollection().toArray().length > 0 && items.get(i).getProductoCollection().toArray().length > 0) {
                trafosDisponibles.add(items.get(i));
            }
        }
        return trafosDisponibles;
    }

    public void cambiarRangoFecha(ValueChangeEvent e) {
        String rangoSeleccionado = e.getNewValue().toString();
        if (rangoSeleccionado.equals("1")) {
            this.rangoFechaCambiado1 = true;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = false;
            Calendar calendar = Calendar.getInstance();
            this.fechaFin = calendar.getTime();
            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            this.fechaInicio = calendar.getTime();

        } else if (rangoSeleccionado.equals("2")) {
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = true;
            this.rangoFechaCambiado3 = false;
            Calendar calendar = Calendar.getInstance();
            this.fechaFin = calendar.getTime();
            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -30);
            this.fechaInicio = calendar.getTime();

        } else {
            this.fechaInicio = null;
            this.fechaFin = null;
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = true;
        }
    }

    private void calcularEstadoAmarre() {
        this.eventoAmarre = this.ejbEventosAmarre.listaEventos(this.plcTu, fechaInicio, fechaFin);
        if (this.eventoAmarre.size() > 0) {
            int tam = eventoAmarre.size();
            int contEstadoActivo = 0;
            for (int i = 0; i < tam; i++) {
                if (eventoAmarre.get(i).getEstadoAmarre() == 1) {
                    contEstadoActivo++;
                }
            }
            float porcetajeAmarre = (contEstadoActivo * 100) / tam;

            if (porcetajeAmarre >= 70) {
                this.estadosDeAmarre.add(true);
            } else {
                this.estadosDeAmarre.add(false);
            }
        }

    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (trafo != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(trafo);
                } else {
                    getFacade().remove(trafo);
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
        this.trafo = trafo;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.execute("PF('seleccionarFecha').show()");

    }

    public void cancelarConsulta() {
        this.trafo = new Trafo();
        this.trafoSeleccionado = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarFecha').hide()");
    }

    public void cambiarEstadoSeleccionado() {
        this.trafoSeleccionado = false;
    }

    public void procesarConsulta() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        reiniciarListas();
        this.macro = ejbMacro.buscarMacroPorTrafoObj(trafo);
        if (macro != null) {
            if (fechaInicio.after(fechaFin)) {
                FacesContext.getCurrentInstance().addMessage("formFecha:fechaInicio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha de inicio no puede ser mayor a la fecha de fin."));
            } else {
                this.cargarLista();
                this.calcularEstadoAmarre();
                requestContext.execute("PF('seleccionarFecha').hide()");
                this.trafoSeleccionado = true;
                requestContext.update("trafoSeleccionado");
            }
        }

    }

    public void seleccionarTrafoSolicitud(Trafo trafo) {
        this.trafo = trafo;
        this.trafoSeleccionado = true;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("envioSolicitud");

        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.execute("PF('enviarSolicitud').show()");

    }

    public void cancelarSolicitud() {
        this.trafo = null;
        trafoSeleccionado = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('enviarSolicitud').hide()");
        requestContext.update("trafoSeleccionado");

    }

    public void enviarSolicitud() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.macro = ejbMacro.buscarMacroPorTrafoObj(trafo);
        if (macro != null) {
            this.trama = "$@1";
            this.plcMms = ejbPlcMms.buscarPorIdTrafoObj(trafo);
            if (this.plcMms != null) {
                if (this.plcMms.getNumeroCelular() != null) {
                    String numeroCelular = "&msisdn=57" + this.plcMms.getNumeroCelular();
                    //String numeroCelular = this.plcMms.getNumeroCelular();
                    enviarMensaje(numeroCelular, trama);
                } else {
                    this.mensajeError = "El maestro asociado al trafo no tiene un número de celular";
                    requestContext.update("formMensajeError");
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    this.trafoSeleccionado = false;
                }

            } else {
                this.mensajeError = "El trafo no tiene asociado a un maestro";
                requestContext.update("formMensajeError");
                requestContext.execute("PF('errorEnvioMensaje').show()");
                this.trafoSeleccionado = false;
            }
        } else {
            this.mensajeError = "El trafo no tiene vinculado a un macro";
            requestContext.update("formMensajeError");
            requestContext.execute("PF('errorEnvioMensaje').show()");

            this.trafoSeleccionado = false;
        }

    }

    public void cargarLista() {

        this.listaProductos = this.ejbProducto.buscarListaProductosTrafo(trafo);
        if (listaProductos.size() > 0) {
            for (int i = 0; i < this.listaProductos.size(); i++) {
                this.cliente = ejbCliente.find(listaProductos.get(i).getCedula().getCedula());

                if (this.ejbPlcTu.buscarPorProducto(listaProductos.get(i)).size() > 0) {
                    PlcTu plcTu = this.ejbPlcTu.buscarPorProducto(listaProductos.get(i)).get(0);
                    this.listPlcTu.add(plcTu);
                }
                if (this.cliente != null) {
                    this.listaClientes.add(this.cliente);
                    clientes = true;
                }
            }
        } else {
            this.clientes = false;
        }
    }

    public void enviarMensaje(String numeroCelular, String trama) {
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
            data += numeroCelular;

            URL url = new URL("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String dat = null;
            while ((line = rd.readLine()) != null) {
                String respuesta[] = line.split("\\|");

                if (!respuesta[0].equalsIgnoreCase("25")) {
                    requestContext.execute("PF('exitoEnvioMensaje').show()");
                    this.trafoSeleccionado = false;
                } else {
                    this.mensajeError = "No cuenta con suficiente saldo para enviar la solicitud";
                    requestContext.update("formMensajeError");
                    requestContext.execute("PF('enviarSolicitud').hide()");
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    this.trafoSeleccionado = false;
                }
            }
            wr.close();
            rd.close();
            this.trafoSeleccionado = false;

        } catch (Exception e) {
            this.mensajeError = "Error inesperado, intente nuevamente";
            requestContext.update("errorEnvioSolicitud");
            requestContext.execute("PF('errorEnvioMensaje').show()");
            this.trafoSeleccionado = false;
        }
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

    public void reiniciarVariables() {

        idTrafo = "";
        cargarTrafos();
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

    public void reiniciarVariablesPlcMms() {

        idTrafo = "";
        cargarTrafos();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.update("PlcMmsListForm");
        requestContext.update("informacionTrafo");
    }

    public void ventanaEliminar(Trafo trafo) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.trafo = trafo;
        requestContext.execute("PF('eliminarTrafo').show()");
    }

    public void eliminarTrafo() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.trafo != null) {
            if (this.trafo.getMacroCollection().isEmpty() && this.trafo.getPlcMmsCollection().isEmpty() && this.trafo.getProductoCollection().isEmpty()) {
                this.ejbTrafo.remove(this.trafo);
                requestContext.update("TrafoListForm");
                requestContext.execute("PF('eliminarTrafo').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.trafo = new Trafo();

            } else {
                this.trafo = new Trafo();
                requestContext.execute("PF('eliminarTrafo').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
                requestContext.update("TrafoListForm");
            }
        }
        reiniciarCampoBusqueda();
    }

    public void reiniciarObj() {
        this.producto = new Producto();
        this.producto.setEstado("A");
    }

    public void registrarTrafo() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        ejbTrafo.create(this.trafo);
        items = ejbTrafo.findAll();
        this.trafo = new Trafo();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La información del trafo se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");

    }

    public void editarInfoTrafo() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.trafo != null) {
            this.ejbTrafo.edit(this.trafo);
            requestContext.execute("PF('TrafoEditDialog').hide()");
            requestContext.execute("PF('edicionCorrecta').show()");
            this.trafo = new Trafo();
        }
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
