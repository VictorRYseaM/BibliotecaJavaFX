/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author VictorY
 */
public class Documento {

    private int id_documento;
    private String Titulo;
    private String Tipo;
    private String Autor;
    private LocalDate Fecha_publicacion;
    private byte[] Resumen;
    private byte[] Indice;
    private byte[] Img_portada;
    private byte[] Archivopdf;
    
    public Documento(int id_documento, String Tipo){
        this.id_documento = id_documento;
        this.Tipo = Tipo;
    }
    
    public Documento(int id_documento, String Titulo, String Tipo, String Autor, LocalDate Fecha_publicacion, byte[] Resumen, byte[] Indice, byte[] Img_portada, byte[] Archivopdf) {
        this.id_documento = id_documento;
        this.Titulo = Titulo;
        this.Tipo = Tipo;
        this.Autor = Autor;
        this.Fecha_publicacion = Fecha_publicacion;
        this.Resumen = Resumen;
        this.Indice = Indice;
        this.Img_portada = Img_portada;
        this.Archivopdf = Archivopdf;
    }

    public Documento(int id_documento, String Titulo, String Tipo, String Autor, byte[] Img_portada) {
        this.id_documento = id_documento;
        this.Titulo = Titulo;
        this.Tipo = Tipo;
        this.Autor = Autor;
        this.Img_portada = Img_portada;

    }

    public Documento() {
    }

    public Documento(int id_documento, String Titulo, String Tipo, LocalDate Fecha_publicacion) {
        this.id_documento = id_documento;
        this.Titulo = Titulo;
        this.Tipo = Tipo;
        this.Fecha_publicacion = Fecha_publicacion;
    }

   

    public void getdatos() {
        System.out.println("\n" + id_documento);
        System.out.println(Titulo);
        System.out.println(Tipo);
        System.out.println(Autor);
        System.out.println("---------------------");
    }

    /**
     * @return the id_documento
     */
    public int getId_documento() {
        return id_documento;
    }

    /**
     * @param id_documento the id_documento to set
     */
    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    /**
     * @return the Titulo
     */
    public String getTitulo() {
        return Titulo;
    }

    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the Autor
     */
    public String getAutor() {
        return Autor;
    }

    /**
     * @param Autor the Autor to set
     */
    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    /**
     * @return the Fecha_publicacion
     */
    public LocalDate getFecha_publicacion() {
        return Fecha_publicacion;
    }

    /**
     * @param Fecha_publicacion the Fecha_publicacion to set
     */
    public void setFecha_publicacion(LocalDate Fecha_publicacion) {
        this.Fecha_publicacion = Fecha_publicacion;
    }

    /**
     * @return the Resumen
     */
    public byte[] getResumen() {
        return Resumen;
    }

    /**
     * @param Resumen the Resumen to set
     */
    public void setResumen(byte[] Resumen) {
        this.Resumen = Resumen;
    }

    /**
     * @return the Indice
     */
    public byte[] getIndice() {
        return Indice;
    }

    /**
     * @param Indice the Indice to set
     */
    public void setIndice(byte[] Indice) {
        this.Indice = Indice;
    }

    /**
     * @return the Img_portada
     */
    public byte[] getImg_portada() {
        return Img_portada;
    }

    /**
     * @param Img_portada the Img_portada to set
     */
    public void setImg_portada(byte[] Img_portada) {
        this.Img_portada = Img_portada;
    }

    /**
     * @return the Archivopdf
     */
    public byte[] getArchivopdf() {
        return Archivopdf;
    }

    /**
     * @param Archivopdf the Archivopdf to set
     */
    public void setArchivopdf(byte[] Archivopdf) {
        this.Archivopdf = Archivopdf;
    }

    public static Image convertPdfBytesToImage(byte[] pdfData) throws IOException {
        // Crear un archivo temporal con los datos del PDF
        File tempFile = File.createTempFile("tempPdf", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfData);
        }

        // Convertir el archivo PDF temporal en una imagen
        try (PDDocument document = Loader.loadPDF(tempFile)) {
            PDFRenderer renderer = new PDFRenderer(document);

            // Renderizar la primera página como imagen
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0, 72); // 150 DPI para calidad moderada

            // Convertir BufferedImage a InputStream para JavaFX Image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Devolver la imagen
            return new Image(inputStream);
        } finally {
            // Eliminar el archivo temporal para liberar espacio
            tempFile.delete();
        }
    }

    public static Image convertPdfBytesToImageInformesyTesis(byte[] pdfBytes) throws IOException {
        // Crear un archivo temporal para cargar el PDF
        File tempFile = File.createTempFile("tempPdf", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfBytes);
        }

        try (PDDocument document = Loader.loadPDF(tempFile)) {
            PDFRenderer renderer = new PDFRenderer(document);

            // Renderizar la primera página con un DPI mayor para mayor calidad
            float dpi = 300; // Mayor DPI = Mayor resolución
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0, dpi);

            // Recortar o ampliar la imagen para simular un zoom
            int targetWidth = 1000; // Ajusta según necesites
            int targetHeight = 1200; // Ajusta según necesites
            BufferedImage zoomedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

            // Dibujar la imagen escalada al nuevo tamaño
            Graphics2D g2d = zoomedImage.createGraphics();
            g2d.drawImage(bufferedImage, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();

            // Convertir BufferedImage a InputStream para JavaFX Image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(zoomedImage, "png", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return new Image(inputStream);
        }
    }

    public File convertirByteAArchivo(byte[] datos, String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo.endsWith(".pdf") ? nombreArchivo : nombreArchivo + ".pdf");
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(datos);
        }
        return archivo;
    }

    public static File convertirByteAArchivo2(byte[] data, String nombreArchivo) throws IOException {
        // Verifica que los datos no sean nulos o vacíos
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("El arreglo de bytes está vacío o es nulo.");
        }

        // Define un nombre único para el archivo en el sistema temporal
        String tempDir = System.getProperty("java.io.tmpdir"); // Directorio temporal del sistema
        File archivo = new File(tempDir, nombreArchivo + ".pdf");

        // Escribe los datos en el archivo
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(data);
        }

        return archivo; // Retorna el archivo creado
    }

    public void guardarArchivo(MouseEvent e, Documento doc) {
        if (doc.getResumen()== null || doc.getResumen().length == 0) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setTitle("Datos no encontrados");
            alert1.setContentText("Parece que no tenemos un archivo almacenado");
            alert1.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo PDF");

        // Configuración de extensión por defecto
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el cuadro de diálogo para guardar archivo
        File archivoSeleccionado = fileChooser.showSaveDialog(((Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow()));

        if (archivoSeleccionado != null) {
            try {
                // Convertir byte[] a archivo y guardar en la ubicación seleccionada
                File archivoGuardado = convertirByteAArchivo(doc.getResumen(), archivoSeleccionado.getAbsolutePath());
                System.out.println("Archivo guardado en: " + archivoGuardado.getAbsolutePath());
            } catch (IOException ex) {
                System.err.println("Error al guardar el archivo: " + ex.getMessage());
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText(null);
            alert2.setTitle("Error en guardado");
            alert2.setContentText("No se seleccionó una ubicación para guardar el archivo. ");
            alert2.showAndWait();
        }
    }
}
