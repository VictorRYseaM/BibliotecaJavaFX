/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.AjustesController;
import Controladores.InicioController;
import Controladores.RegistrarController;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author VictorY
 */
public class registmodel {

    // Atributos que recibirán los valores de los controles
    private TextField texttitulo, textautor, textcedula, codtesis, textempresa, texteditorial, textedicion, textestante, textlomo;
    private ComboBox<String> cbcarrera, cbdocumento;
    private DatePicker fecha;
    private File selectedFile;

    public registmodel() {
    }
    // Método para establecer los valores de los campos
    // Arreglos de bytes para los archivos
    private byte[] pdfindice;
    private byte[] resumenpdf;
    private byte[] pdfportada;
    private byte[] archivopdf;  // Para el archivo PDF

    // Método para establecer los valores de los campos
    public registmodel(TextField texttitulo, TextField textautor, TextField textcedula, TextField codtesis,
            ComboBox<String> cbcarrera, ComboBox<String> cbdocumento,
            DatePicker fecha, byte[] pdfindice, byte[] resumenpdf,
            byte[] pdfportada, byte[] archivopdf) {
        this.texttitulo = texttitulo;
        this.textautor = textautor;
        this.textcedula = textcedula;
        this.codtesis = codtesis;
        this.cbcarrera = cbcarrera;
        this.cbdocumento = cbdocumento;
        this.fecha = fecha;
        this.pdfindice = pdfindice;
        this.resumenpdf = resumenpdf;
        this.pdfportada = pdfportada;
        this.archivopdf = archivopdf;
    }

