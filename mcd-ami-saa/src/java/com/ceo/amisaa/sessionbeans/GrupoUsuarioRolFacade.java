/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.GrupoUsuarioRol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ROED26
 */
@Stateless
public class GrupoUsuarioRolFacade extends AbstractFacade<GrupoUsuarioRol> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoUsuarioRolFacade() {
        super(GrupoUsuarioRol.class);
    }

    public List<GrupoUsuarioRol> buscarPorNombreUsuario(String nombreUsuario) {
        Query query = getEntityManager().createNamedQuery("GrupoUsuarioRol.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<GrupoUsuarioRol> resultList = query.getResultList();
        return resultList;
    }

    public GrupoUsuarioRol buscarPorNombreUsuarioObj(String nombreUsuario) {
        Query query = getEntityManager().createNamedQuery("GrupoUsuarioRol.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<GrupoUsuarioRol> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }

    }
    public boolean buscarPorNombreUsuarioBool(String nombreUsuario) {
        Query query = getEntityManager().createNamedQuery("GrupoUsuarioRol.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<GrupoUsuarioRol> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }
}
