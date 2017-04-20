/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.managedbean;

import com.ceo.amisaa.clases.BalanceMC;
import com.ceo.amisaa.clases.BalanceMacro;
import com.ceo.amisaa.clases.BalanceTU;
import com.ceo.amisaa.clases.ConsumosMacro;
import com.ceo.amisaa.clases.ConsumosMc;
import com.ceo.amisaa.clases.ConsumosTu;
import com.ceo.amisaa.entidades.Cliente;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.EventosConsumoMacro;
import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMacroFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMcFacade;
import com.ceo.amisaa.sessionbeans.MacroFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import com.ceo.amisaa.sessionbeans.ProductoFacade;
import static com.sun.faces.facelets.tag.composite.ImplementationHandler.Name;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.Math.abs;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named(value="balanceController")
@SessionScoped
/**
 *
 * @author Natic_000
 */
public class BalanceController implements Serializable{

    @EJB
    private MacroFacade ejbMacro;
    @EJB
    private EventosConsumoMacroFacade ejbEventoConsumoMacro;
    @EJB
    private ProductoFacade ejbProducto;
    @EJB
    private PlcTuFacade ejbPlcTu;
    @EJB
    private EventosConsumoFacade ejbEventoConsumo;
    @EJB
    private EventosConsumoMcFacade ejbEventoConsumoMc;
    
    
    private Date fechaInicio;
    private Date fechaInicioUsar;
    private Date fechaFin;    
    private String rangoFecha;
    private boolean rangoFechaCambiado1;
    private boolean rangoFechaCambiado2;
    private boolean rangoFechaCambiado3;
    private boolean sinConsumo;
    private boolean trafoSeleccionado;
    private SimpleDateFormat formatoFecha;
    private SimpleDateFormat formatoFechaReporte;
    private CartesianChartModel consumo;
    private Trafo trafo;
    private Macro macro;           
    private String rangoBalanceI;
    private String rangoBalanceF;
    private float rangoBalance;  

    //listas
    private List<EventosConsumoMacro> eventosConsumoMacroI; 
    private List<EventosConsumo> eventoConsumoI;    
    private List<Producto> Productos;
    private List<Cliente> items = null;
    private List<EventosConsumoMc> eventoConsumoMc;
    private List<ConsumosTu> consumosTu;
    private List<ConsumosMc> consumosMc;
    private List<ConsumosMacro> consumosMacro;
    
    //Reporte excel
     Workbook wb;
     HSSFFont fuente;
     HSSFCellStyle estiloCeldaTitulo;
     HSSFCellStyle estiloCelda;
     ArrayList<BalanceTU> ReporteTU ;
     ArrayList<BalanceMC> ReporteMC ;
     ArrayList<BalanceMacro> ReporteMacro ;
     //Calendar calendarioNombre = Calendar.getInstance();
     

    public BalanceController() {
    
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        this.formatoFechaReporte = new SimpleDateFormat("dd-MM-yyyy");
    }
    
