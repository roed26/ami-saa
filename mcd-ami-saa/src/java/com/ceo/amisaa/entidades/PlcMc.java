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
@Table(name = "plc_mc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlcMc.findAll", query = "SELECT p FROM PlcMc p"),
    @NamedQuery(name = "PlcMc.findByIdPlcMc", query = "SELECT p FROM PlcMc p WHERE p.idPlcMc = :idPlcMc"),
    @NamedQuery(name = "PlcMc.findByMacPlcMc", query = "SELECT p FROM PlcMc p WHERE p.macPlcMc = :macPlcMc"),
    @NamedQuery(name = "PlcMc.findByVersionFw", query = "SELECT p FROM PlcMc p WHERE p.versionFw = :versionFw"),
    @NamedQuery(name = "PlcMc.findByEstado", query = "SELECT p FROM PlcMc p WHERE p.estado = :estado"),
    @NamedQuery(name = "PlcMc.findByGtx", query = "SELECT p FROM PlcMc p WHERE p.gtx = :gtx"),
    @NamedQuery(name = "PlcMc.findByGrx", query = "SELECT p FROM PlcMc p WHERE p.grx = :grx"),
    @NamedQuery(name = "PlcMc.findByBps", query = "SELECT p FROM PlcMc p WHERE p.bps = :bps"),
    @NamedQuery(name = "PlcMc.findByRtx", query = "SELECT p FROM PlcMc p WHERE p.rtx = :rtx")})
public class PlcMc implements Serializable {

    @OneToMany(mappedBy = "macPlcMc")
    private Collection<EventosConsumoMc> eventosConsumoMcCollection;

    @OneToMany(mappedBy = "macPlcMc")
    private Collection<EventosAmarreMc> eventosAmarreMcCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_plc_mc")
    private String idPlcMc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "mac_plc_mc")
    private String macPlcMc;
    @Size(max = 20)
    @Column(name = "version_fw")
    private String versionFw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "gtx")
    private String gtx;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "grx")
    private String grx;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "bps")
    private String bps;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "rtx")
    private String rtx;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMc")
    private Collection<EventosConsumo> eventosConsumoCollection;
    @OneToMany(mappedBy = "macPlcMc")
    private Collection<Producto> productoCollection;

    public PlcMc() {
    }

    public PlcMc(String idPlcMc) {
        this.idPlcMc = idPlcMc;
    }

    public PlcMc(String idPlcMc, String macPlcMc, String estado, String gtx, String grx, String bps, String rtx) {
        this.idPlcMc = idPlcMc;
        this.macPlcMc = macPlcMc;
        this.estado = estado;
        this.gtx = gtx;
        this.grx = grx;
        this.bps = bps;
        this.rtx = rtx;
    }

    public String getIdPlcMc() {
        return idPlcMc;
    }

    public void setIdPlcMc(String idPlcMc) {
        this.idPlcMc = idPlcMc;
    }

    public String getMacPlcMc() {
        return macPlcMc;
    }

    public void setMacPlcMc(String macPlcMc) {
        this.macPlcMc = macPlcMc;
    }

    public String getVersionFw() {
        return versionFw;
    }

    public void setVersionFw(String versionFw) {
        this.versionFw = versionFw;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGtx() {
        return gtx;
    }

    public void setGtx(String gtx) {
        this.gtx = gtx;
    }

    public String getGrx() {
        return grx;
    }

    public void setGrx(String grx) {
        this.grx = grx;
    }

    public String getBps() {
        return bps;
    }

    public void setBps(String bps) {
        this.bps = bps;
    }

    public String getRtx() {
        return rtx;
    }

    public void setRtx(String rtx) {
        this.rtx = rtx;
    }

    @XmlTransient
    public Collection<EventosConsumo> getEventosConsumoCollection() {
        return eventosConsumoCollection;
    }

    public void setEventosConsumoCollection(Collection<EventosConsumo> eventosConsumoCollection) {
        this.eventosConsumoCollection = eventosConsumoCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlcMc != null ? idPlcMc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlcMc)) {
            return false;
        }
        PlcMc other = (PlcMc) object;
        if ((this.idPlcMc == null && other.idPlcMc != null) || (this.idPlcMc != null && !this.idPlcMc.equals(other.idPlcMc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.PlcMc[ idPlcMc=" + idPlcMc + " ]";
    }

    @XmlTransient
    public Collection<EventosAmarreMc> getEventosAmarreCollection() {
        return eventosAmarreMcCollection;
    }

    public void setEventosAmarreCollection(Collection<EventosAmarreMc> eventosAmarreMcCollection) {
        this.eventosAmarreMcCollection = eventosAmarreMcCollection;
    }

    @XmlTransient
    public Collection<EventosConsumoMc> getEventosConsumoMcCollection() {
        return eventosConsumoMcCollection;
    }

    public void setEventosConsumoMcCollection(Collection<EventosConsumoMc> eventosConsumoMcCollection) {
        this.eventosConsumoMcCollection = eventosConsumoMcCollection;
    }
    
}
