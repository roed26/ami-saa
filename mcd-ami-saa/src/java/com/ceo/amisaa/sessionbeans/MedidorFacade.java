/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Medidor;
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
    
    public List<Medidor> buscarMedidorDeProducto(Producto producto) {
        Query query = getEntityManager().createNamedQuery("Medidor.findByidProducto");
        query.setParameter("idProducto", producto);
        List<Medidor> resultList = query.getResultList();
        return resultList;
    }
    public List<Medidor> buscarPorPlcTu1(PlcTu plcTu1) {
        Query query = getEntityManager().createNamedQuery("Medidor.findByidPlcTu1");
        query.setParameter("idPlcTu1", plcTu1);
        List<Medidor> resultList = query.getResultList();
        return resultList;
    }
}
