/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ROED26
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByEstado", query = "SELECT p FROM Producto p WHERE p.estado = :estado"),
    @NamedQuery(name = "Producto.findByLatitud", query = "SELECT p FROM Producto p WHERE p.latitud = :latitud"),
    @NamedQuery(name = "Producto.findByLongitud", query = "SELECT p FROM Producto p WHERE p.longitud = :longitud"),
    @NamedQuery(name = "Producto.findByProductos", query = "SELECT p FROM Producto p WHERE LOWER(p.idProducto) LIKE :idProducto "),
    @NamedQuery(name = "Producto.findByCedula", query = "SELECT p FROM Producto p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "Producto.findByProductosTrafo", query = "SELECT p FROM Producto p WHERE p.idTrafo = :idTrafo"),
    @NamedQuery(name = "Producto.findByProductosPlcMc", query = "SELECT p FROM Producto p WHERE p.macPlcMc = :macPlcMc")
})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_producto")
    private String idProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitud")
    private float latitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitud")
    private float longitud;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<PlcTu> plcTuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Medidor> medidorCollection;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Cliente cedula;
    @JoinColumn(name = "mac_plc_mc", referencedColumnName = "mac_plc_mc")
    @ManyToOne
    private PlcMc macPlcMc;
    @JoinColumn(name = "id_trafo", referencedColumnName = "id_trafo")
    @ManyToOne
    private Trafo idTrafo;

    public Producto() {
    }

    public Producto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(String idProducto, String estado, float latitud, float longitud) {
        this.idProducto = idProducto;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    @XmlTransient
    public Collection<PlcTu> getPlcTuCollection() {
        return plcTuCollection;
    }

    public void setPlcTuCollection(Collection<PlcTu> plcTuCollection) {
        this.plcTuCollection = plcTuCollection;
    }

    @XmlTransient
    public Collection<Medidor> getMedidorCollection() {
        return medidorCollection;
    }

    public void setMedidorCollection(Collection<Medidor> medidorCollection) {
        this.medidorCollection = medidorCollection;
    }

    public Cliente getCedula() {
        return cedula;
    }

    public void setCedula(Cliente cedula) {
        this.cedula = cedula;
    }

    public PlcMc getMacPlcMc() {
        return macPlcMc;
    }

    public void setMacPlcMc(PlcMc macPlcMc) {
        this.macPlcMc = macPlcMc;
    }

    public Trafo getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(Trafo idTrafo) {
        this.idTrafo = idTrafo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.Producto[ idProducto=" + idProducto + " ]";
    }

}
