/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;


import com.ceo.amisaa.entidades.Notificacion;
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
public class NotificacionFacade extends AbstractFacade<Notificacion> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionFacade() {
        super(Notificacion.class);
    }    
    
    public List<Notificacion> getNotificacionesNuevas()
    {
        Query query = getEntityManager().createNamedQuery("Notificacion.newNotificaciones");        
        List<Notificacion> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Notificacion> getNotificacionesRevisadas()
    {
        Query query = getEntityManager().createNamedQuery("Notificacion.oldNotificaciones");        
        List<Notificacion> resultList = query.getResultList();
        return resultList;
    }
   
}
