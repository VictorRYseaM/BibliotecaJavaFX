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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

}
