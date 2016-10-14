/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
public class MarcadoresController implements Serializable {

    private MapModel emptyModel;

    private String title;

    private double lat;

    private double lng;
    private Trafo trafo;
    private boolean trafoSeleccionado;
    @EJB
    private TrafoFacade ejbTrafo;

    @EJB
    private ProductoFacade ejbProducto;

    private String idTrafo;

    public MarcadoresController() {
        this.ejbTrafo = new TrafoFacade();

    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public MapModel getEmptyModel() {

        return emptyModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
    }

    public void cargarInformacion() {

        List<Producto> listaProductos = new ArrayList();

        listaProductos = this.ejbProducto.buscarListaProductosTrafo(trafo);
        Marker marker = new Marker(new LatLng(trafo.getLatitud(), trafo.getLongitud()), trafo.getIdTrafo());
        marker.setIcon("../resources/img/iconos/icon-trafo.png");

        emptyModel.addOverlay(marker);
        if (listaProductos.size() > 0) {
            for (int i = 0; i < listaProductos.size(); i++) {
                marker = new Marker(new LatLng(listaProductos.get(i).getLatitud(), listaProductos.get(i).getLongitud()), listaProductos.get(i).getCedula().getNombres());
                emptyModel.addOverlay(marker);
            }
        }

    }

    public void addMarker() {

        Marker marker = new Marker(new LatLng(lat, lng), title);
        emptyModel.addOverlay(marker);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }

    public void seleccionarTrafo(Trafo trafo) {
        this.trafo = trafo;
        trafoSeleccionado = true;
        cargarInformacion();

    }
}
