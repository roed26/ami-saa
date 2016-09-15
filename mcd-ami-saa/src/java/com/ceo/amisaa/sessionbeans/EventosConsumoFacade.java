/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.EventosConsumo;
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
public class EventosConsumoFacade extends AbstractFacade<EventosConsumo> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventosConsumoFacade() {
        super(EventosConsumo.class);
    }
    public List<EventosConsumo> listaEventos(PlcTu plcTu, Date fechaHoraInicio, Date fechaHoraFin) {
        Query query = getEntityManager().createNamedQuery("EventosConsumo.findBylistaEventosPlcTu");
        query.setParameter("plcTu", plcTu);
        query.setParameter("fechaHoraInicio", fechaHoraInicio);
        query.setParameter("fechaHoraFin", fechaHoraFin);
        List<EventosConsumo> resultList = query.getResultList();
        return resultList;
    }
}
