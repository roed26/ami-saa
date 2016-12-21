/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    public List<Usuario> buscarPorDato(String dato) {
        Query query = getEntityManager().createNamedQuery("Usuario.findByDato");
        query.setParameter("dato", "%" + dato + "%");
        List<Usuario> resultList = query.getResultList();
        return resultList;
    }
    public boolean buscarPorId(String cedula) {
        
        Query query = getEntityManager().createNamedQuery("Usuario.findByCedula");
        query.setParameter("cedula", cedula);
        List<Usuario> resultList = query.getResultList();
        return !resultList.isEmpty();

    }
    public boolean buscarPorNombreUsuario(String nombreUsuario) {
        
        Query query = getEntityManager().createNamedQuery("Usuario.findByNombreUsuario");
        query.setParameter("nombreUsuario", nombreUsuario);
        List<Usuario> resultList = query.getResultList();
        return !resultList.isEmpty();

    }
}
