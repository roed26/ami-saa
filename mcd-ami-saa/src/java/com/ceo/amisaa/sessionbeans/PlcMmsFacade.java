/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
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
public class PlcMmsFacade extends AbstractFacade<PlcMms> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlcMmsFacade() {
        super(PlcMms.class);
    }

    
    
    public List<PlcMms> buscarPorDato(String dato) {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByDato");
        query.setParameter("dato", "%" + dato + "%");
        List<PlcMms> resultList = query.getResultList();
        return resultList;
    }
    
    public PlcMms buscarPorIdTrafo(String idTrafo) {
        PlcMms plcMms; 
        Query query = getEntityManager().createNamedQuery("PlcMms.findByIdTrafo");
        query.setParameter("idTrafo", "%" + idTrafo + "%");
        List<PlcMms> resultList = query.getResultList();
        if(resultList.size()>0){
         plcMms=resultList.get(0);
        }else{
         plcMms=null;
        }
        return plcMms;
    }
    
    public PlcMms buscarPorIdTrafoObj(Trafo idTrafo) {
        PlcMms plcMms;
        Query query = getEntityManager().createNamedQuery("PlcMms.findByIdTrafoObj");
        query.setParameter("idTrafo", idTrafo);
        List<PlcMms> resultList = query.getResultList();
        if (resultList.size() > 0) {
            plcMms = resultList.get(0);
        } else {
            plcMms = null;
        }
        return plcMms;
    }
    
    public List<PlcMms> buscarPorIdTrafoLista(Trafo idTrafo) {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByIdTrafoObj");
        query.setParameter("idTrafo", idTrafo);
        List<PlcMms> resultList = query.getResultList();
        
        return resultList;
    }

    public boolean buscarPorId(String idPlcMms) {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByIdPlcMms");
        query.setParameter("idPlcMms", idPlcMms);
        List<PlcMms> resultList = query.getResultList();
        return !resultList.isEmpty();

    }

    public boolean buscarPorMac(String macPlcMms) {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByMacPlcMms");
        query.setParameter("macPlcMms", macPlcMms);
        List<PlcMms> resultList = query.getResultList();
        return !resultList.isEmpty();

    }

    public boolean buscarPorNumeroCelular(String numeroCelular) {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByNumeroCelular");
        query.setParameter("numeroCelular", numeroCelular);
        List<PlcMms> resultList = query.getResultList();
        return !resultList.isEmpty();

    }
    
    public PlcMms findByIdPlcMms(String idPlcMms)
    {
        Query query = getEntityManager().createNamedQuery("PlcMms.findByIdPlcMms");
        query.setParameter("idPlcMms", idPlcMms);
        List<PlcMms> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return  resultList.get(0);
        } 
        return null;
    }
}
