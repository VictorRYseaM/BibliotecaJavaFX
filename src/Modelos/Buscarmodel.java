/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.BuscarController;
import Controladores.InicioController;
import Controladores.RegistrarController;
import Controladores.VistaInformeController;
import Controladores.VistaLibroController;
import Controladores.VistaTesisController;
import animatefx.animation.FadeIn;
import com.sun.javafx.logging.PlatformLogger.Level;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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
        // Verificar si el texto de búsqueda y los filtros adicionales están vacíos
        if ((textoBusqueda == null || textoBusqueda.isEmpty()) && (filtrosAdicionales == null || filtrosAdicionales.isEmpty()) && (tipoFiltro == null || tipoFiltro.isEmpty() || tipoFiltro.equalsIgnoreCase("Todos"))) {
            // Mostrar una alerta si no hay filtros ni texto de búsqueda
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información de Búsqueda");
            alert.setHeaderText(null);
            alert.setContentText("No ha ingresado datos para realizar la búsqueda. Por favor, ingrese un texto de búsqueda o active un filtro.");
            alert.showAndWait();
            return new ArrayList<>();
        }

        List<Documento> resultados = new ArrayList<>();
        String sqlBase = "SELECT id_documento, titulo, autor, img_portada, tipo FROM documento";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = prepararConsulta(con, sqlBase, textoBusqueda, tipoFiltro, filtrosAdicionales); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Documento doc = new Documento();
                doc.setId_documento(rs.getInt("id_documento"));
                doc.setTitulo(rs.getString("titulo"));
                doc.setAutor(rs.getString("autor"));
                doc.setImg_portada(rs.getBytes("img_portada"));
                doc.setTipo(rs.getString("tipo"));
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

        // Filtros adicionales
        if (filtrosAdicionales != null && !filtrosAdicionales.isEmpty()) {
            for (Map.Entry<String, String> entry : filtrosAdicionales.entrySet()) {
                String filtroNombre = entry.getKey();
                String valor = entry.getValue();

                if (filtroNombre.equalsIgnoreCase("Carrera") && valor != null && !valor.isEmpty()) {
                    if ("Trabajos de Grado".equalsIgnoreCase(tipoFiltro)) {
                        consulta.append(" AND id_documento IN (");
                        consulta.append("SELECT d.id_documento FROM documento d ");
                        consulta.append("JOIN tesis t ON d.id_documento = t.id_documento WHERE t.Carrera = ?)");
                        parametros.add(valor);
                    } else if ("Informes de Pasantía".equalsIgnoreCase(tipoFiltro)) {
                        consulta.append(" AND id_documento IN (");
                        consulta.append("SELECT d.id_documento FROM documento d ");
                        consulta.append("JOIN informepasantia i ON d.id_documento = i.id_documento WHERE i.Carrera = ?)");
                        parametros.add(valor);
                    }
                } else if (filtroNombre.equalsIgnoreCase("Año") && valor != null && !valor.isEmpty()) {
                    int anio = Integer.parseInt(valor);
                    String fechaInicio = anio + "-01-01";
                    String fechaFin = anio + "-12-31";

                    consulta.append((parametros.isEmpty() ? " WHERE" : " AND") + " Fecha_publicacion BETWEEN ? AND ?");
                    parametros.add(fechaInicio);
                    parametros.add(fechaFin);
                } else if (filtroNombre.equalsIgnoreCase("Editorial") && valor != null && !valor.isEmpty()) {
                    if ("Libros".equalsIgnoreCase(tipoFiltro)) {
                        consulta.append((parametros.isEmpty() ? " WHERE" : " AND") + " id_documento IN (");
                        consulta.append("SELECT d.id_documento FROM documento d ");
                        consulta.append("JOIN libro l ON d.id_documento = l.id_documento WHERE l.Editorial = ?)");
                        parametros.add(valor);
                    }
                } else if (valor != null && !valor.isEmpty()) {
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

    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            BuscarController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpagelibro(String page, AnchorPane viewpane, int id_doc) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            VistaLibroController ac = load.getController();
            ac.setviewpane(viewpane);
            ac.setlibro(obtenerLibroPorId(id_doc));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpageinforme(String page, AnchorPane viewpane, int id_doc) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            VistaInformeController ac = load.getController();

            ac.setviewpane(viewpane);
            ac.setInforme(obtenerInformePasantiaPorId(id_doc));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpagetesis(String page, AnchorPane viewpane, int id_doc) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            VistaTesisController ac = load.getController();
            if (ac == null) {
                System.out.println("No se pudo obtener un controlador de la vista");
            } else {
                System.out.println("Controlador obtenido");
            }
            ac.setviewpane(viewpane);
            ac.settesis(obtenerTesisPorId(id_doc));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public Libro obtenerLibroPorId(int idDocumento) {
        Libro libro = null;

        String query = "SELECT d.titulo, d.tipo, d.autor, d.fecha_publicacion, d.resumen, "
                + "d.indice, d.img_portada, d.archivopdf, "
                + "l.editorial, l.edicion, l.estante, l.cod_lomo "
                + "FROM documento d "
                + "INNER JOIN libro l ON d.id_documento = l.id_documento "
                + "WHERE d.id_documento = ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDocumento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto Libro con los datos obtenidos
                    libro = new Libro();

                    // Datos de la tabla `documento`
                    libro.setId_documento(idDocumento);
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setTipo(rs.getString("tipo"));
                    libro.setAutor(rs.getString("autor"));
                    libro.setFecha_publicacion(rs.getDate("fecha_publicacion").toLocalDate());
                    libro.setResumen(rs.getBytes("resumen"));
                    libro.setIndice(rs.getBytes("indice"));
                    libro.setImg_portada(rs.getBytes("img_portada"));
                    libro.setArchivopdf(rs.getBytes("archivopdf"));

                    // Datos de la tabla `libro`
                    libro.setEditorial(rs.getString("editorial"));
                    libro.setEdicion(rs.getString("edicion"));
                    libro.setEstante(rs.getString("estante"));
                    libro.setCod_lomo(rs.getString("cod_lomo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libro;
    }

    public TrabajoGrado obtenerTesisPorId(int idDocumento) {
        TrabajoGrado tesis = null;
        System.out.println("ID recibido para obtener tesis: " + idDocumento);
        String query = "SELECT d.titulo, d.tipo, d.autor, d.fecha_publicacion, d.resumen, "
                + "d.indice, d.img_portada, d.archivopdf, "
                + "t.carrera, t.codigo, t.cedula "
                + "FROM documento d "
                + "INNER JOIN tesis t ON d.id_documento = t.id_documento "
                + "WHERE d.id_documento = ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDocumento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto Tesis con los datos obtenidos
                    tesis = new TrabajoGrado();

                    // Datos de la tabla `documento`
                    tesis.setId_documento(idDocumento);
                    tesis.setTitulo(rs.getString("titulo"));
                    tesis.setTipo(rs.getString("tipo"));
                    tesis.setAutor(rs.getString("autor"));
                    tesis.setFecha_publicacion(rs.getDate("fecha_publicacion").toLocalDate());
                    tesis.setResumen(rs.getBytes("resumen"));
                    tesis.setIndice(rs.getBytes("indice"));
                    tesis.setImg_portada(rs.getBytes("img_portada"));
                    tesis.setArchivopdf(rs.getBytes("archivopdf"));

                    // Datos de la tabla `tesis`
                    tesis.setCarrera(rs.getString("carrera"));
                    tesis.setCodigo(rs.getString("codigo"));
                    tesis.setCedula(rs.getString("cedula"));
                } else {
                    System.out.println("NO se encontro un carajo e tesis");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tesis;
    }

    public InformePasantia obtenerInformePasantiaPorId(int idDocumento) {
        InformePasantia informePasantia = null;

        String query = "SELECT d.titulo, d.tipo, d.autor, d.fecha_publicacion, d.resumen, "
                + "d.indice, d.img_portada, d.archivopdf, "
                + "i.empresa, i.carrera, i.cedula "
                + "FROM documento d "
                + "INNER JOIN informepasantia i ON d.id_documento = i.id_documento "
                + "WHERE d.id_documento = ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDocumento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto InformePasantia con los datos obtenidos
                    informePasantia = new InformePasantia();

                    // Datos de la tabla `documento`
                    informePasantia.setId_documento(idDocumento);
                    informePasantia.setTitulo(rs.getString("titulo"));
                    informePasantia.setTipo(rs.getString("tipo"));
                    informePasantia.setAutor(rs.getString("autor"));
                    informePasantia.setFecha_publicacion(rs.getDate("fecha_publicacion").toLocalDate());
                    informePasantia.setResumen(rs.getBytes("resumen"));
                    informePasantia.setIndice(rs.getBytes("indice"));
                    informePasantia.setImg_portada(rs.getBytes("img_portada"));
                    informePasantia.setArchivopdf(rs.getBytes("archivopdf"));

                    // Datos de la tabla `informepasantia`
                    informePasantia.setEmpresa(rs.getString("empresa"));
                    informePasantia.setCarrera(rs.getString("carrera"));
                    informePasantia.setCedula(rs.getString("cedula"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return informePasantia;
    }

}
