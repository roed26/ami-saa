/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosAmarreMacro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@Stateless
public class EventosAmarreMacroFacade extends AbstractFacade<EventosAmarreMacro> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosAmarreMacroFacade() {
        super(EventosAmarreMacro.class);
    }
    
    public List<EventosAmarreMacro> findByIdNotificacion(Integer idNotificacion) {
        Query query = getEntityManager().createNamedQuery("EventosAmarreMacro.findByIdNotificacion");
        query.setParameter("idNotificacion", idNotificacion);
        
        List<EventosAmarreMacro> resultList = query.getResultList();
        return resultList;
    }
    
}
