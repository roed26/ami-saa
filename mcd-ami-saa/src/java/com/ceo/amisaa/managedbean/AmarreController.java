package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosConsumoMacro;
import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.servicios.ResultadosEstadistica;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMacroFacade;
import com.ceo.amisaa.sessionbeans.MacroFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import com.ceo.amisaa.sessionbeans.TrafoFacade;
import static com.sun.faces.facelets.tag.composite.ImplementationHandler.Name;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "amarreController")
@SessionScoped
public class AmarreController implements Serializable {

    @EJB
    private MacroFacade ejbMacro;
    @EJB
    private EventosConsumoMacroFacade ejbEventoConsumoMacro;
    @EJB
    private TrafoFacade ejbTrafo;
    @EJB
    private EventosAmarreFacade ejbEventosAmarre;

    private Date fechaInicio;
    private Date fechaInicioUsar;
    private Date fechaFin;
    private String rangoFecha;
    private boolean rangoFechaCambiado1;
    private boolean rangoFechaCambiado2;
    private boolean rangoFechaCambiado3;
    private boolean sinConsumo;
    private boolean trafoSeleccionado;
    private boolean procesado;
    private boolean histograma;
    private boolean torta;
    private boolean curvas;
    private SimpleDateFormat formatoFecha;
    private CartesianChartModel porcentajeAmarre;
    private Trafo trafo;
    private Macro macro;

    //listas
    private List<EventosConsumoMacro> eventosConsumoMacro;
    private List<EventosAmarre> eventosAmarre;

    private PieChartModel modeloResultadosEstadisticas;

