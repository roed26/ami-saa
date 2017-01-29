/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author edwin
 */
@Named(value = "routingControllerOperario")
@SessionScoped
public class RoutingControllerOperario implements Serializable {

    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public RoutingControllerOperario() {

    }

    public void irGestionPlcTu() {
        this.ruta = "/operario/plcTu/listadoPlcTu.xhtml";

    }

    public void irDesvincularPlcTu() {
        this.ruta = "/operario/desvinculos/DesvincularProductoPlcTu.xhtml";
    }

    public void irGestionPlcMc() {
        this.ruta = "/operario/plcMc/listadoPlcMc.xhtml";
    }

    public void irRegistroPlcMc() {
        this.ruta = "/operario/plcMc/RegistroPlcMc.xhtml";
    }

    public void irVincularPlcMc() {
        this.ruta = "/operario/plcMc/VincularPlcMc.xhtml";
    }
    public void irDesvincularPlcMc() {
        this.ruta = "/operario/desvinculos/DesvincularProductosPlcMc.xhtml";
    }

    public void irVincularProductoTrafo() {
        this.ruta = "/operario/vinculos/VincularProductoTrafo.xhtml";
    }

    public void irDesvincularProductoTrafo() {
        this.ruta = "/operario/desvinculos/DesvincularProductoTrafo.xhtml";
    }

    public void irVincularProductoCliente() {
        this.ruta = "/operario/vinculos/VincularProductoCliente.xhtml";
    }
    public void irDesvincularProductoCliente() {
        this.ruta = "/operario/desvinculos/DesvincularProductoCliente.xhtml";
    }

    public void irVincularProductoMedidor() {
        this.ruta = "/operario/vinculos/VincularProductoMedidor.xhtml";
    }

    public void irDesvincularProductoMedidor() {
        this.ruta = "/operario/desvinculos/DesvincularProductoMedidor.xhtml";
    }

    public void irGestionPlcMms() {
        this.ruta = "/operario/plcMms/listadoPlcMms.xhtml";
    }

    public void irRegistroPlcMms() {
        this.ruta = "/operario/plcMms/RegistroPlcMms.xhtml";
    }

    public void irVincularPlcMms() {
        this.ruta = "/operario/plcMms/VincularPlcMms.xhtml";
    }

    public void irDesvincularPlcMms() {
        this.ruta = "/operario/desvinculos/DesvincularPlcMms.xhtml";
    }

    public void irEstadoAmarre() {
        this.ruta = "/operario/cliente/estadoAmarre.xhtml";
    }

    public void irEstadisticasAmarre() {
        this.ruta = "/operario/amarre/estadisticasDeAmarre.xhtml";
    }

    public void irListaClientes() {
        this.ruta = "/operario/trafo/listaClientes.xhtml";
    }

    public void irSolicitudTiempoReal() {
        this.ruta = "/operario/solicitudes/solicitudEstadoTR.xhtml";
    }

    public void irSolicitudTiempoRealCliente() {
        this.ruta = "/operario/solicitudes/solicitudEstadoCliente.xhtml";
    }

    public void irSolicitudSuspensionCliente() {
        this.ruta = "/operario/solicitudes/solicitudSuspensionCliente.xhtml";
    }

    public void irSolicitudReconexionCliente() {
        this.ruta = "/operario/solicitudes/solicitudReconexionCliente.xhtml";
    }

    public void irSolicitudConfiguracion() {
        this.ruta = "/operario/solicitudes/solicitudConfiguracion.xhtml";
    }

    public void irConsumoCliente() {
        this.ruta = "/operario/eventosConsumo/consumoPorCliente.xhtml";
    }

    public void irConsumoTrafo() {
        this.ruta = "/operario/eventosConsumo/consumoPorTrafo.xhtml";
    }

    public void irTrafoClientes() {
        this.ruta = "/operario/mapas/trafoClientes.xhtml";
    }

    public void irGeomarre() {
        this.ruta = "/operario/mapas/geoamarre.xhtml";
    }

    public void irRegistrarUsuario() {
        this.ruta = "/operario/usuario/RegistrarUsuario.xhtml";
    }

    public void irRegistrarCliente() {
        this.ruta = "/operario/cliente/RegistrarCliente.xhtml";
    }

    public void irGestionarCliente() {
        this.ruta = "/operario/cliente/ListadoClientes.xhtml";
    }

    public void irRegistrarProducto() {
        this.ruta = "/operario/producto/RegistrarProducto.xhtml";
    }

    public void irGestionarProducto() {
        this.ruta = "/operario/producto/ListadoProductos.xhtml";
    }

    public void irGestionarTrafo() {
        this.ruta = "/operario/trafo/ListadoTrafos.xhtml";
    }


    public void irGestionarMedidores() {
        this.ruta = "/operario/medidor/ListadoMedidores.xhtml";
    }

    public void irRegistrarMacromedidor() {
        this.ruta = "/operario/macro/RegistrarMacro.xhtml";
    }

    public void irGestionarMacromedidores() {
        this.ruta = "/operario/macro/ListadoMacros.xhtml";
    }

    public void irPerfilDeUsuario() {
        this.ruta = "/operario/perfilDeUsuario.xhtml";
    }

    public void irNotificaciones() {
        this.ruta = "/operario/notificaciones/notificaciones.xhtml";
    }
    
    public void irNotificacion()
    {
        this.ruta = "/operario/notificaciones/notificacion.xhtml";
    }
}
