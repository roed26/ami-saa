/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosAmarreMacro;
import com.ceo.amisaa.entidades.EventosAmarreMc;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.EventosConsumoMacro;
import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMacroFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMcFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMacroFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMcFacade;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

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
    
     @EJB 
    private EventosAmarreMacroFacade ejbEventosAmarreMacroFacade;
    
    @EJB
    private EventosConsumoMacroFacade ejbEventosConsumoMacroFacade;
    
    private MapModel puntosModelo;
    
    
    private String centroMapa = "2.440834, -76.606994";
    
    private String conteoNotificacionesNuevas;

    private int conteo = 0;
    
    private boolean mostrarnotificacion = false;
    
    private boolean notificacionTipo1= false;
    
    private boolean notificacionTipo2 = false;
    
    private boolean notificacionTipo3 = false;
    
    private boolean notificacionTipoError = false;
    
    private Notificacion notificacionSelected;  
    
    private PlcMms plcMmsNotificacion;
    
    private PlcMc plcMcNotificacion;
    
    private List <EventosAmarre> listaEventosAmareNotificacion = null;
    
    private List <EventosConsumo> listaEventosConsumoNotificacion = null;
    
    private List <EventosAmarreMc> listaEventosAmarreMcNotificacion = null;
    
    private List <EventosConsumoMc> listaEventosConsumoMcNotificacion = null;
    
    private List <EventosAmarreMacro> listaEventosAmarreMacroNotificacion = null;
    
    private List <EventosConsumoMacro> listaEventosConsumoMacroNotificacion = null;
    
    private Cliente clienteSelected = null;
    
    private EventosConsumo eventosConsumoSelected = null;
    
    private EventosConsumoMc eventosConsumoMcSelected = null;
    
    private EventosConsumoMacro eventosConsumoMacroSelected = null;
    /**
     * Creates a new instance of NotificacionController
     */
    public NotificacionController() 
    {
    }
    
    @PostConstruct
    public void init() {
        puntosModelo = new DefaultMapModel();
        
        System.out.println(puntosModelo.getMarkers().size());
    }
    
    public String getConteoNotificacionesNuevas()
    {
        return conteoNotificacionesNuevas;
    }

    public void setConteoNotificacionesNuevas(String conteoNotificacionesNuevas) {
        this.conteoNotificacionesNuevas = conteoNotificacionesNuevas;
    }
    
    public MapModel getPuntosModelo() 
    {
        return puntosModelo;
    }
    
    public String getCentroMapa()
    {
        if(puntosModelo.getMarkers().size()>0)
        {
            centroMapa=puntosModelo.getMarkers().get(0).getLatlng().getLat() + ", " +puntosModelo.getMarkers().get(0).getLatlng().getLng();
        }
        
        return centroMapa;
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
    
    public boolean isNotificacionTipo3() {
        return notificacionTipo3;
    }

    public void setNotificacionTipo3(boolean notificacionTipo3) {
        this.notificacionTipo3 = notificacionTipo3;
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
    
    public List<EventosAmarreMacro> getListaEventosAmareMacroNotificacion() {
        return listaEventosAmarreMacroNotificacion;
    }

    public void setListaEventosAmareMacroNotificacion(List<EventosAmarreMacro> listaEventosAmarreMacroNotificacion) {
        this.listaEventosAmarreMacroNotificacion = listaEventosAmarreMacroNotificacion;
    }

    public List<EventosConsumo> getListaEventosConsumoNotificacion() {
        return listaEventosConsumoNotificacion;
    }

    public void setListaEventosConsumoNotificacion(List<EventosConsumo> listaEventosConsumoNotificacion) {
        this.listaEventosConsumoNotificacion = listaEventosConsumoNotificacion;
    }
    
    public List<EventosConsumoMacro> getListaEventosConsumoMacroNotificacion() {
        return listaEventosConsumoMacroNotificacion;
    }

    public void setListaEventosConsumoMacroNotificacion(List<EventosConsumoMacro> listaEventosConsumoMacroNotificacion) {
        this.listaEventosConsumoMacroNotificacion = listaEventosConsumoMacroNotificacion;
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
    
    public EventosConsumoMacro getEventosConsumoMacroSelected() {
        return eventosConsumoMacroSelected;
    }

    public void setEventosConsumoMacroSelected(EventosConsumoMacro eventosConsumoMacroSelected) {
        this.eventosConsumoMacroSelected = eventosConsumoMacroSelected;
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
                notificacionTipo3 = false;
                notificacionTipoError= false;
                break;
            case 2:
                listaEventosAmarreMcNotificacion = ejbEventosAmarreMcFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMcNotificacion =ejbEventosConsumoMcFacade.findByIdNotificacion(notificacion.getIdNotificacion());                
                plcMmsNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMms();
                plcMcNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMc();
                notificacionTipo1 = false;
                notificacionTipo2 = true;
                notificacionTipo3 = false;
                notificacionTipoError = false;
                break;
            case 3:
                listaEventosAmarreMacroNotificacion =  ejbEventosAmarreMacroFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMacroNotificacion = ejbEventosConsumoMacroFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());              
                plcMmsNotificacion = listaEventosAmarreMacroNotificacion.get(0).getMacPlcMms();
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipo3 = true;
                notificacionTipoError= false;
            break;
            case -1:
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipo3 = false;
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
                notificacionTipo3 = false;
                notificacionTipoError= false;
                break;
            case 2:
                listaEventosAmarreMcNotificacion = ejbEventosAmarreMcFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMcNotificacion =ejbEventosConsumoMcFacade.findByIdNotificacion(notificacion.getIdNotificacion());                
                plcMmsNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMms();
                plcMcNotificacion = listaEventosAmarreMcNotificacion.get(0).getMacPlcMc();
                notificacionTipo1 = false;
                notificacionTipo3 = false;
                notificacionTipo2 = true;
                notificacionTipoError = false;
                break;
            case 3:
                listaEventosAmarreMacroNotificacion =  ejbEventosAmarreMacroFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());
                listaEventosConsumoMacroNotificacion = ejbEventosConsumoMacroFacade.findByIdNotificacion(notificacionSelected.getIdNotificacion());              
                plcMmsNotificacion = listaEventosAmarreMacroNotificacion.get(0).getMacPlcMms();
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipo3 = true;
                notificacionTipoError= false;
                break;         
            case -1:
                notificacionTipo1 = false;
                notificacionTipo2 = false;
                notificacionTipo3 = false;
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
    
    public void verEventoConsumoMacro(Macro macro)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        for(EventosConsumoMacro evConMacro : listaEventosConsumoMacroNotificacion)
        {
            if(macro.getIdMacro().equals(evConMacro.getIdMacro().getIdMacro()))
            {
                eventosConsumoMacroSelected = evConMacro;
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
    
    public void showMapTrafo()
    {
       puntosModelo = new DefaultMapModel();
       centroMapa = "2.440834, -76.606994";       
       Trafo trafo = plcMmsNotificacion.getIdTrafo();
       if(trafo!= null)
       {        
           Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo(),trafo);
           marker.setIcon("../resources/img/iconos/icon-trafo.png");
           puntosModelo.addOverlay(marker);
       }
    }
    
    public void showMapTu(EventosAmarre eventoAmarre)
    {
       puntosModelo = new DefaultMapModel();
       centroMapa = "2.440834, -76.606994";
       PlcTu tu = eventoAmarre.getMacPlcTu();
       Producto p = tu.getIdProducto();
       
       Trafo trafo = plcMmsNotificacion.getIdTrafo();
       if(trafo!= null)
       {        
           Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo(),trafo);
           marker.setIcon("../resources/img/iconos/icon-trafo.png");
           puntosModelo.addOverlay(marker);
       }
       
       if(p != null)
       {
           Marker marker = new Marker(new LatLng(p.getLatitud(), p.getLongitud()), p.getCedula().getNombres(),p);           
           puntosModelo.addOverlay(marker);
       }
       
    }
    
    public void showMapMedidor(EventosAmarreMc eventoAmarreMc)
    {
       puntosModelo = new DefaultMapModel();
       centroMapa = "2.440834, -76.606994";
       Medidor medidor = eventoAmarreMc.getIdMedidor();
       Producto p = medidor.getIdProducto();
       
       Trafo trafo = plcMmsNotificacion.getIdTrafo();
       if(trafo!= null)
       {        
           Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo(),trafo);
           marker.setIcon("../resources/img/iconos/icon-trafo.png");
           puntosModelo.addOverlay(marker);
       }
       
       if(p != null)
       {
           Marker marker = new Marker(new LatLng(p.getLatitud(), p.getLongitud()), p.getCedula().getNombres(),p);           
           puntosModelo.addOverlay(marker);
       }
       
    }
    
    /*public void showMapMacro(EventosAmarreMacro eventoAmarreMacro)
    {
       puntosModelo = new DefaultMapModel();
       centroMapa = "2.440834, -76.606994";
       Macro macro = eventoAmarreMacro.getIdMacro().get
       Producto p =macro.get
       
       Trafo trafo = plcMmsNotificacion.getIdTrafo();
       if(trafo!= null)
       {        
           Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo(),trafo);
           marker.setIcon("../resources/img/iconos/icon-trafo.png");
           puntosModelo.addOverlay(marker);
       }
       
       if(p != null)
       {
           Marker marker = new Marker(new LatLng(p.getLatitud(), p.getLongitud()), p.getCedula().getNombres(),p);           
           puntosModelo.addOverlay(marker);
       }
       
    }*/
}
