/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.Ajustesmodel;
import Modelos.Conexion;
import Modelos.DepuracionModel;
import Modelos.DocumentoDepuracion;
import Modelos.StageMovement;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class DepuracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane viewp;
    private StageMovement stmodel = new StageMovement();

    @FXML
    private JFXButton btnguarcambios;
    @FXML
    private AnchorPane viewpane1;
    @FXML
    private ImageView logopsm;
    @FXML
    private JFXButton btnver;

    @FXML
    private JFXButton btndepurarsinmiedo;

    @FXML
    private ImageView logobanda;

    @FXML
    private JFXButton btnvolver;
    @FXML
    private TableView<DocumentoDepuracion> tableDepuracion;

    @FXML
    private TableColumn<DocumentoDepuracion, Integer> columnId;

    @FXML
    private TableColumn<DocumentoDepuracion, String> columnTitulo;

    @FXML
    private TableColumn<DocumentoDepuracion, LocalDate> columnFecha;

    @FXML
    private TableColumn<DocumentoDepuracion, String> columnTipo;

    @FXML
    private TableColumn<DocumentoDepuracion, Integer> columnAniosVejez;

    private final DepuracionModel dao = new DepuracionModel();
    private boolean datoscargados=false;
    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpage("Inipro", viewp);
    }

    @FXML
    public void verdep(MouseEvent e) {
        try {
            ObservableList<DocumentoDepuracion> documentos = dao.cargarDocumentos();
            if (documentos == null) {
                mostrarAlertaInfo("Atención", "No hay documentos para depurar.");
            } else {
                tableDepuracion.setItems(documentos);
                datoscargados=true;
            }

        } catch (SQLException f) {
            mostrarAlertaError("Error al cargar los documentos", f.getMessage());
        }
    }

    @FXML
    public void realizardepuracion(MouseEvent e) {
        // Crear una alerta de confirmación
        
        if (datoscargados) {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de Depuración");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar los documentos antiguos?");
            alert.setContentText("Esta acción no se puede deshacer.");

            // Mostrar la alerta y esperar la respuesta del usuario
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                try {
                    dao.eliminarDocumentosAntiguos(); // Llamar al método para eliminar documentos
                    datoscargados=false;
                    tableDepuracion.setItems(null);
                    mostrarAlertaInfo("Éxito", "Los documentos antiguos han sido eliminados correctamente.");
                    

                } catch (SQLException ex) {
                    mostrarAlertaError("Error", "Ocurrió un error al intentar eliminar los documentos. Por favor, inténtelo de nuevo.");

                }
            }
        }else{
            mostrarAlertaError("Atención", "No hay datos cargados, se recomienda cargarlos para visualizar las bajas del sistema.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Configurar las columnas de la tabla
        columnId.setCellValueFactory(new PropertyValueFactory<>("idDocumento"));
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacion"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnAniosVejez.setCellValueFactory(new PropertyValueFactory<>("aniosVejez"));
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
