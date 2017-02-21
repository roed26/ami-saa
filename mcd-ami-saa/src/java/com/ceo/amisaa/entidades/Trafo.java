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
@Table(name = "trafo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trafo.findAll", query = "SELECT t FROM Trafo t"),
    @NamedQuery(name = "Trafo.findByIdTrafo", query = "SELECT t FROM Trafo t WHERE t.idTrafo = :idTrafo"),
    @NamedQuery(name = "Trafo.findByLatitud", query = "SELECT t FROM Trafo t WHERE t.latitud = :latitud"),
    @NamedQuery(name = "Trafo.findByLongitud", query = "SELECT t FROM Trafo t WHERE t.longitud = :longitud"),
    @NamedQuery(name = "Trafo.findByTrafos", query = "SELECT t FROM Trafo t WHERE LOWER(t.idTrafo) LIKE :idTrafo ")

})
public class Trafo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "latitud")
    private double latitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitud")
    private double longitud;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_trafo")
    private String idTrafo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrafo")
    private Collection<Macro> macroCollection;
    @OneToMany(mappedBy = "idTrafo")
    private Collection<Producto> productoCollection;
    @OneToMany(mappedBy = "idTrafo")
    private Collection<PlcMms> plcMmsCollection;

    public Trafo() {
    }

    public Trafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }

    public Trafo(String idTrafo, float latitud, float longitud) {
        this.idTrafo = idTrafo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(String idTrafo) {
        this.idTrafo = idTrafo;
    }


    @XmlTransient
    public Collection<Macro> getMacroCollection() {
        return macroCollection;
    }

    public void setMacroCollection(Collection<Macro> macroCollection) {
        this.macroCollection = macroCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @XmlTransient
    public Collection<PlcMms> getPlcMmsCollection() {
        return plcMmsCollection;
    }

    public void setPlcMmsCollection(Collection<PlcMms> plcMmsCollection) {
        this.plcMmsCollection = plcMmsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrafo != null ? idTrafo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trafo)) {
            return false;
        }
        Trafo other = (Trafo) object;
        if ((this.idTrafo == null && other.idTrafo != null) || (this.idTrafo != null && !this.idTrafo.equals(other.idTrafo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.Trafo[ idTrafo=" + idTrafo + " ]";
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
}
