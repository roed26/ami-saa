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
@Table(name = "plc_tu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlcTu.findAll", query = "SELECT p FROM PlcTu p"),
    @NamedQuery(name = "PlcTu.findByIdPlcTu", query = "SELECT p FROM PlcTu p WHERE p.idPlcTu = :idPlcTu"),
    @NamedQuery(name = "PlcTu.findByMacPlcTu", query = "SELECT p FROM PlcTu p WHERE p.macPlcTu = :macPlcTu"),
    @NamedQuery(name = "PlcTu.findByVersionFw", query = "SELECT p FROM PlcTu p WHERE p.versionFw = :versionFw"),
    @NamedQuery(name = "PlcTu.findByEstado", query = "SELECT p FROM PlcTu p WHERE p.estado = :estado"),
    @NamedQuery(name = "PlcTu.findByGtx", query = "SELECT p FROM PlcTu p WHERE p.gtx = :gtx"),
    @NamedQuery(name = "PlcTu.findByGrx", query = "SELECT p FROM PlcTu p WHERE p.grx = :grx"),
    @NamedQuery(name = "PlcTu.findByBps", query = "SELECT p FROM PlcTu p WHERE p.bps = :bps"),
    @NamedQuery(name = "PlcTu.findByRtx", query = "SELECT p FROM PlcTu p WHERE p.rtx = :rtx"),
    @NamedQuery(name = "PlcTu.findByDato", query = "SELECT p FROM PlcTu p WHERE LOWER(CONCAT(CONCAT(CONCAT(CONCAT(p.idPlcTu,' '),p.macPlcTu),' '),p.versionFw)) LIKE :dato"),
    @NamedQuery(name = "PlcTu.findByProducto", query = "SELECT p FROM PlcTu p WHERE p.idProducto = :idProducto")

})
public class PlcTu implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcTu")
    private Collection<EventosAmarre> eventosAmarreCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_plc_tu")
    private String idPlcTu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "mac_plc_tu")
    private String macPlcTu;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "macPlcTu")
    private Collection<EventosConsumo> eventosConsumoCollection;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public PlcTu() {
    }

    public PlcTu(String idPlcTu) {
        this.idPlcTu = idPlcTu;
    }

    public PlcTu(String idPlcTu, String macPlcTu, String estado, String gtx, String grx, String bps, String rtx) {
        this.idPlcTu = idPlcTu;
        this.macPlcTu = macPlcTu;
        this.estado = estado;
        this.gtx = gtx;
        this.grx = grx;
        this.bps = bps;
        this.rtx = rtx;
    }

    public String getIdPlcTu() {
        return idPlcTu;
    }

    public void setIdPlcTu(String idPlcTu) {
        this.idPlcTu = idPlcTu;
    }

    public String getMacPlcTu() {
        return macPlcTu;
    }

    public void setMacPlcTu(String macPlcTu) {
        this.macPlcTu = macPlcTu;
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

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlcTu != null ? idPlcTu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlcTu)) {
            return false;
        }
        PlcTu other = (PlcTu) object;
        if ((this.idPlcTu == null && other.idPlcTu != null) || (this.idPlcTu != null && !this.idPlcTu.equals(other.idPlcTu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.PlcTu[ idPlcTu=" + idPlcTu + " ]";
    }

    @XmlTransient
    public Collection<EventosAmarre> getEventosAmarreCollection() {
        return eventosAmarreCollection;
    }

    public void setEventosAmarreCollection(Collection<EventosAmarre> eventosAmarreCollection) {
        this.eventosAmarreCollection = eventosAmarreCollection;
    }
    
}
