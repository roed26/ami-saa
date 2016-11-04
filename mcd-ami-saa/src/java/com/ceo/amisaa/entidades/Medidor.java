package com.ceo.amisaa.entidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "medidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medidor.findAll", query = "SELECT m FROM Medidor m"),
    @NamedQuery(name = "Medidor.findByIdMedidor", query = "SELECT m FROM Medidor m WHERE m.idMedidor = :idMedidor"),
    @NamedQuery(name = "Medidor.findByEstado", query = "SELECT m FROM Medidor m WHERE m.estado = :estado"),
    @NamedQuery(name = "Medidor.findByTipo", query = "SELECT m FROM Medidor m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Medidor.findByMarca", query = "SELECT m FROM Medidor m WHERE m.marca = :marca"),
    @NamedQuery(name = "Medidor.findByModelo", query = "SELECT m FROM Medidor m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "Medidor.findByClase", query = "SELECT m FROM Medidor m WHERE m.clase = :clase"),
    @NamedQuery(name = "Medidor.findByProducto", query = "SELECT m FROM Medidor m WHERE m.idProducto = :idProducto"),
    @NamedQuery(name = "Medidor.findByMedidores", query = "SELECT m FROM Medidor m WHERE LOWER(m.idMedidor) LIKE :idMedidor ")
})
public class Medidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_medidor")
    private String idMedidor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "clase")
    private String clase;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    
    public Medidor() {
    }

    public Medidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public Medidor(String idMedidor, String estado, String tipo, String marca, String modelo, String clase) {
        this.idMedidor = idMedidor;
        this.estado = estado;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.clase = clase;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
        hash += (idMedidor != null ? idMedidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medidor)) {
            return false;
        }
        Medidor other = (Medidor) object;
        if ((this.idMedidor == null && other.idMedidor != null) || (this.idMedidor != null && !this.idMedidor.equals(other.idMedidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Medidor[ idMedidor=" + idMedidor + " ]";
    }

}

/*package com.ceo.amisaa.entidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
/*
@Entity
@Table(name = "medidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medidor.findAll", query = "SELECT m FROM Medidor m"),
    @NamedQuery(name = "Medidor.findByIdMedidor", query = "SELECT m FROM Medidor m WHERE m.idMedidor = :idMedidor"),
    @NamedQuery(name = "Medidor.findByEstado", query = "SELECT m FROM Medidor m WHERE m.estado = :estado"),
    @NamedQuery(name = "Medidor.findByTipo", query = "SELECT m FROM Medidor m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Medidor.findByMarca", query = "SELECT m FROM Medidor m WHERE m.marca = :marca"),
    @NamedQuery(name = "Medidor.findByModelo", query = "SELECT m FROM Medidor m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "Medidor.findByClase", query = "SELECT m FROM Medidor m WHERE m.clase = :clase"),
    @NamedQuery(name = "Medidor.findByProducto", query = "SELECT m FROM Medidor m WHERE m.idProducto = :idProducto")
})
public class Medidor implements Serializable {

    @OneToMany(mappedBy = "idMedidor")
    private Collection<EventosConsumoMc> eventosConsumoMcCollection;
    @OneToMany(mappedBy = "idMedidor")
    private Collection<EventosAmarreMc> eventosAmarreMcCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_medidor")
    private String idMedidor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "clase")
    private String clase;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false)
    private Producto idProducto;
    
    public Medidor() {
    }

    public Medidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public Medidor(String idMedidor, String estado, String tipo, String marca, String modelo, String clase) {
        this.idMedidor = idMedidor;
        this.estado = estado;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.clase = clase;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
        hash += (idMedidor != null ? idMedidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medidor)) {
            return false;
        }
        Medidor other = (Medidor) object;
        if ((this.idMedidor == null && other.idMedidor != null) || (this.idMedidor != null && !this.idMedidor.equals(other.idMedidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.managedbean.Medidor[ idMedidor=" + idMedidor + " ]";
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
    
}
*/
