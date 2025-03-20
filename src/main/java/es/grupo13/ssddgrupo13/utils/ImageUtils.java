package es.grupo13.ssddgrupo13.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ImageUtils {
    public Blob loadImage(String path) {
        try {
            
            InputStream inputStream = new ClassPathResource("static/" + path).getInputStream();
            return BlobProxy.generateProxy(inputStream, inputStream.available());
        } catch (IOException e) {
            System.err.println("⚠ No se pudo cargar la imagen: " + path + ". Usando imagen por defecto.");
            return loadDefaultImage();
        }
    }
    
    public Blob loadDefaultImage() {
        try {
            InputStream inputStream = new ClassPathResource("img/default.jpg").getInputStream();
            return BlobProxy.generateProxy(inputStream, inputStream.available());
        } catch (IOException e) {
            System.err.println("⚠ No se pudo cargar la imagen por defecto. Se usará un Blob vacío.");
            return BlobProxy.generateProxy(new byte[0]); // Uses an empty Blob instead of null
        }
    }
}
