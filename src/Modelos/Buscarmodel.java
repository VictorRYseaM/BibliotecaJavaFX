/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author VictorY
 */
public class Buscarmodel {

    Documento doc = new Documento();

    public Buscarmodel() {
    }
    // Método para obtener los autores de la base de datos

    public ObservableList<String> obtenerAutores() {
        ObservableList<String> autores = FXCollections.observableArrayList();

        // Consulta SQL para obtener los autores de los documentos donde el Tipo sea 'Libros'
        String query = "SELECT Autor FROM documento WHERE Tipo = 'Libros'";

        try (java.sql.Connection con = new Conexion().conectar(); // Asegúrate de que la conexión sea exitosa
                 Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Verificar si la consulta ha devuelto algún resultado
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron autores.");
                return autores;  // Regresa la lista vacía si no hay resultados
            }

            // Procesar los resultados de la consulta
            while (rs.next()) {
                String autor = rs.getString("Autor"); // Recupera el nombre del autor
                if (autor != null && !autor.trim().isEmpty()) {
                    autores.add(autor); // Solo agrega autores no nulos o vacíos
                }
            }

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores válidos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Agregar detalles de la excepción para depurar mejor el problema
            System.out.println("Error al obtener los autores: " + e.getMessage());
        }

        // Devuelve la lista de autores (puede estar vacía si no hay resultados)
        return autores;
    }

    // Método para obtener las editoriales de la base de datos
    public ObservableList<String> obtenerEditoriales() {
        ObservableList<String> editoriales = FXCollections.observableArrayList();

        String query = "SELECT Editorial FROM libro";

        try (java.sql.Connection con = new Conexion().conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String editorial = rs.getString("Editorial");
                editoriales.add(editorial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return editoriales;
    }

    public List<Documento> buscarDocumentos(String textoBusqueda, String tipoFiltro, Map<String, String> filtrosAdicionales) {
        List<Documento> resultados = new ArrayList<>();
        String sqlBase = "SELECT id_documento, titulo, autor, img_portada FROM documento";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = prepararConsulta(con, sqlBase, textoBusqueda, tipoFiltro, filtrosAdicionales); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Documento doc = new Documento();
                doc.setId_documento(rs.getInt("id_documento"));
                doc.setTitulo(rs.getString("titulo"));
                doc.setAutor(rs.getString("autor"));
                doc.setImg_portada(rs.getBytes("img_portada"));
                resultados.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private PreparedStatement prepararConsulta(Connection con, String sqlBase, String textoBusqueda, String tipoFiltro, Map<String, String> filtrosAdicionales) throws SQLException {
        StringBuilder consulta = new StringBuilder(sqlBase);
        List<Object> parametros = new ArrayList<>();

        // Filtro base por texto de búsqueda (Título o Autor)
        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
            consulta.append(" WHERE (titulo LIKE ? OR autor LIKE ?)");
            parametros.add("%" + textoBusqueda + "%");
            parametros.add("%" + textoBusqueda + "%");
        }

        // Filtro por tipo de documento
        if (tipoFiltro != null && !tipoFiltro.isEmpty() && !tipoFiltro.equalsIgnoreCase("Todos")) {
            consulta.append((parametros.isEmpty() ? " WHERE" : " AND") + " tipo = ?");
            parametros.add(tipoFiltro);
        }

        // Filtros adicionales basados en el mapa recibido
        if (filtrosAdicionales != null && !filtrosAdicionales.isEmpty()) {
            for (Map.Entry<String, String> entry : filtrosAdicionales.entrySet()) {
                String filtroNombre = entry.getKey();
                String valor = entry.getValue();

                if (valor != null && !valor.isEmpty()) {
                    consulta.append((parametros.isEmpty() ? " WHERE" : " AND") + " " + filtroNombre + " = ?");
                    parametros.add(valor);
                }
            }
        }

        // Preparar la consulta
        PreparedStatement pst = con.prepareStatement(consulta.toString());
        for (int i = 0; i < parametros.size(); i++) {
            pst.setObject(i + 1, parametros.get(i));
        }
        return pst;
    }

    public static Image convertPdfToImage(File pdfFile) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);

            // Renderizar la primera página como una imagen
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0, 150); // 150 DPI para calidad moderada

            // Convertir BufferedImage a InputStream para JavaFX Image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return new Image(inputStream);
        }
    }

    public static File savePdfToTempFile(byte[] pdfData) throws IOException {
        File tempFile = File.createTempFile("tempPdf", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfData);
        }
        return tempFile;
    }

}
