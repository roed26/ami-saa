/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcMms;
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
public class PlcMcFacade extends AbstractFacade<PlcMc> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlcMcFacade() {
        super(PlcMc.class);
    }

    public List<PlcMc> buscarPorDato(String dato) {
        Query query = getEntityManager().createNamedQuery("PlcMc.findByDato");
        query.setParameter("dato", "%" + dato + "%");
        List<PlcMc> resultList = query.getResultList();
        return resultList;
    }

    public boolean buscarPorIdBool(String idPlcMc) {
        Query query = getEntityManager().createNamedQuery("PlcMc.findByIdPlcMc");
        query.setParameter("idPlcMc", idPlcMc);
        List<PlcMc> resultList = query.getResultList();
        return !resultList.isEmpty();

    }

    public PlcMc buscarPorId(String idPlcMc) {
        Query query = getEntityManager().createNamedQuery("PlcMc.findByIdPlcMc");
        query.setParameter("idPlcMc", idPlcMc);
        List<PlcMc> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }

    }
    
    public PlcMc findByMacPlcMc(String macPlcMc) {
        Query query = getEntityManager().createNamedQuery("PlcMc.findByMacPlcMc");
        query.setParameter("macPlcMc", macPlcMc);
        List<PlcMc> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }

    }

    public boolean buscarPorMac(String macPlcMc) {
        Query query = getEntityManager().createNamedQuery("PlcMc.findByMacPlcMc");
        query.setParameter("macPlcMc", macPlcMc);
        List<PlcMc> resultList = query.getResultList();
        return !resultList.isEmpty();

    }

}
