/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosConsumoMacro;
import com.ceo.amisaa.entidades.Macro;
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
public class EventosConsumoMacroFacade extends AbstractFacade<EventosConsumoMacro> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosConsumoMacroFacade() {
        super(EventosConsumoMacro.class);
    }
    
    public List<EventosConsumoMacro> listaEventosConsumoMacro(Macro macro, Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosConsumoMacro.findBylistaEventosMacro");
        query.setParameter("macro", macro);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosConsumoMacro> resultList = query.getResultList();
        return resultList;
    }
    
    public List<EventosConsumoMacro> findByIdNotificacion(Integer idNotificacion) {
        Query query = getEntityManager().createNamedQuery("EventosConsumoMacro.findByIdNotificacion");
        query.setParameter("idNotificacion", idNotificacion);
        
        List<EventosConsumoMacro> resultList = query.getResultList();
        return resultList;
    }
    
}
