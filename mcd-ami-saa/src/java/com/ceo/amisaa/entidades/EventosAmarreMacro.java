/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aranda
 */
@Entity
@Table(name = "eventos_amarre_macro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventosAmarreMacro.findAll", query = "SELECT e FROM EventosAmarreMacro e"),
    @NamedQuery(name = "EventosAmarreMacro.findByIdAmarre", query = "SELECT e FROM EventosAmarreMacro e WHERE e.idAmarre = :idAmarre"),
    @NamedQuery(name = "EventosAmarreMacro.findByEstadoAmarre", query = "SELECT e FROM EventosAmarreMacro e WHERE e.estadoAmarre = :estadoAmarre"),
    @NamedQuery(name = "EventosAmarreMacro.findByIdNotificacion", query = "SELECT e FROM EventosAmarreMacro e WHERE e.idNotificacion.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "EventosAmarreMacro.findByFechaHora", query = "SELECT e FROM EventosAmarreMacro e WHERE e.fechaHora = :fechaHora")})
public class EventosAmarreMacro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_amarre")
    private Integer idAmarre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_amarre")
    private int estadoAmarre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "mac_plc_mms", referencedColumnName = "mac_plc_mms")
    @ManyToOne(optional = false)
    private PlcMms macPlcMms;
    @JoinColumn(name = "id_macro", referencedColumnName = "id_macro")
    @ManyToOne
    private Macro idMacro;
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")
    @ManyToOne(optional = false)
    private Notificacion idNotificacion;

    public EventosAmarreMacro() {
    }

    public EventosAmarreMacro(Integer idAmarre) {
        this.idAmarre = idAmarre;
    }

    public EventosAmarreMacro(Integer idAmarre, int estadoAmarre, Date fechaHora) {
        this.idAmarre = idAmarre;
        this.estadoAmarre = estadoAmarre;
        this.fechaHora = fechaHora;
    }

    public Integer getIdAmarre() {
        return idAmarre;
    }

    public void setIdAmarre(Integer idAmarre) {
        this.idAmarre = idAmarre;
    }

    public int getEstadoAmarre() {
        return estadoAmarre;
    }

    public void setEstadoAmarre(int estadoAmarre) {
        this.estadoAmarre = estadoAmarre;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public PlcMms getMacPlcMms() {
        return macPlcMms;
    }

    public void setMacPlcMms(PlcMms macPlcMms) {
        this.macPlcMms = macPlcMms;
    }

    public Macro getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(Macro idMacro) {
        this.idMacro = idMacro;
    }

    public Notificacion getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Notificacion idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmarre != null ? idAmarre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventosAmarreMacro)) {
            return false;
        }
        EventosAmarreMacro other = (EventosAmarreMacro) object;
        if ((this.idAmarre == null && other.idAmarre != null) || (this.idAmarre != null && !this.idAmarre.equals(other.idAmarre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.entidades.EventosAmarreMacro[ idAmarre=" + idAmarre + " ]";
    }
    
}
