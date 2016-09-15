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
@Table(name = "eventos_consumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventosConsumo.findAll", query = "SELECT e FROM EventosConsumo e"),
    @NamedQuery(name = "EventosConsumo.findByIdConsumo", query = "SELECT e FROM EventosConsumo e WHERE e.idConsumo = :idConsumo"),
    @NamedQuery(name = "EventosConsumo.findByEnergia", query = "SELECT e FROM EventosConsumo e WHERE e.energia = :energia"),
    @NamedQuery(name = "EventosConsumo.findByPotencia", query = "SELECT e FROM EventosConsumo e WHERE e.potencia = :potencia"),
    @NamedQuery(name = "EventosConsumo.findByVoltaje", query = "SELECT e FROM EventosConsumo e WHERE e.voltaje = :voltaje"),
    @NamedQuery(name = "EventosConsumo.findByCorriente", query = "SELECT e FROM EventosConsumo e WHERE e.corriente = :corriente"),
    @NamedQuery(name = "EventosConsumo.findByFechaHora", query = "SELECT e FROM EventosConsumo e WHERE e.fechaHora = :fechaHora"),
    @NamedQuery(name = "EventosConsumo.findBylistaEventosPlcTu", query = "SELECT e FROM EventosConsumo e WHERE e.macPlcTu = :plcTu AND(e.fechaHora BETWEEN :fechaHoraInicio AND :fechaHoraFin)")

})
public class EventosConsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @JoinColumn(name = "mac_plc_mms", referencedColumnName = "mac_plc_mms")
    @ManyToOne(optional = false)
    private PlcMms macPlcMms;
    @JoinColumn(name = "mac_plc_tu", referencedColumnName = "mac_plc_tu")
    @ManyToOne(optional = false)
    private PlcTu macPlcTu;

    public EventosConsumo() {
    }

    public EventosConsumo(Integer idConsumo) {
        this.idConsumo = idConsumo;
    }

    public EventosConsumo(Integer idConsumo, float energia, float potencia, float voltaje, float corriente, Date fechaHora) {
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

    public PlcMms getMacPlcMms() {
        return macPlcMms;
    }

    public void setMacPlcMms(PlcMms macPlcMms) {
        this.macPlcMms = macPlcMms;
    }

    public PlcTu getMacPlcTu() {
        return macPlcTu;
    }

    public void setMacPlcTu(PlcTu macPlcTu) {
        this.macPlcTu = macPlcTu;
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
        if (!(object instanceof EventosConsumo)) {
            return false;
        }
        EventosConsumo other = (EventosConsumo) object;
        if ((this.idConsumo == null && other.idConsumo != null) || (this.idConsumo != null && !this.idConsumo.equals(other.idConsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.EventosConsumo[ idConsumo=" + idConsumo + " ]";
    }
    
}
