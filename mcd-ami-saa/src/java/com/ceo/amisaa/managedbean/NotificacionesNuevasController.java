/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author geovanny
 */
@Named(value = "notificacionesNuevasController")
@RequestScoped
public class NotificacionesNuevasController {

    @EJB
    private NotificacionFacade ejbNotificacionFacade; 
   private List<Notificacion> notificacionesnuevas = null;
   
    public NotificacionesNuevasController() {
    }
    
    public List<Notificacion> getNotificacionesnuevas() {
        if(notificacionesnuevas == null)
        {
           notificacionesnuevas = ejbNotificacionFacade.getNotificacionesNuevas(); 
        }
        
        return notificacionesnuevas;
    }

    public void setNotificacionesnuevas(List<Notificacion> notificacionesnuevas) {
        this.notificacionesnuevas = notificacionesnuevas;
    }
    
    public String formatiarTipoEvento(int tipoevento)
    {
        String tipoeventoformater="";
        switch(tipoevento)
        {
            case 1:
                tipoeventoformater="Evento de Amarre y Consumo entre MMS y TU";
            break;
                
        }
        
        return tipoeventoformater;
    }
}
