/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
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
public class EventosAmarreFacade extends AbstractFacade<EventosAmarre> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosAmarreFacade() {
        super(EventosAmarre.class);
    }
    
    public List<EventosAmarre> listaEventos(PlcTu plcTu, Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosAmarre.findBylistaEventosPlcTu");
        query.setParameter("plcTu", plcTu);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosAmarre> resultList = query.getResultList();
        return resultList;
    }
    public List<EventosAmarre> listaEventosPorFecha(PlcMms plcMms,Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosAmarre.findBylistaEventosPorFecha");
        query.setParameter("plcMms", plcMms);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosAmarre> resultList = query.getResultList();
        return resultList;
    }
    
    public List<EventosAmarre> listaEventosPorDia(PlcMms plcMms,Date fechaHoraDia) {
        Query query = getEntityManager().createNamedQuery("EventosAmarre.findBylistaEventosPorDia");
        query.setParameter("plcMms", plcMms);
        query.setParameter("fechaHoraDia", fechaHoraDia);
        List<EventosAmarre> resultList = query.getResultList();
        return resultList;
    }
    
    public List<EventosAmarre> findByIdNotificacion(Integer idNotificacion) {
        Query query = getEntityManager().createNamedQuery("EventosAmarre.findByIdNotificacion");
        query.setParameter("idNotificacion", idNotificacion);
        
        List<EventosAmarre> resultList = query.getResultList();
        return resultList;
    }
}
