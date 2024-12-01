/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.Buscarmodel;
import Modelos.Documento;
import Modelos.InformePasantia;
import Modelos.Libro;
import Modelos.StageMovement;
import Modelos.TrabajoGrado;
import Modelos.registmodel;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.SlideInUp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.w3c.dom.Text;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class RegistrarController implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private AnchorPane base;
    @FXML
    private JFXDialog dialog;
    @FXML
    private Label labelreg;

    @FXML
    private DatePicker fecha;
    @FXML
    private JFXTextField codtesis;
    @FXML
    private JFXComboBox<String> cbcarrera;

    @FXML
    private JFXComboBox<String> cbdocumento;

    @FXML
    private JFXTextField texttitulo;

    @FXML
    private JFXTextField textautor;

    @FXML
    private JFXTextField textcedula;

    @FXML
    private JFXTextField textcarrera;

    @FXML
    private JFXTextField textfecha;
    @FXML
    private JFXButton btnregistrar;
    @FXML
    private JFXButton btnregistrar2;
    @FXML
    private JFXButton btnbuscararchivo;

    @FXML
    private JFXButton btnimgindice;

    @FXML
    private JFXButton btnimgresumen;

    @FXML
    private JFXButton btnimgportada;
    @FXML
    private JFXButton btnvolver;
    @FXML
    private Pane paneInforme;

    @FXML
    private Pane panelibros;
    @FXML
    private Pane paneini;
    @FXML
    private Pane paneTesis;
    @FXML
    private ImageView imgarchivo;
    @FXML
    private ImageView imgindice;
    @FXML
    private ImageView imgresumen;
    @FXML
    private ImageView imgportada;
    @FXML
    private ImageView imgarchivo2;
    @FXML
    private ImageView imgindice2;
    @FXML
    private ImageView imgresumen2;
    @FXML
    private ImageView imgportada2;
    @FXML
    private ImageView imgarchivo3;
    @FXML
    private ImageView imgindice3;
    @FXML
    private ImageView imgresumen3;
    @FXML
    private ImageView imgportada3;
    @FXML
    private JFXTextField texttitulo2;

    @FXML
    private JFXTextField textautor2;

    @FXML
    private JFXTextField textcedula2;

    @FXML
    private JFXTextField textempresa;

    @FXML
    private JFXComboBox<String> cbcarrera2;

    @FXML
    private DatePicker fecha2;

    @FXML
    private JFXButton btnbuscararchivo2;

    @FXML
    private JFXTextField texttitulo3;

    @FXML
    private JFXTextField textautor3;

    @FXML
    private JFXTextField texteditorial;

    @FXML
    private JFXTextField textedicion;

    @FXML
    private JFXTextField textestante;

    @FXML
    private JFXTextField textlomo;

    @FXML
    private DatePicker fecha3;

    @FXML
    private JFXButton btnbuscararchivo3;

    @FXML
    private JFXButton btnimgindice3;

    @FXML
    private JFXButton btnimgresumen3;

    @FXML
    private JFXButton btnimgportada3;

    @FXML
    private JFXButton btnregistrar3;

    /**
     * Initializes the controller class.
     */
    @FXML
    private Image imgnop = new Image(getClass().getResourceAsStream("/img/neinei.png"));
    @FXML
    private Image imgyes = new Image(getClass().getResourceAsStream("/img/aprobado.png"));
    private File selectedFile;
    private AnchorPane viewp;
    private byte[] pdfindice;
    private byte[] resumenpdf;
    private byte[] pdfportada;
    private StageMovement stmodel = new StageMovement();
    private final registmodel registrarmodel = new registmodel();
    private Libro lib;
    private InformePasantia informe;
    private TrabajoGrado tesis;

    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    private void limitarnumerosycomasbolas(JFXTextField a) {

        StringConverter<Number> converter = new NumberStringConverter();
        TextFormatter<Number> textFormatter = new TextFormatter<>(converter, 0, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*(\\,\\d*)?")) {
                return change;
            }
            return null;
        });

        a.setTextFormatter(textFormatter);
    }

    @FXML
    private void limitarCaracteres100(KeyEvent event) {
        // Identifica el TextField que activó el evento
        JFXTextField textField = (JFXTextField) event.getSource();
        textField.addEventHandler(KeyEvent.KEY_TYPED, evt -> {
            evt.consume();
        });
        // Verifica y bloquea si excede el límite de caracteres
        if (textField.getText().length() >= 100) {
            event.consume(); // Bloquea el ingreso de más caracteres
        }
    }

    @FXML
    private void limitarCaracteres20(KeyEvent event) {
        // Identifica el TextField que activó el evento
        JFXTextField textField = (JFXTextField) event.getSource();

        // Verifica y bloquea si excede el límite de caracteres
        if (textField.getText().length() >= 20) {
            event.consume(); // Bloquea el ingreso de más caracteres
        }
    }

    @FXML
    private void limitarCaracteres50(KeyEvent event) {
        // Identifica el TextField que activó el evento
        JFXTextField textField = (JFXTextField) event.getSource();

        // Verifica y bloquea si excede el límite de caracteres
        if (textField.getText().length() >= 50) {
            event.consume(); // Bloquea el ingreso de más caracteres
        }
    }

    @FXML
    private void limitarCaracteres25(KeyEvent event) {
        // Identifica el TextField que activó el evento
        JFXTextField textField = (JFXTextField) event.getSource();

        // Verifica y bloquea si excede el límite de caracteres
        if (textField.getText().length() >= 25) {
            event.consume(); // Bloquea el ingreso de más caracteres
        }
    }

    @FXML
    private void testMethod(KeyEvent event) {
        System.out.println("Tecla presionada: " + event.getCharacter());
    }

    @FXML
    private void limitarNumerosYComas(KeyEvent event) {
        // Obtén el TextField que activó el evento
        // Obtén el TextField que activó el evento
        TextField textField = (TextField) event.getSource();

        // Obtén el carácter recién ingresado
        String newChar = event.getCharacter();

        // Imprime información para depurar
        System.out.println("Carácter ingresado: " + newChar);
        System.out.println("Texto actual en el TextField: " + textField.getText());

        // Verifica si el nuevo carácter es un número o una coma
        if (!newChar.matches("[0-9,]")) {
            System.out.println("Caracter no permitido");

            event.consume(); // Bloquea la entrada no válida
            return;
        }

        // Verifica si la longitud total del texto excede el máximo
        if (textField.getText().length() >= 40) {
            System.out.println("Límite de caracteres alcanzado");
            event.consume(); // Bloquea la entrada si se excede el límite de caracteres
        }
    }

    @FXML
    private void limitarNumerosYComasYpuntos(KeyEvent event) {
        // Obtén el TextField que activó el evento
        JFXTextField textField = (JFXTextField) event.getSource();

        // Obtén el carácter recién ingresado
        String newChar = event.getCharacter();

        // Permite solo números, coma y respeta el límite de caracteres
        if (!newChar.matches("[0-9,.-]") || textField.getText().length() >= 10) {
            event.consume(); // Bloquea la entrada no válida
        }
    }

    @FXML
    private void abrirDialogo(MouseEvent event) {
        // Crear el contenido del diálogo
        StackPane stackPane = (StackPane) base.getScene().getRoot();

        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new javafx.scene.text.Text("Seleccione las páginas del índice"));
        contenido.setPrefWidth(1000);
        contenido.setPrefHeight(600);

        // Campos para los números de página
        JFXTextField paginaInicio = new JFXTextField();
        paginaInicio.setPromptText("Página de inicio");
        JFXTextField paginaFin = new JFXTextField();
        paginaFin.setPromptText("Página final");

        // Contenedor para las imágenes (usamos ScrollPane para desplazarnos por las páginas)
        ScrollPane scrollPane = new ScrollPane();

        // VBox para las imágenes, asegurándonos de que las imágenes se alineen al centro
        VBox contenedorImagenes = new VBox(10);
        contenedorImagenes.setAlignment(Pos.CENTER); // Alineamos el contenido del VBox al centro
        scrollPane.setContent(contenedorImagenes);
        scrollPane.setFitToWidth(true); // Aseguramos que el contenido se ajuste al ancho del ScrollPane
        scrollPane.setFitToHeight(true); // Ajustar también la altura al contenido

        // Crear el modelo
        registmodel pdfModel = new registmodel();

        // Botón para visualizar las páginas
        JFXButton btnVisualizar = new JFXButton("Visualizar");
        btnVisualizar.setOnAction(e -> {
            int inicio = Integer.parseInt(paginaInicio.getText());
            int fin = Integer.parseInt(paginaFin.getText());

            try {
                // Cargar las imágenes de las páginas seleccionadas en el contenedor
                pdfModel.cargarPaginasComoImagenes(selectedFile, inicio, fin, contenedorImagenes);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Label para mostrar mensaje de guardado
        Label mensajeGuardado = new Label();
        mensajeGuardado.setVisible(false); // Inicialmente no se muestra

        // Botón para aceptar
        JFXButton btnAceptar = new JFXButton("Aceptar");
        btnAceptar.setOnAction(e -> {
            int inicio = Integer.parseInt(paginaInicio.getText());
            int fin = Integer.parseInt(paginaFin.getText());

            try {
                // Guardar las páginas seleccionadas
                pdfindice = pdfModel.guardarPaginasSeleccionadas(selectedFile, inicio, fin);
            } catch (IOException ex) {
                Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, null, ex);
            }
            mensajeGuardado.setText("Índice guardado exitosamente.");
            mensajeGuardado.setVisible(true); // Hacer visible el mensaje
            imgindice.setImage(imgyes);
            imgindice2.setImage(imgyes);
            imgindice3.setImage(imgyes);
        });

        // Agregar los botones, campos y el mensaje al contenido del diálogo
        contenido.setBody(new VBox(10, paginaInicio, paginaFin, btnVisualizar, scrollPane, btnAceptar, mensajeGuardado));
        contenido.setAlignment(Pos.CENTER);

        // Crear y mostrar el JFXDialog
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    @FXML
    private void abrirDialogoResumen(MouseEvent event) {
        // Crear el contenido del diálogo
        StackPane stackPane = (StackPane) base.getScene().getRoot();
        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new javafx.scene.text.Text("Seleccione la página del resumen"));
        contenido.setPrefWidth(1000);
        contenido.setPrefHeight(600);

        // Campo para el número de la página del resumen
        JFXTextField paginaResumen = new JFXTextField();
        paginaResumen.setPromptText("Página del resumen");

        // Contenedor para la imagen del resumen
        ScrollPane scrollPane = new ScrollPane();
        VBox contenedorImagenes = new VBox(10);
        contenedorImagenes.setAlignment(Pos.CENTER); // Alineamos el contenido del VBox al centro
        scrollPane.setContent(contenedorImagenes);
        scrollPane.setFitToWidth(true); // Aseguramos que el contenido se ajuste al ancho del ScrollPane
        scrollPane.setFitToHeight(true); // Ajustar también la altura al contenido

        // Crear el modelo
        registmodel pdfModel = new registmodel();

        // Botón para visualizar la página del resumen
        JFXButton btnVisualizar = new JFXButton("Visualizar");
        btnVisualizar.setOnAction(e -> {
            int pagina = Integer.parseInt(paginaResumen.getText());

            try {
                // Cargar la imagen de la página seleccionada en el contenedor
                ImageView imageView = pdfModel.cargarPaginaComoImagen(selectedFile, pagina);
                contenedorImagenes.getChildren().clear();
                contenedorImagenes.getChildren().add(imageView);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Label para mostrar mensaje de guardado
        Label mensajeGuardado = new Label();
        mensajeGuardado.setVisible(false); // Inicialmente no se muestra

        // Botón para aceptar
        JFXButton btnAceptar = new JFXButton("Aceptar");
        btnAceptar.setOnAction(e -> {
            int pagina = Integer.parseInt(paginaResumen.getText());

            try {
                // Guardar la página seleccionada (resumen)
                resumenpdf = pdfModel.guardarPaginaResumen(selectedFile, pagina);
            } catch (IOException ex) {
                Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, null, ex);
            }
            mensajeGuardado.setText("Resumen guardado exitosamente.");
            mensajeGuardado.setVisible(true); // Hacer visible el mensaje
            imgresumen.setImage(imgyes);
            imgresumen2.setImage(imgyes);
            imgresumen3.setImage(imgyes);
        });

        // Agregar los botones, campos y el mensaje al contenido del diálogo
        contenido.setBody(new VBox(10, paginaResumen, btnVisualizar, scrollPane, btnAceptar, mensajeGuardado));
        contenido.setAlignment(Pos.CENTER);

        // Crear y mostrar el JFXDialog
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    @FXML
    private void onRegistrarButtonClick(MouseEvent e) {
        registmodel modelbytes = new registmodel();
        byte[] archivopdf = modelbytes.convertirArchivoABytes(selectedFile);
        registmodel model = new registmodel(texttitulo, textautor, textcedula, codtesis, cbcarrera, cbdocumento, fecha, pdfindice, resumenpdf, pdfportada, archivopdf);
        boolean registrado = model.registrarDocumentoYtesis();

        if (registrado) {
            // Mostrar mensaje de éxito
            mostrarMensaje("Registro exitoso", "Los datos han sido registrados correctamente.");
            imgarchivo.setImage(imgnop);
            imgindice.setImage(imgnop);
            imgportada.setImage(imgnop);
            imgresumen.setImage(imgnop);
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    @FXML
    private void onRegistrarinformeButtonClick(MouseEvent e) {

        registmodel model = new registmodel(texttitulo2, textautor2, textcedula2, textempresa, cbcarrera2, cbdocumento, fecha2, pdfindice, resumenpdf, pdfportada, selectedFile);
        boolean registrado = model.registrarDocumentoYInformePasantia();

        if (registrado) {
            // Mostrar mensaje de éxito
            mostrarMensaje("Registro exitoso", "Los datos han sido registrados correctamente.");
            imgarchivo2.setImage(imgnop);
            imgindice2.setImage(imgnop);
            imgportada2.setImage(imgnop);
            imgresumen2.setImage(imgnop);
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    @FXML
    private void onRegistrarlibroButtonClick(MouseEvent e) {

        registmodel model = new registmodel(texttitulo3, textautor3, texteditorial, textedicion, textestante, textlomo, cbdocumento, fecha3, selectedFile, pdfindice, resumenpdf, pdfportada);
        boolean registrado = model.registrarDocumentoYLibro();

        if (registrado) {
            // Mostrar mensaje de éxito
            mostrarMensaje("Registro exitoso", "Los datos han sido registrados correctamente.");
            imgarchivo3.setImage(imgnop);
            imgindice3.setImage(imgnop);
            imgportada3.setImage(imgnop);
            imgresumen3.setImage(imgnop);
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    // Método para mostrar un mensaje de éxito o error
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para abrir el FileChooser
    public void openFileChooser(MouseEvent e) {
        // Crear el FileChooser
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) btnbuscararchivo.getScene().getWindow();

        // Configurar el título del diálogo
        fileChooser.setTitle("Selecciona un archivo PDF");

        // Agregar filtros para solo archivos PDF
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );

        // Mostrar el FileChooser y obtener el archivo seleccionado
        selectedFile = fileChooser.showOpenDialog(stage);

        // Verificar si se seleccionó un archivo
        if (selectedFile != null) {
            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
            imgarchivo.setImage(imgyes);
            imgarchivo2.setImage(imgyes);
            imgarchivo3.setImage(imgyes);
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpage("Inipro", viewp);
    }

    @FXML
    private void abrirDialogoPortada(MouseEvent event) {
        // Crear el contenido del diálogo
        StackPane stackPane = (StackPane) base.getScene().getRoot();

        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new javafx.scene.text.Text("Portada del documento"));
        contenido.setPrefWidth(1000);
        contenido.setPrefHeight(600);

        // Contenedor para la imagen de la portada
        ScrollPane scrollPane = new ScrollPane();

        // VBox para la portada, asegurándonos de que se alinee al centro
        VBox contenedorImagenes = new VBox(10);
        contenedorImagenes.setAlignment(Pos.CENTER); // Alineamos el contenido del VBox al centro
        scrollPane.setContent(contenedorImagenes);
        scrollPane.setFitToWidth(true); // Aseguramos que el contenido se ajuste al ancho del ScrollPane
        scrollPane.setFitToHeight(true); // Ajustar también la altura al contenido

        // Label para mostrar mensaje de guardado
        Label mensajeGuardado = new Label();
        mensajeGuardado.setVisible(false); // Inicialmente no se muestra

        // Crear el modelo
        registmodel pdfModel = new registmodel();

        // Cargar la portada (primera página)
        try {
            pdfModel.cargarPortada(selectedFile, contenedorImagenes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Botón de Aceptar para guardar la portada
        JFXButton btnAceptar = new JFXButton("Aceptar");
        btnAceptar.setOnAction(e -> {
            try {
                // Guardar solo la portada (primera página)
                pdfportada = pdfModel.guardarPaginasSeleccionadas(selectedFile, 1, 1); // Solo la primera página
                mensajeGuardado.setText("Portada guardada exitosamente.");
                mensajeGuardado.setVisible(true); // Mostrar el mensaje de éxito
                imgportada.setImage(imgyes);
                imgportada2.setImage(imgyes);
                imgportada3.setImage(imgyes);
            } catch (IOException ex) {
                ex.printStackTrace();
                mensajeGuardado.setText("Error al guardar la portada.");
                mensajeGuardado.setVisible(true);
            }
        });

        // Agregar el botón y el mensaje al contenido del diálogo
        contenido.setBody(new VBox(10, scrollPane, btnAceptar, mensajeGuardado));
        contenido.setAlignment(Pos.CENTER);

        // Crear y mostrar el JFXDialog
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    // Método para comprobar si se seleccionó un archivo
    public boolean isFileSelected() {
        return selectedFile != null;
    }

    // Método para obtener el archivo seleccionado
    public File getSelectedFile() {
        return selectedFile;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbdocumento.getItems().addAll("Trabajos de Grado", "Informes de Pasantía", "Libros");
        cbcarrera.getItems().addAll("Arquitectura", "Ing. Civil", "Ing. Eléctrica", "Ing. Electrónica", "Ing. Industrial", "Ing. en Mtto. Mecánico", "Ing. de Sistemas", "Ing. en Diseño Industrial", "Ing. Química", "Ing. de Petróleo");
        cbcarrera2.getItems().addAll("Arquitectura", "Ing. Civil", "Ing. Eléctrica", "Ing. Electrónica", "Ing. Industrial", "Ing. en Mtto. Mecánico", "Ing. de Sistemas", "Ing. en Diseño Industrial", "Ing. Química", "Ing. de Petróleo");
        cbdocumento.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cambiarPane(newValue);
        });

        limitarnumerosycomasbolas(textcedula);
        //registrarmodel.configurarDatePicker(fecha);
    }

    private void cambiarPane(String paneName) {
        Pane paneToShow = null;
        int elec = 0;
        // Determinar qué pane mostrar
        switch (paneName) {
            case "Trabajos de Grado" -> {
                paneToShow = paneTesis;
                elec = 1;
            }
            case "Informes de Pasantia" -> {
                paneToShow = paneInforme;
                elec = 2;
            }
            case "Libros" -> {
                paneToShow = panelibros;
                elec = 3;
            }
        }

        if (paneToShow != null) {
            final Pane finalPaneToShow = paneToShow; // Hacerlo "efectivamente final"
            Pane paneToHide = obtenerPaneVisible();

            if (paneToHide != null && paneToHide != finalPaneToShow) {
                final Pane finalPaneToHide = paneToHide; // Hacerlo "efectivamente final"

                datosnuevapagina(elec);
                // Crear animación para ocultar el pane actual
                finalPaneToHide.setVisible(false);
                finalPaneToShow.setVisible(true);
                new FadeIn(finalPaneToShow).play();
                /*
                FadeOut fadeOut = new FadeOut(finalPaneToHide);
                fadeOut.setOnFinished(event -> {
                    finalPaneToHide.setVisible(false);
                    finalPaneToShow.setVisible(true);

                    // Crear animación para mostrar el nuevo pane
                    FadeIn fadeIn = new FadeIn(finalPaneToShow);
                    fadeIn.play();
                });
                fadeOut.play();
                 */
            } else if (paneToHide == null) {
                // Si no hay un pane visible, simplemente mostrar el seleccionado
                datosnuevapagina(elec);
                paneini.setVisible(false);
                finalPaneToShow.setVisible(true);
                new FadeIn(finalPaneToShow).play();
            }
        }
    }

    private Pane obtenerPaneVisible() {
        if (paneInforme.isVisible()) {
            return paneInforme;
        }
        if (panelibros.isVisible()) {
            return panelibros;
        }
        if (paneTesis.isVisible()) {
            return paneTesis;
        }
        return null;
    }

    public void datosnuevapagina(int e) {

        switch (e) {
            case 1 -> {
                imgarchivo.setImage(imgnop);
                imgindice.setImage(imgnop);
                imgresumen.setImage(imgnop);
                imgportada.setImage(imgnop);
            }
            case 2 -> {
                imgarchivo2.setImage(imgnop);
                imgindice2.setImage(imgnop);
                imgresumen2.setImage(imgnop);
                imgportada2.setImage(imgnop);
            }
            case 3 -> {
                imgarchivo3.setImage(imgnop);
                imgindice3.setImage(imgnop);
                imgresumen3.setImage(imgnop);
                imgportada3.setImage(imgnop);
            }

        }
    }

    @FXML
    public void setpanemodlibro(Libro lib) {

        paneini.setVisible(false);
        paneInforme.setVisible(false);
        paneTesis.setVisible(false);
        panelibros.setVisible(true);

        this.lib = lib;
        texttitulo3.setText(lib.getTitulo());
        textautor3.setText(lib.getAutor());
        textedicion.setText(lib.getEdicion());
        textestante.setText(lib.getEstante());
        texteditorial.setText(lib.getEditorial());
        textlomo.setText(lib.getCod_lomo());
        fecha3.setValue(lib.getFecha_publicacion());

        if (lib.getImg_portada() != null) {
            this.pdfportada = lib.getImg_portada();
            imgportada3.setImage(imgyes);
        } else {
            imgportada3.setImage(imgnop);
        }

        if (lib.getIndice() != null) {
            this.pdfindice = lib.getIndice();
            imgindice3.setImage(imgyes);
        } else {
            imgindice3.setImage(imgnop);
        }

        if (lib.getResumen() != null) {
            this.resumenpdf = lib.getResumen();
            imgresumen3.setImage(imgyes);
        } else {
            imgresumen3.setImage(imgnop);
        }

        if (lib.getArchivopdf() != null) {
            try {
                // Convierte los bytes en un archivo PDF
                this.selectedFile = Documento.convertirByteAArchivo2(lib.getArchivopdf(), lib.getTitulo());
                imgarchivo3.setImage(imgyes); // Cambia la imagen indicando éxito
            } catch (IOException ex) {
                Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, "Error al convertir bytes a archivo PDF", ex);
                imgarchivo3.setImage(imgnop); // Cambia la imagen indicando fallo
            }
        } else {
            imgarchivo3.setImage(imgnop); // Si no hay archivo, muestra imagen de error
        }

        btnregistrar3.setText("Modificar");
        labelreg.setText("Modificar");

        cbdocumento.setValue("Libros"); // Establecer "Libros" como opción seleccionada
        cbdocumento.setEditable(false); // No permitir escribir en el ComboBox
        cbdocumento.setDisable(true);   // Deshabilitar para que no pueda cambiarse

        // Cambiar dinámicamente el evento del botón
        btnregistrar3.setOnMouseClicked(e -> modificarLibro());
        btnvolver.setOnMouseClicked(e -> volveralibro());
        // Configurar el ComboBox

    }

    @FXML
    public void modificarLibro() {
        registmodel modif = new registmodel(texttitulo3, textautor3, texteditorial, textedicion, textestante, textlomo, cbdocumento, fecha3, selectedFile, pdfindice, resumenpdf, pdfportada);
        boolean registrado = modif.modificarDocumentoYLibro(lib.getId_documento());
        if (registrado) {
            // Mostrar mensaje de éxito
            imgarchivo3.setImage(imgnop);
            imgindice3.setImage(imgnop);
            imgportada3.setImage(imgnop);
            imgresumen3.setImage(imgnop);
            mostrarMensaje("Modificación exitosa", "Los datos han sido modificados correctamente.");
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    @FXML
    public void volveralibro() {
        Buscarmodel busqueda = new Buscarmodel();
        busqueda.loadpagelibro("visualizaciondellibro", viewp, lib.getId_documento());
    }

    @FXML
    public void setpanemodtesis(TrabajoGrado tesis) {

        paneini.setVisible(false);
        paneInforme.setVisible(false);
        paneTesis.setVisible(true);
        panelibros.setVisible(false);

        this.tesis = tesis;
        texttitulo.setText(tesis.getTitulo());
        textautor.setText(tesis.getAutor());
        textcedula.setText(tesis.getCedula());
        codtesis.setText(tesis.getCodigo());
        cbcarrera.setValue(tesis.getCarrera());
        fecha.setValue(tesis.getFecha_publicacion());

        if (tesis.getImg_portada() != null) {
            this.pdfportada = tesis.getImg_portada();
            imgportada.setImage(imgyes);
        } else {
            imgportada.setImage(imgnop);
        }

        if (tesis.getIndice() != null) {
            this.pdfindice = tesis.getIndice();
            imgindice.setImage(imgyes);
        } else {
            imgindice.setImage(imgnop);
        }

        if (tesis.getResumen() != null) {
            this.resumenpdf = tesis.getResumen();
            imgresumen.setImage(imgyes);
        } else {
            imgresumen.setImage(imgnop);
        }

        if (tesis.getArchivopdf() != null) {
            try {
                // Convierte los bytes en un archivo PDF
                this.selectedFile = Documento.convertirByteAArchivo2(tesis.getArchivopdf(), tesis.getTitulo());

                imgarchivo.setImage(imgyes); // Cambia la imagen indicando éxito
            } catch (IOException ex) {
                Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, "Error al convertir bytes a archivo PDF", ex);
                imgarchivo.setImage(imgnop); // Cambia la imagen indicando fallo
            }
        } else {
            imgarchivo.setImage(imgnop); // Si no hay archivo, muestra imagen de error
        }

        btnregistrar.setText("Modificar");
        labelreg.setText("Modificar");
        cbdocumento.setValue("Trabajos de Grado"); // Establecer "Libros" como opción seleccionada
        cbdocumento.setEditable(false); // No permitir escribir en el ComboBox
        cbdocumento.setDisable(true);   // Deshabilitar para que no pueda cambiarse

        // Cambiar dinámicamente el evento del botón
        btnregistrar.setOnMouseClicked(e -> modificarTesis());
        btnvolver.setOnMouseClicked(e -> volveratesis());
        // Configurar el ComboBox

    }

    @FXML
    public void modificarTesis() {
        registmodel modif = new registmodel(texttitulo, textautor, textcedula, codtesis, cbcarrera, cbdocumento, fecha, pdfindice, resumenpdf, pdfportada, tesis.getArchivopdf());
        boolean registrado = modif.modificarDocumentoYtesis(tesis.getId_documento());
        if (registrado) {
            // Mostrar mensaje de éxito
            imgarchivo.setImage(imgnop);
            imgindice.setImage(imgnop);
            imgportada.setImage(imgnop);
            imgresumen.setImage(imgnop);
            mostrarMensaje("Modificación exitosa", "Los datos han sido modificados correctamente.");
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    @FXML
    public void volveratesis() {
        Buscarmodel busqueda = new Buscarmodel();
        busqueda.loadpagetesis("visualizaciontrabajosdegrado2", viewp, tesis.getId_documento());
    }

    @FXML
    public void setpanemodinforme(InformePasantia informe) {

        paneini.setVisible(false);
        paneInforme.setVisible(true);
        paneTesis.setVisible(false);
        panelibros.setVisible(false);

        this.informe = informe;
        texttitulo2.setText(informe.getTitulo());
        textautor2.setText(informe.getAutor());
        textcedula2.setText(informe.getCedula());
        textempresa.setText(informe.getEmpresa());
        cbcarrera2.setValue(informe.getCarrera());
        fecha2.setValue(informe.getFecha_publicacion());

        if (informe.getImg_portada() != null) {
            this.pdfportada = informe.getImg_portada();
            imgportada2.setImage(imgyes);
        } else {
            imgportada2.setImage(imgnop);
        }

        if (informe.getIndice() != null) {
            this.pdfindice = informe.getIndice();
            imgindice2.setImage(imgyes);
        } else {
            imgindice2.setImage(imgnop);
        }

        if (informe.getResumen() != null) {
            this.resumenpdf = informe.getResumen();
            imgresumen2.setImage(imgyes);
        } else {
            imgresumen2.setImage(imgnop);
        }

        if (informe.getArchivopdf() != null) {
            try {
                // Convierte los bytes en un archivo PDF
                this.selectedFile = Documento.convertirByteAArchivo2(informe.getArchivopdf(), informe.getTitulo());

                imgarchivo2.setImage(imgyes); // Cambia la imagen indicando éxito
            } catch (IOException ex) {
                Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, "Error al convertir bytes a archivo PDF", ex);
                imgarchivo2.setImage(imgnop); // Cambia la imagen indicando fallo
            }
        } else {
            imgarchivo2.setImage(imgnop); // Si no hay archivo, muestra imagen de error
        }

        btnregistrar2.setText("Modificar");
        labelreg.setText("Modificar");
        cbdocumento.setValue("Informes de Pasantía"); // Establecer "Libros" como opción seleccionada
        cbdocumento.setEditable(false); // No permitir escribir en el ComboBox
        cbdocumento.setDisable(true);   // Deshabilitar para que no pueda cambiarse

        // Cambiar dinámicamente el evento del botón
        btnregistrar2.setOnMouseClicked(e -> modificarInforme());
        btnvolver.setOnMouseClicked(e -> volverainforme());
        // Configurar el ComboBox

    }

    @FXML
    public void modificarInforme() {
        registmodel modif = new registmodel(texttitulo2, textautor2, textcedula2, textempresa, cbcarrera2, cbdocumento, fecha2, pdfindice, resumenpdf, pdfportada, selectedFile);
        boolean registrado = modif.modificarDocumentoYInformePasantia(informe.getId_documento());
        if (registrado) {
            // Mostrar mensaje de éxito
            imgarchivo2.setImage(imgnop);
            imgindice2.setImage(imgnop);
            imgportada2.setImage(imgnop);
            imgresumen2.setImage(imgnop);
            mostrarMensaje("Modificación exitosa", "Los datos han sido modificados correctamente.");
        } else {
            // Mostrar mensaje de error
            mostrarMensaje("Error", "Hubo un problema al registrar los datos.");
        }
    }

    @FXML
    public void volverainforme() {
        Buscarmodel busqueda = new Buscarmodel();
        busqueda.loadpageinforme("vistapasantias", viewp, informe.getId_documento());
    }

}
