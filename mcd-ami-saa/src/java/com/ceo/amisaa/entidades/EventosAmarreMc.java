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
 * @author ROED26
 */
@Entity
@Table(name = "eventos_amarre_mc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventosAmarreMc.findAll", query = "SELECT e FROM EventosAmarreMc e"),
    @NamedQuery(name = "EventosAmarreMc.findByIdAmarreMc", query = "SELECT e FROM EventosAmarreMc e WHERE e.idAmarreMc = :idAmarreMc"),
    @NamedQuery(name = "EventosAmarreMc.findByEstadoAmarre", query = "SELECT e FROM EventosAmarreMc e WHERE e.estadoAmarre = :estadoAmarre"),
    @NamedQuery(name = "EventosAmarreMc.findByFechaHora", query = "SELECT e FROM EventosAmarreMc e WHERE e.fechaHora = :fechaHora")})
public class EventosAmarreMc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_amarre_mc")
    private Integer idAmarreMc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_amarre")
    private int estadoAmarre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "id_medidor", referencedColumnName = "id_medidor")
    @ManyToOne
    private Medidor idMedidor;
    @JoinColumn(name = "mac_plc_mc", referencedColumnName = "mac_plc_mc")
    @ManyToOne(optional = false)
    private PlcMc macPlcMc;
    @JoinColumn(name = "mac_plc_mms", referencedColumnName = "mac_plc_mms")
    @ManyToOne(optional = false)
    private PlcMms macPlcMms;
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")
    @ManyToOne(optional = false)
    private Notificacion idNotificacion;

    public EventosAmarreMc() {
    }

    public EventosAmarreMc(Integer idAmarreMc) {
        this.idAmarreMc = idAmarreMc;
    }

    public EventosAmarreMc(Integer idAmarreMc, int estadoAmarre, Date fechaHora) {
        this.idAmarreMc = idAmarreMc;
        this.estadoAmarre = estadoAmarre;
        this.fechaHora = fechaHora;
    }

    public Integer getIdAmarreMc() {
        return idAmarreMc;
    }

    public void setIdAmarreMc(Integer idAmarreMc) {
        this.idAmarreMc = idAmarreMc;
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

    public Medidor getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(Medidor idMedidor) {
        this.idMedidor = idMedidor;
    }

    public PlcMc getMacPlcMc() {
        return macPlcMc;
    }

    public void setMacPlcMc(PlcMc macPlcMc) {
        this.macPlcMc = macPlcMc;
    }

    public PlcMms getMacPlcMms() {
        return macPlcMms;
    }

    public void setMacPlcMms(PlcMms macPlcMms) {
        this.macPlcMms = macPlcMms;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmarreMc != null ? idAmarreMc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventosAmarreMc)) {
            return false;
        }
        EventosAmarreMc other = (EventosAmarreMc) object;
        if ((this.idAmarreMc == null && other.idAmarreMc != null) || (this.idAmarreMc != null && !this.idAmarreMc.equals(other.idAmarreMc))) {
            return false;
        }
        return true;
    }

    public Notificacion getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Notificacion idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    @Override
    public String toString() {
        return "com.ceo.amisaa.entidades.EventosAmarreMc[ idAmarreMc=" + idAmarreMc + " ]";
    }
    
}
