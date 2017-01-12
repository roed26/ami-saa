package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.cifrado.Cifrar;
import com.ceo.amisaa.entidades.GrupoUsuarioRol;
import com.ceo.amisaa.entidades.GrupoUsuarioRolPK;
import com.ceo.amisaa.entidades.Rol;
import com.ceo.amisaa.entidades.Usuario;
import com.ceo.amisaa.managedbean.util.JsfUtil;
import com.ceo.amisaa.managedbean.util.JsfUtil.PersistAction;
import com.ceo.amisaa.sessionbeans.GrupoUsuarioRolFacade;
import com.ceo.amisaa.sessionbeans.UsuarioFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private com.ceo.amisaa.sessionbeans.UsuarioFacade ejbUsuario;
    @EJB
    private GrupoUsuarioRolFacade ejbGrupoUsuarioRol;
    private List<Usuario> items = null;
    private Usuario usuario;
    private Rol rol;

    //boolean
    private boolean mostrarNombres;
    private boolean mostrarApellidos;
    private boolean mostrarCelular;
    private boolean mostrarNombreDeUsuario;
    private boolean mostrarContrasena;

    //String
    private String repetirContrasena;
    private String dato;
    private String nombres;
    private String apellidos;
    private String celular;
    private String nombreDeUsuario;
    private String contrasena;

    public UsuarioController() {
        this.rol = new Rol();
        this.usuario = new Usuario();
    }

    @PostConstruct
    public void init() {
        this.mostrarNombres = true;
        this.mostrarApellidos = true;
        this.mostrarCelular = true;
        this.mostrarNombreDeUsuario = true;
        this.mostrarContrasena = true;
        this.rol = new Rol();
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public boolean isMostrarNombres() {
        return mostrarNombres;
    }

    public void setMostrarNombres(boolean mostrarNombres) {
        this.mostrarNombres = mostrarNombres;
    }

    public boolean isMostrarApellidos() {
        return mostrarApellidos;
    }

    public void setMostrarApellidos(boolean mostrarApellidos) {
        this.mostrarApellidos = mostrarApellidos;
    }

    public boolean isMostrarCelular() {
        return mostrarCelular;
    }

    public void setMostrarCelular(boolean mostrarCelular) {
        this.mostrarCelular = mostrarCelular;
    }

    public boolean isMostrarNombreDeUsuario() {
        return mostrarNombreDeUsuario;
    }

    public void setMostrarNombreDeUsuario(boolean mostrarNombreDeUsuario) {
        this.mostrarNombreDeUsuario = mostrarNombreDeUsuario;
    }

    public boolean isMostrarContrasena() {
        return mostrarContrasena;
    }

    public void setMostrarContrasena(boolean mostrarContrasena) {
        this.mostrarContrasena = mostrarContrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRepetirContrasena() {
        return repetirContrasena;
    }

    public void setRepetirContrasena(String repetirContrasena) {
        this.repetirContrasena = repetirContrasena;
    }

    protected void setEmbeddableKeys() {
    }

    private UsuarioFacade getFacade() {
        return ejbUsuario;
    }

    public void ventanaEliminar(Usuario usuario) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.usuario = usuario;
        requestContext.execute("PF('eliminarUsuario').show()");
    }

    public void registrarUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (!this.usuario.getContrasena().equals(repetirContrasena)) {
            FacesContext.getCurrentInstance().addMessage("UsuarioCreateForm:contrasena1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "las contraseñas no coinciden."));
            FacesContext.getCurrentInstance().addMessage("UsuarioCreateForm:contrasena2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "las contraseñas no coinciden."));
        } else {
            this.usuario.setContrasena(Cifrar.sha256(usuario.getContrasena()));
            GrupoUsuarioRol grupoUsuarioRol = new GrupoUsuarioRol();

            GrupoUsuarioRolPK grupoUsuarioRolPK = new GrupoUsuarioRolPK();
            grupoUsuarioRolPK.setIdRol(rol.getIdRol());
            grupoUsuarioRolPK.setCedula(usuario.getCedula());

            grupoUsuarioRol.setGrupoUsuarioRolPK(grupoUsuarioRolPK);
            grupoUsuarioRol.setNombreUsuario(usuario.getNombreUsuario());

            ejbUsuario.create(usuario);
            ejbGrupoUsuarioRol.create(grupoUsuarioRol);
            items = ejbUsuario.findAll();
            this.usuario = new Usuario();
            this.rol = new Rol();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El usuario se registro con exito."));
            requestContext.execute("PF('mensajeRegistroExitoso').show()");
        }

    }

    public void eliminarUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (this.usuario != null) {
            if (true) {
                this.ejbUsuario.remove(this.usuario);
                requestContext.update("UsuarioListForm");
                requestContext.execute("PF('eliminarUsuario').hide()");
                requestContext.execute("PF('eliminacionCorrecta').show()");
                this.usuario = new Usuario();
                this.items = getFacade().findAll();
            } else {
                this.usuario = new Usuario();
                requestContext.execute("PF('eliminarUsuario').hide()");
                requestContext.execute("PF('noSePuedeEliminar').show()");
            }
        }
    }

    public void reiniciarObj() {
        this.usuario = new Usuario();
        this.rol = new Rol();
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void buscarUsuario() {
        this.items = ejbUsuario.buscarPorDato(this.dato.toLowerCase());
        this.dato = "";
    }

    public void reiniciarCampo() {
        this.dato = "";
        this.items = ejbUsuario.findAll();

    }

    public Usuario getUsuario(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void mostrarModificarNombres() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarNombres = false;
        if (this.usuario.getNombres() != null) {
            this.nombres = this.usuario.getNombres();
        }
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarNombres() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarNombres = true;
        this.nombres = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarNombres() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarNombres = true;
        if (!this.nombres.isEmpty()) {
            this.usuario.setNombres(this.nombres);
        }
        this.ejbUsuario.edit(this.usuario);
        requestContext.update("UsuarioEditForm");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Los nombres se editaron con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void mostrarModificarApellidos() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarApellidos = false;
        if (this.usuario.getApellidos() != null) {
            this.apellidos = this.usuario.getApellidos();
        }
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarApellidos() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarApellidos = true;
        this.apellidos = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarApellidos() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarApellidos = true;
        if (!this.apellidos.isEmpty()) {
            this.usuario.setApellidos(this.apellidos);
        }
        this.ejbUsuario.edit(this.usuario);
        requestContext.update("UsuarioEditForm");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Los apellidos se editaron con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void mostrarModificarCelular() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarCelular = false;
        if (this.usuario.getCelular() != null) {
            this.celular = this.usuario.getCelular();
        }
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarCelular() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarCelular = true;
        this.celular = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarCelular() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarCelular = true;
        if (!this.celular.isEmpty()) {
            this.usuario.setCelular(this.celular);
        }
        this.ejbUsuario.edit(this.usuario);
        requestContext.update("UsuarioEditForm");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El número de celular se edito con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void mostrarModificarNombreDeUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarNombreDeUsuario = false;
        if (this.usuario.getNombreUsuario() != null) {
            this.nombreDeUsuario = this.usuario.getNombreUsuario();
        }
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarNombreDeUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarNombreDeUsuario = true;
        this.nombreDeUsuario = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarNombreDeUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (!this.nombreDeUsuario.isEmpty()) {
            if (!this.nombreDeUsuario.equals(this.usuario.getNombreUsuario()) && ejbGrupoUsuarioRol.buscarPorNombreUsuarioBool(this.nombreDeUsuario)) {
                FacesContext.getCurrentInstance().addMessage("UsuarioEditForm:nombreUsuario", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El nombre de usuario esta en uso."));
            } else {
                GrupoUsuarioRol grupoUsuarioRol = ejbGrupoUsuarioRol.buscarPorNombreUsuarioObj(this.usuario.getNombreUsuario());
                grupoUsuarioRol.setNombreUsuario(nombreDeUsuario);
                this.usuario.setNombreUsuario(this.nombreDeUsuario);
                ejbGrupoUsuarioRol.edit(grupoUsuarioRol);
                this.mostrarNombreDeUsuario = true;
            }
        }
        this.ejbUsuario.edit(this.usuario);
        requestContext.update("UsuarioEditForm");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El nombre de usuario se edito con exito."));
        requestContext.execute("PF('mensajeRegistroExitoso').show()");
    }

    public void mostrarModificarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = false;
        if (this.usuario.getContrasena() != null) {
            this.contrasena = this.usuario.getContrasena();
        }
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = true;
        this.contrasena = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        if (!this.contrasena.isEmpty()) {
            if (this.contrasena.length() < 6) {
                FacesContext.getCurrentInstance().addMessage("UsuarioEditForm:contrasena", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Minimo 6 caracteres (" + this.contrasena.length() + " de 6)"));
                requestContext.update("UsuarioEditForm");
            } else {
                this.usuario.setContrasena(Cifrar.sha256(this.contrasena));
                this.mostrarContrasena = true;
                this.ejbUsuario.edit(this.usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "La contraseña se edito con exito."));
                requestContext.execute("PF('mensajeRegistroExitoso').show()");
                requestContext.update("UsuarioEditForm");
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("UsuarioEditForm:contrasena", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Campo requerido"));
            requestContext.update("UsuarioEditForm");
            
        }

        

    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getCedula());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
