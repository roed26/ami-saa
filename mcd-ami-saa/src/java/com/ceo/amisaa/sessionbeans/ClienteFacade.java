/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public List<Cliente> buscarPorNombresApellidos(String nombresApellidos) {
        Query query = getEntityManager().createNamedQuery("Cliente.findByNombresApellidos");
        query.setParameter("nombresApellidos","%" +nombresApellidos+"%" );
        List<Cliente> resultList = query.getResultList();
        return resultList;
    }

    public Cliente buscarPorCedula(String cedula) {
        Cliente cliente = new Cliente();
        Query query = getEntityManager().createNamedQuery("Cliente.findByCedula");
        query.setParameter("cedula",cedula);
        List<Cliente> resultList = query.getResultList();

        if (resultList.size() > 0) {
            cliente = resultList.get(0);
        } else {
            cliente = null;
        }
        return cliente;
    }
}
