/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.webservice;

import com.ceo.amisaa.cifrado.Cifrar;
import com.ceo.amisaa.entidades.Usuario;
import com.ceo.amisaa.sessionbeans.AbstractFacade;
import com.ceo.amisaa.sessionbeans.UsuarioFacade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Edwin marulanda
 */
@Stateless
@Path("/Login")
public class LoginWebServices extends AbstractFacade<Usuario> {

    @EJB
    private UsuarioFacade ejbUsuario;

    @PersistenceContext(unitName = "mcd-ami-saaPU")
    private EntityManager em;

    public LoginWebServices() {
        super(Usuario.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String principal() {
        return "Iniciar Sesión";
    }

    @GET
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public String iniciarSesion(@QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("contrasenia") String contrasenia) {
        Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(nombreUsuario);
        try {
            JSONObject jsonObj;
            if (usuario != null) {

                if (usuario.getContrasena().equals(Cifrar.sha256(contrasenia))) {
                    jsonObj = new JSONObject("{\"respuesta\":true}");
                    return jsonObj.toString();
                } else {
                    jsonObj = new JSONObject("{\"respuesta\":false}");
                    return jsonObj.toString();
                }
            } else {
                jsonObj = new JSONObject("{\"respuesta\":false}");
                return jsonObj.toString();
            }
        } catch (JSONException ex) {
            Logger.getLogger(LoginWebServices.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
