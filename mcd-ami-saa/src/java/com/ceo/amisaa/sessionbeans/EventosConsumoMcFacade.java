/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.PlcMc;
import java.util.Date;
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
public class EventosConsumoMcFacade extends AbstractFacade<EventosConsumoMc> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosConsumoMcFacade() {
        super(EventosConsumoMc.class);
    }
    
    public List<EventosConsumoMc> findByIdNotificacion(Integer idNotificacion) {
        Query query = getEntityManager().createNamedQuery("EventosConsumoMc.findByIdNotificacion");
        query.setParameter("idNotificacion", idNotificacion);
        
        List<EventosConsumoMc> resultList = query.getResultList();
        return resultList;
    }
    
    public List<EventosConsumoMc> listaEventosMc(PlcMc plcMc, Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosConsumoMc.findBylistaEventosPlcMc");
        query.setParameter("plcMc", plcMc);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosConsumoMc> resultList = query.getResultList();
        return resultList;
    }
    
    public List<EventosConsumoMc> listaEventosMcMedidor(PlcMc plcMc, Medidor medidor, Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosConsumoMc.findBylistaEventosPlcMcMedidor");
        query.setParameter("plcMc", plcMc);
        query.setParameter("medidor", medidor);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosConsumoMc> resultList = query.getResultList();
        return resultList;
    }    
    
}
