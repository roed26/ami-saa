/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceo.amisaa.webservice;

import com.ceo.amisaa.entidades.EventosAmarre;
import com.ceo.amisaa.entidades.EventosAmarreMacro;
import com.ceo.amisaa.entidades.EventosAmarreMc;
import com.ceo.amisaa.entidades.EventosConsumo;
import com.ceo.amisaa.entidades.EventosConsumoMacro;
import com.ceo.amisaa.entidades.EventosConsumoMc;
import com.ceo.amisaa.entidades.Macro;
import com.ceo.amisaa.entidades.Medidor;
import com.ceo.amisaa.entidades.Notificacion;
import com.ceo.amisaa.entidades.PlcMc;
import com.ceo.amisaa.entidades.PlcMms;
import com.ceo.amisaa.entidades.PlcTu;
import com.ceo.amisaa.entidades.Producto;
import com.ceo.amisaa.entidades.Trafo;
import com.ceo.amisaa.sessionbeans.EventosAmarreFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMacroFacade;
import com.ceo.amisaa.sessionbeans.EventosAmarreMcFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMacroFacade;
import com.ceo.amisaa.sessionbeans.EventosConsumoMcFacade;
import com.ceo.amisaa.sessionbeans.MacroFacade;
import com.ceo.amisaa.sessionbeans.MedidorFacade;
import com.ceo.amisaa.sessionbeans.NotificacionFacade;
import com.ceo.amisaa.sessionbeans.PlcMcFacade;
import com.ceo.amisaa.sessionbeans.PlcMmsFacade;
import com.ceo.amisaa.sessionbeans.PlcTuFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    //private  final String  RUTAFTPDIRPENDIENTES = "/Users/aranda/Documents/archivos/pendientes";
    private  final String  RUTAFTPDIR = "/home/usuarioftp/amisaaftp/ftp";
    private  final String  RUTAFTPDIRMMAESTROS = "/home/usuarioftp/amisaaftp/archivos/maestros/";
    private  final String  RUTAFTPDIRPENDIENTES = "/home/usuarioftp/amisaaftp/archivos/pendientes";
    
    static final long ONE_MINUTE_IN_MILLIS=60000;
    static final long LENGHT_MINUTES = 4;
    
    static final long MINIMUM_LENGHT_ACCESS_FILE_MILLIS= 180000;
    
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
    private MacroFacade ejbMacroFacade;
    
    @EJB 
    private EventosAmarreMcFacade ejbEventosAmarreMcFacade;
    
    @EJB 
    private EventosConsumoMcFacade ejbEventosConsumoMcFacade;
    
    @EJB
    private EventosConsumoMacroFacade ejbEventosConsumoMacroFacade;
    
    @EJB
    private EventosAmarreMacroFacade ejbEventosAmarreMacroFacade;
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
        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date dateAddingMins=new Date(t + (LENGHT_MINUTES * ONE_MINUTE_IN_MILLIS));
        
        if (f.exists())//si el directorio existe procedemos
        { 
            File[] ficheros = f.listFiles();
            for (File fichero : ficheros) { 
                
                Date newDate = new Date();
                if(newDate.before(dateAddingMins))
                {
                    //procesamos uno a uno los archivos que contenga el
                    //directorio ftp
                    try
                    {
                       BasicFileAttributes attr = Files.readAttributes(Paths.get(fichero.getPath()), BasicFileAttributes.class);
                       long lastModifiedFile = attr.lastModifiedTime().toMillis() ;
                       Date dateLastModifiedFile = new Date(lastModifiedFile + MINIMUM_LENGHT_ACCESS_FILE_MILLIS);
                       if(dateLastModifiedFile.before(newDate))
                       {
                           procesarArchivo(fichero);
                       }
                    }
                    catch(IOException ex)
                    {
                    }
                }                
                
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
        boolean elnombredelArchivoEstabien = true;
        String motivo = "";
        PlcMms plcMms = null;
        if(!evalualarExtencionArchivoCSV(file.getName()))
        {
            elnombredelArchivoEstabien = false;
            motivo = "La extension de archivo no es csv";
        }
        else
        {
            if(!evaluarNombreFormatoCorrecto(file.getName()))
            {
                elnombredelArchivoEstabien = false;
                motivo = "El nombre del archivo no tiene el formato correcto";
            }
            else
            {
                if(!evaluarTipoDeArchivo(file.getName()))
                {
                    elnombredelArchivoEstabien = false;
                    motivo = "Tipo de archivo Desconocido";
                }
                else
                {
                    String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
                    plcMms = ejbPlcMmsFacade.findByIdPlcMms(idMms);
                    if(plcMms == null)
                    {
                        elnombredelArchivoEstabien = false;
                        motivo = "Id del MMS no registrado";
                    }
                    else
                    {
                        Trafo trafoMms = plcMms.getIdTrafo();
                        if(trafoMms == null)
                        {
                            elnombredelArchivoEstabien = false;
                            motivo = "El MMS no esta asociado a un trafo";
                        }
                    }
                }
            }
        }
        
        if (elnombredelArchivoEstabien) 
        {
            String tipofilesp = file.getName().split("_")[0];
            System.out.print(tipofilesp);
            switch (tipofilesp) 
            {
                case "01":
                    // si el archivo es tipo 1 corresponde a un archivo
                    //mms - tu
                    procesarArchivo_PlcMms_PlcTu(file,plcMms);
                break;

                case "02":
                    //procesar archivo tipo 2

                    procesarArchivo_PlcMms_PlcMc(file,plcMms);

                break;

                case "03":
                    //procesar archivo tipo3
                    procesarArchivo_PlcMms_Macromedidor(file,plcMms);
                break;
                
                default:
                    break;
            }
        }
        else
        {
            createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
        }
        
    }
    /**
     * Metodo que se encarga de procesar un archivo tipo 1 
     * mms - tu
     * @param file tipo 1 a procesar
     */
    private void procesarArchivo_PlcMms_PlcTu(File file, PlcMms macPlcMms)
    {
        String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
        String directorioamover = RUTAFTPDIRMMAESTROS + idMms;//el nombre de la carpeta final de destino
        
        List<EventosAmarre> listaEventosAmarre = new ArrayList();
        List<EventosConsumo> listaEventosConsumo = new ArrayList();
        boolean archivoProcesadoCorrectamente = true;
        String motivo = "";
        //es el id del maestro que envio el archivo
        Notificacion notificacion = new Notificacion();
        notificacion.setRevisadoNotificacion(0);
        notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
        notificacion.setFechaNotificacion(new Date());
        notificacion.setTipoEvento(1);       
        
        
        FileReader fr = null;
        int contadorlinea = 0;
        BufferedReader br;
         try {        
         fr = new FileReader (file);
         br = new BufferedReader(fr);
         
         int numero_eventos = 0;
         
         String fecha="";
         String linea =br.readLine();
         String idtrafoPlcMms = macPlcMms.getIdTrafo().getIdTrafo();
         
         while(linea!= null && !linea.equals(" @;;;;;;") && contadorlinea >=0)//con convencion el @ indica el final del archivo
         {             
            if(contadorlinea == 0)
            {
                // de la primera linea sacamos la fecha
               fecha= linea.split(";")[1];
               fecha =fecha.replace("/", "-");
               System.out.println(fecha);
               
            }
            else if(contadorlinea == 1)
            {
                String event_number = linea.split(";")[1].trim();
                numero_eventos = Integer.parseInt(event_number);
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
                
                PlcTu macPlcTu = ejbPlcTuFacade.getPorMac(stringmacPlcTu);
                
                if (macPlcTu != null)
                {
                    Producto productoPlcTu = macPlcTu.getIdProducto();
                    if(productoPlcTu != null)
                    {
                        
                        Trafo trafoPlcTu = productoPlcTu.getIdTrafo();
                        if(trafoPlcTu != null)
                        {
                            String idtrafoPlcTu = trafoPlcTu.getIdTrafo();
                            if(idtrafoPlcTu.equals(idtrafoPlcMms))
                            {
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
                                if(estadoAmarre == 1)
                                {
                                    Float energia = Float.parseFloat(lineaEvento[3].trim());
                                    Float potencia = Float.parseFloat(lineaEvento[4].trim());
                                    Float voltaje = Float.parseFloat(lineaEvento[5].trim());
                                    Float corriente = Float.parseFloat(lineaEvento[6].trim());
                                    eventoConsumo.setEnergia(energia);
                                    eventoConsumo.setPotencia(potencia);
                                    eventoConsumo.setVoltaje(voltaje);
                                    eventoConsumo.setCorriente(corriente);                        
                                }                    
                                listaEventosAmarre.add(eventoAmarre);
                                listaEventosConsumo.add(eventoConsumo);
                            }
                            else
                            {
                                archivoProcesadoCorrectamente = false;
                                motivo ="El PlcTu  de la linea " + (contadorlinea+1)+" del archivo que inicialmente estaba vinculado al Trafo "+ idtrafoPlcTu+ " actualmente reporta vinculación al Trafo "+idtrafoPlcMms+". Se sugiere revisión.";
                                contadorlinea = -100;
                            }
                        }
                        else
                        {
                            archivoProcesadoCorrectamente = false;
                            motivo ="El producto asociado al  PlcTu  de la linea " + (contadorlinea+1)+" del archivo no tieno un trafo asciado";
                            contadorlinea = -100;
                        }
                    }
                    else
                    {
                        archivoProcesadoCorrectamente = false;
                        motivo ="El PlcTu  de la linea " + (contadorlinea+1)+" del archivo no tieno un producto asciado";
                        contadorlinea = -100;
                    }
                }
                else
                {
                    archivoProcesadoCorrectamente = false;
                    motivo ="Id del TU de la linea " + (contadorlinea+1)+" del archivo no esta registrado";
                    contadorlinea = -100;
                }
            }            
            contadorlinea ++;
            linea =br.readLine();
         }         
         
         File f = new File(directorioamover);
        
        if (!f.exists())
        { 
            f.mkdirs();// si el archivo destino no existe lo creamos
        } 
        
        if(archivoProcesadoCorrectamente)
        {
            if(listaEventosAmarre.size()>0)
            {
                //movemos el archivo ya procesado a la ruta destino correspondiente
                
                if (listaEventosAmarre.size() == numero_eventos) {
                    File fil = new File(directorioamover + "/" + file.getName());
                    if (fil.exists()) {
                        Files.delete(Paths.get(file.getPath()));
                    } else {
                        ejbNotificacionFacade.create(notificacion);
                        for (int i = 0; i < listaEventosAmarre.size(); i++) {
                            ejbEventosAmarreFacade.create(listaEventosAmarre.get(i));
                            ejbEventosConsumoFacade.create(listaEventosConsumo.get(i));
                        }

                        Files.move(Paths.get(file.getPath()), Paths.get(directorioamover + "/" + file.getName()));
                    }

                }
                else
                {
                    motivo = "El número de eventos esperado en el archivo es: "+numero_eventos+" y el número de eventos que contiene el archivo es : "+ listaEventosAmarre.size();
                    createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
                }
                
                
                
            }
            else
            {
                motivo = "El archivo no contiene eventos de consumo y amarre";
                createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
            }
            
            
         
        }
        else
        {            
            createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
        }
         
         
            
      }
      catch(IOException | NumberFormatException | ParseException  | java.lang.ArrayIndexOutOfBoundsException e )      
      {
          
          System.out.print("exception: "+e.getMessage());
          
          motivo ="el archivo tiene un problema";
          createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
          //e.printStackTrace();
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
     * Metodo que se encarga de procesar un archivo tipo 3
     * mms - macromedidor
     * @param file tipo 3 a procesar
     */
    private void procesarArchivo_PlcMms_Macromedidor(File file, PlcMms macPlcMms)
    {
        String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
        String directorioamover = RUTAFTPDIRMMAESTROS + idMms;//el nombre de la carpeta final de destino
        
        List<EventosAmarreMacro> listaEventosAmarre = new ArrayList();
        List<EventosConsumoMacro> listaEventosConsumo = new ArrayList();
        boolean archivoProcesadoCorrectamente = true;
        String motivo = "";
        //es el id del maestro que envio el archivo
        Notificacion notificacion = new Notificacion();
        notificacion.setRevisadoNotificacion(0);
        notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
        notificacion.setFechaNotificacion(new Date());
        notificacion.setTipoEvento(3);       
        
        
        FileReader fr = null;
        int contadorlinea = 0;
        BufferedReader br;
         try {        
         fr = new FileReader (file);
         br = new BufferedReader(fr);
         
         int numero_eventos = 0;
         
         String fecha="";
         String linea =br.readLine();
         while(linea != null && !linea.equals(" @;;;;;;") && contadorlinea >=0)//con convencion el @ indica el final del archivo
         {             
            if(contadorlinea == 0)
            {
                // de la primera linea sacamos la fecha
               fecha= linea.split(";")[1];
               fecha =fecha.replace("/", "-");
               System.out.println(fecha);
               
            }
            else if(contadorlinea == 1)
            {
                numero_eventos = 1;
            }
            else if(contadorlinea > 2)
            {
                //apartir de la 3 linea sacamos la informacion de amarre
                String [] lineaEvento = linea.split(";");
                String stringIdMacro = lineaEvento[0].trim();
                String hora = lineaEvento[1].trim().replace("/", ":");
                String intero = lineaEvento[2].trim();
                System.out.println(intero);
                int estadoAmarre =Integer.parseInt(intero);
                String fecha_hora = fecha + " "+ hora;
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");                
                Date fechaHora = formatter.parse(fecha_hora);                
                
                Macro idMacro = ejbMacroFacade.findById(stringIdMacro);
                
                if (idMacro != null)
                {
                    EventosAmarreMacro eventoAmarre = new EventosAmarreMacro();
                    eventoAmarre.setEstadoAmarre(estadoAmarre);
                    eventoAmarre.setFechaHora(fechaHora);
                    eventoAmarre.setMacPlcMms(macPlcMms);
                    eventoAmarre.setIdMacro(idMacro);
                    eventoAmarre.setIdNotificacion(notificacion);
                    EventosConsumoMacro eventoConsumo = new EventosConsumoMacro();
                    eventoConsumo.setMacPlcMms(macPlcMms);
                    eventoConsumo.setIdNotificacion(notificacion);
                    eventoConsumo.setFechaHora(fechaHora);
                    eventoConsumo.setIdMacro(idMacro);                    
                    if(estadoAmarre == 1)
                    {
                        Float energia = Float.parseFloat(lineaEvento[3].trim());
                        Float potencia = Float.parseFloat(lineaEvento[4].trim());
                        Float voltaje = Float.parseFloat(lineaEvento[5].trim());
                        Float corriente = Float.parseFloat(lineaEvento[6].trim());
                        eventoConsumo.setEnergia(energia);
                        eventoConsumo.setPotencia(potencia);
                        eventoConsumo.setVoltaje(voltaje);
                        eventoConsumo.setCorriente(corriente);                        
                    }                    
                    listaEventosAmarre.add(eventoAmarre);
                    listaEventosConsumo.add(eventoConsumo);
                }
                else
                {
                    archivoProcesadoCorrectamente = false;
                    motivo ="Id del Macro de la linea " + (contadorlinea+1)+" del archivo no esta registrado";
                    contadorlinea = -100;
                }
                
                
            }            
            contadorlinea ++;
            linea =br.readLine();
         }         
         
         File f = new File(directorioamover);
        
        if (!f.exists())
        { 
            f.mkdirs();// si el archivo destino no existe lo creamos
        } 
        
        if(archivoProcesadoCorrectamente)
        {
            if(listaEventosAmarre.size()>0)
            {
                //movemos el archivo ya procesado a la ruta destino correspondiente
                
                if (listaEventosAmarre.size() == numero_eventos) {
                    File fil = new File(directorioamover + "/" + file.getName());
                    if (fil.exists()) {
                        Files.delete(Paths.get(file.getPath()));
                    } else {
                        ejbNotificacionFacade.create(notificacion);
                        for (int i = 0; i < listaEventosAmarre.size(); i++) {
                            ejbEventosAmarreMacroFacade.create(listaEventosAmarre.get(i));
                            ejbEventosConsumoMacroFacade.create(listaEventosConsumo.get(i));
                        }

                        Files.move(Paths.get(file.getPath()), Paths.get(directorioamover + "/" + file.getName()));
                    }

                }
                else
                {
                    motivo = "El número de eventos esperado en el archivo es: "+numero_eventos+" y el número de eventos que contiene el archivo es : "+ listaEventosAmarre.size();
                    createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
                }
                
                
                
            }
            else
            {
                motivo = "El archivo no contiene eventos de consumo y amarre";
                createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
            }
            
            
         
        }
        else
        {            
            createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
        }
         
         
            
      }
      catch(IOException | NumberFormatException | ParseException | java.lang.ArrayIndexOutOfBoundsException e)      
      {
          
          System.out.print("exception: "+e.getMessage());
          
          motivo ="el archivo tiene un problema";
          createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
          //e.printStackTrace();
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
    private void procesarArchivo_PlcMms_PlcMc(File file , PlcMms macPlcMms)
    {
        String idMms = file.getName().split("_")[1];//obtenemos el id del dispositivo maestro        
        String directorioamover = RUTAFTPDIRMMAESTROS + idMms;//el nombre de la carpeta final de destino
         //es el id del maestro que envio el archivo
         
        List<EventosAmarreMc> listaEventosAmarreMc = new ArrayList();
        List<EventosConsumoMc> listaEventosConsumoMc = new ArrayList();
        boolean archivoProcesadoCorrectamente = true;
        String motivo = "";
        
        Notificacion notificacion = new Notificacion();
        notificacion.setRevisadoNotificacion(0);
        notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
        notificacion.setFechaNotificacion(new Date());
        notificacion.setTipoEvento(2);
        
        
        int contadorlinea = 0;
        FileReader fr = null;
        BufferedReader br;
         try {        
         fr = new FileReader (file);
         br = new BufferedReader(fr);
                  
         String fecha="";
         int numero_eventos =0;
         PlcMc plcMc = null;
         String linea=br.readLine();
         String idtrafoPlcMms = macPlcMms.getIdTrafo().getIdTrafo();
         
         while(linea!= null && !linea.equals(" @;;;;;;") && contadorlinea >=0)//con convencion el @ indica el final del archivo
         {             
            if(contadorlinea == 0)
            {
                // de la primera linea sacamos la fecha
               fecha= linea.split(";")[1];
               fecha =fecha.replace("/", "-");
               System.out.println(fecha);
               
            }
            else if(contadorlinea == 1)
            {
                String event_number = linea.split(";")[1].trim();
                numero_eventos = Integer.parseInt(event_number);
            }
            else if(contadorlinea == 2)
            {
                String mac_Plc_mc = linea.split(";")[1];
                plcMc = ejbPlcMcFacade.findByMacPlcMc(mac_Plc_mc);
                
                if(plcMc == null)
                {
                    archivoProcesadoCorrectamente = false;
                    motivo ="Id del MC de la linea " + (contadorlinea+1)+" del archivo no esta registrado";
                    contadorlinea = -100;
                }
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
                Medidor medidor = ejbMedidorFacade.buscarMedidorPorId(stringIdmedidor);                
                if (medidor != null)
                {       
                    
                    Producto productoMedidor = medidor.getIdProducto();
                    if(productoMedidor != null)
                    {
                        Trafo trafoMedidor = productoMedidor.getIdTrafo();
                        if(trafoMedidor != null)
                        {
                            String idTrafoMedidor = trafoMedidor.getIdTrafo();
                            if(idTrafoMedidor.equals(idtrafoPlcMms))
                            {
                                EventosAmarreMc eventosAmarreMc = new EventosAmarreMc();
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
                                if(estadoAmarre == 1)
                                {
                                    Float energia = Float.parseFloat(lineaEvento[3].trim());
                                    Float potencia = Float.parseFloat(lineaEvento[4].trim());
                                    Float voltaje = Float.parseFloat(lineaEvento[5].trim());
                                    Float corriente = Float.parseFloat(lineaEvento[6].trim());
                                    eventoConsumoMc.setEnergia(energia);
                                    eventoConsumoMc.setPotencia(potencia);
                                    eventoConsumoMc.setVoltaje(voltaje);
                                    eventoConsumoMc.setCorriente(corriente);

                                }                    
                                listaEventosAmarreMc.add(eventosAmarreMc);
                                listaEventosConsumoMc.add(eventoConsumoMc);
                            }
                            else
                            {
                                archivoProcesadoCorrectamente = false;
                                motivo ="El Meidor  de la linea " + (contadorlinea+1)+" del archivo que inicialmente estaba vinculado al Trafo "+ idTrafoMedidor+ " actualmente reporta vinculación al Trafo "+idtrafoPlcMms+". Se sugiere revisión.";
                                contadorlinea = -100;
                            }
                        }
                        else
                        {
                            archivoProcesadoCorrectamente = false;
                            motivo ="El producto asociado al  Medidor  de la linea " + (contadorlinea+1)+" del archivo no tieno un trafo asciado";
                            contadorlinea = -100;
                        }
                    }
                    else
                    {
                        archivoProcesadoCorrectamente = false;
                        motivo ="El Medidor  de la linea " + (contadorlinea+1)+" del archivo no tieno un producto asciado";
                        contadorlinea = -100;
                    }
                }
                else
                {
                    archivoProcesadoCorrectamente = false;
                    motivo ="Id del Medidor de la linea " + (contadorlinea+1)+" del archivo no esta registrado";
                    contadorlinea = -100;
                }
            }            
            contadorlinea ++;
            linea=br.readLine();
         }         
         
         File f = new File(directorioamover);
        
        if (!f.exists())
        { 
            f.mkdirs();// si el archivo destino no existe lo creamos
        } 
        
        if(archivoProcesadoCorrectamente)
        {
            if(listaEventosAmarreMc.size()>0)
            {
                //movemos el archivo ya procesado a la ruta destino correspondiente
                
                if (listaEventosAmarreMc.size() == numero_eventos) {
                    File fil = new File(directorioamover + "/" + file.getName());
                    if (fil.exists()) {
                        Files.delete(Paths.get(file.getPath()));
                    } else {
                        ejbNotificacionFacade.create(notificacion);
                        for (int i = 0; i < listaEventosAmarreMc.size(); i++) {
                            ejbEventosAmarreMcFacade.create(listaEventosAmarreMc.get(i));
                            ejbEventosConsumoMcFacade.create(listaEventosConsumoMc.get(i));
                        }

                        Files.move(Paths.get(file.getPath()), Paths.get(directorioamover + "/" + file.getName()));
                    }

                }
                else
                {
                    motivo = "El número de eventos esperado en el archivo es: "+numero_eventos+" y el número de eventos que contiene el archivo es : "+ listaEventosAmarreMc.size();
                    createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
                }
                
                
                
            }
            else
            {
                motivo = "MC no respondio : El archivo no contiene eventos de consumo y amarre";
                createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
            }
            
         
        }
        else
        {            
            createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
        }
            
      }
      catch(Exception e)      
      {
          System.out.print("exception: "+e.getMessage());
          
          motivo ="el archivo tiene un problema";
          createNotificacionArchivoConFalla_MoverPendientes(file, motivo);
          //e.printStackTrace();
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
    
    // metodos helpers
    
    /**
     * evalua la extension del archivo para saber si es csv.
     * @return false o true dependiendo si el archivo tiene la extension correcta.
     */
    private boolean evalualarExtencionArchivoCSV(String fileName)
    {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i >= 0 && fileName.length() > i+1) 
        {
            extension = fileName.substring(i+1);
            
            if(extension.equals("csv"))
            {
                return true;
            }
            
        }
        return false;                
    }
    /**
     * evalua que el nombre del archivo cumpla con 4 datos separados por "_"
     * @return true false dependiendo si el nombre del archivo cumple
     * */
    private boolean evaluarNombreFormatoCorrecto(String fileName)
    {
        String [] split = fileName.split("_");
        return split != null && split.length == 4;
    }    
    
    /**
     * evalua que el tipo del archivo cumpla se o tipo 01,02 o 03
     * @return true false dependiendo si el tipo del archivo cumple
     * */
    private boolean evaluarTipoDeArchivo(String fileName)
    {
        String tipofile = fileName.split("_")[0];        
        return tipofile.equals("01") || tipofile.equals("02") || tipofile.equals("03");        
    }
    
    /**
     * Crea la notificacion y mueve el archivo a pendientes
     */
    private void createNotificacionArchivoConFalla_MoverPendientes(File file,String motivo)
    {
        try
        {
            String directorioamover = RUTAFTPDIRPENDIENTES;
            Notificacion notificacion = new Notificacion();
            notificacion.setRevisadoNotificacion(0);
            notificacion.setRutaarchivoNotificacion(directorioamover+"/"+file.getName());
            notificacion.setFechaNotificacion(new Date());
            notificacion.setTipoEvento(-1);
            notificacion.setMotivo(motivo);
        //movemos el archivo ya procesado a la ruta destino correspondiente
        
            File f = new File(directorioamover+"/"+file.getName());
            if(f.exists())
            {
                Files.delete(Paths.get(file.getPath()));
            }
            else
            {
               Files.move(Paths.get(file.getPath()),Paths.get(directorioamover+"/"+file.getName()) );           
               ejbNotificacionFacade.create(notificacion); 
            }
        
            
        }
        catch(Exception e)      
        {            
          e.printStackTrace();
        }
        
    }
  
}