    //Constructor para libros
    public registmodel(TextField texttitulo3, TextField textautor3, TextField texteditorial, TextField textedicion,
            TextField textestante, TextField textlomo, ComboBox<String> cbdocumento,
            DatePicker fecha3, File selectedFile, byte[] pdfindice, byte[] resumenpdf, byte[] pdfportada) {

        this.texttitulo = texttitulo3;
        this.textautor = textautor3;
        this.texteditorial = texteditorial;
        this.textedicion = textedicion;
        this.textestante = textestante;
        this.textlomo = textlomo;
        this.cbdocumento = cbdocumento;
        this.fecha = fecha3;
        this.pdfindice = pdfindice;
        this.resumenpdf = resumenpdf;
        this.pdfportada = pdfportada;
        try {
            this.archivopdf = selectedFile != null ? Files.readAllBytes(selectedFile.toPath()) : null; // Convierte el archivo seleccionado a byte[] si es necesario
        } catch (IOException ex) {
            Logger.getLogger(registmodel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Constructor para establecer los valores de los campos
// Constructor para establecer los valores de los campos (usando los atributos existentes)
    public registmodel(TextField texttitulo2, TextField textautor2, TextField textcedula2, TextField textempresa,
            ComboBox<String> cbcarrera2, ComboBox<String> cbdocumento, DatePicker fecha2,
            byte[] pdfindice, byte[] resumenpdf, byte[] pdfportada, File selectedFile) {
        this.texttitulo = texttitulo2;  // Reutiliza los atributos existentes
        this.textautor = textautor2;
        this.textcedula = textcedula2;
        this.textempresa = textempresa;  // Nuevo campo
        this.cbcarrera = cbcarrera2;
        this.cbdocumento = cbdocumento;
        this.fecha = fecha2;
        this.pdfindice = pdfindice;
        this.resumenpdf = resumenpdf;
        this.pdfportada = pdfportada;
        try {
            this.archivopdf = selectedFile != null ? Files.readAllBytes(selectedFile.toPath()) : null; // Convierte el archivo seleccionado a byte[] si es necesario
        } catch (IOException ex) {
            Logger.getLogger(registmodel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpiarFormulario() {
        texttitulo.clear();
        textautor.clear();
        textcedula.clear();
        codtesis.clear();
        cbcarrera.getSelectionModel().clearSelection();
        cbdocumento.getSelectionModel().select(0);
        fecha.setValue(null);
        selectedFile = null;
        pdfindice = null;
        resumenpdf = null;
        pdfportada = null;
        archivopdf = null;
    }

// Método para registrar el documento y la tesis
    public boolean registrarDocumentoYtesis() {
        if (camposValidos()) {
            String sqlDocumento = "INSERT INTO documento (Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (java.sql.Connection con = new Conexion().conectar()) {
                PreparedStatement pst = con.prepareStatement(sqlDocumento, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, texttitulo.getText());
                pst.setString(2, cbdocumento.getValue());
                pst.setString(3, textautor.getText());
                pst.setDate(4, java.sql.Date.valueOf(fecha.getValue()));
                pst.setBytes(5, resumenpdf);
                pst.setBytes(6, pdfindice);
                pst.setBytes(7, pdfportada);
                pst.setBytes(8, archivopdf);

                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    ResultSet rs = pst.getGeneratedKeys();
                    int idDocumento = 0;
                    if (rs.next()) {
                        idDocumento = rs.getInt(1);
                    }

                    String sqlTesis = "INSERT INTO tesis (id_documento, Carrera, codigo, Cedula) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstTesis = con.prepareStatement(sqlTesis);
                    pstTesis.setInt(1, idDocumento);
                    pstTesis.setString(2, cbcarrera.getValue());
                    pstTesis.setString(3, codtesis.getText());
                    pstTesis.setString(4, textcedula.getText());

                    int filasAfectadasTesis = pstTesis.executeUpdate();

                    if (filasAfectadasTesis > 0) {
                        limpiarFormulario(); // Limpia los campos
                        texttitulo.requestFocus(); // Hace foco en el campo de título
                        return true;
                    }
                }
                return false;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return false;
        }
    }

    public boolean registrarDocumentoYInformePasantia() {
        if (camposValidos2()) {
            // Insertar en la tabla documento
            String sqlDocumento = "INSERT INTO documento (Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (java.sql.Connection con = new Conexion().conectar()) {
                // Insertar datos en la tabla documento
                PreparedStatement pst = con.prepareStatement(sqlDocumento, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, texttitulo.getText());   // Titulo
                pst.setString(2, cbdocumento.getValue()); // Tipo (extraído de ComboBox)
                pst.setString(3, textautor.getText());    // Autor
                //pst.setString(4, textcedula.getText());   // Cedula
                pst.setDate(4, java.sql.Date.valueOf(fecha.getValue())); // Fecha_publicacion
                pst.setBytes(5, resumenpdf);              // Resumen (archivo PDF)
                pst.setBytes(6, pdfindice);               // Indice (archivo PDF)
                pst.setBytes(7, pdfportada);              // Img_portada (archivo PDF)
                pst.setBytes(8, archivopdf);              // Archivopdf (archivo PDF)

                // Ejecutar insert en la tabla documento
                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    // Obtener el id_documento generado
                    ResultSet rs = pst.getGeneratedKeys();
                    int idDocumento = 0;
                    if (rs.next()) {
                        idDocumento = rs.getInt(1); // Obtener el valor del id_documento generado
                    }

                    // Insertar en la tabla informepasantia usando el id_documento obtenido
                    String sqlInformePasantia = "INSERT INTO informepasantia (id_documento, Empresa, Carrera, Cedula) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstInforme = con.prepareStatement(sqlInformePasantia);
                    pstInforme.setInt(1, idDocumento);               // id_documento (relacionado)
                    pstInforme.setString(2, textempresa.getText());  // Empresa
                    pstInforme.setString(3, cbcarrera.getValue());   // Carrera (extraído de ComboBox)
                    pstInforme.setString(4, textcedula.getText());  // Cedula

                    int filasAfectadasInforme = pstInforme.executeUpdate();

                    if (filasAfectadasInforme > 0) {
                        limpiarFormularioinforme(); // Limpia los campos
                        texttitulo.requestFocus(); // Hace foco en el campo de título
                        return true;
                    }

                }
                return false;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return false;
        }
    }

    public boolean registrarDocumentoYLibro() {
        if (camposValidos3()) {
            // Insertar en la tabla documento
            String sqlDocumento = "INSERT INTO documento (Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (java.sql.Connection con = new Conexion().conectar()) {
                // Insertar datos en la tabla documento
                PreparedStatement pst = con.prepareStatement(sqlDocumento, PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, texttitulo.getText());   // Titulo
                pst.setString(2, cbdocumento.getValue()); // Tipo (extraído de ComboBox)
                pst.setString(3, textautor.getText());    // Autor
                pst.setDate(4, java.sql.Date.valueOf(fecha.getValue())); // Fecha_publicacion
                pst.setBytes(5, resumenpdf);              // Resumen (archivo PDF)
                pst.setBytes(6, pdfindice);               // Indice (archivo PDF)
                pst.setBytes(7, pdfportada);              // Img_portada (archivo PDF)
                pst.setBytes(8, archivopdf);              // Archivopdf (archivo PDF)

                // Ejecutar insert en la tabla documento
                int filasAfectadas = pst.executeUpdate();

                if (filasAfectadas > 0) {
                    // Obtener el id_documento generado
                    ResultSet rs = pst.getGeneratedKeys();
                    int idDocumento = 0;
                    if (rs.next()) {
                        idDocumento = rs.getInt(1); // Obtener el valor del id_documento generado
                    }

                    // Insertar en la tabla informepasantia usando el id_documento obtenido
                    String sqlLibro = "INSERT INTO libro (id_documento, Editorial, Edicion, Estante, Cod_Lomo) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstLibro = con.prepareStatement(sqlLibro);
                    pstLibro.setInt(1, idDocumento);               // id_documento (relacionado)
                    pstLibro.setString(2, texteditorial.getText());  // Empresa
                    pstLibro.setString(3, textedicion.getText());  // Empresa
                    pstLibro.setString(4, textestante.getText());  // Empresa
                    pstLibro.setString(5, textlomo.getText());  // Empresa

                    int filasAfectadasLibro = pstLibro.executeUpdate();

                    if (filasAfectadasLibro > 0) {
                        limpiarFormulariolibro(); // Limpia los campos
                        texttitulo.requestFocus(); // Hace foco en el campo de título
                        return true;
                    }

                }
                return false;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            mostrarAlerta("Error", "Por favor, complete todos los campos.");
            return false;
        }
    }

// Método para limpiar los campos del formulario
    private void limpiarFormularioinforme() {
        texttitulo.clear();
        textautor.clear();
        textcedula.clear();
        textempresa.clear();
        cbcarrera.getSelectionModel().clearSelection();
        cbdocumento.getSelectionModel().select(1);
        fecha.setValue(null);
        selectedFile = null;
        pdfindice = null;
        resumenpdf = null;
        pdfportada = null;
        archivopdf = null;
        // Si se usan archivos, también puedes resetear la variable si es necesario
    }

    private void limpiarFormulariolibro() {
        texttitulo.clear();
        textautor.clear();
        texteditorial.clear();
        textedicion.clear();
        textestante.clear();
        textlomo.clear();
        cbdocumento.getSelectionModel().select(2);
        fecha.setValue(null);
        selectedFile = null;
        pdfindice = null;
        resumenpdf = null;
        pdfportada = null;
        archivopdf = null;
        // Si se usan archivos, también puedes resetear la variable si es necesario
    }

    // Método para verificar si los campos están completos
    private boolean camposValidos() {
        return !texttitulo.getText().isEmpty() && !textautor.getText().isEmpty()
                && !textcedula.getText().isEmpty() && !codtesis.getText().isEmpty() && cbdocumento.getValue() != null
                && cbcarrera.getValue() != null && fecha.getValue() != null && pdfindice != null && resumenpdf != null && pdfportada != null && archivopdf != null;
    }

    // Método para verificar si los campos están completos
    private boolean camposValidos2() {
        return !texttitulo.getText().isEmpty() && !textautor.getText().isEmpty()
                && !textcedula.getText().isEmpty() && !textempresa.getText().isEmpty() && cbdocumento.getValue() != null
                && cbcarrera.getValue() != null && fecha.getValue() != null && pdfindice != null && resumenpdf != null && pdfportada != null && archivopdf != null;
    }

    // Método para verificar si los campos están completos
    private boolean camposValidos3() {
        boolean respuesta = true;

        if (pdfindice == null || resumenpdf == null || pdfportada == null || archivopdf == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Hay archivos vacíos. ¿Seguro que desea proceder?");

            // Agregar los botones Sí y No al Alert
            ButtonType buttonSi = new ButtonType("Sí");
            ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonSi, buttonNo);
            // Personalizar los botones de la alerta con CSS en línea
            // Personalizar los botones individualmente
            // Agregar los botones al contenedor
            Button btnSi = (Button) alert.getDialogPane().lookupButton(buttonSi);
            btnSi.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10 20;");

            Button btnNo = (Button) alert.getDialogPane().lookupButton(buttonNo);
            btnNo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

            HBox customButtonBar = new HBox(10); // Espaciado de 10 entre botones
            customButtonBar.setStyle("-fx-alignment: center; -fx-padding: 10;");

            // Agregar los botones al contenedor
            customButtonBar.getChildren().addAll(btnSi, btnNo);

            // Reemplazar el contenido predeterminado del DialogPane con el contenedor personalizado
            alert.getDialogPane().setContentText(null); // Elimina el texto predeterminado para añadirlo manualmente
            alert.getDialogPane().setContent(new VBox(
                    new Label("Hay archivos vacíos. ¿Seguro que desea proceder?"),
                    customButtonBar
            ));
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // A// Ajustar el tamaño mínimo

            // Mostrar el diálogo y capturar la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            // Evaluar la respuesta
            if (result.isPresent() && result.get() == buttonSi) {
                respuesta = true;  // Si seleccionó "Sí"
            } else {
                respuesta = false; // Si seleccionó "No" o cerró el cuadro
            }
        }

        if (texttitulo.getText().isEmpty() || textautor.getText().isEmpty()
                || texteditorial.getText().isEmpty() || textedicion.getText().isEmpty() || textestante.getText().isEmpty() || textlomo.getText().isEmpty() || cbdocumento.getValue() == null
                || fecha.getValue() == null) {
            respuesta = false;
            System.out.println("faltan campos por llenar: ");
            System.out.println("Titulo: " + texttitulo.getText());
            System.out.println("Autor: " + textautor.getText());
            System.out.println("Editorial: " + texteditorial.getText());
            System.out.println("Edicion: " + textedicion.getText());
            System.out.println("Estante: " + textestante.getText());
            System.out.println("Lomo: " + textlomo.getText());
            System.out.println("Documento: " + cbdocumento.getValue());
            System.out.println("Fecha: " + fecha.getValue());

        }
        return respuesta;
    }

    // Método para convertir el archivo a un array de bytes
    public byte[] convertirArchivoABytes(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para mostrar un mensaje de alerta
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void AvisoRegistro(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            RegistrarController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public ImageView cargarPaginaComoImagen(File selectedFile, int pagina) throws IOException {
        PDDocument documento = Loader.loadPDF(selectedFile);
        PDFRenderer renderer = new PDFRenderer(documento);

        // Renderizamos la página seleccionada como una imagen
        BufferedImage imagen = renderer.renderImageWithDPI(pagina - 1, 150); // Usamos DPI de 150
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(imagen, null));
        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-alignment: center;");

        documento.close();
        return imageView;
    }

    // Método para guardar una página específica del PDF en un nuevo archivo PDF
    public byte[] guardarPaginaResumen(File selectedFile, int pagina) throws IOException {
        PDDocument documentoOriginal = Loader.loadPDF(selectedFile);
        PDDocument nuevoDocumento = new PDDocument();

        // Obtener solo la página del resumen
        PDPage paginaResumen = documentoOriginal.getPage(pagina - 1);
        nuevoDocumento.addPage(paginaResumen);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        nuevoDocumento.save(baos);
        nuevoDocumento.close();
        documentoOriginal.close();

        return baos.toByteArray(); // Retorna el PDF como byte array
    }

    // Método para cargar un rango de páginas del PDF como imágenes
    public void cargarPaginasComoImagenes(File selectedFile, int inicio, int fin, VBox contenedorImagenes) throws IOException {
        // Limpiar el contenedor de imágenes antes de agregar nuevas
        contenedorImagenes.getChildren().clear();

        // Usar PDFBox 3.0.3 para cargar el archivo PDF con el Loader
        PDDocument documento = Loader.loadPDF(selectedFile);

        // Usamos PDFRenderer para obtener las imágenes de las páginas
        PDFRenderer renderer = new PDFRenderer(documento);

        for (int i = inicio - 1; i < fin; i++) { // Los índices de página en PDFBox son 0-based
            BufferedImage imagen = renderer.renderImageWithDPI(i, 150); // Resolución de 150 DPI
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(imagen, null));
            imageView.setFitWidth(600); // Tamaño ajustado para un zoom más suave
            imageView.setPreserveRatio(true);
            imageView.setStyle("-fx-alignment: center;");

            // Asegurarnos de que la imagen esté centrada
            VBox.setMargin(imageView, new Insets(10, 0, 10, 0)); // Opcional: Margen alrededor de la imagen

            // Agregar funcionalidad de zoom con Ctrl + rueda del ratón
            imageView.setOnScroll(event -> {
                if (event.isControlDown()) {  // Verifica si la tecla Ctrl está presionada
                    double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9; // Zoom in/out
                    imageView.setScaleX(imageView.getScaleX() * zoomFactor);
                    imageView.setScaleY(imageView.getScaleY() * zoomFactor);
                }
            });

            // Agregar la imagen al contenedor de imágenes
            contenedorImagenes.getChildren().add(imageView);
        }

        // Cerrar el documento después de usarlo
        documento.close();
    }

    // Método para guardar las páginas seleccionadas en un nuevo archivo PDF
    public byte[] guardarPaginasSeleccionadas(File selectedFile, int inicio, int fin) throws IOException {
        // Usar PDFBox 3.0.3 para cargar el archivo PDF original con el Loader
        PDDocument documentoOriginal = Loader.loadPDF(selectedFile);
        PDDocument nuevoDocumento = new PDDocument();

        for (int i = inicio - 1; i < fin; i++) {
            PDPage pagina = documentoOriginal.getPage(i);
            nuevoDocumento.addPage(pagina);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        nuevoDocumento.save(baos);
        nuevoDocumento.close();
        documentoOriginal.close();

        return baos.toByteArray(); // Retorna el PDF como byte array
    }

    // Método para cargar la portada (primera página del PDF) como imagen
    public void cargarPortada(File selectedFile, VBox contenedorImagenes) throws IOException {
        // Limpiar el contenedor de imágenes antes de agregar la nueva portada
        contenedorImagenes.getChildren().clear();

        // Usar PDFBox 3.0.3 para cargar el archivo PDF con el Loader
        PDDocument documento = Loader.loadPDF(selectedFile);

        // Usamos PDFRenderer para obtener la imagen de la primera página
        PDFRenderer renderer = new PDFRenderer(documento);
        BufferedImage imagen = renderer.renderImageWithDPI(0, 150); // Resolución de 150 DPI (para la primera página)
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(imagen, null));
        imageView.setFitWidth(600); // Tamaño ajustado para un zoom más suave
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-alignment: center;");

        // Asegurarnos de que la imagen esté centrada
        VBox.setMargin(imageView, new Insets(10, 0, 10, 0)); // Opcional: Margen alrededor de la imagen

        // Agregar la imagen al contenedor de imágenes
        contenedorImagenes.getChildren().add(imageView);

        // Cerrar el documento después de usarlo
        documento.close();
    }
    /*
    public void configurarDatePicker(JFXDatePicker datePicker) {
        // Formateador para mostrar solo mes y año
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

        // Cambiar el formato mostrado en el DatePicker
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? monthYearFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, monthYearFormatter);
            }
        });

        // Crear un DayCellFactory para deshabilitar días
        Callback<DatePicker, DateCell> dayCellFactory = picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Ocultar días, solo permitir selección por mes/año
                if (!empty) {
                    setDisable(true);
                }
            }
        };

        // Asignar el DayCellFactory al DatePicker
        datePicker.setDayCellFactory(dayCellFactory);

        // Configurar el texto de guía
        datePicker.setPromptText("Seleccione mes y año");
    }*/

}