    @PostConstruct
    public void init(){
        sinConsumo=true;
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

    public String getRangoBalanceI() {
        return rangoBalanceI;
    }

    public void setRangoBalanceI(String rangoBalanceI) {
        this.rangoBalanceI = rangoBalanceI;
    }

    public String getRangoBalanceF() {
        return rangoBalanceF;
    }

    public void setRangoBalanceF(String rangoBalanceF) {
        this.rangoBalanceF = rangoBalanceF;
    }

    public float getRangoBalance() {
        return rangoBalance;
    }

    public void setRangoBalance(Float rangoBalance) {
        this.rangoBalance = rangoBalance;
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

    public void setSinConsumo(boolean sinConsumo) {
        this.sinConsumo = sinConsumo;
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

    public CartesianChartModel getConsumo() {
        return consumo;
    }

    public void setConsumo(CartesianChartModel consumo) {
        this.consumo = consumo;
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

    public void seleccionarTrafo(Trafo trafo) {
        this.trafo = trafo;
        this.trafoSeleccionado = true;
        this.rangoFechaCambiado1 = false;
        this.rangoFechaCambiado2 = false;
        this.rangoFechaCambiado3 = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formFecha");                
        requestContext.update("formLoading"); 
        requestContext.execute("PF('seleccionarTrafo').hide()");
        requestContext.execute("PF('cargando').hide()");
        requestContext.execute("PF('seleccionarFecha').show()");        
               
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
                this.rangoBalanceI = ""+formatoFecha.format(this.fechaInicio);
                this.rangoBalanceF = ""+formatoFecha.format(this.fechaFin);
                requestContext.execute("PF('seleccionarFecha').hide()");
                requestContext.execute("PF('cargando').show()");
                this.trafoSeleccionado = true;                                
                consultarConsumo();                
                
                this.rangoFechaCambiado1 = false;
                this.rangoFechaCambiado2 = false;
                this.rangoFechaCambiado3 = false;
                this.rangoFecha = "";
                this.fechaInicio = null;
                this.fechaFin = null;
                requestContext.update("trafoSeleccionado");
                requestContext.execute("PF('cargando').hide()");
            }
        }
    }
    
    public void procesarConsultaPor() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.macro = ejbMacro.buscarMacroPorTrafoObj(this.trafo);
        if (macro != null) {
            if (fechaInicio.after(fechaFin)) {
                FacesContext.getCurrentInstance().addMessage("formFecha:fechaInicio", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "la fecha de inicio no puede ser mayor a la fecha de fin."));
            } else {
                this.rangoBalanceI = ""+formatoFecha.format(this.fechaInicio);
                this.rangoBalanceF = ""+formatoFecha.format(this.fechaFin);
                requestContext.execute("PF('seleccionarFecha').hide()");
                requestContext.execute("PF('cargando').show()");
                this.trafoSeleccionado = true;                                
                consultarConsumoPor();                                
                this.rangoFechaCambiado1 = false;
                this.rangoFechaCambiado2 = false;
                this.rangoFechaCambiado3 = false;
                this.rangoFecha = "";
                this.fechaInicio = null;
                this.fechaFin = null;
                requestContext.update("trafoSeleccionado");
                requestContext.execute("PF('cargando').hide()");
            }
        }
    }
    
