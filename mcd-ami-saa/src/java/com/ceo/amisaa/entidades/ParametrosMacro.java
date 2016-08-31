/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROED26
 */
@Entity
@Table(name = "parametros_macro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametrosMacro.findAll", query = "SELECT p FROM ParametrosMacro p"),
    @NamedQuery(name = "ParametrosMacro.findByIdParametro", query = "SELECT p FROM ParametrosMacro p WHERE p.idParametro = :idParametro"),
    @NamedQuery(name = "ParametrosMacro.findByCmR", query = "SELECT p FROM ParametrosMacro p WHERE p.cmR = :cmR"),
    @NamedQuery(name = "ParametrosMacro.findByCmS", query = "SELECT p FROM ParametrosMacro p WHERE p.cmS = :cmS"),
    @NamedQuery(name = "ParametrosMacro.findByCmT", query = "SELECT p FROM ParametrosMacro p WHERE p.cmT = :cmT"),
    @NamedQuery(name = "ParametrosMacro.findByPotAc", query = "SELECT p FROM ParametrosMacro p WHERE p.potAc = :potAc"),
    @NamedQuery(name = "ParametrosMacro.findByPotRe", query = "SELECT p FROM ParametrosMacro p WHERE p.potRe = :potRe"),
    @NamedQuery(name = "ParametrosMacro.findByPotAp", query = "SELECT p FROM ParametrosMacro p WHERE p.potAp = :potAp")})
public class ParametrosMacro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_parametro")
    private Integer idParametro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cm_r")
    private Float cmR;
    @Column(name = "cm_s")
    private Float cmS;
    @Column(name = "cm_t")
    private Float cmT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pot_ac")
    private float potAc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pot_re")
    private String potRe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pot_ap")
    private String potAp;
    @JoinColumn(name = "id_macro", referencedColumnName = "id_macro")
    @ManyToOne(optional = false)
    private Macro idMacro;

    public ParametrosMacro() {
    }

    public ParametrosMacro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public ParametrosMacro(Integer idParametro, float potAc, String potRe, String potAp) {
        this.idParametro = idParametro;
        this.potAc = potAc;
        this.potRe = potRe;
        this.potAp = potAp;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Float getCmR() {
        return cmR;
    }

    public void setCmR(Float cmR) {
        this.cmR = cmR;
    }

    public Float getCmS() {
        return cmS;
    }

    public void setCmS(Float cmS) {
        this.cmS = cmS;
    }

    public Float getCmT() {
        return cmT;
    }

    public void setCmT(Float cmT) {
        this.cmT = cmT;
    }

    public float getPotAc() {
        return potAc;
    }

    public void setPotAc(float potAc) {
        this.potAc = potAc;
    }

    public String getPotRe() {
        return potRe;
    }

    public void setPotRe(String potRe) {
        this.potRe = potRe;
    }

    public String getPotAp() {
        return potAp;
    }

    public void setPotAp(String potAp) {
        this.potAp = potAp;
    }

    public Macro getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(Macro idMacro) {
        this.idMacro = idMacro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrosMacro)) {
            return false;
        }
        ParametrosMacro other = (ParametrosMacro) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.ParametrosMacro[ idParametro=" + idParametro + " ]";
    }
    
}
