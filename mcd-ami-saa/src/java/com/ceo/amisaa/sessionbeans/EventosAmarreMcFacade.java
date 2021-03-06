/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosAmarreMc;
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
public class EventosAmarreMcFacade extends AbstractFacade<EventosAmarreMc> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosAmarreMcFacade() {
        super(EventosAmarreMc.class);
    }
    
    public List<EventosAmarreMc> findByIdNotificacion(Integer idNotificacion) {
        Query query = getEntityManager().createNamedQuery("EventosAmarreMc.findByIdNotificacion");
        query.setParameter("idNotificacion", idNotificacion);
        
        List<EventosAmarreMc> resultList = query.getResultList();
        return resultList;
    }
    
}
