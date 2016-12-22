package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Usuario;
import com.ceo.amisaa.servicios.Utilidades;
import com.ceo.amisaa.sessionbeans.GrupoUsuarioRolFacade;
import com.ceo.amisaa.sessionbeans.UsuarioFacade;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Edwin Marulanda
 */
@ManagedBean
@SessionScoped
public class UsuarioSessionController implements Serializable {

    @EJB
    private GrupoUsuarioRolFacade grupoUsuarioRolEJB;
    @EJB
    private UsuarioFacade usuarioEJB;
    String nombreDeUsuario;
    String contrasena;

    private Boolean haySesion;
    private boolean errorSesion;
    private String cedula;
    private Usuario usuario;

    public UsuarioSessionController() {
        haySesion = false;
    }

    public Boolean getHaySesion() {
        return haySesion;
    }

    public void setHaySesion(Boolean haySesion) {
        this.haySesion = haySesion;
    }

    public boolean isErrorSesion() {
        return errorSesion;
    }

    public void setErrorSesion(boolean errorSesion) {
        this.errorSesion = errorSesion;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getIdentificacion() {
        return cedula;
    }

    public void setIdentificacion(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void login() throws IOException, ServletException {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            try {
                req.login(this.nombreDeUsuario, this.contrasena);
                req.getServletContext().log("Autenticacion exitosa");
                haySesion = true;
                this.errorSesion = false;
                if (this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioRolPK().getIdRol() == 1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/mcd-ami-saa/faces/plantilla/usuarioMain.xhtml");
                    cedula = this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getCedula();
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/mcd-ami-saa/faces/plantilla/usuarioMain.xhtml");
                    cedula = this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getCedula();
                }
            } catch (ServletException e) {

                this.errorSesion = true;

            }
        } else if (this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getGrupoUsuarioRolPK().getIdRol() == 1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/mcd-ami-saa/faces/plantilla/usuarioMain.xhtml");
            cedula = this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getCedula();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/mcd-ami-saa/faces/plantilla/usuarioMain.xhtml");
            cedula = this.grupoUsuarioRolEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getCedula();
        }
    }

    public void logout() throws IOException, ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/mcd-ami-saa/");

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", "Logout failed on backend"));
        }

    }

    public void ventanaInicioSession() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
        this.contrasena = null;
        this.nombreDeUsuario = null;
        requestContext.update("formularioInicioSession");
        requestContext.execute("PF('IniciarSesion').show()");
    }

    public boolean esusuarioSinSession() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return true;
        }
        return false;
    }

    public boolean esusuarioConSession() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return false;

        } else {
            /*if(this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getMuUsuariogrupoPK().getGruId().equals(2))
            {
                return true;
            }*/
            return false;
        }

    }

    public boolean esAdministrador() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return false;

        } else {
            /* if(this.usuarioGrupoEJB.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuariogrupoPK().getGruid().equals("admin"))
            {
                return true;
            }*/
            return false;
        }

    }

    public String nombreUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return "";
        } else {
            Usuario usuario= usuarioEJB.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
            if(usuario==null){
            return "";
            }else{
               return usuario.getNombres()+" "+usuario.getApellidos();
            }
            
            
        }
    }

    public StreamedContent getImagenPorDefecto() {
        return Utilidades.getImagenPorDefecto("foto");
    }
}
