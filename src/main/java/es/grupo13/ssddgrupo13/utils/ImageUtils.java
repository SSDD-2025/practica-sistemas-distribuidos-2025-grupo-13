package es.grupo13.ssddgrupo13.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ImageUtils {

    public Blob loadImage(String path) {
        try (InputStream inputStream = new ClassPathResource("static/" + path).getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new SerialBlob(bytes);
        } catch (IOException | SQLException e) {
            System.err.println("⚠ No se pudo cargar la imagen: " + path + ". Usando imagen por defecto.");
            return loadDefaultImage();
        }
    }

    public Blob loadDefaultImage() {
        try (InputStream inputStream = new ClassPathResource("img/default.jpg").getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new SerialBlob(bytes);
        } catch (IOException | SQLException e) {
            System.err.println("⚠ No se pudo cargar la imagen por defecto. Se usará un Blob vacío.");
            try {
                return new SerialBlob(new byte[0]);
            } catch (SQLException ex) {
                throw new RuntimeException("Error creando Blob vacío", ex);
            }
        }
    }
}

