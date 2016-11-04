/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;


import com.ceo.amisaa.entidades.Medidor;
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
public class MedidorFacade extends AbstractFacade<Medidor> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedidorFacade() {
        super(Medidor.class);
    }
    public Medidor buscarMedidorDeProducto(Producto producto) {
        Medidor medidor = new Medidor();
        Query query = getEntityManager().createNamedQuery("Medidor.findByProducto");
        query.setParameter("idProducto", producto);
        List<Medidor> resultList = query.getResultList();
        if(resultList.isEmpty()){
         medidor=null;
        }else{
            medidor=resultList.get(0);
        }
        return medidor;
    }
    public List<Medidor> buscarPorId(String idMedidor) {
        Query query = getEntityManager().createNamedQuery("Medidor.findByMedidores");
        query.setParameter("idMedidor", "%" + idMedidor + "%");
        List<Medidor> resultList = query.getResultList();
        return resultList;
    }
    public Medidor buscarMedidorPorId(String idMedidor) {
        Query query = getEntityManager().createNamedQuery("Medidor.findByMedidores");
        query.setParameter("idMedidor", "%" + idMedidor + "%");
        List<Medidor> resultList = query.getResultList();

        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {

            return null;
        }
    }
}


/*
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Producto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class MedidorFacade extends AbstractFacade<Medidor> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedidorFacade() {
        super(Medidor.class);
    }

    public Medidor buscarMedidorDeProducto(Producto producto) {
        Medidor medidor = new Medidor();
        Query query = getEntityManager().createNamedQuery("Medidor.findAll");
        query.setParameter("idProducto", producto);
        List<Medidor> resultList = query.getResultList();
        if(resultList.isEmpty()){
         medidor=null;
        }else{
            medidor=resultList.get(0);
        }
        return medidor;
    }
}
*/