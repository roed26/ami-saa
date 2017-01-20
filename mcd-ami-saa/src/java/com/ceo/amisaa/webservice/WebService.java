/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.webservice;

import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosAmarreMc;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMcFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMcFacade;
import com.ceo.amisaa.sessionbeans.MedidorFacade;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 * este servicio web tiene la implementacion de la logica
 * necesaria para gestionar los archivos que son enviados por los 
 * dispositivos maestros
 * @author Wilson Geovanny Carvajal 2016
 */
@Path("webservice")
public class WebService {

    /**
     * rutas de los directorios, el directorio ftp donde llegan los archivos
     * y el directorio donde finalmente son movidos los archivos
     */
    //private  final String  RUTAFTPDIR = "/Users/aranda/Documents/ftp";
    //private  final String  RUTAFTPDIRMMAESTROS = "/Users/aranda/Documents/archivos/maestros/";
    private  final String  RUTAFTPDIR = "/home/usuarioftp/amisaaftp/ftp";
    private  final String  RUTAFTPDIRMMAESTROS = "/home/usuarioftp/amisaaftp/archivos/maestros/";
    @Context
    private UriInfo context;  
    
    /**
     * Enterprise java bean
     */
    @EJB 
    private PlcMmsFacade ejbPlcMmsFacade;
    
    @EJB 
    private PlcTuFacade ejbPlcTuFacade;
    
    @EJB 
    private EventosAmarreFacade ejbEventosAmarreFacade;
    
    @EJB 
    private EventosConsumoFacade ejbEventosConsumoFacade;
    
    @EJB 
    private NotificacionFacade ejbNotificacionFacade;
    
    @EJB
    private MedidorFacade ejbMedidorFacade;
    
    @EJB
    private PlcMcFacade ejbPlcMcFacade;
    
    @EJB 
    private EventosAmarreMcFacade ejbEventosAmarreMcFacade;
    
