package com.ceo.amisaa.servicios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class Utilidades {

    /**
     * Devuelve la imagen por defecto para las fotos: vacio.jpg
     *
     * @param tipo foto ó noticia
     * @return un flujo que se carga en el componente p:graphicsImage
     */
    public static StreamedContent getImagenPorDefecto(String tipo) {
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String absoluteDiskPath = (String) servletContext.getRealPath("/");
            if (OS.contains("nux")) {
                if (tipo.equals("foto")) {
                    absoluteDiskPath = absoluteDiskPath.replace("build/", "") + "/resources/img/fotoDefecto.jpg";
                }
                
            } else {
                if (tipo.equals("foto")) {
                    absoluteDiskPath = absoluteDiskPath.replace("build\\", "") + "resources\\img\\fotoDefecto.jpg";
                }

            }

            File file = new File(absoluteDiskPath);
            return new DefaultStreamedContent(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            
        }
        return null;
    }
    
    
}
