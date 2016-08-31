/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.sessionbeans;

import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.PlcMms;
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
public class MacroFacade extends AbstractFacade<Macro> {

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MacroFacade() {
        super(Macro.class);
    }

    public List<Macro> buscarMacro(PlcMms plcMms) {
        Query query = getEntityManager().createNamedQuery("Macro.findByidplcMms");
        query.setParameter("plcMms", plcMms);
        List<Macro> resultList = query.getResultList();
        return resultList;
    }

    public List<Macro> buscarMacroPorTrafo(Trafo trafo) {
        Query query = getEntityManager().createNamedQuery("Macro.findByTrafo");
        query.setParameter("trafo", trafo);
        List<Macro> resultList = query.getResultList();
        return resultList;
    }

    public Macro buscarMacroPorTrafoObj(Trafo trafo) {
        Macro macro = new Macro();
        Query query = getEntityManager().createNamedQuery("Macro.findByTrafo");
        query.setParameter("trafo", trafo);
        List<Macro> resultList = query.getResultList();
        if (resultList.size() > 0) {
            macro = resultList.get(0);
        } else {
            macro = null;
        }
        return macro;
    }

}
