package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

@Named("plcMmsController")
@SessionScoped
public class PlcMmsController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.PlcMmsFacade ejbPlcMms;
    private List<PlcMms> items = null;
    private PlcMms objPlcMms;
    private String dato;
    private Trafo trafo;
    private boolean trafoSeleccionado;
    private List<PlcMms> listaPlcMmsSinVinculo;
    private boolean maestroSeleccionado;

    public PlcMmsController() {
        this.objPlcMms = new PlcMms();
    }

    @PostConstruct
    private void init() {
        trafoSeleccionado = false;
    }

    public PlcMms getSelected() {
        return objPlcMms;
    }

    public void setSelected(PlcMms selected) {
        this.objPlcMms = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PlcMmsFacade getFacade() {
        return ejbPlcMms;
    }

    public Trafo getTrafo() {
        return trafo;
    }

    public void setTrafo(Trafo trafo) {
        this.trafo = trafo;
    }

    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
    }

    public PlcMms prepareCreate() {
        objPlcMms = new PlcMms();
        initializeEmbeddableKey();
        return objPlcMms;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public boolean isMaestroSeleccionado() {
        return maestroSeleccionado;
    }

    public void setMaestroSeleccionado(boolean maestroSeleccionado) {
        this.maestroSeleccionado = maestroSeleccionado;
    }

    
    public List<PlcMms> getListaPlcMmsSinVinculo() {
        this.getItems();
        listaPlcMmsSinVinculo = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getIdTrafo() == null) {
                listaPlcMmsSinVinculo.add(items.get(i));
            }
        }
        return listaPlcMmsSinVinculo;
    }

    public void setListaPlcMmsSinVinculo(ArrayList<PlcMms> listaPlcMmsSinVinculo) {
        this.listaPlcMmsSinVinculo = listaPlcMmsSinVinculo;
    }

    public List<PlcMms> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (objPlcMms != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(objPlcMms);
                } else {
                    getFacade().remove(objPlcMms);
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

    public PlcMms getPlcMms(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<PlcMms> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PlcMms> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PlcMms.class)
    public static class PlcMmsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlcMmsController controller = (PlcMmsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "plcMmsController");
            return controller.getPlcMms(getKey(value));
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
            if (object instanceof PlcMms) {
                PlcMms o = (PlcMms) object;
                return getStringKey(o.getIdPlcMms());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PlcMms.class.getName()});
                return null;
            }
        }

    }

    public void buscarDato() {
        this.items = ejbPlcMms.buscarPorDato(this.dato.toLowerCase());
    }

    public void seleccionarTrafo(Trafo trafo) {
        this.trafo = trafo;
        trafoSeleccionado = true;
        /*RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("PlcMmsListForm");
        requestContext.execute("PF('seleccionarTrafo').hide()");*/

    }

    public void newObj() {
        this.objPlcMms = new PlcMms();
    }

    public void vincularTrafo(PlcMms plcMms) {
        this.objPlcMms = plcMms;
        this.objPlcMms.setIdTrafo(trafo);
        this.ejbPlcMms.edit(this.objPlcMms);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        trafoSeleccionado = false;
        requestContext.update("PlcMmsListForm");
        requestContext.execute("PF('mensajeVinculo').show()");
    }

    public void registrarPlcMms() {
        this.ejbPlcMms.create(objPlcMms);

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();

        this.objPlcMms = new PlcMms();
        this.items = getFacade().findAll();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El PLC_MMS se registro con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void editarInfoPlcMms() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcMms != null) {

            if (this.objPlcMms.getNumeroCelular().equalsIgnoreCase("")) {
                this.objPlcMms.setNumeroCelular(null);
            }
            this.ejbPlcMms.edit(this.objPlcMms);
            requestContext.execute("PF('PlcMmsEditDialog').hide()");
            requestContext.execute("PF('edicionCorrecta').show()");
            this.objPlcMms = new PlcMms();
        }
    }
    public void enviarSolicitudConfiguracion(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcMms != null) {
           String trama="";
           String numeroCelular = "&msisdn=57" + this.objPlcMms.getNumeroCelular();
            enviarMensajeTexto(numeroCelular, trama);

            //registro en base de datos nuevos parametros
            if (this.objPlcMms.getNumeroCelular().equalsIgnoreCase("")) {
                this.objPlcMms.setNumeroCelular(null);
            }
            this.ejbPlcMms.edit(this.objPlcMms);
            this.objPlcMms = new PlcMms();
        }
    }
    private void enviarMensajeTexto(String numeroCelular, String trama) {
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
                    //this.clienteSeleccionado = false;
                } else {
                    //this.mensajeError = "No cuenta con suficiente saldo para enviar la solicitud";
                    requestContext.update("errorEnvioSolicitud");
                    requestContext.execute("PF('errorEnvioMensaje').show()");
                    //this.clienteSeleccionado = false;
                }
            }
            wr.close();
            rd.close();
            //this.clienteSeleccionado = false;

        } catch (Exception e) {
            //this.mensajeError = "Error inesperado, intente nuevamente";
            requestContext.update("errorEnvioSolicitud");
            requestContext.execute("PF('errorEnvioMensaje').show()");
            //this.clienteSeleccionado = false;
        }
    }        
    public void seleccionarClienteSolicitud(PlcMms plcMms) {
        this.objPlcMms = plcMms;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("PlcMmsEditForm");
        requestContext.execute("PF('seleccionarPlcMms').hide()");
        
    }

    public void ventanaEliminarPlcMms(PlcMms selected) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.objPlcMms = selected;
        requestContext.execute("PF('eliminarPlcMms').show()");
    }

    public void eliminarPlcMms() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.objPlcMms != null) {
            if (true) {
                this.ejbPlcMms.remove(this.objPlcMms);
                requestContext.update("PlcMmsListForm");
                requestContext.execute("PF('eliminarPlcMms').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.objPlcMms = new PlcMms();
                this.items = getFacade().findAll();
            } else {
                requestContext.execute("PF('eliminarPlcMms').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }
}