    @EJB 
    private EventosConsumoMcFacade ejbEventosConsumoMcFacade;
    /**
     * 
     * Creates a new instance of WebService
     */
    public WebService() {
    }
    /**
     * Metodo Get esperando la llegada de nuevos archivos enviados por maestros 
     * a la carpeta ftp
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        
        revisarDirectorio(); // al recibir la notificacion revisamos el directorio ftp      
        return "true";
    }
    
    private void revisarDirectorio()
    {
        String sDirectorio = RUTAFTPDIR;//ruta del directorio ftp
        File f = new File(sDirectorio);
        
        if (f.exists())//si el directorio existe procedemos
        { 
            File[] ficheros = f.listFiles();
            for (File fichero : ficheros) {                
                procesarArchivo(fichero);//procesamos uno a uno los archivos que contenga el
                //directorio ftp
            }
        }        
    }
    /**
     * Metodo que se encarga de procesar un archivo
     * identificando el tipo de archivo y asi decidiendo que accion aplicar
     * @param file archivo que vamos a procesar
     */
    private void procesarArchivo(File file)
    {
        
        String tipofilesp = file.getName().split("_")[0];
         System.out.print(tipofilesp);       
        switch (tipofilesp) 
        {       
            case "01":
                 // si el archivo es tipo 1 corresponde a un archivo
                //mms - tu
                procesarArchivo_PlcMms_PlcTu(file);
             break;
        
            case "02":
                //procesar archivo tipo 2
                
                procesarArchivo_PlcMms_PlcMc(file);
                
            break;
       
            default:
                 //procesar archivo tipo3
            break;
        }
    }
    /**
     * Metodo que se encarga de procesar un archivo tipo 1 
     * mms - tu
     * @param file tipo 1 a procesar
     */
    private void procesarArchivo_PlcMms_PlcTu(File file)
    {
        String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
        PlcMms macPlcMms = ejbPlcMmsFacade.find(idMms);// con el id obtenemos la mac
        
        String directorioamover = RUTAFTPDIRMMAESTROS + idMms;//el nombre de la carpeta final de destino
         //es el id del maestro que envio el archivo
        Notificacion notificacion = new Notificacion();
        notificacion.setRevisadoNotificacion(0);
        notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
        notificacion.setFechaNotificacion(new Date());
        notificacion.setTipoEvento(1);
        
        ejbNotificacionFacade.create(notificacion);
        
        FileReader fr = null;
        BufferedReader br;
         try {        
         fr = new FileReader (file);
         br = new BufferedReader(fr);
         String linea;
         int contadorlinea = 0;
         String fecha="";
         while(!(linea=br.readLine()).equals(" @"))//con convencion el @ indica el final del archivo
         {             
            if(contadorlinea == 0)
            {
                // de la primera linea sacamos la fecha
               fecha= linea.split(";")[1];
               fecha =fecha.replace("/", "-");
               System.out.println(fecha);
               
            }
            else if(contadorlinea > 2)
            {
                //apartir de la 3 linea sacamos la informacion de amarre
                String [] lineaEvento = linea.split(";");
                String stringmacPlcTu = lineaEvento[0].trim();
                String hora = lineaEvento[1].trim().replace("/", ":");
                String intero = lineaEvento[2].trim();
                System.out.println(intero);
                int estadoAmarre =Integer.parseInt(intero);
                String fecha_hora = fecha + " "+ hora;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");                
                Date fechaHora = formatter.parse(fecha_hora);
                
                float energia = Float.parseFloat(lineaEvento[3].trim());
                float potencia = Float.parseFloat(lineaEvento[4].trim());
                float voltaje = Float.parseFloat(lineaEvento[5].trim());
                float corriente = Float.parseFloat(lineaEvento[6].trim());
                PlcTu macPlcTu = ejbPlcTuFacade.getPorMac(stringmacPlcTu).get(0);
                
                EventosAmarre eventoAmarre = new EventosAmarre();
                eventoAmarre.setEstadoAmarre(estadoAmarre);
                eventoAmarre.setFechaHora(fechaHora);
                eventoAmarre.setIdPlcMms(macPlcMms);
                eventoAmarre.setMacPlcTu(macPlcTu);
                eventoAmarre.setIdNotificacion(notificacion);
                
                EventosConsumo eventoConsumo = new EventosConsumo();
                eventoConsumo.setMacPlcMms(macPlcMms);
                eventoConsumo.setIdNotificacion(notificacion);
                eventoConsumo.setFechaHora(fechaHora);
                eventoConsumo.setMacPlcTu(macPlcTu);
                eventoConsumo.setEnergia(energia);
                eventoConsumo.setPotencia(potencia);
                eventoConsumo.setVoltaje(voltaje);
                eventoConsumo.setCorriente(corriente);
                ejbEventosAmarreFacade.create(eventoAmarre);//creamos el evento amarre en la BD  
                ejbEventosConsumoFacade.create(eventoConsumo);//creamos el evento de consumo en la BD
            }            
            contadorlinea ++;
         }         
         
         File f = new File(directorioamover);
        
        if (!f.exists())
        { 
            f.mkdirs();// si el archivo destino no existe lo creamos
        } 
         //movemos el archivo ya procesado a la ruta destino correspondiente
         Files.move(Paths.get(file.getPath()),Paths.get(directorioamover+"/"+file.getName()) );
            
      }
      catch(Exception e)      
      {
          e.printStackTrace();
      }
         finally{
         
         try{                    
            if( null != fr ){ 
               
               fr.close();     
            }                  
         }catch (Exception e2){ 
         }
      }
        
    }
    
    
    /**
     * Metodo que se encarga de procesar un archivo tipo 2 
     * mms - mc
     * @param file tipo 2 a procesar
     */
    private void procesarArchivo_PlcMms_PlcMc(File file)
    {
        String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
        PlcMms macPlcMms = ejbPlcMmsFacade.find(idMms);// con el id obtenemos la mac
        
        String directorioamover = RUTAFTPDIRMMAESTROS + idMms;//el nombre de la carpeta final de destino
         //es el id del maestro que envio el archivo
        Notificacion notificacion = new Notificacion();
        notificacion.setRevisadoNotificacion(0);
        notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
        notificacion.setFechaNotificacion(new Date());
        notificacion.setTipoEvento(2);
        
        ejbNotificacionFacade.create(notificacion);
        
        FileReader fr = null;
        BufferedReader br;
         try {        
         fr = new FileReader (file);
         br = new BufferedReader(fr);
         String linea;
         int contadorlinea = 0;
         String fecha="";
         PlcMc plcMc = null;
         while(!(linea=br.readLine()).equals(" @"))//con convencion el @ indica el final del archivo
         {             
            if(contadorlinea == 0)
            {
                // de la primera linea sacamos la fecha
               fecha= linea.split(";")[1];
               fecha =fecha.replace("/", "-");
               System.out.println(fecha);
               
            }
            else if(contadorlinea == 2)
            {
                String mac_Plc_mc = linea.split(";")[1];
                plcMc = ejbPlcMcFacade.findByMacPlcMc(mac_Plc_mc);
            }
            else if(contadorlinea > 3)
            {
                //apartir de la 3 linea sacamos la informacion de amarre
                String [] lineaEvento = linea.split(";");
                String stringIdmedidor = lineaEvento[0].trim();
                String hora = lineaEvento[1].trim().replace("/", ":");
                String intero = lineaEvento[2].trim();
                System.out.println(intero);
                int estadoAmarre =Integer.parseInt(intero);
                String fecha_hora = fecha + " "+ hora;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");                
                Date fechaHora = formatter.parse(fecha_hora);
                
                float energia = Float.parseFloat(lineaEvento[3].trim());
                float potencia = Float.parseFloat(lineaEvento[4].trim());
                float voltaje = Float.parseFloat(lineaEvento[5].trim());
                float corriente = Float.parseFloat(lineaEvento[6].trim());
                Medidor medidor = ejbMedidorFacade.buscarMedidorPorId(stringIdmedidor);
                
                EventosAmarreMc eventosAmarreMc = new EventosAmarreMc();
                eventosAmarreMc.setEstadoAmarre(estadoAmarre);
                
                
                
                eventosAmarreMc.setEstadoAmarre(estadoAmarre);
                eventosAmarreMc.setFechaHora(fechaHora);
                eventosAmarreMc.setMacPlcMms(macPlcMms);
                eventosAmarreMc.setMacPlcMc(plcMc);
                eventosAmarreMc.setIdMedidor(medidor);
                eventosAmarreMc.setIdNotificacion(notificacion);
                
                EventosConsumoMc eventoConsumoMc = new EventosConsumoMc();
                eventoConsumoMc.setMacPlcMms(macPlcMms);
                eventoConsumoMc.setIdNotificacion(notificacion);
                eventoConsumoMc.setFechaHora(fechaHora);
                eventoConsumoMc.setIdMedidor(medidor);
                eventoConsumoMc.setMacPlcMc(plcMc);
                eventoConsumoMc.setEnergia(energia);
                eventoConsumoMc.setPotencia(potencia);
                eventoConsumoMc.setVoltaje(voltaje);
                eventoConsumoMc.setCorriente(corriente);
                ejbEventosAmarreMcFacade.create(eventosAmarreMc);//creamos el evento amarre en la BD  
                ejbEventosConsumoMcFacade.create(eventoConsumoMc);//creamos el evento de consumo en la BD
            }            
            contadorlinea ++;
         }         
         
         File f = new File(directorioamover);
        
        if (!f.exists())
        { 
            f.mkdirs();// si el archivo destino no existe lo creamos
        } 
         //movemos el archivo ya procesado a la ruta destino correspondiente
         Files.move(Paths.get(file.getPath()),Paths.get(directorioamover+"/"+file.getName()) );
            
      }
      catch(Exception e)      
      {
          e.printStackTrace();
      }
         finally{
         
         try{                    
            if( null != fr ){ 
               
               fr.close();     
            }                  
         }catch (Exception e2){ 
         }
      }
        
    }

  
}
