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
@Table(name = "plc_mms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlcMms.findAll", query = "SELECT p FROM PlcMms p"),
    @NamedQuery(name = "PlcMms.findByIdPlcMms", query = "SELECT p FROM PlcMms p WHERE p.idPlcMms = :idPlcMms"),
    @NamedQuery(name = "PlcMms.findByMacPlcMms", query = "SELECT p FROM PlcMms p WHERE p.macPlcMms = :macPlcMms"),
    @NamedQuery(name = "PlcMms.findByVersionFw", query = "SELECT p FROM PlcMms p WHERE p.versionFw = :versionFw"),
    @NamedQuery(name = "PlcMms.findByEstado", query = "SELECT p FROM PlcMms p WHERE p.estado = :estado"),
    @NamedQuery(name = "PlcMms.findByGtx", query = "SELECT p FROM PlcMms p WHERE p.gtx = :gtx"),
    @NamedQuery(name = "PlcMms.findByGrx", query = "SELECT p FROM PlcMms p WHERE p.grx = :grx"),
    @NamedQuery(name = "PlcMms.findByBps", query = "SELECT p FROM PlcMms p WHERE p.bps = :bps"),
    @NamedQuery(name = "PlcMms.findByRtx", query = "SELECT p FROM PlcMms p WHERE p.rtx = :rtx"),
    @NamedQuery(name = "PlcMms.findByNumeroCelular", query = "SELECT p FROM PlcMms p WHERE p.numeroCelular = :numeroCelular"),
    @NamedQuery(name = "PlcMms.findByDato", query = "SELECT p FROM PlcMms p WHERE LOWER(CONCAT(CONCAT(CONCAT(CONCAT(p.idPlcMms,' '),p.macPlcMms),' '),p.versionFw)) LIKE :dato"),
    @NamedQuery(name = "PlcMms.findByIdTrafo", query = "SELECT p FROM PlcMms p WHERE p.idTrafo.idTrafo = :idTrafo"),
    @NamedQuery(name = "PlcMms.findByIdTrafoObj", query = "SELECT p FROM PlcMms p WHERE p.idTrafo = :idTrafo")
})
public class PlcMms implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMms")
    private Collection<EventosConsumoMacro> eventosConsumoMacroCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMms")
    private Collection<EventosConsumoMc> eventosConsumoMcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMms")
    private Collection<EventosAmarreMc> eventosAmarreMcCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMms")
    private Collection<EventosAmarre> eventosAmarreCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_plc_mms")
    private String idPlcMms;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "mac_plc_mms")
    private String macPlcMms;
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
    @Column(name = "numero_celular")
    private String numeroCelular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcMms")
    private Collection<EventosConsumo> eventosConsumoCollection;
    @JoinColumn(name = "id_trafo", referencedColumnName = "id_trafo")
    @ManyToOne
    private Trafo idTrafo;

    public PlcMms() {
    }

    public PlcMms(String idPlcMms) {
        this.idPlcMms = idPlcMms;
    }

    public PlcMms(String idPlcMms, String macPlcMms, String estado, String gtx, String grx, String bps, String rtx,String numeroCelular) {
        this.idPlcMms = idPlcMms;
        this.macPlcMms = macPlcMms;
        this.estado = estado;
        this.gtx = gtx;
        this.grx = grx;
        this.bps = bps;
        this.rtx = rtx;
        this.numeroCelular=numeroCelular;
    }

    public String getIdPlcMms() {
        return idPlcMms;
    }

    public void setIdPlcMms(String idPlcMms) {
        this.idPlcMms = idPlcMms;
    }

    public String getMacPlcMms() {
        return macPlcMms;
    }

    public void setMacPlcMms(String macPlcMms) {
        this.macPlcMms = macPlcMms;
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

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
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
        hash += (idPlcMms != null ? idPlcMms.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlcMms)) {
            return false;
        }
        PlcMms other = (PlcMms) object;
        if ((this.idPlcMms == null && other.idPlcMms != null) || (this.idPlcMms != null && !this.idPlcMms.equals(other.idPlcMms))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.PlcMms[ idPlcMms=" + idPlcMms + " ]";
    }

    @XmlTransient
    public Collection<EventosAmarre> getEventosAmarreCollection() {
        return eventosAmarreCollection;
    }

    public void setEventosAmarreCollection(Collection<EventosAmarre> eventosAmarreCollection) {
        this.eventosAmarreCollection = eventosAmarreCollection;
    }

    @XmlTransient
    public Collection<EventosConsumoMc> getEventosConsumoMcCollection() {
        return eventosConsumoMcCollection;
    }

    public void setEventosConsumoMcCollection(Collection<EventosConsumoMc> eventosConsumoMcCollection) {
        this.eventosConsumoMcCollection = eventosConsumoMcCollection;
    }

    @XmlTransient
    public Collection<EventosAmarreMc> getEventosAmarreMcCollection() {
        return eventosAmarreMcCollection;
    }

    public void setEventosAmarreMcCollection(Collection<EventosAmarreMc> eventosAmarreMcCollection) {
        this.eventosAmarreMcCollection = eventosAmarreMcCollection;
    }

    @XmlTransient
    public Collection<EventosConsumoMacro> getEventosConsumoMacroCollection() {
        return eventosConsumoMacroCollection;
    }

    public void setEventosConsumoMacroCollection(Collection<EventosConsumoMacro> eventosConsumoMacroCollection) {
        this.eventosConsumoMacroCollection = eventosConsumoMacroCollection;
    }
    
}
