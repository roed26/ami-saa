/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author geovanny
 */
@Entity
@Table(name = "notificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"),
    @NamedQuery(name = "Notificacion.newNotificaciones", query = "SELECT n FROM Notificacion n WHERE n.revisadoNotificacion = 0"),
    @NamedQuery(name = "Notificacion.oldNotificaciones", query = "SELECT n FROM Notificacion n WHERE n.revisadoNotificacion = 1"),
    @NamedQuery(name = "Notificacion.findByIdNotificacion", query = "SELECT n FROM Notificacion n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificacion.findByFechaNotificacion", query = "SELECT n FROM Notificacion n WHERE n.fechaNotificacion = :fechaNotificacion"),
    @NamedQuery(name = "Notificacion.findByRutaarchivoNotificacion", query = "SELECT n FROM Notificacion n WHERE n.rutaarchivoNotificacion = :rutaarchivoNotificacion"),
    @NamedQuery(name = "Notificacion.findByRevisadoNotificacion", query = "SELECT n FROM Notificacion n WHERE n.revisadoNotificacion = :revisadoNotificacion")})
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_notificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNotificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rutaarchivo_notificacion")
    private String rutaarchivoNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revisado_notificacion")
    private int revisadoNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_evento")
    private int tipoEvento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotificacion")
    private Collection<EventosAmarre> eventosAmarreCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotificacion")
    private Collection<EventosAmarreMc> eventosAmarreMcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotificacion")
    private Collection<EventosConsumo> eventosConsumoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotificacion")
    private Collection<EventosConsumoMc> eventosConsumoMcCollection;
    
    @Size(max = 100)
    @Column(name = "motivo")
    private String motivo;

    public Notificacion() {
    }

    public Notificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificacion(Integer idNotificacion, Date fechaNotificacion, String rutaarchivoNotificacion, int revisadoNotificacion) {
        this.idNotificacion = idNotificacion;
        this.fechaNotificacion = fechaNotificacion;
        this.rutaarchivoNotificacion = rutaarchivoNotificacion;
        this.revisadoNotificacion = revisadoNotificacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    
    
    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getRutaarchivoNotificacion() {
        return rutaarchivoNotificacion;
    }

    public void setRutaarchivoNotificacion(String rutaarchivoNotificacion) {
        this.rutaarchivoNotificacion = rutaarchivoNotificacion;
    }

    public int getRevisadoNotificacion() {
        return revisadoNotificacion;
    }

    public void setRevisadoNotificacion(int revisadoNotificacion) {
        this.revisadoNotificacion = revisadoNotificacion;
    }
    
    public int getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @XmlTransient
    public Collection<EventosAmarre> getEventosAmarreCollection() {
        return eventosAmarreCollection;
    }

    public void setEventosAmarreCollection(Collection<EventosAmarre> eventosAmarreCollection) {
        this.eventosAmarreCollection = eventosAmarreCollection;
    }

    @XmlTransient
    public Collection<EventosAmarreMc> getEventosAmarreMcCollection() {
        return eventosAmarreMcCollection;
    }

    public void setEventosAmarreMcCollection(Collection<EventosAmarreMc> eventosAmarreMcCollection) {
        this.eventosAmarreMcCollection = eventosAmarreMcCollection;
    }

    @XmlTransient
    public Collection<EventosConsumo> getEventosConsumoCollection() {
        return eventosConsumoCollection;
    }

    public void setEventosConsumoCollection(Collection<EventosConsumo> eventosConsumoCollection) {
        this.eventosConsumoCollection = eventosConsumoCollection;
    }

    @XmlTransient
    public Collection<EventosConsumoMc> getEventosConsumoMcCollection() {
        return eventosConsumoMcCollection;
    }

    public void setEventosConsumoMcCollection(Collection<EventosConsumoMc> eventosConsumoMcCollection) {
        this.eventosConsumoMcCollection = eventosConsumoMcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.entidades.Notificacion[ idNotificacion=" + idNotificacion + " ]";
    }
    
}
