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
@Table(name = "macro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Macro.findAll", query = "SELECT m FROM Macro m"),
    @NamedQuery(name = "Macro.findByIdMacro", query = "SELECT m FROM Macro m WHERE m.idMacro = :idMacro"),
    @NamedQuery(name = "Macro.findByEstado", query = "SELECT m FROM Macro m WHERE m.estado = :estado"),
    @NamedQuery(name = "Macro.findByTipo", query = "SELECT m FROM Macro m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Macro.findByModelo", query = "SELECT m FROM Macro m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "Macro.findByMarca", query = "SELECT m FROM Macro m WHERE m.marca = :marca"),
    @NamedQuery(name = "Macro.findByClase", query = "SELECT m FROM Macro m WHERE m.clase = :clase"),
    @NamedQuery(name = "Macro.findByTrafo", query = "SELECT m FROM Macro m WHERE m.idTrafo = :trafo")
})
public class Macro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_macro")
    private String idMacro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "clase")
    private String clase;
    @JoinColumn(name = "id_trafo", referencedColumnName = "id_trafo")
    @ManyToOne(optional = false)
    private Trafo idTrafo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMacro")
    private Collection<ParametrosMacro> parametrosMacroCollection;
    //@OneToMany(mappedBy = "idMacro")
    //private Collection<EventosConsumo> eventosConsumoCollection;

    public Macro() {
    }

    public Macro(String idMacro) {
        this.idMacro = idMacro;
    }

    public Macro(String idMacro, String estado, String tipo, String modelo, String marca, String clase) {
        this.idMacro = idMacro;
        this.estado = estado;
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
        this.clase = clase;
    }

    public String getIdMacro() {
        return idMacro;
    }

    public void setIdMacro(String idMacro) {
        this.idMacro = idMacro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Trafo getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo(Trafo idTrafo) {
        this.idTrafo = idTrafo;
    }

    @XmlTransient
    public Collection<ParametrosMacro> getParametrosMacroCollection() {
        return parametrosMacroCollection;
    }

    public void setParametrosMacroCollection(Collection<ParametrosMacro> parametrosMacroCollection) {
        this.parametrosMacroCollection = parametrosMacroCollection;
    }

    /*@XmlTransient
    public Collection<EventosConsumo> getEventosConsumoCollection() {
        return eventosConsumoCollection;
    }

    public void setEventosConsumoCollection(Collection<EventosConsumo> eventosConsumoCollection) {
        this.eventosConsumoCollection = eventosConsumoCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMacro != null ? idMacro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Macro)) {
            return false;
        }
        Macro other = (Macro) object;
        if ((this.idMacro == null && other.idMacro != null) || (this.idMacro != null && !this.idMacro.equals(other.idMacro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.Macro[ idMacro=" + idMacro + " ]";
    }
    
}
