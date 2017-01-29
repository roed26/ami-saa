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
@Named(value = "routingController")
@SessionScoped
public class RoutingController implements Serializable {

    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public RoutingController() {

    }

    public void irGestionPlcTu() {
        this.ruta = "/administrador/plcTu/listadoPlcTu.xhtml";

    }

    public void irRegistroPlcTu() {
        this.ruta = "/administrador/plcTu/registroPlcTu.xhtml";
    }

    public void irVincularPlcTu() {
        this.ruta = "/administrador/plcTu/VincularPlcTu.xhtml";
    }

    public void irDesvincularPlcTu() {
        this.ruta = "/administrador/desvinculos/DesvincularProductoPlcTu.xhtml";
    }

    public void irGestionPlcMc() {
        this.ruta = "/administrador/plcMc/listadoPlcMc.xhtml";
    }

    public void irRegistroPlcMc() {
        this.ruta = "/administrador/plcMc/RegistroPlcMc.xhtml";
    }

    public void irVincularPlcMc() {
        this.ruta = "/administrador/plcMc/VincularPlcMc.xhtml";
    }
    public void irDesvincularPlcMc() {
        this.ruta = "/administrador/desvinculos/DesvincularProductosPlcMc.xhtml";
    }

    public void irVincularProductoTrafo() {
        this.ruta = "/administrador/vinculos/VincularProductoTrafo.xhtml";
    }

    public void irDesvincularProductoTrafo() {
        this.ruta = "/administrador/desvinculos/DesvincularProductoTrafo.xhtml";
    }

    public void irVincularProductoCliente() {
        this.ruta = "/administrador/vinculos/VincularProductoCliente.xhtml";
    }
    public void irDesvincularProductoCliente() {
        this.ruta = "/administrador/desvinculos/DesvincularProductoCliente.xhtml";
    }

    public void irVincularProductoMedidor() {
        this.ruta = "/administrador/vinculos/VincularProductoMedidor.xhtml";
    }

    public void irDesvincularProductoMedidor() {
        this.ruta = "/administrador/desvinculos/DesvincularProductoMedidor.xhtml";
    }

    public void irGestionPlcMms() {
        this.ruta = "/administrador/plcMms/listadoPlcMms.xhtml";
    }

    public void irRegistroPlcMms() {
        this.ruta = "/administrador/plcMms/RegistroPlcMms.xhtml";
    }

    public void irVincularPlcMms() {
        this.ruta = "/administrador/plcMms/VincularPlcMms.xhtml";
    }

    public void irDesvincularPlcMms() {
        this.ruta = "/administrador/desvinculos/DesvincularPlcMms.xhtml";
    }

    public void irEstadoAmarre() {
        this.ruta = "/administrador/cliente/estadoAmarre.xhtml";
    }

    public void irEstadisticasAmarre() {
        this.ruta = "/administrador/amarre/estadisticasDeAmarre.xhtml";
    }

    public void irListaClientes() {
        this.ruta = "/administrador/trafo/listaClientes.xhtml";
    }

    public void irSolicitudTiempoReal() {
        this.ruta = "/administrador/solicitudes/solicitudEstadoTR.xhtml";
    }

    public void irSolicitudTiempoRealCliente() {
        this.ruta = "/administrador/solicitudes/solicitudEstadoCliente.xhtml";
    }

    public void irSolicitudSuspensionCliente() {
        this.ruta = "/administrador/solicitudes/solicitudSuspensionCliente.xhtml";
    }

    public void irSolicitudReconexionCliente() {
        this.ruta = "/administrador/solicitudes/solicitudReconexionCliente.xhtml";
    }

    public void irSolicitudConfiguracion() {
        this.ruta = "/administrador/solicitudes/solicitudConfiguracion.xhtml";
    }

    public void irConsumoCliente() {
        this.ruta = "/administrador/eventosConsumo/consumoPorCliente.xhtml";
    }

    public void irConsumoTrafo() {
        this.ruta = "/administrador/eventosConsumo/consumoPorTrafo.xhtml";
    }

    public void irTrafoClientes() {
        this.ruta = "/administrador/mapas/trafoClientes.xhtml";
    }

    public void irGeomarre() {
        this.ruta = "/administrador/mapas/geoamarre.xhtml";
    }

    public void irRegistrarUsuario() {
        this.ruta = "/administrador/usuario/RegistrarUsuario.xhtml";
    }

    public void irRegistrarCliente() {
        this.ruta = "/administrador/cliente/RegistrarCliente.xhtml";
    }

    public void irGestionarCliente() {
        this.ruta = "/administrador/cliente/ListadoClientes.xhtml";
    }

    public void irRegistrarProducto() {
        this.ruta = "/administrador/producto/RegistrarProducto.xhtml";
    }

    public void irGestionarProducto() {
        this.ruta = "/administrador/producto/ListadoProductos.xhtml";
    }

    public void irRegistrarTrafo() {
        this.ruta = "/administrador/trafo/RegistrarTrafo.xhtml";
    }

    public void irGestionarTrafo() {
        this.ruta = "/administrador/trafo/ListadoTrafos.xhtml";
    }

    public void irGestionarUsuario() {
        this.ruta = "/administrador/usuario/ListadoUsuarios.xhtml";
    }

    public void irRegistrarMedidor() {
        this.ruta = "/administrador/medidor/RegistrarMedidor.xhtml";
    }

    public void irGestionarMedidores() {
        this.ruta = "/administrador/medidor/ListadoMedidores.xhtml";
    }

    public void irRegistrarMacromedidor() {
        this.ruta = "/administrador/macro/RegistrarMacro.xhtml";
    }

    public void irGestionarMacromedidores() {
        this.ruta = "/administrador/macro/ListadoMacros.xhtml";
    }

    public void irPerfilDeUsuario() {
        this.ruta = "/administrador/perfilDeUsuario.xhtml";
    }

    public void irNotificaciones() {
        this.ruta = "/administrador/notificaciones/notificaciones.xhtml";
    }
    
    public void irNotificacion()
    {
        this.ruta = "/administrador/notificaciones/notificacion.xhtml";
    }
}
