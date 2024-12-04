/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.Documento;
import Modelos.FormateadorTexto;
import Modelos.Prestamo;
import Modelos.PrestamosModel;
import Modelos.registmodel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.sql.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class PrestamosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane viewp;
    private Ajustesmodel ajmodel = new Ajustesmodel();
    private StageMovement stmodel = new StageMovement();
    private PrestamosModel modelopdf = new PrestamosModel();
    private JFXComboBox<Integer> comboBoxMes;
    private JFXComboBox<Integer> comboBoxAnio;
    private JFXComboBox<Integer> comboBoxMes2;
    private JFXComboBox<Integer> comboBoxAnio2;
    private JFXComboBox<Integer> comboBoxMesFin;
    private JFXComboBox<Integer> comboBoxAnioFin;
    public File reporteTemporal;
    @FXML
    private StackPane base;

    @FXML
    private JFXButton btnreportes;
    @FXML
    private JFXButton btnguarcambios;
    @FXML
    private AnchorPane viewpane1;

    @FXML
    private JFXButton btnvolver;
    @FXML
    private JFXComboBox<String> cbbuscar;
    @FXML
    private JFXTextField txtIdDocumento;

    @FXML
    private JFXTextField textcedula;
    @FXML
    private JFXTextField textfecha;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnaceptar;
    @FXML
    private JFXTextField txtTipoDocumento;
    @FXML
    private TableView<Prestamo> tablaprestamos;

    @FXML
    private TableColumn<Prestamo, Integer> colIdPrestamo;
    @FXML
    private TableColumn<Prestamo, Integer> colIdDocumento;
    @FXML
    private TableColumn<Prestamo, String> colTitulo;
    @FXML
    private TableColumn<Prestamo, String> colTipo;
    @FXML
    private TableColumn<Prestamo, String> colCedula;

    @FXML
    private TableColumn<Prestamo, String> colFechaHora;
    @FXML
    private TableColumn<Prestamo, String> colEstado;
    private Map<String, Documento> titulosMap; // Mapa de títulos con IDs y tipos
    private boolean isUserTyping = true; // Evitar ciclos de eventos
    PrestamosModel cargartabla = new PrestamosModel();

    private String roundedBorderStyle = "-fx-background-color: transparent;"
            + // Fondo transparente
            "-fx-border-color: #FA731F;"
            + // Color del borde
            "-fx-border-radius: 15;"
            + // Borde redondeado (15px)
            "-fx-border-width: 2;"
            + // Grosor del borde
            "-fx-text-fill: black;"   //#FA731F
            + // Color del texto
            "-fx-padding: 5 15;" + "-fx-background-radius: 15;"; // Espaciado interno del botón

    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpage("Inipro", viewp);
    }

    @FXML
    public void registrarprestamo(MouseEvent e) {
        if (cbbuscar.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Datos");
            alert.setHeaderText(null);
            alert.setContentText("Tiene que seleccionar un documento.");
            alert.showAndWait();
        } else if (textcedula.getText().trim().equals("V-")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Datos");
            alert.setHeaderText(null);
            alert.setContentText("Tiene que ingresar la cédula.");
            alert.showAndWait();
        } else {
            PrestamosModel prestar = new PrestamosModel();
            prestar.registrarPrestamo(Integer.parseInt(txtIdDocumento.getText()), textfecha.getText(), textcedula.getText());
            cargartabla.cargarDatosPrestamos(tablaprestamos);
        }

    }

    @FXML
    public void limpiar(MouseEvent e) {
        FormateadorTexto.configurarFormatoCedula(textcedula);
        textfecha.clear();
        txtIdDocumento.clear();
        txtTipoDocumento.clear();
        cbbuscar.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarComboBox();
        FormateadorTexto.configurarFormatoCedula(textcedula);
        inicializartabla();

    }

    /*
    @FXML
    public void inicializarcombobox() {
        // Obtener el año actual
        int anioActual = LocalDate.now().getYear();

// Llenar el ComboBox de años con los últimos 10 años, incluido el actual
        for (int i = anioActual; i >= anioActual - 10; i--) {
            comboBoxAnio.getItems().add(String.valueOf(i));
            comboBoxAnioFin.getItems().add(String.valueOf(i));
        }

// Llenar el ComboBox de meses (1 a 12)
        for (int i = 1; i <= 12; i++) {
            comboBoxMes.getItems().add(i < 10 ? "0" + i : String.valueOf(i));
            comboBoxMesFin.getItems().add(i < 10 ? "0" + i : String.valueOf(i));
        }

// Establecer un valor predeterminado en el ComboBox de años y meses si lo deseas
        comboBoxAnio.getSelectionModel().selectFirst();
        comboBoxAnioFin.getSelectionModel().selectFirst();
        comboBoxMes.getSelectionModel().selectFirst();
        comboBoxMesFin.getSelectionModel().selectFirst();
    }
     */
    @FXML
    public void inicializartabla() {
        colIdPrestamo.setCellValueFactory(new PropertyValueFactory<>("idPrestamo"));
        colIdDocumento.setCellValueFactory(new PropertyValueFactory<>("idDocumento"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoTexto"));
        colEstado.setCellFactory(column -> {
            return new TableCell<Prestamo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setStyle(null);
                    } else {
                        if (item != null) {
                            setText(item);
                            if (item.equalsIgnoreCase("Pendiente")) {
                                // Estilo para "Pendiente"
                                setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold;");
                            } else {
                                // Estilo para "Completado"
                                setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");
                            }
                        }
                    }
                }
            };
        });

        // Crear el menú contextual
        ContextMenu contextMenu = new ContextMenu();
        MenuItem marcarCompletado = new MenuItem("Marcar como completado");
        MenuItem marcarPendiente = new MenuItem("Marcar como pendiente");

        // Agregar acciones a los elementos del menú
        marcarCompletado.setOnAction(event -> {
            Prestamo selectedPrestamo = tablaprestamos.getSelectionModel().getSelectedItem();
            if (selectedPrestamo != null) {
                selectedPrestamo.setEstado(1); // Marcar como completado
                actualizarBaseDatos(selectedPrestamo); // Actualizar en la base de datos
                cargartabla.cargarDatosPrestamos(tablaprestamos); // Refrescar la tabla
            }
        });

        marcarPendiente.setOnAction(event -> {
            Prestamo selectedPrestamo = tablaprestamos.getSelectionModel().getSelectedItem();
            if (selectedPrestamo != null) {
                selectedPrestamo.setEstado(0); // Marcar como pendiente
                actualizarBaseDatos(selectedPrestamo); // Actualizar en la base de datos
                cargartabla.cargarDatosPrestamos(tablaprestamos); // Refrescar la tabla
            }
        });

        // Agregar los elementos al menú
        contextMenu.getItems().addAll(marcarCompletado, marcarPendiente);

        // Asociar el menú contextual a las filas de la tabla
        tablaprestamos.setRowFactory(tv -> {
            TableRow<Prestamo> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });

        // Cargar los datos de la base de datos
        PrestamosModel cargartabla = new PrestamosModel();
        cargartabla.cargarDatosPrestamos(tablaprestamos);
    }

    // Método para inicializar el ComboBox
    public void inicializarComboBox() {
        cbbuscar.setEditable(true); // Permitir edición en el ComboBox

        // Listener para texto en el editor
        cbbuscar.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUserTyping && !newValue.isEmpty()) {
                actualizarOpcionesComboBox(newValue); // Actualizar títulos según el texto
            }
        });

        // Listener para manejar la selección
        cbbuscar.setOnAction(e -> {
            if (isUserTyping) { // Evitar eventos generados al actualizar las opciones
                String tituloSeleccionado = cbbuscar.getValue();
                if (tituloSeleccionado != null && titulosMap != null && titulosMap.containsKey(tituloSeleccionado)) {
                    Documento resultado = titulosMap.get(tituloSeleccionado);
                    txtIdDocumento.setText(String.valueOf(resultado.getId_documento())); // Mostrar ID
                    txtTipoDocumento.setText(resultado.getTipo()); // Mostrar Tipo de Documento
                    PrestamosModel.establecerFechaActual(textfecha);
                }
            }
        });

        // Listener para manejar edición manual tras selección
        cbbuscar.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                case DELETE:
                    isUserTyping = true; // Permitir edición
                    break;
                case ENTER:
                    String textoActual = cbbuscar.getEditor().getText();
                    if (!textoActual.isEmpty()) {
                        cbbuscar.setValue(textoActual); // Asegurar valor actualizado
                    }
                    break;
                default:
                    break;
            }
        });
    }

    // Método para actualizar las opciones del ComboBox
    private void actualizarOpcionesComboBox(String textoBusqueda) {
        isUserTyping = false; // Evitar bucles de eventos
        titulosMap = new PrestamosModel().buscarTitulosDocumentos(textoBusqueda); // Buscar títulos

        // Actualizar las opciones del ComboBox
        ObservableList<String> opciones = FXCollections.observableArrayList(titulosMap.keySet());
        cbbuscar.setItems(opciones);

        // Restaurar el texto actual mientras se actualizan las opciones
        cbbuscar.getEditor().setText(textoBusqueda);
        cbbuscar.getEditor().positionCaret(textoBusqueda.length()); // Mover el cursor al final
        cbbuscar.hide();
        cbbuscar.show();

        isUserTyping = true; // Habilitar interacción del usuario
    }

    // Método para actualizar la base de datos con el estado del préstamo
    private void actualizarBaseDatos(Prestamo prestamo) {
        PrestamosModel modelo = new PrestamosModel();
        modelo.actualizarEstadoPrestamo(prestamo); // Método para actualizar en la base de datos
    }

    // Método para mostrar un archivo PDF en un contenedor de imágenes
    private void mostrarArchivoPDF(File archivo, VBox contenedorImagenes) {
        contenedorImagenes.getChildren().clear();
        try (PDDocument documento = Loader.loadPDF(archivo)) {
            PDFRenderer renderer = new PDFRenderer(documento);
            for (int i = 0; i < documento.getNumberOfPages(); i++) {
                Image imagen = SwingFXUtils.toFXImage(renderer.renderImageWithDPI(i, 150), null);
                ImageView vistaImagen = new ImageView(imagen);
                contenedorImagenes.getChildren().add(vistaImagen);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            mostrarAlertaError("Error al mostrar el archivo PDF", ex.getMessage());
        }
    }

// Métodos para mostrar alertas
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void abrirDialogoReportes(MouseEvent event) {
        // Crear el contenido del diálogo
        StackPane stackPane = (StackPane) base.getScene().getRoot();
        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new javafx.scene.text.Text("Generar Reportes de Préstamos"));
        contenido.setPrefWidth(1000);
        contenido.setPrefHeight(600);

        // Botones para seleccionar tipo de reporte
        JFXButton btnDiario = new JFXButton("Reporte Diario");
        JFXButton btnMensual = new JFXButton("Reporte Mensual");
        JFXButton btnRangoFechas = new JFXButton("Rango de Fechas");

        // ScrollPane y contenedor de imágenes
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //labels
        Label mensajefecha = new Label("Escoger Fecha");
        mensajefecha.setFont(Font.font("Open Sans", 16));

        Label labelMes = new Label("Mes");
        labelMes.setFont(Font.font("Open Sans", 16));

        Label labelMes2 = new Label("Mes");
        labelMes.setFont(Font.font("Open Sans", 16));

        Label labelMes3 = new Label("Mes");
        labelMes.setFont(Font.font("Open Sans", 16));

        Label labelGuion = new Label("-");
        labelGuion.setFont(Font.font("Open Sans", 16));

        Label labelGuion2 = new Label("-");
        labelGuion2.setFont(Font.font("Open Sans", 16));

        Label labelGuion3 = new Label("-");
        labelGuion3.setFont(Font.font("Open Sans", 16));

        Label labelAnio = new Label("Año");
        labelAnio.setFont(Font.font("Open Sans", 16));

        Label labelAnio2 = new Label("Año");
        labelAnio.setFont(Font.font("Open Sans", 16));

        Label labelAnio3 = new Label("Año");
        labelAnio.setFont(Font.font("Open Sans", 16));

        Label labelTitulo = new Label("Elegir Mes y Año del Reporte");
        labelTitulo.setFont(Font.font("Open Sans", 16));

        Label labelDesde = new Label("Desde:");
        labelDesde.setFont(Font.font("Open Sans", 16));

        Label labelInicio = new Label("Elegir Fecha Inicio");
        labelInicio.setFont(Font.font("Open Sans", 16));

        Label labelHasta = new Label("Hasta: ");
        labelHasta.setFont(Font.font("Open Sans", 16));

        Label labelFin = new Label("Elegir Fecha Fin");
        labelFin.setFont(Font.font("Open Sans", 16));

        //vbox
        VBox contenedorImagenes = new VBox(10);
        contenedorImagenes.setAlignment(Pos.CENTER);
        scrollPane.setContent(contenedorImagenes);

        //hbox
        HBox hbox = new HBox(10); // 10 es el espacio entre los botones
        HBox Hboxcampos = new HBox(10);
        HBox.setMargin(Hboxcampos, new Insets(30, 0, 0, 0));
        Hboxcampos.setPadding(new Insets(20, 0, 0, 0));
        Hboxcampos.setAlignment(Pos.CENTER);
        Hboxcampos.setVisible(false);

        // DatePickers y ComboBoxes
        DatePicker datePickerDiario = new DatePicker();

        comboBoxMes = new JFXComboBox<>();
        comboBoxAnio = new JFXComboBox<>();
        comboBoxMes2 = new JFXComboBox<>();
        comboBoxAnio2 = new JFXComboBox<>();
        comboBoxMesFin = new JFXComboBox<>();
        comboBoxAnioFin = new JFXComboBox<>();

        int anioActual = LocalDate.now().getYear();
        // Inicializar ComboBoxes con valores de ejemplo
        for (int i = 1; i <= 12; i++) {
            comboBoxMes.getItems().add(i);
            comboBoxMes2.getItems().add(i);
            comboBoxMesFin.getItems().add(i);
        }
        for (int i = anioActual; i >= anioActual - 10; i--) {
            comboBoxAnio.getItems().add(i);
            comboBoxAnio2.getItems().add(i);
            comboBoxAnioFin.getItems().add(i);
        }

        // Llenar Vbox para hboxcontenido
        HBox hBoxInicio = new HBox(10); // Espaciado entre elementos
        hBoxInicio.setAlignment(Pos.CENTER_LEFT);
        //hBoxInicio.setPadding(new Insets(10));
        hBoxInicio.getChildren().addAll(labelDesde, labelMes2, comboBoxMes2, labelGuion2, labelAnio2, comboBoxAnio2, labelInicio);

        HBox hBoxFin = new HBox(10);
        hBoxFin.setAlignment(Pos.CENTER_LEFT);
        //hBoxFin.setPadding(new Insets(10));
        hBoxFin.getChildren().addAll(labelHasta, labelMes3, comboBoxMesFin, labelGuion3, labelAnio3, comboBoxAnioFin, labelFin);

        // Configurar VBox para contener los HBox
        VBox vboxinterna = new VBox(15); // Espaciado entre las filas
        vboxinterna.setAlignment(Pos.CENTER);
        //vboxinterna.setPadding(new Insets(15));
        vboxinterna.getChildren().addAll(hBoxInicio, hBoxFin);
        // Configurar visibilidad de componentes según selección
        datePickerDiario.setVisible(false);
        comboBoxMes.setVisible(false);
        comboBoxMesFin.setVisible(false);

        btnDiario.setOnAction(e -> {
            Hboxcampos.getChildren().clear();
            Hboxcampos.setPadding(new Insets(20, 0, 0, 0));
            Hboxcampos.setAlignment(Pos.CENTER);
            datePickerDiario.setVisible(true);
            comboBoxMes.setVisible(false);
            comboBoxMesFin.setVisible(false);
            Hboxcampos.getChildren().addAll(datePickerDiario, mensajefecha);
            Hboxcampos.setVisible(true);

        });

        btnMensual.setOnAction(e -> {
            Hboxcampos.getChildren().clear();
            Hboxcampos.setPadding(new Insets(20, 0, 0, 0));
            Hboxcampos.setAlignment(Pos.CENTER);
            datePickerDiario.setVisible(false);
            comboBoxMes.setVisible(true);
            comboBoxMesFin.setVisible(false);
            Hboxcampos.getChildren().addAll(labelMes, comboBoxMes, labelGuion, labelAnio, comboBoxAnio, labelTitulo);
            Hboxcampos.setVisible(true);

        });

        btnRangoFechas.setOnAction(e -> {
            Hboxcampos.getChildren().clear();
            Hboxcampos.setPadding(new Insets(20, 0, 0, 0));
            Hboxcampos.setAlignment(Pos.CENTER);
            datePickerDiario.setVisible(false);
            comboBoxMes.setVisible(false);
            comboBoxMesFin.setVisible(true);
            Hboxcampos.getChildren().addAll(vboxinterna);
            Hboxcampos.setVisible(true);

        });

        // Botón para generar reporte
        btnDiario.setStyle(roundedBorderStyle);
        btnMensual.setStyle(roundedBorderStyle);
        btnRangoFechas.setStyle(roundedBorderStyle);
        JFXButton btnGenerarReporte = new JFXButton("Generar Reporte");
        btnGenerarReporte.setStyle(roundedBorderStyle);
        JFXButton btnGuardar = new JFXButton("Guardar Reporte");
        btnGuardar.setStyle(roundedBorderStyle);

        hbox.getChildren().addAll(btnDiario, btnMensual, btnRangoFechas);

        // Configurar el contenido del diálogo
        contenido.setBody(new VBox(10, hbox, Hboxcampos, btnGenerarReporte, scrollPane, btnGuardar));

        // Acción del botón Generar Reporte
        btnGenerarReporte.setOnAction(e -> {
            try {
                reporteTemporal = null;
                if (datePickerDiario.isVisible() && datePickerDiario.getValue() != null) {
                    // Opción por día específico
                    LocalDate fechaSeleccionada = datePickerDiario.getValue();
                    Date fecha = Date.valueOf(fechaSeleccionada);
                    List<String[]> datos = modelopdf.buscarDatosPrestamo(fecha, fecha, "porDia");
                    reporteTemporal = modelopdf.generarReporteTemporal("Reporte Diario", "Fecha: " + fechaSeleccionada, datos);
                    datePickerDiario.setValue(null);
                } else if (comboBoxMes.isVisible() && comboBoxAnio.getValue() != null) {
                    // Opción por mes y año
                    int mes = comboBoxMes.getSelectionModel().getSelectedItem();
                    int anio = comboBoxAnio.getSelectionModel().getSelectedItem();
                    LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
                    LocalDate fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
                    Date fechaInicioDate = Date.valueOf(fechaInicio);
                    Date fechaFinDate = Date.valueOf(fechaFin);
                    List<String[]> datos = modelopdf.buscarDatosPrestamo(fechaInicioDate, fechaFinDate, "porMesAnio");
                    reporteTemporal = modelopdf.generarReporteTemporal("Reporte Mensual", "Mes: " + mes + " Año: " + anio, datos);
                    comboBoxMes.setValue(null);
                    comboBoxAnio.setValue(null);
                } else if (comboBoxMesFin.isVisible() && comboBoxAnioFin.getValue() != null) {
                    // Opción por rango de fechas
                    int mesInicio = comboBoxMes2.getSelectionModel().getSelectedItem();
                    int anioInicio = comboBoxAnio2.getSelectionModel().getSelectedItem();
                    int mesFin = comboBoxMesFin.getSelectionModel().getSelectedItem();
                    int anioFin = comboBoxAnioFin.getSelectionModel().getSelectedItem();
                    LocalDate fechaInicio = LocalDate.of(anioInicio, mesInicio, 1);
                    LocalDate fechaFin = LocalDate.of(anioFin, mesFin, fechaInicio.lengthOfMonth());
                    Date fechaInicioDate = Date.valueOf(fechaInicio);
                    Date fechaFinDate = Date.valueOf(fechaFin);
                    List<String[]> datos = modelopdf.buscarDatosPrestamo(fechaInicioDate, fechaFinDate, "porRangoMesAnio");
                    reporteTemporal = modelopdf.generarReporteTemporal("Reporte por Rango de Fechas", "De " + fechaInicio + " a " + fechaFin, datos);
                    comboBoxMes2.setValue(null);
                    comboBoxAnio2.setValue(null);
                    comboBoxMesFin.setValue(null);
                    comboBoxAnioFin.setValue(null);
                } else {
                    // Mensaje de advertencia si no se han seleccionado fechas
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("RANGO NO VÁLIDO");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, seleccione un rango de fechas válido.");
                    alert.showAndWait();
                    return;
                }

                // Mostrar las imágenes del archivo PDF en el diálogo
                if (reporteTemporal != null) {
                    registmodel regi = new registmodel();
                    contenedorImagenes.getChildren().clear();
                    System.out.println("Se limpio el contenedor");
                    regi.cargarPaginasComoImagenes(reporteTemporal, 1, contarPaginas(reporteTemporal), contenedorImagenes);
                    //mostrarArchivoPDF(reporteTemporal, contenedorImagenes);
                    //scrollPane.setContent(contenedorImagenes);

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Botón para guardar el archivo PDF
        btnGuardar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            fileChooser.setTitle("Guardar Reporte PDF");

            File archivoSeleccionado = fileChooser.showSaveDialog(null);
            if (archivoSeleccionado != null) {
                if (!archivoSeleccionado.getName().endsWith(".pdf")) {
                    archivoSeleccionado = new File(archivoSeleccionado.getAbsolutePath() + ".pdf");
                }

                try {
                    Files.copy(reporteTemporal.toPath(), archivoSeleccionado.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Archivo guardado correctamente en: " + archivoSeleccionado.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Mostrar el diálogo
        JFXDialog dialogo = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialogo.show();
    }

    private int contarPaginas(File archivoPdf) throws IOException {
        try (PDDocument documento = Loader.loadPDF(archivoPdf)) {
            return documento.getNumberOfPages();
        }
    }

    @FXML
    public void cargarprestamo(Documento docu) {
        cbbuscar.setValue(docu.getTitulo());
        txtIdDocumento.setText(String.valueOf(docu.getId_documento())); // Mostrar ID
        txtTipoDocumento.setText(docu.getTipo()); // Mostrar Tipo de Documento
        PrestamosModel.establecerFechaActual(textfecha);

    }
}
