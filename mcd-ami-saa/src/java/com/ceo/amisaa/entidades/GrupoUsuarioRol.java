/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ROED26
 */
@Entity
@Table(name = "grupo_usuario_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoUsuarioRol.findAll", query = "SELECT g FROM GrupoUsuarioRol g"),
    @NamedQuery(name = "GrupoUsuarioRol.findByIdRol", query = "SELECT g FROM GrupoUsuarioRol g WHERE g.grupoUsuarioRolPK.idRol = :idRol"),
    @NamedQuery(name = "GrupoUsuarioRol.findByCedula", query = "SELECT g FROM GrupoUsuarioRol g WHERE g.grupoUsuarioRolPK.cedula = :cedula"),
    @NamedQuery(name = "GrupoUsuarioRol.findByNombreUsuario", query = "SELECT g FROM GrupoUsuarioRol g WHERE g.nombreUsuario = :nombreUsuario")})
public class GrupoUsuarioRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoUsuarioRolPK grupoUsuarioRolPK;
    @Size(max = 15)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public GrupoUsuarioRol() {
    }

    public GrupoUsuarioRol(GrupoUsuarioRolPK grupoUsuarioRolPK) {
        this.grupoUsuarioRolPK = grupoUsuarioRolPK;
    }

    public GrupoUsuarioRol(int idRol, String cedula) {
        this.grupoUsuarioRolPK = new GrupoUsuarioRolPK(idRol, cedula);
    }

    public GrupoUsuarioRolPK getGrupoUsuarioRolPK() {
        return grupoUsuarioRolPK;
    }

    public void setGrupoUsuarioRolPK(GrupoUsuarioRolPK grupoUsuarioRolPK) {
        this.grupoUsuarioRolPK = grupoUsuarioRolPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoUsuarioRolPK != null ? grupoUsuarioRolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuarioRol)) {
            return false;
        }
        GrupoUsuarioRol other = (GrupoUsuarioRol) object;
        if ((this.grupoUsuarioRolPK == null && other.grupoUsuarioRolPK != null) || (this.grupoUsuarioRolPK != null && !this.grupoUsuarioRolPK.equals(other.grupoUsuarioRolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ceo.amisaa.entidades.GrupoUsuarioRol[ grupoUsuarioRolPK=" + grupoUsuarioRolPK + " ]";
    }
    
}
