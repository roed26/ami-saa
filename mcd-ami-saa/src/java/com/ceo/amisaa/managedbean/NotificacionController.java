/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosAmarreMc;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMcFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMcFacade;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;

/**
 *
 * @author geovanny
 */
@Named(value = "notificacionController")
@SessionScoped
public class NotificacionController implements Serializable {

    
    @EJB
    private NotificacionFacade ejbNotificacionFacade; 
    
    @EJB 
    private EventosAmarreFacade ejbEventosAmarreFacade;
    
    @EJB
    private EventosConsumoFacade ejbEventosConsumoFacade;
    
    @EJB 
    private EventosAmarreMcFacade ejbEventosAmarreMcFacade;
    
    @EJB
    private EventosConsumoMcFacade ejbEventosConsumoMcFacade;
    
    
    
    private String conteoNotificacionesNuevas;

    private int conteo = 0;
    
    private boolean mostrarnotificacion = false;
    
    private boolean notificacionTipo1= false;
    
    private boolean notificacionTipo2 = false;
    
    private boolean notificacionTipoError = false;
    
    private Notificacion notificacionSelected;  
    
    private PlcMms plcMmsNotificacion;
    
    private PlcMc plcMcNotificacion;
    
    private List <EventosAmarre> listaEventosAmareNotificacion = null;
    
    private List <EventosConsumo> listaEventosConsumoNotificacion = null;
    
    private List <EventosAmarreMc> listaEventosAmarreMcNotificacion = null;
    
    private List <EventosConsumoMc> listaEventosConsumoMcNotificacion = null;
    
    private Cliente clienteSelected = null;
    
    private EventosConsumo eventosConsumoSelected = null;
    
    private EventosConsumoMc eventosConsumoMcSelected = null;
    /**
     * Creates a new instance of NotificacionController
     */
    public NotificacionController() 
    {
    }
    
    public String getConteoNotificacionesNuevas()
    {
        return conteoNotificacionesNuevas;
    }

    public void setConteoNotificacionesNuevas(String conteoNotificacionesNuevas) {
        this.conteoNotificacionesNuevas = conteoNotificacionesNuevas;
    }
    
    
    
    public void comprobarNotificaciones()
    {
        List<Notificacion> listanotificacion = ejbNotificacionFacade.getNotificacionesNuevas();
        int size = listanotificacion.size();
        if(size > conteo)
        {
            mostrarnotificacion = true;
            conteo = size;
            conteoNotificacionesNuevas = size +"";
             RequestContext requestContext = RequestContext.getCurrentInstance();
             requestContext.addCallbackParam("respuesta", true);
             requestContext.update("infoUsuario");
             requestContext.update("tituloPagina");
        }
        else
        {
            if(size!=0 && size < conteo)
            {
                conteo = size;
                 conteoNotificacionesNuevas = size +"";
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.addCallbackParam("respuesta", false);
                requestContext.update("infoUsuario");
                requestContext.update("tituloPagina");
            }
            else
            {
                if(size == 0)
                {
                    conteo= 0;
                    mostrarnotificacion = false;
                    conteoNotificacionesNuevas= "";
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.addCallbackParam("respuesta", false);
                    requestContext.update("infoUsuario");
                    requestContext.update("tituloPagina");
                }
            }
        }
    }
    
    public boolean isMostrarnotificacion() {
        return mostrarnotificacion;
    }

    public void setMostrarnotificacion(boolean mostrarnotificacion) {
        this.mostrarnotificacion = mostrarnotificacion;
    }
    
    
    public Notificacion getNotificacionSelected() {
        return notificacionSelected;
    }

    public void setNotificacionSelected(Notificacion notificacionSelected) {
        this.notificacionSelected = notificacionSelected;
    }

    public boolean isNotificacionTipo1() {
        return notificacionTipo1;
    }

    public void setNotificacionTipo1(boolean notificacionTipo1) {
        this.notificacionTipo1 = notificacionTipo1;
    }

    public boolean isNotificacionTipo2() {
        return notificacionTipo2;
    }

    public void setNotificacionTipo2(boolean notificacionTipo2) {
        this.notificacionTipo2 = notificacionTipo2;
    }
    
    public boolean isNotificacionTipoError() {
        return notificacionTipoError;
    }

    public void setNotificacionTipoError(boolean notificacionTipoError) {
        this.notificacionTipoError = notificacionTipoError;
    }

    public PlcMms getPlcMmsNotificacion() {
        return plcMmsNotificacion;
    }

    public void setPlcMmsNotificacion(PlcMms plcMmsNotificacion) {
        this.plcMmsNotificacion = plcMmsNotificacion;
    }

    public List<EventosAmarre> getListaEventosAmareNotificacion() {
        return listaEventosAmareNotificacion;
    }

    public void setListaEventosAmareNotificacion(List<EventosAmarre> listaEventosAmareNotificacion) {
        this.listaEventosAmareNotificacion = listaEventosAmareNotificacion;
    }

    public List<EventosConsumo> getListaEventosConsumoNotificacion() {
        return listaEventosConsumoNotificacion;
    }

    public void setListaEventosConsumoNotificacion(List<EventosConsumo> listaEventosConsumoNotificacion) {
        this.listaEventosConsumoNotificacion = listaEventosConsumoNotificacion;
    }

