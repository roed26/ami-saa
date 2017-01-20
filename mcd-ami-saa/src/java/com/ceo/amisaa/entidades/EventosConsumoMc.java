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
@Table(name = "eventos_consumo_mc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventosConsumoMc.findAll", query = "SELECT e FROM EventosConsumoMc e"),
    @NamedQuery(name = "EventosConsumoMc.findByIdConsumo", query = "SELECT e FROM EventosConsumoMc e WHERE e.idConsumo = :idConsumo"),
    @NamedQuery(name = "EventosConsumoMc.findByEnergia", query = "SELECT e FROM EventosConsumoMc e WHERE e.energia = :energia"),
    @NamedQuery(name = "EventosConsumoMc.findByPotencia", query = "SELECT e FROM EventosConsumoMc e WHERE e.potencia = :potencia"),
    @NamedQuery(name = "EventosConsumoMc.findByVoltaje", query = "SELECT e FROM EventosConsumoMc e WHERE e.voltaje = :voltaje"),
    @NamedQuery(name = "EventosConsumoMc.findByCorriente", query = "SELECT e FROM EventosConsumoMc e WHERE e.corriente = :corriente"),
    @NamedQuery(name = "EventosConsumoMc.findByFechaHora", query = "SELECT e FROM EventosConsumoMc e WHERE e.fechaHora = :fechaHora")})
public class EventosConsumoMc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_consumo")
    private Integer idConsumo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "energia")
    private float energia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "potencia")
    private float potencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "voltaje")
    private float voltaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "corriente")
    private float corriente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.DATE)
    private Date fechaHora;
    @JoinColumn(name = "id_medidor", referencedColumnName = "id_medidor")
    @ManyToOne
    private Medidor idMedidor;
    @JoinColumn(name = "mac_plc_mc", referencedColumnName = "mac_plc_mc")
    @ManyToOne
    private PlcMc macPlcMc;
    @JoinColumn(name = "mac_plc_mms", referencedColumnName = "mac_plc_mms")
    @ManyToOne(optional = false)
    private PlcMms macPlcMms;
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")
    @ManyToOne(optional = false)
    private Notificacion idNotificacion;

    public EventosConsumoMc() {
    }

    public EventosConsumoMc(Integer idConsumo) {
        this.idConsumo = idConsumo;
    }

    public EventosConsumoMc(Integer idConsumo, float energia, float potencia, float voltaje, float corriente, Date fechaHora) {
        this.idConsumo = idConsumo;
        this.energia = energia;
        this.potencia = potencia;
        this.voltaje = voltaje;
        this.corriente = corriente;
        this.fechaHora = fechaHora;
    }

    public Integer getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(Integer idConsumo) {
        this.idConsumo = idConsumo;
    }

    public float getEnergia() {
        return energia;
    }

    public void setEnergia(float energia) {
        this.energia = energia;
    }

    public float getPotencia() {
        return potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    public float getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(float voltaje) {
        this.voltaje = voltaje;
    }

    public float getCorriente() {
        return corriente;
    }

    public void setCorriente(float corriente) {
        this.corriente = corriente;
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
        hash += (idConsumo != null ? idConsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventosConsumoMc)) {
            return false;
        }
        EventosConsumoMc other = (EventosConsumoMc) object;
        if ((this.idConsumo == null && other.idConsumo != null) || (this.idConsumo != null && !this.idConsumo.equals(other.idConsumo))) {
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
        return "com.ceo.amisaa.entidades.EventosConsumoMc[ idConsumo=" + idConsumo + " ]";
    }
    
}
