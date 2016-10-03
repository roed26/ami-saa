package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosConsumo;
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
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.MacroFacade;
import com.ceo.amisaa.sessionbeans.MedidorFacade;
import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named("clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.ClienteFacade ejbFacade;
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
    private EventosConsumoFacade ejbEventoConsumo;
    @EJB
    private ClienteFacade ejbCliente;

    private List<Cliente> items = null;
    private Cliente selected;
    private String nombreOApellidos;
    private boolean clienteSeleccionado;
    private Producto producto;
    private Medidor medidor;
    private PlcTu plcTu;
    private String datosPlctu;
    private PlcMms plcMms;
    private Macro macro;
    private Trafo trafo;
    private List<EventosAmarre> eventoAmarre;
    private List<EventosConsumo> eventoConsumo;
    private boolean existeTrafo;
    private boolean activo;
    private Date fechaInicio;
    private Date fechaInicioUsar;
    private Date fechaFin;
    private String rangoFecha;
    private boolean rangoFechaCambiado1;
    private boolean rangoFechaCambiado2;
    private boolean rangoFechaCambiado3;
    private boolean sinConsumo;
    private SimpleDateFormat formatoFecha;
    private CartesianChartModel consumo;
    private String trama;

    public ClienteController() {
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostConstruct
    private void init() {
        this.cargarClientes();
        existeTrafo = false;
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    public CartesianChartModel getConsumo() {
        return consumo;
    }

    public void setConsumo(CartesianChartModel consumo) {
        this.consumo = consumo;
    }

    public boolean isSinConsumo() {
        return sinConsumo;
    }

    public void setSinConsumo(boolean sinConsumo) {
        this.sinConsumo = sinConsumo;
    }

    public String getNombreOApellidos() {
        return nombreOApellidos;
    }

    public void setNombreOApellidos(String nombreOApellidos) {
        this.nombreOApellidos = nombreOApellidos;
    }

    public boolean isClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(boolean clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getDatosPlctu() {
        return datosPlctu;
    }

    public void setDatosPlctu(String datosPlctu) {
        this.datosPlctu = datosPlctu;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public Trafo getTrafo() {
        return trafo;
    }

    public void setTrafo(Trafo trafo) {
        this.trafo = trafo;
    }

    public String getRangoFecha() {
        return rangoFecha;
    }

    public void setRangoFecha(String rangoFecha) {
        this.rangoFecha = rangoFecha;
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

    public boolean isExisteTrafo() {
        return existeTrafo;
    }

    public void setExisteTrafo(boolean existeTrafo) {
        this.existeTrafo = existeTrafo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ClienteFacade getFacade() {
        return ejbFacade;
    }

    public Cliente prepareCreate() {
        selected = new Cliente();
        initializeEmbeddableKey();
        return selected;
    }

    public void cambiarFechaInicio(Date fechaInicio) {
        Calendar calendar = Calendar.getInstance();
        this.fechaInicio = fechaInicio;
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        this.fechaInicioUsar = calendar.getTime();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ClienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ClienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ClienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cliente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Cliente> getListaClientes() {
        return items;
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

            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            this.fechaInicioUsar = calendar.getTime();

        } else if (rangoSeleccionado.equals("2")) {
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = true;
            this.rangoFechaCambiado3 = false;
            Calendar calendar = Calendar.getInstance();
            this.fechaFin = calendar.getTime();
            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -30);
            this.fechaInicio = calendar.getTime();

            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            this.fechaInicioUsar = calendar.getTime();

        } else {
            this.fechaInicio = null;
            this.fechaFin = null;
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = true;
        }
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

    public Cliente getCliente(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Cliente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cliente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void buscarPorNombresOApellidos() {
        this.items = getFacade().buscarPorNombresApellidos(this.nombreOApellidos.toLowerCase());

    }

    public void cargarClientes() {
        this.items = getFacade().findAll();
    }

    public void seleccionarCliente(Cliente cliente) {
        this.selected = cliente;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarCliente').hide()");
        requestContext.execute("PF('seleccionarFecha').show()");
    }

    public void seleccionarClienteSolicitud(Cliente cliente) {
        this.selected = cliente;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarCliente').hide()");
        requestContext.execute("PF('enviarSolicitud').show()");
    }

    public void procesarConsulta() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        if (fechaInicio.after(fechaFin)) {
            FacesContext.getCurrentInstance().addMessage("formFecha:fechaInicio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha de inicio no puede ser mayor a la fecha de fin."));
        } else {

            this.consultarEstadoAmarre();
            requestContext.execute("PF('seleccionarFecha').hide()");
            this.clienteSeleccionado = true;
            consultarConsumo();
            this.fechaInicio = null;
            this.fechaFin = null;
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = false;
            this.rangoFecha = "";
            requestContext.update("clienteSeleccionado");
        }
        /*consumoCliente.set("26", 1100);
                consumoCliente.set("27", 1700);
                    consumoCliente.set("28", 1900);
                    consumo.addSeries(consumoCliente);*/
    }

    public void consultarConsumo() {
        consumo = new CartesianChartModel();

        final ChartSeries consumoCliente = new ChartSeries("Consumo");

        this.producto = this.ejbProducto.buscarProductoDeCliente(selected);
        if (this.producto != null) {
            this.plcTu = this.ejbPlcTu.buscarIdProducto(this.producto);
            if (this.plcTu != null) {
                cambiarFechaInicio(fechaInicio);
                this.eventoConsumo = this.ejbEventoConsumo.listaEventos(this.plcTu, fechaInicioUsar, fechaFin);
                if (this.eventoConsumo.size() > 0) {

                    for (int i = 0; i < eventoConsumo.size(); i++) {
                        if (i == 0) {
                            float energia = eventoConsumo.get(i).getEnergia();
                            //consumoCliente.set(eventoConsumo.get(i).getFechaHora(), energia);

                        } else {
                            float energia = 0;
                            if (eventoConsumo.get(i - 1).getEnergia() > eventoConsumo.get(i).getEnergia()) {
                                energia = eventoConsumo.get(i - 1).getEnergia() - eventoConsumo.get(i).getEnergia();
                            } else {
                                energia = eventoConsumo.get(i).getEnergia() - eventoConsumo.get(i - 1).getEnergia();
                            }
//eventoConsumo.get(i).getCorriente()-eventoConsumo.get(i-1).getCorriente()
                            consumoCliente.set(formatoFecha.format(eventoConsumo.get(i).getFechaHora()), energia);

                        }

                    }
                    consumo.addSeries(consumoCliente);
                    sinConsumo = false;
                } else {
                    consumoCliente.set(0, 0);
                    consumo.addSeries(consumoCliente);
                    sinConsumo = true;

                }
            }
        }

    }

    public void consultarEstadoAmarre() {
        existeTrafo = false;
        this.producto = this.ejbProducto.buscarProductoDeCliente(selected);
        if (this.producto != null) {
            this.plcTu = this.ejbPlcTu.buscarIdProducto(this.producto);
            if (this.producto.getIdTrafo() != null) {
                this.trafo = this.ejbTrafo.buscarPorIdObj(this.producto.getIdTrafo().getIdTrafo());
                if (trafo != null) {
                    existeTrafo = true;
                }
            }
            if (this.plcTu != null) {
                this.eventoAmarre = this.ejbEventosAmarre.listaEventos(this.plcTu, fechaInicio, fechaFin);
                if (this.eventoAmarre.size() > 0) {
                    calcularEstadoAmarre();
                }
            }
        }

    }

    public void cambiarEstadoSeleccion() {
        this.clienteSeleccionado = false;
    }

    public void cancelarProceso() {
        this.fechaInicio = null;
        this.fechaFin = null;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        this.rangoFecha = "";
        this.clienteSeleccionado = false;
    }

    public void enviarSolicitud() {
        //trama = "Trafo seleccionado: " + selected.getIdTrafo();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.producto = ejbProducto.buscarProductoDeCliente(selected);

        if (producto != null) {
            this.trafo = ejbTrafo.buscarPorIdObj(this.producto.getIdTrafo().getIdTrafo());
            if (this.trafo != null) {
                this.macro = ejbMacro.buscarMacroPorTrafoObj(trafo);
                if (macro != null) {
                    this.trama = "$@A0x5101" + macro.getIdMacro();
                    this.plcMms = ejbPlcMms.buscarPorIdTrafo(trafo.getIdTrafo());
                    if (this.plcMms != null) {

                        if (this.plcMms.getNumeroCelular() != null) {
                            String numeroCelular = "&msisdn=57" + this.plcMms.getNumeroCelular();
                            //String numeroCelular = "&msisdn=57" + "3114760156";

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
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                        requestContext.execute("PF('exitoEnvioMensaje').show()");
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No hay creditos"));
                                        requestContext.execute("PF('errorCreditos').show()");
                                    }

                                }

                                wr.close();
                                rd.close();

                                this.clienteSeleccionado = false;

                            } catch (Exception e) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                requestContext.execute("PF('errorEnvioMensaje').show()");
                                this.clienteSeleccionado = false;
                            }

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                            requestContext.execute("PF('errorEnvioMensaje').show()");
                            this.clienteSeleccionado = false;
                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                        requestContext.execute("PF('errorEnvioMensaje').show()");
                        this.clienteSeleccionado = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    this.clienteSeleccionado = false;
                }
            }
        }

    }

    public void enviarSolicitudSuspension() {
        //trama = "Trafo seleccionado: " + selected.getIdTrafo();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.producto = ejbProducto.buscarProductoDeCliente(selected);

        if (producto != null) {
            this.trafo = ejbTrafo.buscarPorIdObj(this.producto.getIdTrafo().getIdTrafo());
            if (this.trafo != null) {
                this.macro = ejbMacro.buscarMacroPorTrafoObj(trafo);
                if (macro != null) {
                    this.trama = "$@A0x5101" + macro.getIdMacro();
                    this.plcMms = ejbPlcMms.buscarPorIdTrafo(trafo.getIdTrafo());
                    if (this.plcMms != null) {

                        if (this.plcMms.getNumeroCelular() != null) {
                            String numeroCelular = "&msisdn=57" + this.plcMms.getNumeroCelular();
                            //String numeroCelular = "&msisdn=57" + "3114760156";

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
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                        requestContext.execute("PF('exitoEnvioMensaje').show()");
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No hay creditos"));
                                        requestContext.execute("PF('errorCreditos').show()");
                                    }

                                }

                                wr.close();
                                rd.close();

                                this.clienteSeleccionado = false;

                            } catch (Exception e) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                requestContext.execute("PF('errorEnvioMensaje').show()");
                                this.clienteSeleccionado = false;
                            }

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                            requestContext.execute("PF('errorEnvioMensaje').show()");
                            this.clienteSeleccionado = false;
                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                        requestContext.execute("PF('errorEnvioMensaje').show()");
                        this.clienteSeleccionado = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    this.clienteSeleccionado = false;
                }
            }
        }

    }

    public void enviarSolicitudReconexion() {
        //trama = "Trafo seleccionado: " + selected.getIdTrafo();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.producto = ejbProducto.buscarProductoDeCliente(selected);

        if (producto != null) {
            this.trafo = ejbTrafo.buscarPorIdObj(this.producto.getIdTrafo().getIdTrafo());
            if (this.trafo != null) {
                this.macro = ejbMacro.buscarMacroPorTrafoObj(trafo);
                if (macro != null) {
                    this.trama = "$@A0x5101" + macro.getIdMacro();
                    this.plcMms = ejbPlcMms.buscarPorIdTrafo(trafo.getIdTrafo());
                    if (this.plcMms != null) {

                        if (this.plcMms.getNumeroCelular() != null) {
                            String numeroCelular = "&msisdn=57" + this.plcMms.getNumeroCelular();
                            //String numeroCelular = "&msisdn=57" + "3114760156";

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
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                        requestContext.execute("PF('exitoEnvioMensaje').show()");
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No hay creditos"));
                                        requestContext.execute("PF('errorCreditos').show()");
                                    }

                                }

                                wr.close();
                                rd.close();

                                this.clienteSeleccionado = false;

                            } catch (Exception e) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                                requestContext.execute("PF('errorEnvioMensaje').show()");
                                this.clienteSeleccionado = false;
                            }

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                            requestContext.execute("PF('errorEnvioMensaje').show()");
                            this.clienteSeleccionado = false;
                        }

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                        requestContext.execute("PF('errorEnvioMensaje').show()");
                        this.clienteSeleccionado = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La solicitud se envio con exito"));
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    this.clienteSeleccionado = false;
                }
            }
        }

    }

    public void cancelarSolicitud() {
        this.selected = null;
        clienteSeleccionado = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('enviarSolicitud').hide()");
        requestContext.update("@all");

    }

    private void calcularEstadoAmarre() {
        int tam = eventoAmarre.size();
        int contEstadoActivo = 0;
        for (int i = 0; i < tam; i++) {
            if (eventoAmarre.get(i).getEstadoAmarre() == 1) {
                contEstadoActivo++;
            }
        }
        float porcetajeAmarre = (contEstadoActivo * 100) / tam;

        if (porcetajeAmarre >= 70) {
            this.activo = true;
        } else {
            this.activo = false;
        }
    }

    @FacesConverter(forClass = Cliente.class)
    public static class ClienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClienteController controller = (ClienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clienteController");
            return controller.getCliente(getKey(value));
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
            if (object instanceof Cliente) {
                Cliente o = (Cliente) object;
                return getStringKey(o.getCedula());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cliente.class.getName()});
                return null;
            }
        }

    }

}