    public void consultarConsumo() {                                     
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
        consumo = new CartesianChartModel();
        ReporteMacro = new ArrayList<BalanceMacro>();
        ReporteTU = new ArrayList<BalanceTU>();
        ReporteMC = new ArrayList<BalanceMC>();
        configurarExcel();
        final ChartSeries consumoCliente = new ChartSeries("Diferencia");
        consumo.setShowPointLabels(true);        
        

        this.Productos = this.ejbProducto.buscarListaProductosTrafo(this.trafo);
        if (this.Productos.size() > 0) {
            float suma = 0;
            List<PlcTu> t = new ArrayList<>();     
            List<PlcMc> mc = new ArrayList<>();            
            System.out.println("El tamaño del arreglo productos es: " + this.Productos.size());
            for (int i = 0; i < this.Productos.size(); i++) {                
                List<PlcTu> resultList = this.ejbPlcTu.buscarPorProducto(this.Productos.get(i));                                                
                if (resultList.size() > 0) {
                    System.out.println("El ID del PLC_TU es: " + resultList.get(0).getIdPlcTu());
                    System.out.println("El tu asociado es: " + resultList.get(0));
                    PlcTu tu = resultList.get(0);                    
                    System.out.println("V o F: " + t.add(tu));
                }else{
                    PlcMc m = this.Productos.get(i).getMacPlcMc();
                    if(!mc.contains(m)){                        
                        System.out.println("La MAC del MC es: "+m.getMacPlcMc());
                        System.out.println("V o F MC:" + mc.add(m)); 
                    }                                                                               
                }                
            }
            System.out.println("El tamaño de la lista de MC's: " + mc.size());
            System.out.println("El tamaño de la lista de TU's: " + t.size());

            if (t.size()+mc.size() > 0) {                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicio);
                calendar.add(Calendar.DAY_OF_YEAR, -1);

                int dias = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000);
                //dias = dias++;
                System.out.println("Los dias son: " + dias);
                float consumoMcs, consumoTus, consumoMicro, consumoMacro, energia;                
                boolean ultimoDia = false;
                for (int z = -1; z <= dias; z++) {
                    System.out.println("El valor de z en dias es: "+dias);
                    if (z == -1) {                        
                        consumoTus = this.consultarConsumoInicialTu(t, calendar.getTime());                        
                        consumoMcs = this.consultarConsumoInicialMc(mc, calendar.getTime());
                        consumoMacro = consultarConsumoInicialMacro(this.macro, calendar.getTime());                        
                    } else {
                        if(z == dias){
                            ultimoDia=true;
                        }
                        consumoTus = this.consultarConsumoTu(t, calendar.getTime(),ultimoDia);                        
                        consumoMcs = this.consultarConsumoMc(mc, calendar.getTime(),ultimoDia);
                        consumoMacro = this.consultarConsumoMacro(this.macro, calendar.getTime(),ultimoDia);
                        
                        consumoMicro = consumoTus+consumoMcs;                        

                        System.out.println("Resultados para la fecha: " + calendar.getTime());
                        System.out.println("El consumo micro es: "+consumoMicro);
                        System.out.println("El consumo macro es: " + consumoMacro);

                        energia = consumoMacro - consumoMicro;
                        System.out.println("Las perdidas en total son: " + energia);
                        consumoCliente.set(formatoFecha.format(calendar.getTime()), energia);
                        suma = suma + energia;
                    }
                    calendar.add(Calendar.DAY_OF_YEAR, +1);                                        
                }                
                this.rangoBalance = suma;
                consumo.addSeries(consumoCliente);
                if(this.rangoBalance == 0.0){
                   this.sinConsumo = true; 
                }else{
                    this.sinConsumo = false;
                }
                // requestContext.update("trafoSeleccionado");
            }
        } else {            
            consumoCliente.set(0, 0);
            consumo.addSeries(consumoCliente);
            this.sinConsumo = true;
        }                
       // requestContext.update("trafoSeleccionado");
       guardar(this.trafo, this.fechaInicio,this.fechaFin);
       consumo.setShowPointLabels(true);       
    }
    
    public void consultarConsumoPor() {                                     
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
        consumo = new CartesianChartModel();

        final ChartSeries consumoCliente = new ChartSeries("Diferencia");                

        this.Productos = this.ejbProducto.buscarListaProductosTrafo(this.trafo);
        if (this.Productos.size() > 0) {
            float suma = 0;
            List<PlcTu> t = new ArrayList<>();     
            List<PlcMc> mc = new ArrayList<>(); 
            ReporteTU = new ArrayList<BalanceTU>();
            ReporteMC = new ArrayList<BalanceMC>();
            configurarExcel();
            System.out.println("El tamaño del arreglo productos es: " + this.Productos.size());
            for (int i = 0; i < this.Productos.size(); i++) {                
                List<PlcTu> resultList = this.ejbPlcTu.buscarPorProducto(this.Productos.get(i));                                                
                if (resultList.size() > 0) {
                    System.out.println("El ID del PLC_TU es: " + resultList.get(0).getIdPlcTu());
                    System.out.println("El tu asociado es: " + resultList.get(0));
                    PlcTu tu = resultList.get(0);                    
                    System.out.println("V o F: " + t.add(tu));
                }else{
                    PlcMc m = this.Productos.get(i).getMacPlcMc();
                    if(!mc.contains(m)){                        
                        System.out.println("La MAC del MC es: "+m.getMacPlcMc());
                        System.out.println("V o F MC:" + mc.add(m)); 
                    }                                                                               
                }                
            }
            System.out.println("El tamaño de la lista de MC's: " + mc.size());
            System.out.println("El tamaño de la lista de TU's: " + t.size());

            if (t.size()+mc.size() > 0) {                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicio);
                calendar.add(Calendar.DAY_OF_YEAR, -1);

                int dias = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / 86400000);
                System.out.println("Los dias son: " + dias);
                float consumoMcs, consumoTus, consumoMicro, consumoMacro, energia;                  
                boolean ultimoDia= false;
                for (int z = 0; z <= dias; z++) {                    
                    if (z == 0) {                        
                        consumoTus = this.consultarConsumoInicialTu(t, calendar.getTime());                        
                        consumoMcs = this.consultarConsumoInicialMc(mc, calendar.getTime());
                        consumoMacro = consultarConsumoInicialMacro(this.macro, calendar.getTime());                        
                    } else {
                        
                        
                        if(z==dias){
                            ultimoDia=true;
                        }
                        consumoTus = this.consultarConsumoTu(t, calendar.getTime(),ultimoDia);                        
                        consumoMcs = this.consultarConsumoMc(mc, calendar.getTime(),ultimoDia);
                        consumoMacro = this.consultarConsumoMacro(this.macro, calendar.getTime(),ultimoDia);                        
                        
                        consumoMicro = consumoTus+consumoMcs;                        

                        System.out.println("Resultados para la fecha: " + calendar.getTime());
                        System.out.println("El consumo micro es: "+consumoMicro);
                        System.out.println("El consumo macro es: " + consumoMacro);

                        energia = consumoMacro - consumoMicro;
                        suma = suma + energia;
                        if(energia != 0){
                            if(consumoMacro == 0){
                                energia = 100;
                            }else{
                                energia = (energia / consumoMacro)*100;
                            }                            
                        }
                        System.out.println("Las perdidas en total son: " + energia);
                        consumoCliente.set(formatoFecha.format(calendar.getTime()), energia);
                    }
                    
                    calendar.add(Calendar.DAY_OF_YEAR, +1);
                    
                                        
                }                
                this.rangoBalance = suma;
                consumo.addSeries(consumoCliente);
                if(this.rangoBalance == 0.0){
                   this.sinConsumo = true; 
                }else{
                    this.sinConsumo = false;
                }
                // requestContext.update("trafoSeleccionado");
            }                        
        } else {            
            consumoCliente.set(0, 0);
            consumo.addSeries(consumoCliente);
            this.sinConsumo = true;
        }                
       // requestContext.update("trafoSeleccionado");
       guardar(this.trafo, this.fechaInicio,this.fechaFin);
       consumo.setShowPointLabels(true);       
    }        

    public float consultarConsumoMacro(Macro macro, Date fecha, boolean ultimodia) {    
                        
        float consumoTotal = 0;
        float consumo = 0;
        float energia = 0;         
            
        this.eventosConsumoMacroI = this.ejbEventoConsumoMacro.listaEventosConsumoMacro(macro, fecha, fecha);
            
        for (int r=0; r<this.eventosConsumoMacroI.size(); r++){
            if(this.eventosConsumoMacroI.get(r).getEnergia() == 0){
                BalanceMacro balanceMacro = new BalanceMacro();
                balanceMacro.setFecha(formatoFecha.format(eventosConsumoMacroI.get(r).getFechaHora()));
                balanceMacro.setId_macro(macro.getIdMacro());
                System.out.println("Reporte Macro: "+ balanceMacro.getId_macro()+" Fecha:"+balanceMacro.getFecha());

                ReporteMacro.add(balanceMacro);  
                                           
            } else{           
                energia = this.eventosConsumoMacroI.get(r).getEnergia();
                for(ConsumosMacro con : this.consumosMacro){
                    if(con.getIdMacro() == macro.getIdMacro()){
                        consumo = energia - con.getConsumo();
                        this.consumosMacro.get(this.consumosMacro.indexOf(con)).setConsumo(energia);
                        consumoTotal = consumoTotal + consumo;
                        break;
                    }
                } 
            }
        }
                                                                               
        if(ultimodia==true){
            System.out.println("Reporte macro enviado:"+ReporteMacro.size());
            ExportarMacro(ReporteMacro);                    
        }

        return consumoTotal;
    }

    public void cambiarEstadoSeleccion() {
        this.trafoSeleccionado = false;
    }
    
    public float consultarConsumoInicialTu(List<PlcTu> tu, Date fecha){        
        
        this.consumosTu = new ArrayList<>();
        
        for(int s = 0; s<tu.size(); s++){                    
        
            float consumo = 0;                        
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);

            for(int i = 0; i < 7; i++){
                this.eventoConsumoI = this.ejbEventoConsumo.listaEventos(tu.get(s), calendar.getTime(), calendar.getTime());
                for (int r=0; r<this.eventoConsumoI.size(); r++){
                    if(this.eventoConsumoI.get(r).getEnergia() != 0){
                        consumo = this.eventoConsumoI.get(r).getEnergia();
                        System.out.println("El evento del tu: "+tu.get(s).getMacPlcTu()+" con energia: "+consumo);
                        i = 7;
                    }else{
                        calendar.add(Calendar.DAY_OF_YEAR, -1);
                    }               
                }
            }

            ConsumosTu t = new ConsumosTu();        
            t.setMacTu(tu.get(s).getMacPlcTu());
            t.setConsumo(consumo);
            this.consumosTu.add(t);
        }
        float consumoTotal = 0;
        for(ConsumosTu c : this.consumosTu){
            consumoTotal = consumoTotal + c.getConsumo();
        }
        return consumoTotal;
    }
    
    public float consultarConsumoTu(List<PlcTu> tu, Date fecha, boolean ultimoDia){        
        
        float consumoTotal = 0;
        
        for(int s = 0; s<tu.size(); s++){                    
        
            float consumo = 0, energia = 0;            
            String nombre, apellido;            
            
            this.eventoConsumoI = this.ejbEventoConsumo.listaEventos(tu.get(s), fecha, fecha);
            for (int r=0; r<this.eventoConsumoI.size(); r++){
                if(this.eventoConsumoI.get(r).getEnergia() == 0){
                    BalanceTU TU = new BalanceTU();
                    TU.setPLC_TU(tu.get(s).getIdPlcTu());
                    TU.setMacPLC_TU(tu.get(s).getMacPlcTu());
                    TU.setFecha(formatoFecha.format(eventoConsumoI.get(r).getFechaHora()));
                    TU.setProducto(this.ejbPlcTu.buscarTUPorId(tu.get(s).getIdPlcTu()).getIdProducto().getIdProducto());
                    TU.setCedula(this.ejbPlcTu.buscarTUPorId(tu.get(s).getIdPlcTu()).getIdProducto().getCedula().getCedula());
                    nombre=this.ejbPlcTu.buscarTUPorId(tu.get(s).getIdPlcTu()).getIdProducto().getCedula().getNombres();
                    apellido=this.ejbPlcTu.buscarTUPorId(tu.get(s).getIdPlcTu()).getIdProducto().getCedula().getApellidos();
                    TU.setNombreCliente(apellido+" "+nombre);
                   
                    
                    System.out.println(" Reporte:FECHA "+ TU.getFecha() + " PLC_TU: "+ TU.getPLC_TU()+" PRODUCTO:"+TU.getProducto()
                    +" CEDULA:"+TU.getCedula()+ "Nombre:"+ TU.getNombreCliente());
                    ReporteTU.add(TU);
                                      
                } else {
                    energia = this.eventoConsumoI.get(r).getEnergia();                     
                    for(ConsumosTu con : this.consumosTu){
                        if(con.getMacTu() == this.eventoConsumoI.get(r).getMacPlcTu().getMacPlcTu()){
                            consumo = energia - con.getConsumo();
                            this.consumosTu.get(this.consumosTu.indexOf(con)).setConsumo(energia);
                            consumoTotal = consumoTotal + consumo;
                            break;
                        }
                    }                    
                }                         
            }                                         
        }
        
        if(ultimoDia==true ){
            System.out.println("Tamaño TU enviado:"+ReporteTU.size());
            /*for(int a=0;a<ReporteTU.size();a++){
              System.out.println(ReporteTU.get(a).getPLC_TU());
              }*/
            ExportarTU(ReporteTU);   
        }                
        return consumoTotal;
    }
    
    public float consultarConsumoInicialMc(List<PlcMc> mc, Date fecha){        
        
        System.out.println("Entre a consultarConsumoInicialMc");
        this.consumosMc = new ArrayList<>();
        
        for(int s = 0; s<mc.size(); s++){                            
            System.out.println("Mc No: "+s+" con MAC: "+mc.get(s).getMacPlcMc());
            Calendar calendarA = Calendar.getInstance();
            calendarA.setTime(fecha);
            
            this.eventoConsumoMc = this.ejbEventoConsumoMc.listaEventosMc(mc.get(s), calendarA.getTime(), calendarA.getTime());
            List<EventosConsumoMc> eventoParcial = new ArrayList<>();
            System.out.println("El tamaño de eventoConsumoMc es: "+this.eventoConsumoMc.size());
            
            for(int y = 0; y<this.eventoConsumoMc.size(); y++){                
                
                float consumo = 0;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                            
                Medidor med = this.eventoConsumoMc.get(y).getIdMedidor();
                System.out.println("Medidor con ID: "+med.getIdMedidor());
                
                for(int i = 0; i < 7; i++){
                    System.out.println("MC: "+mc.get(s).getMacPlcMc()+" con Medidor: "+med.getIdMedidor()+" y fecha: "+calendar.getTime());
                    eventoParcial = this.ejbEventoConsumoMc.listaEventosMcMedidor(mc.get(s), med, calendar.getTime(), calendar.getTime());                    
                    System.out.println("El tamaño de evento parcial es: "+eventoParcial.size());
                    
                    for (int r = 0; r < eventoParcial.size(); r++){
                        System.out.println("Entre al for de evento parcial con consumo: "+eventoParcial.get(r).getEnergia());
                        if(eventoParcial.get(r).getEnergia() != 0){
                            consumo = eventoParcial.get(r).getEnergia();
                            System.out.println("El evento del mc: "+mc.get(s).getMacPlcMc()+" con medidor: "+eventoParcial.get(r).getIdMedidor().getIdMedidor()+" con energia: "+consumo);
                            i = 7;
                        }else{
                            System.out.println("Else dia menos");
                            calendar.add(Calendar.DAY_OF_YEAR, -1);
                        }               
                    }
                }
                System.out.println("El valor de consumo es "+consumo);
                ConsumosMc t = new ConsumosMc();        
                t.setMacMc(mc.get(s).getMacPlcMc());
                t.setMedidor(med.getIdMedidor());
                t.setConsumo(consumo);                                
                
                this.consumosMc.add(t);
                System.out.println("Consumo: "+t.getConsumo());
            }                                                                                
        }
        float consumoTotal = 0;
        for(ConsumosMc c : this.consumosMc){
            consumoTotal = consumoTotal + c.getConsumo();
        }
        return consumoTotal;
    }
    
    public float consultarConsumoMc(List<PlcMc> mc, Date fecha, boolean ultimoDia){        
        
        float consumoTotal = 0;
        
        for(int s = 0; s<mc.size(); s++){                                                    
            
            this.eventoConsumoMc = this.ejbEventoConsumoMc.listaEventosMc(mc.get(s), fecha, fecha);
            
            List<EventosConsumoMc> eventoParcial = new ArrayList<>();
            System.out.println("El tamaño de eventos consumo mc para "+mc.get(s).getMacPlcMc()+" es de "+this.eventoConsumoMc.size());
            
            for(int y = 0; y<this.eventoConsumoMc.size(); y++){ // Recorrer los consumos de los medidores asociados a cada PlcMc
               
                float consumo = 0, energia = 0;                
                String nombre, apellido;                
                            
                Medidor med = this.eventoConsumoMc.get(y).getIdMedidor();
                System.out.println("Medidor con ID: "+med.getIdMedidor());
                                
                eventoParcial = this.ejbEventoConsumoMc.listaEventosMcMedidor(mc.get(s), med, fecha, fecha);
                System.out.println("El tamaño de evento parcial es: "+eventoParcial.size());
                    
                for (int r = 0; r < eventoParcial.size(); r++){
                    System.out.println("Entre al for de evento parcial");
                    if(eventoParcial.get(r).getEnergia() == 0){
                        BalanceMC MC = new BalanceMC();
                        MC.setPLC_MC(mc.get(s).getIdPlcMc());
                        MC.setId_medidor(med.getIdMedidor());
                        MC.setFecha(formatoFecha.format(eventoParcial.get(r).getFechaHora()));
                        MC.setProducto(this.ejbProducto.buscarProductoDeMc(mc.get(s)).getIdProducto());
                        MC.setCedula(this.ejbProducto.buscarProductoDeMc(mc.get(s)).getCedula().getCedula());
                        nombre=this.ejbProducto.buscarProductoDeMc(mc.get(s)).getCedula().getNombres();
                        apellido=this.ejbProducto.buscarProductoDeMc(mc.get(s)).getCedula().getApellidos();
                        MC.setNombreCliente(apellido+" "+nombre);
                        
                        System.out.println(" Reporte:FECHA "+ MC.getFecha() + " PLC_MC: "+ MC.getPLC_MC()+" PRODUCTO:"+MC.getProducto()
                        +" CEDULA: "+MC.getCedula()+ " Nombre: "+ MC.getNombreCliente());
                        ReporteMC.add(MC);
                    } else{
                        energia = eventoParcial.get(r).getEnergia();
                        for(ConsumosMc con : this.consumosMc){
                            if(con.getMacMc() == mc.get(s).getMacPlcMc() && con.getMedidor() == med.getIdMedidor()){
                                consumo = energia - con.getConsumo();
                                this.consumosMc.get(this.consumosMc.indexOf(con)).setConsumo(energia);
                                consumoTotal = consumoTotal + consumo;
                                break;
                            }
                        } 
                        System.out.println("El consumo se asigno al valor "+consumo);
                    }                                          
                }                                                                                                     
            }
        }                         
        
        if(ultimoDia==true){
            System.out.println("Tamaño MC enviado:"+ReporteMC.size());
            ExportarMC(ReporteMC);                         
        }
        
        return consumoTotal;
    }
    
    public float consultarConsumoInicialMacro(Macro m, Date fecha){        
        
        this.consumosMacro = new ArrayList<>();
        
        float consumoA = 0;        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        for(int i = 0; i < 7; i++){
            this.eventosConsumoMacroI = this.ejbEventoConsumoMacro.listaEventosConsumoMacro(m, calendar.getTime(), calendar.getTime());
            for (int r=0; r<this.eventosConsumoMacroI.size(); r++){
                if(this.eventosConsumoMacroI.get(r).getEnergia() != 0){
                    consumoA = this.eventosConsumoMacroI.get(r).getEnergia();
                    System.out.println("El evento del macro: "+m.getIdMacro()+" con energia: "+consumoA);
                    i = 7;
                }else{
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                }               
            }
        }

        ConsumosMacro mac = new ConsumosMacro();
        mac.setIdMacro(m.getIdMacro());
        mac.setConsumo(consumoA);                
        this.consumosMacro.add(mac);
        
        float consumoTotal = 0;
        for(ConsumosMacro c : this.consumosMacro){
            consumoTotal = consumoTotal + c.getConsumo();
        }
        return consumoTotal;
    }   
    
    public void configurarExcel(){
        
        wb=new HSSFWorkbook();
        
        fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short)11);
        fuente.setFontName(fuente.FONT_ARIAL);
        fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        // CONFIGURACIÓN ENCABEZADO
            estiloCeldaTitulo = (HSSFCellStyle) wb.createCellStyle();
            estiloCeldaTitulo.setWrapText(true);
            estiloCeldaTitulo.setAlignment(HSSFCellStyle. ALIGN_CENTER);
            estiloCeldaTitulo.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
            estiloCeldaTitulo.setFont(fuente);
        
            // Sombreado de nuestra celda
            estiloCeldaTitulo.setFillForegroundColor((short)22);
            estiloCeldaTitulo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
            // Bordes
            estiloCeldaTitulo.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            estiloCeldaTitulo.setBottomBorderColor((short)8);
            estiloCeldaTitulo.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            estiloCeldaTitulo.setLeftBorderColor((short)8);
            estiloCeldaTitulo.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            estiloCeldaTitulo.setRightBorderColor((short)8);
            estiloCeldaTitulo.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            estiloCeldaTitulo.setTopBorderColor((short)8);
       
        //CONFIGURACIÓN CONTENIDO  
            estiloCelda = (HSSFCellStyle) wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment(HSSFCellStyle. ALIGN_CENTER);
            estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

       
    }
      
    public void ExportarTU(List<BalanceTU> ReporteTU ){
   
        System.out.println("Exportar :reporte recibido: "+ ReporteTU.size());
        Sheet hoja = wb.createSheet("PLC_TU excluidos ");
        BalanceTU TU= new BalanceTU();
        int numFilaTU= ReporteTU.size();
        
        
       // try {
            for (int i = -1; i < numFilaTU; i++) {
                Row fila = hoja.createRow(i+1);
                for (int j = 0; j < 6; j++) {
                    Cell celda = fila.createCell(j);
                    if(i==-1){
                        celda.setCellValue(TU.getTableHeaders().get(j));
                        celda.setCellStyle(estiloCeldaTitulo);
                        
                    }else{
                        celda.setCellStyle(estiloCelda);
                       
                        switch (j){
                            
                            case 0: celda.setCellValue(ReporteTU.get(i).getFecha()); break;
                            case 1: celda.setCellValue(ReporteTU.get(i).getProducto()); break;
                            case 2: celda.setCellValue(ReporteTU.get(i).getPLC_TU()); break;
                            case 3: celda.setCellValue(ReporteTU.get(i).getMacPLC_TU()); break;
                            case 4: celda.setCellValue(ReporteTU.get(i).getCedula()); break;
                            case 5: celda.setCellValue(ReporteTU.get(i).getNombreCliente()); break;                      
                        }
                    }
                  
                    //System.out.println(celda.getStringCellValue());
                }  
            }
       
    for(int j = 0; j < 6; j++) { hoja.autoSizeColumn((short)j); }
}
    
    public void ExportarMC(List<BalanceMC> ReporteMC ){
        System.out.println("Entré a exportar MC "+ ReporteMC.size());
        
        Sheet hoja2 = wb.createSheet("PLC_MC excluidos"); hoja2.setDefaultColumnWidth(22);
        BalanceMC MC= new BalanceMC();
        int numFilaMC= ReporteMC.size();
        System.out.println("num fila mc es" +numFilaMC);
        
        //try {
             for (int i = -1; i < numFilaMC; i++) {
                Row fila = hoja2.createRow(i+1);
                for (int j = 0; j < 6; j++) {
                    Cell celda = fila.createCell(j);
                    if(i==-1){
                       celda.setCellValue(MC.getTableHeaders().get(j));
                       celda.setCellStyle(estiloCeldaTitulo);
                      
                       
                    }else{
                        celda.setCellStyle(estiloCelda);
                        switch (j){
                            
                            case 0: celda.setCellValue(ReporteMC.get(i).getFecha()); break;
                            case 1: celda.setCellValue(ReporteMC.get(i).getProducto()); break;
                            case 2: celda.setCellValue(ReporteMC.get(i).getPLC_MC()); break;
                            case 3: celda.setCellValue(ReporteMC.get(i).getId_medidor()); break;
                            case 4: celda.setCellValue(ReporteMC.get(i).getCedula()); break;
                            case 5: celda.setCellValue(ReporteMC.get(i).getNombreCliente()); break;                      
                        }
                    }
                  //  System.out.println(celda.getStringCellValue());    
                }  
            }
            for(int j = 0; j < 6; j++) { hoja2.autoSizeColumn((short)j); }
          
    }   
    
    public void ExportarMacro(List<BalanceMacro> ReporteMacro ){
        System.out.println("Entré a exportar Macro "+ ReporteMacro.size());
        
        Sheet hoja3 = wb.createSheet("Reporte Macro");
        BalanceMacro balanceMacro= new BalanceMacro();
        int numFilaMacro= ReporteMacro.size();
        
        
        //try {
             for (int i = -1; i < numFilaMacro; i++) {
                Row fila = hoja3.createRow(i+1);
                for (int j = 0; j < 2; j++) {
                    Cell celda = fila.createCell(j);
                    
                    if(i==-1){
                       celda.setCellValue(balanceMacro.getTableHeaders().get(j));
                       celda.setCellStyle(estiloCeldaTitulo);
                      
                       
                    }else{
                        celda.setCellStyle(estiloCelda);
                        switch (j){
                            
                            case 0: celda.setCellValue(ReporteMacro.get(i).getFecha()); break;
                            case 1: celda.setCellValue(ReporteMacro.get(i).getId_macro()); break;
                                                 
                        }
                    }
                  
                    //System.out.println(celda.getStringCellValue());
                    
                }  
            }
             
             for(int j = 0; j < 2; j++) { hoja3.autoSizeColumn((short)j); } 
          
}   
    
    public boolean guardar(Trafo t, Date Inicio, Date Fin){
         boolean guardar=false;
         String trafo= t.getIdTrafo();
         String fechaInicial= formatoFechaReporte.format(Inicio);
         String fechaFinal=formatoFechaReporte.format(Fin);
         String nombreArchivo="Trafo"+trafo+"de"+fechaInicial+"a"+fechaFinal;
         String rutaArchivo=null;
         
         try{ 
             String usuarioSistema=System.getProperty("user.home");
             File folder = new File(usuarioSistema+"/Desktop/Balances");
             if(!folder.exists()){
                 folder.mkdir(); 
             }
             rutaArchivo=folder+"/"+ nombreArchivo +".xls";
             FileOutputStream archivo = new FileOutputStream(rutaArchivo);
             wb.write(archivo);
             archivo.close();
             guardar=true;
            
            }catch (Exception e) {
             System.out.println("No se almacenó");
            System.err.println(e.getMessage());
        }
        System.out.println("El archivo se almacenó?"+ guardar); 
        abrir(rutaArchivo);
        return guardar;
    }
    
    public boolean abrir(String rutaArchivo){
        boolean abrir=false;
        try{
          Runtime.getRuntime().exec("cmd /c start "+ rutaArchivo);
          abrir = true;
          }catch(IOException  e){
              e.printStackTrace();
          }
        return abrir ;
    }
}