    public PlcMc getPlcMcNotificacion() {
        return plcMcNotificacion;
    }

    public void setPlcMcNotificacion(PlcMc plcMcNotificacion) {
        this.plcMcNotificacion = plcMcNotificacion;
    }

    public List<EventosAmarreMc> getListaEventosAmarreMcNotificacion() {
        return listaEventosAmarreMcNotificacion;
    }

    public void setListaEventosAmarreMcNotificacion(List<EventosAmarreMc> listaEventosAmarreMcNotificacion) {
        this.listaEventosAmarreMcNotificacion = listaEventosAmarreMcNotificacion;
    }

    public List<EventosConsumoMc> getListaEventosConsumoMcNotificacion() {
        return listaEventosConsumoMcNotificacion;
    }

    public void setListaEventosConsumoMcNotificacion(List<EventosConsumoMc> listaEventosConsumoMcNotificacion) {
        this.listaEventosConsumoMcNotificacion = listaEventosConsumoMcNotificacion;
    }   
    

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public EventosConsumo getEventosConsumoSelected() {
        return eventosConsumoSelected;
    }

    public void setEventosConsumoSelected(EventosConsumo eventosConsumoSelected) {
        this.eventosConsumoSelected = eventosConsumoSelected;
    }

    public EventosConsumoMc getEventosConsumoMcSelected() {
        return eventosConsumoMcSelected;
    }

    public void setEventosConsumoMcSelected(EventosConsumoMc eventosConsumoMcSelected) {
        this.eventosConsumoMcSelected = eventosConsumoMcSelected;
    }  
    
    public void seleccionarNotificacion(Notificacion notificacion,RoutingController routingController)
    {
        notificacionSelected = notificacion;
        
        switch (notificacionSelected.getTipoEvento()) {
            case 1:
                
                listaEventosAmareNotificacion =  ejbEventosAmarreFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoNotificacion = ejbEventosConsumoFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());              
                plcMmsNotificacion = listaEventosAmareNotificacion.get(0).getIdPlcMms();
                notificacionTipo1 = true;
                notificacionTipo2 = false;
                notificacionTipoError= false;
                break;
            case 2:
                listaEventosAmarreMcNotificacion = ejbEventosAmarreMcFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMcNotificacion =ejbEventosConsumoMcFacade.findByIdNotificacion(notificacion.getIdNotificacion());                
                plcMmsNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMms();
                plcMcNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMc();
                notificacionTipo1 = false;
                notificacionTipo2 = true;
                notificacionTipoError = false;
                break;
            case -1:
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipoError = true;
                break;
        }
        
        notificacionSelected.setRevisadoNotificacion(1);        
        ejbNotificacionFacade.edit(notificacionSelected);        
        routingController.irNotificacion();
    }
    
    public void seleccionarNotificacionRevisada(Notificacion notificacion,RoutingController routingController)
    {
        notificacionSelected = notificacion;
        
        switch (notificacionSelected.getTipoEvento()) {
            case 1:
                
                listaEventosAmareNotificacion =  ejbEventosAmarreFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoNotificacion = ejbEventosConsumoFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());              
                plcMmsNotificacion = listaEventosAmareNotificacion.get(0).getIdPlcMms();
                notificacionTipo1 = true;
                notificacionTipo2 = false;
                notificacionTipoError= false;
                break;
            case 2:
                listaEventosAmarreMcNotificacion = ejbEventosAmarreMcFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMcNotificacion =ejbEventosConsumoMcFacade.findByIdNotificacion(notificacion.getIdNotificacion());                
                plcMmsNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMms();
                plcMcNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMc();
                notificacionTipo1 = false;
                notificacionTipo2 = true;
                notificacionTipoError = false;
                break;
            case -1:
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipoError = true;
                break;
        }        
                
        routingController.irNotificacion();
    }
    
    public void verCliente(Cliente cliente)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        clienteSelected = cliente;
        
        requestContext.update("idDialogCliente");
        requestContext.execute("PF('dialogCliente').show()");
        
    }
    
    public void verEventoConsumo(PlcTu plcTu)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        for(EventosConsumo evCon : listaEventosConsumoNotificacion)
        {
            if(plcTu.getIdPlcTu().equals(evCon.getMacPlcTu().getIdPlcTu()))
            {
                eventosConsumoSelected = evCon;
                break;
            }
        }
        
        requestContext.update("idDialogConsumo");
        requestContext.execute("PF('dialogConsumo').show()");
    }
    
    public void verEventoConsumoMc(Medidor medidor)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        for(EventosConsumoMc evConMc : listaEventosConsumoMcNotificacion)
        {
            if(medidor.getIdMedidor().equals(evConMc.getIdMedidor().getIdMedidor()))
            {
                eventosConsumoMcSelected = evConMc;
                break;
            }
        }
        
        requestContext.update("idDialogConsumo");
        requestContext.execute("PF('dialogConsumo').show()");
    }
    
    public String getNombreArchivo()
    {
        String ruta = notificacionSelected.getRutaarchivoNotificacion();
        
        String nombre = ruta.substring(ruta.lastIndexOf("/")+1);
        
        return nombre;
    }
}
