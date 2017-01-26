/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
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
public class PlcTuFacade extends AbstractFacade<PlcTu> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlcTuFacade() {
        super(PlcTu.class);
    }

    public List<PlcTu> buscarPorDato(String dato) {
        Query query = getEntityManager().createNamedQuery("PlcTu.findByDato");
        query.setParameter("dato", "%" + dato + "%");
        List<PlcTu> resultList = query.getResultList();
        return resultList;
    }

    public List<PlcTu> buscarPorProducto(Producto idProducto) {
        Query query = getEntityManager().createNamedQuery("PlcTu.findByProducto");
        query.setParameter("idProducto", idProducto);
        List<PlcTu> resultList = query.getResultList();
        
        return resultList;
 
    }
    
    public boolean buscarPorId(String idPlcTu) {
        Query query = getEntityManager().createNamedQuery("PlcTu.findByIdPlcTu");
        query.setParameter("idPlcTu", idPlcTu);
        List<PlcTu> resultList = query.getResultList();
        return !resultList.isEmpty();

    }
    
    public boolean buscarPorMac(String macPlcTu) {
        Query query = getEntityManager().createNamedQuery("PlcTu.findByMacPlcTu");
        query.setParameter("macPlcTu", macPlcTu);
        List<PlcTu> resultList = query.getResultList();
        return !resultList.isEmpty();

    }
    
    public PlcTu getPorMac(String macPlcTu) {
        Query query = getEntityManager().createNamedQuery("PlcTu.findByMacPlcTu");
        query.setParameter("macPlcTu", macPlcTu);
        List<PlcTu> resultList = query.getResultList();
        if(resultList.size()>0)
        {
            return resultList.get(0);
        }
        return null;

    }
    
    public PlcTu buscarIdProducto(Producto idProducto) {
        PlcTu plcTu= new PlcTu();
        Query query = getEntityManager().createNamedQuery("PlcTu.findByProducto");
        query.setParameter("idProducto", idProducto);
        List<PlcTu> resultList = query.getResultList();
        if(resultList.isEmpty()){
         plcTu=null;
        }else{
            plcTu=resultList.get(0);
        }
        return plcTu;
     }
 }