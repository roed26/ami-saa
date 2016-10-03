/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Trafo;
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
public class TrafoFacade extends AbstractFacade<Trafo> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrafoFacade() {
        super(Trafo.class);
    }

    public List<Trafo> buscarPorId(String idTrafo) {
        Query query = getEntityManager().createNamedQuery("Trafo.findByTrafos");
        query.setParameter("idTrafo", "%" + idTrafo + "%");
        List<Trafo> resultList = query.getResultList();
        return resultList;
    }
    
    
    public Trafo buscarPorIdObj(String idTrafo) {
        Trafo trafo= new Trafo();
        Query query = getEntityManager().createNamedQuery("Trafo.findByTrafos");
        query.setParameter("idTrafo", "%" + idTrafo + "%");
        List<Trafo> resultList = query.getResultList();
        if(resultList.size()>0){
            trafo=resultList.get(0);
        }else{
            trafo= null;
        }
        
        return trafo;
    }
    
    
    
}
