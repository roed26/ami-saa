/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.PlcMc;
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
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public Producto buscarProductoDeCliente(Cliente cliente) {
        Producto producto= new Producto();
        Query query = getEntityManager().createNamedQuery("Producto.findByCedula");
        query.setParameter("cedula", cliente);
        List<Producto> resultList = query.getResultList();
        if(resultList.size()>0){
            producto= resultList.get(0);
        }else{
            producto=null;
        }
        return producto;
    }    
    
    public List<Producto> buscarProductosDeCliente(Cliente cliente) {
        
        Query query = getEntityManager().createNamedQuery("Producto.findByCedula");
        query.setParameter("cedula", cliente);
        List<Producto> resultList = query.getResultList();
        
        return resultList;
        
        
    }    
    public List<Producto> buscarPorId(String idProducto) {
        Query query = getEntityManager().createNamedQuery("Producto.findByProductos");
        query.setParameter("idProducto", "%" + idProducto + "%");
        List<Producto> resultList = query.getResultList();
        return resultList;
    }
    public boolean buscarPorIdBool(String idProducto) {
        Query query = getEntityManager().createNamedQuery("Producto.findByIdProducto");
        query.setParameter("idProducto", idProducto);
        List<Producto> resultList = query.getResultList();
        return !resultList.isEmpty();
    }
    public List<Producto> buscarListaProductosTrafo(Trafo idTrafo) {
        Query query = getEntityManager().createNamedQuery("Producto.findByProductosTrafo");
        query.setParameter("idTrafo", idTrafo );
        List<Producto> resultList = query.getResultList();
        return resultList;
    }
    
    public List<Producto> buscarListaProductosPlcMc(PlcMc plcMc) {
        Query query = getEntityManager().createNamedQuery("Producto.findByProductosPlcMc");
        query.setParameter("macPlcMc", plcMc );
        List<Producto> resultList = query.getResultList();
        return resultList;
    }
    
    public Producto buscarProductoDeMc(PlcMc macPlcMc) {
        Producto producto= new Producto();
        Query query = getEntityManager().createNamedQuery("Producto.findByPlcMc");
        query.setParameter("macPlcMc", macPlcMc);
        List<Producto> resultList = query.getResultList();
        if(resultList.size()>0){
            producto= resultList.get(0);
        }else{
            producto=null;
        }
        return producto;
    } 
    
    
    
}
