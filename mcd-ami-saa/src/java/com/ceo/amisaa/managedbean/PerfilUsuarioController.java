package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.cifrado.Cifrar;
import com.ceo.amisaa.entidades.Usuario;
import com.ceo.amisaa.sessionbeans.UsuarioFacade;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Edwin Marulanda
 */
@ManagedBean
@SessionScoped
public class PerfilUsuarioController implements Serializable {

    @EJB
    private UsuarioFacade usuarioEJB;
    private Usuario usuario;
    private SimpleDateFormat sdf;
    private boolean mostrarContrasena;
    private boolean mostrarTelefono;
    private boolean mostrarExtension;
    private boolean mostrarCelular;
    private String contrasena;
    private String confirmarContrasena;
    private String telefono;
    private String extension;
    private String celular;

    public PerfilUsuarioController() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostConstruct
    private void init() {
        buscarUsuario();
        iniciarVariables();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isMostrarContrasena() {
        return mostrarContrasena;
    }

    public void setMostrarContrasena(boolean mostrarContrasena) {
        this.mostrarContrasena = mostrarContrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }

    public boolean isMostrarTelefono() {
        return mostrarTelefono;
    }

    public void setMostrarTelefono(boolean mostrarTelefono) {
        this.mostrarTelefono = mostrarTelefono;
    }

    public boolean isMostrarExtension() {
        return mostrarExtension;
    }

    public void setMostrarExtension(boolean mostrarExtension) {
        this.mostrarExtension = mostrarExtension;
    }

    public boolean isMostrarCelular() {
        return mostrarCelular;
    }

    public void setMostrarCelular(boolean mostrarCelular) {
        this.mostrarCelular = mostrarCelular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    private void buscarUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() != null) {
            this.usuario = this.usuarioEJB.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
        }
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public void mostrarModificarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = false;
        requestContext.update("formularioPerfilDatosPersonales");
    }

    public void cancelarActualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = true;
        this.contrasena = "";
        this.confirmarContrasena = "";
        requestContext.update("formularioPerfilDatosPersonales");
    }

    public void cambiarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.usuario.setContrasena(Cifrar.sha256(this.contrasena));
        this.usuarioEJB.edit(this.usuario);
        this.mostrarContrasena = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info. Se cambio la contraseña correctamente.", ""));

        requestContext.update("formularioPerfilDatosPersonales");

    }

    public void mostrarModificarCelular() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarCelular = false;
        if (this.usuario.getCelular() != null) {
            this.celular = this.usuario.getCelular() + "";
        }
        requestContext.update("formularioPerfilDatosPersonales");
    }

    public void cancelarActualizarCelular() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarCelular = true;
        this.celular = "";
        requestContext.update("formularioPerfilDatosPersonales");
    }

    public void actualizarCelular() {

        RequestContext requestContext = RequestContext.getCurrentInstance();

        this.mostrarCelular = true;
        if (!this.celular.isEmpty()) {

            this.usuario.setCelular(this.celular);
        } else {
            this.usuario.setCelular(null);
        }
        this.usuarioEJB.edit(this.usuario);

        requestContext.update("formularioPerfilDatosPersonales");
    }

    private void iniciarVariables() {
        this.mostrarContrasena = true;
        this.mostrarTelefono = true;
        this.mostrarExtension = true;
        this.mostrarCelular = true;
    }
}