    public AmarreController() {
        modeloResultadosEstadisticas = new PieChartModel();
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostConstruct
    public void init() {
        this.procesado = false;
        this.macro = new Macro();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicioUsar() {
        return fechaInicioUsar;
    }

    public void setFechaInicioUsar(Date fechaInicioUsar) {
        this.fechaInicioUsar = fechaInicioUsar;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRangoFecha() {
        return rangoFecha;
    }

    public void setRangoFecha(String rangoFecha) {
        this.rangoFecha = rangoFecha;
    }

    public boolean isRangoFechaCambiado1() {
        return rangoFechaCambiado1;
    }

    public void setRangoFechaCambiado1(boolean rangoFechaCambiado1) {
        this.rangoFechaCambiado1 = rangoFechaCambiado1;
    }

    public boolean isRangoFechaCambiado2() {
        return rangoFechaCambiado2;
    }

    public void setRangoFechaCambiado2(boolean rangoFechaCambiado2) {
        this.rangoFechaCambiado2 = rangoFechaCambiado2;
    }

    public boolean isRangoFechaCambiado3() {
        return rangoFechaCambiado3;
    }

    public void setRangoFechaCambiado3(boolean rangoFechaCambiado3) {
        this.rangoFechaCambiado3 = rangoFechaCambiado3;
    }

    public boolean isSinConsumo() {
        return sinConsumo;
    }

    public boolean isHistograma() {
        return histograma;
    }

    public void setHistograma(boolean histograma) {
        this.histograma = histograma;
    }

    public boolean isTorta() {
        return torta;
    }

    public void setTorta(boolean torta) {
        this.torta = torta;
    }

    public boolean isCurvas() {
        return curvas;
    }

    public void setCurvas(boolean curvas) {
        this.curvas = curvas;
    }

    public void setSinConsumo(boolean sinConsumo) {
        this.sinConsumo = sinConsumo;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }

    public PieChartModel getModeloResultadosEstadisticas() {
        return modeloResultadosEstadisticas;
    }

    public void setModeloResultadosEstadisticas(PieChartModel modeloResultadosEstadisticas) {
        this.modeloResultadosEstadisticas = modeloResultadosEstadisticas;
    }

    public boolean isTrafoSeleccionado() {
        return trafoSeleccionado;
    }

    public void setTrafoSeleccionado(boolean trafoSeleccionado) {
        this.trafoSeleccionado = trafoSeleccionado;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public CartesianChartModel getPorcentajeAmarre() {
        return porcentajeAmarre;
    }

    public void setPorcentajeAmarre(CartesianChartModel porcentajeAmarre) {
        this.porcentajeAmarre = porcentajeAmarre;
    }

    public Trafo getTrafo() {
        return trafo;
    }

    public void setTrafo(Trafo trafo) {
        this.trafo = trafo;
    }

    public Macro getMacro() {
        return macro;
    }

    public void setMacro(Macro macro) {
        this.macro = macro;
    }

    public void seleccionarFecha() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarFecha').hide()");

        requestContext.execute("PF('seleccionarTrafo').show()");
    }

    public void seleccionarTrafo(Trafo trafo) {
        this.trafo = trafo;
        estadistica();
        this.trafoSeleccionado = true;
        this.procesado = true;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.update("estadisticas");
    }

    public void cambiarRangoFecha(ValueChangeEvent e) {
        String rangoSeleccionado = e.getNewValue().toString();
        if (rangoSeleccionado.equals("1")) {
            this.rangoFechaCambiado1 = true;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = false;
            Calendar calendar = Calendar.getInstance();
            this.fechaFin = calendar.getTime();
            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            this.fechaInicio = calendar.getTime();

        } else if (rangoSeleccionado.equals("2")) {
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = true;
            this.rangoFechaCambiado3 = false;
            Calendar calendar = Calendar.getInstance();
            this.fechaFin = calendar.getTime();
            calendar.setTime(fechaFin);
            calendar.add(Calendar.DAY_OF_YEAR, -30);
            this.fechaInicio = calendar.getTime();

        } else {
            this.fechaInicio = null;
            this.fechaFin = null;
            this.rangoFechaCambiado1 = false;
            this.rangoFechaCambiado2 = false;
            this.rangoFechaCambiado3 = true;
        }
    }

    public void procesarConsulta() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.macro = ejbMacro.buscarMacroPorTrafoObj(this.trafo);
        if (macro != null) {
            if (fechaInicio.after(fechaFin)) {
                FacesContext.getCurrentInstance().addMessage("formFecha:fechaInicio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha de inicio no puede ser mayor a la fecha de fin."));
            } else {
                requestContext.execute("PF('seleccionarFecha').hide()");
                this.trafoSeleccionado = true;

                consultarConsumo();
                this.fechaInicio = null;
                this.fechaFin = null;
                this.rangoFechaCambiado1 = false;
                this.rangoFechaCambiado2 = false;
                this.rangoFechaCambiado3 = false;
                this.rangoFecha = "";
                requestContext.update("trafoSeleccionado");
            }
        }

    }

    public void estadistica() {
        porcentajeAmarre = new CartesianChartModel();
        
        final ChartSeries porcentajeAmarreDia = new ChartSeries("porcentaje de amarre");

        List<Producto> listaProductosTrafo = new ArrayList<>();
        List<ResultadosEstadistica> listaResultados = new ArrayList<>();
        for (int i = 0; i < trafo.getProductoCollection().toArray().length; i++) {
            listaProductosTrafo.add((Producto) trafo.getProductoCollection().toArray()[i]);
        }

        for (int j = 0; j < listaProductosTrafo.size(); j++) {
            if (listaProductosTrafo.get(j).getPlcTuCollection().size() > 0) {
                ResultadosEstadistica resultadosEstadistica = new ResultadosEstadistica();
                resultadosEstadistica.setPlcTu((PlcTu) listaProductosTrafo.get(j).getPlcTuCollection().toArray()[0]);
                listaResultados.add(resultadosEstadistica);
            }
        }
        int contAmarrados = 1;
        int contNoAmarrados = 0;

        for (int i = 0; i < listaResultados.size(); i++) {
            if (listaResultados.get(i).getAmarre() == 1) {
                contAmarrados++;
            } else {
                contNoAmarrados++;
            }
        }
        int contEncuetas = 0;

        PlcMms plcMms = (PlcMms) this.trafo.getPlcMmsCollection().toArray()[0];
        this.eventosAmarre = this.ejbEventosAmarre.listaEventosPorFecha(plcMms, fechaInicio, fechaFin);
        if (this.eventosAmarre.size() > 0) {
            int i = 0;
            while (this.eventosAmarre.get(i).getFechaHora().getDay() == fechaInicio.getDay()) {
                if (this.eventosAmarre.get(i).getMacPlcTu().equals(listaResultados.get(0).getPlcTu())) {
                    contEncuetas++;
                }
                i++;
            }
            int cont = 0;
            int respondio = 0;
            int noRespondio = 0;
            for (int j = 0; j < (this.eventosAmarre.size()); j++) {

                if (this.eventosAmarre.get(j).getEstadoAmarre() == 0) {
                    noRespondio++;
                } else {
                    respondio++;
                }
                if (cont == listaResultados.size()) {
                    int total = noRespondio + respondio;
                    double porcentajeAmarre = (respondio * 100) / total;
                    porcentajeAmarreDia.set(formatoFecha.format(this.eventosAmarre.get(j).getFechaHora()), porcentajeAmarre);
                    cont = 0;
                    respondio = 0;
                    noRespondio = 0;
                } else {
                    cont++;
                }

            }

            porcentajeAmarre.addSeries(porcentajeAmarreDia);

            modeloResultadosEstadisticas.set("Con amerre", contAmarrados);
            modeloResultadosEstadisticas.set("Sin amarre", contNoAmarrados);

            modeloResultadosEstadisticas.setTitle("Porcentajes amarre");
            modeloResultadosEstadisticas.setLegendPosition("e");
            modeloResultadosEstadisticas.setShowDataLabels(true);
        }
    }

    public void cambiarEstadoSeleccion() {
        this.trafoSeleccionado = false;
    }

    public void consultarConsumo() {
        porcentajeAmarre = new CartesianChartModel();

        final ChartSeries consumoMacromedidor = new ChartSeries("Consumo");

        cambiarFechaInicio(fechaInicio);
        this.eventosConsumoMacro = this.ejbEventoConsumoMacro.listaEventosConsumoMacro(this.macro, fechaInicioUsar, fechaFin);
        if (this.eventosConsumoMacro.size() > 0) {

            for (int i = 0; i < eventosConsumoMacro.size(); i++) {
                if (i == 0) {
                    float energia = eventosConsumoMacro.get(i).getEnergia();
                    //consumoCliente.set(eventoConsumo.get(i).getFechaHora(), energia);

                } else {
                    float energia = 0;
                    if (eventosConsumoMacro.get(i - 1).getEnergia() > eventosConsumoMacro.get(i).getEnergia()) {
                        energia = eventosConsumoMacro.get(i - 1).getEnergia() - eventosConsumoMacro.get(i).getEnergia();
                    } else {
                        energia = eventosConsumoMacro.get(i).getEnergia() - eventosConsumoMacro.get(i - 1).getEnergia();
                    }
                    consumoMacromedidor.set(formatoFecha.format(eventosConsumoMacro.get(i).getFechaHora()), energia);

                }
            }
            porcentajeAmarre.addSeries(consumoMacromedidor);
            sinConsumo = false;
        } else {
            consumoMacromedidor.set(0, 0);
            porcentajeAmarre.addSeries(consumoMacromedidor);
            sinConsumo = true;

        }

    }

    public void cambiarFechaInicio(Date fechaInicio) {
        Calendar calendar = Calendar.getInstance();
        this.fechaInicio = fechaInicio;
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        this.fechaInicioUsar = calendar.getTime();
    }
}