/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author geovanny
 */
@Named(value = "geoAmarreController")
@ManagedBean
@ViewScoped
public class GeoAmarreController implements Serializable{

    private MapModel puntosModelo;
    private Trafo trafo;
    @EJB
    private TrafoFacade ejbTrafo;
    @EJB
    private ProductoFacade ejbProducto;
    @EJB
    private EventosAmarreFacade ejbEventosAmarre;
    @EJB
    private EventosConsumoFacade ejbEventoConsumo;
    @EJB
    private PlcTuFacade ejbPlcTu;
    
    private String centroMapa = "2.440834, -76.606994";
    private boolean trafoSeleccionado;
    private Marker markerSelected;
    
    private Date fechaInicio;
    private Date fechaInicioUsar;
    private Date fechaFin;
    private String rangoFecha;
    private boolean rangoFechaCambiado1;
    private boolean rangoFechaCambiado2;
    private boolean rangoFechaCambiado3;    
    private SimpleDateFormat formatoFecha;
    private boolean existeTrafo;
    private boolean activo;
    private Producto producto;
    private PlcTu plcTu;
    private List<EventosAmarre> eventoAmarre;
    private List<EventosConsumo> eventoConsumo;
    
    public GeoAmarreController() {
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    
    @PostConstruct
    public void init() {
        puntosModelo = new DefaultMapModel();
        
        System.out.println(puntosModelo.getMarkers().size());
    }
    
    //Seters and Getters
    
    public Trafo getTrafo() {
        return trafo;
    }

    public void setTrafo(Trafo trafo) {
        this.trafo = trafo;
    }
     public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public boolean isExisteTrafo() {
        return existeTrafo;
    }

    public void setExisteTrafo(boolean existeTrafo) {
        this.existeTrafo = existeTrafo;
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
    
    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
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
    
    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }
    
    //logica de negocio
    public void seleccionarTrafo(Trafo trafo) 
    {
        this.trafo = trafo;
        trafoSeleccionado = true;
        cargarInformacion();
    }
    
    public void cargarInformacion() {

        List<Producto> listaProductos = new ArrayList();
        puntosModelo = new DefaultMapModel();
        listaProductos = this.ejbProducto.buscarListaProductosTrafo(trafo);        
        Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo(),trafo);
        marker.setIcon("../resources/img/iconos/icon-trafo.png");

        puntosModelo.addOverlay(marker);
        if (listaProductos.size() > 0) {
            for (int i = 0; i < listaProductos.size(); i++) {
                marker = new Marker(new LatLng(listaProductos.get(i).getLatitud(), listaProductos.get(i).getLongitud()), listaProductos.get(i).getCedula().getNombres(),listaProductos.get(i));
                puntosModelo.addOverlay(marker);
            }
        }

    }
    
    public void onMarkerSelect(OverlaySelectEvent event) 
    {
        markerSelected = (Marker) event.getOverlay();
        
        if(!markerSelected.equals(puntosModelo.getMarkers().get(0)))
        {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("formFecha");
            requestContext.execute("PF('seleccionarFecha').show()");
        }
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
    
    public void cancelarProceso() {
        this.fechaInicio = null;
        this.fechaFin = null;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        this.rangoFecha = "";
    }
    
     public void procesarConsulta() 
     { 
        if (fechaInicio.after(fechaFin)) {
            FacesContext.getCurrentInstance().addMessage("formFecha:fechaInicio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha de inicio no puede ser mayor a la fecha de fin."));
        } else {

            this.consultarEstadoAmarre();
            //consultarConsumo();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("formFecha");
            requestContext.execute("PF('seleccionarFecha').hide()"); 
            this.fechaInicio = null;
            this.fechaFin = null;
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = false;
            this.rangoFecha = "";
        }
    }
     
    public void consultarEstadoAmarre() 
    {
        existeTrafo = false;
        this.producto =(Producto) markerSelected.getData();
        if (this.producto != null) {
            this.plcTu = this.ejbPlcTu.buscarIdProducto(this.producto);
            if (this.producto.getIdTrafo() != null) {
                this.trafo = (Trafo)puntosModelo.getMarkers().get(0).getData();
                if (trafo != null) {
                    existeTrafo = true;
                }
            }
            activo = false;
            if (this.plcTu != null) {
                this.eventoAmarre = this.ejbEventosAmarre.listaEventos(this.plcTu, fechaInicio, fechaFin);
                if (this.eventoAmarre.size() > 0) {
                    calcularEstadoAmarre();
                }
            }
            RequestContext requestContext = RequestContext.getCurrentInstance(); 
            requestContext.update("productoSeleccionado");
            requestContext.execute("PF('productoEstadoAmarre').show()");
        }

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
}
