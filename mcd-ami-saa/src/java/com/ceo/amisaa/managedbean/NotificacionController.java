/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
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
    
    private String conteoNotificacionesNuevas;

    private int conteo = 0;
    
    private boolean mostrarnotificacion = false;
    
    private boolean notificacionTipo1= false;
    
    private boolean notificacionTipo2 = false;
    
    private Notificacion notificacionSelected;  
    
    private PlcMms plcMmsNotificacion;
    
    private List <EventosAmarre> listaEventosAmareNotificacion = null;
    
    private List <EventosConsumo> listaEventosConsumoNotificacion = null;
    
    private Cliente clienteSelected = null;
    
    private EventosConsumo eventosConsumoSelected = null;
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
    
    public void seleccionarNotificacion(Notificacion notificacion,RoutingController routingController)
    {
        notificacionSelected = notificacion;
        
        if(notificacionSelected.getTipoEvento() == 1)
        {
            listaEventosAmareNotificacion =  new ArrayList(notificacionSelected.getEventosAmarreCollection());
            listaEventosConsumoNotificacion = new ArrayList(notificacionSelected.getEventosConsumoCollection());
            plcMmsNotificacion = listaEventosAmareNotificacion.get(0).getIdPlcMms();
            notificacionTipo1 = true;
            notificacionTipo2 = false;
        }
        else if(notificacionSelected.getTipoEvento() == 2)
        {
            notificacionTipo1 = false;
            notificacionTipo2 = true;
        }
        
        //notificacionSelected.setRevisadoNotificacion(1);
        
        //ejbNotificacionFacade.edit(notificacionSelected);
        
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
}
