/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.webservice;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author geovanny
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.ceo.amisaa.webservice.ClienteWebServices.class);
        resources.add(com.ceo.amisaa.webservice.LoginWebServices.class);
        resources.add(com.ceo.amisaa.webservice.MacroWebServices.class);
        resources.add(com.ceo.amisaa.webservice.MedidorWebServices.class);
        resources.add(com.ceo.amisaa.webservice.PlcMcWebServices.class);
        resources.add(com.ceo.amisaa.webservice.PlcMmsWebServices.class);
        resources.add(com.ceo.amisaa.webservice.PlcTusResource.class);
        resources.add(com.ceo.amisaa.webservice.ProductoWebServices.class);
        resources.add(com.ceo.amisaa.webservice.TrafoWebServices.class);
        resources.add(com.ceo.amisaa.webservice.WebService.class);
    }
    
}
