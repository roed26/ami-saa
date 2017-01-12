/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
    private Notificacion notificacionSelected;    
    
    
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
    
    
    public void seleccionarNotificacion(Notificacion notificacion,RoutingController routingController)
    {
        notificacionSelected = notificacion;
        
        routingController.irNotificacion();
    }
}
