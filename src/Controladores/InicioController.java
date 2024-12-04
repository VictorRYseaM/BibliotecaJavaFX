/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.Buscarmodel;
import Modelos.DepuracionModel;
import Modelos.PrestamosModel;
import Modelos.registmodel;
import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.Flip;
import animatefx.animation.SlideInUp;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.lang.foreign.SymbolLookup;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class InicioController implements Initializable {

    private double xoffset = 0;
    private double yoffset = 0;
    private Stage stage;
    private StageMovement stmodel = new StageMovement();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane baseventana; //la primera capa
    @FXML
    private ImageView closebutton;
    @FXML
    private ImageView minbutton;
    @FXML
    private ImageView maxbutton;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane1;
    @FXML
    private ImageView menuejem;
    @FXML
    private StackPane stackpantalla;
    @FXML
    private JFXButton btnajustes;
    @FXML
    private JFXButton btnregistrar;
    @FXML
    private AnchorPane viewpane;
    @FXML
    private JFXButton btnbuscar;

    @FXML
    private JFXButton btnprestamos;

    @FXML
    private JFXButton btndepuracion;

    private boolean isTransitioning = false;

    private AnchorPane viewp;

    private Ajustesmodel ajmodel = new Ajustesmodel();
    private registmodel rgmodel = new registmodel();
    private Buscarmodel bsmodel = new Buscarmodel();
    private PrestamosModel prmodel = new PrestamosModel();
    private DepuracionModel dpmodel = new DepuracionModel();

    private double originalWidth;
    private double originalHeight;

    //@FXML
    //private ImageView imagen;
    @FXML
    public void setviewpane() {

        this.viewp = viewpane;
    }

    @FXML
    public void closewin(MouseEvent e) {
        //Node n = (Node) e.getSource(); aqui no es necesario de momento
        //Stage stage = (Stage) n.getScene().getWindow();

        //baseventana.getScene().getWindow().hide(); // Oculta la ventana
        //Platform.exit(); // Cierra completamente la aplicación
        // Crear el cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Seguro que desea cerrar sesión?");

        // Agregar los botones de "Aceptar" y "Cancelar"
        ButtonType botonAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(respuesta -> {
            if (respuesta == botonAceptar) {
                // Cambiar de vista si el usuario confirma
                login();
            } else {
                // Mensaje opcional al cancelar
                System.out.println("El usuario canceló el cambio de vista.");
            }
        });

    }

    @FXML
    void login() {
        try {
            // Cargar la vista de login desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Login_nuevo.fxml"));
            Parent root = loader.load();

            // Crear y mostrar la nueva ventana (Stage)
            Stage loginStage = new Stage();
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo psm dorado sin fondo.png")));
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");

            // Mostrar la nueva ventana
            loginStage.show();

            // Cerrar la ventana actual si es necesario
            Stage currentStage = (Stage) menuejem.getScene().getWindow();
            currentStage.close();
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pagajustes(MouseEvent e) {

        ajmodel.loadpage("ajustes", viewpane);
    }

    public void pagregistrar(MouseEvent e) {
        rgmodel.loadpage("registrar", viewpane);
    }

    public void pagbuscar(MouseEvent e) {
        bsmodel.loadpage("buscar", viewpane);
    }

    public void pagprestamos(MouseEvent e) {
        prmodel.loadpage("Prestamooficial", viewpane);
    }

    public void pagdepuracion(MouseEvent e) {
        dpmodel.loadpage("Depuracionoficial", viewpane);
    }

    @FXML
    public void minwin(MouseEvent e) {
        // Obtén la ventana actual y minimízala
        Stage stage = (Stage) baseventana.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maxwin(MouseEvent e) { //Metodo actualizado para centrar
        //Stage stage = (Stage) baseventana.getScene().getWindow();
        // Alterna entre pantalla completa y modo ventana
        //stage.setFullScreen(!stage.isFullScreen());

        Stage stage = (Stage) menuejem.getScene().getWindow();

        // Obtén las dimensiones de la pantalla principal
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Calcula la posición central para la ventana original
        double centerX = (bounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (bounds.getHeight() - stage.getHeight()) / 2;

        // Restaura la posición original
        stage.setX(centerX);
        stage.setY(centerY);
    }

    /*@FXML
    public void imagenanim() {
        imagen.setVisible(false);
        // Crea una pausa de 3 segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(2));

        // Después de la pausa, ejecuta la animación
        delay.setOnFinished(event -> {
            imagen.setVisible(true);
            new SlideInUp(imagen).play();
        });

        // Inicia la pausa
        delay.play();

    }
     */
    /**
     * Initializes the controller class.
     */
    // Método para inicializar el Stage
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Posicion original de la ventana

        //
        setviewpane();
        stmodel.loadpage("Inipro", viewpane);
        // Configurar el evento de arrastre
        baseventana.setOnMousePressed((MouseEvent event) -> {
            xoffset = event.getSceneX();
            yoffset = event.getSceneY();
        });

        baseventana.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xoffset);
            stage.setY(event.getScreenY() - yoffset);
        });

        pane1.setVisible(false);

        // Transición inicial
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menuejem.setOnMouseClicked(event -> {
            if (!isTransitioning) {
                isTransitioning = true; // Bloquea clics adicionales durante la transición

                pane1.setVisible(true);

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), pane1);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(+600);
                translateTransition1.play();
                new BounceIn(menuejem).play();

                // Restablece la interacción cuando ambas transiciones terminen
                fadeTransition1.setOnFinished(e -> isTransitioning = false);
            }
        });

        pane1.setOnMouseClicked(event -> {
            if (!isTransitioning) {
                isTransitioning = true; // Bloquea clics adicionales durante la transición

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeTransition1.setFromValue(0.15);
                fadeTransition1.setToValue(0);
                fadeTransition1.play();

                fadeTransition1.setOnFinished(event1 -> {
                    pane1.setVisible(false);
                    isTransitioning = false; // Permite nuevamente la interacción
                });

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(-600);
                translateTransition1.play();
            }
        });

    }
}
