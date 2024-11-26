/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.registmodel;
import animatefx.animation.BounceIn;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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

        baseventana.getScene().getWindow().hide(); // Oculta la ventana
        Platform.exit(); // Cierra completamente la aplicaci�n

    }

    public void pagajustes(MouseEvent e) {

        ajmodel.loadpage("ajustes", viewpane);
    }

    public void pagregistrar(MouseEvent e) {
        rgmodel.loadpage("registrar", viewpane);
    }

    public void pagbuscar(MouseEvent e) {
        stmodel.loadpage("buscar", viewpane);
    }

    public void pagprestamos(MouseEvent e) {
        stmodel.loadpage("prestamos", viewpane);
    }

    public void pagdepuracion(MouseEvent e) {
        stmodel.loadpage("depuracion", viewpane);
    }

    @FXML
    public void minwin(MouseEvent e) {
        // Obt�n la ventana actual y minim�zala
        Stage stage = (Stage) baseventana.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maxwin(MouseEvent e) {
        Stage stage = (Stage) baseventana.getScene().getWindow();
        // Alterna entre pantalla completa y modo ventana
        stage.setFullScreen(!stage.isFullScreen());
    }

    /*@FXML
    public void imagenanim() {
        imagen.setVisible(false);
        // Crea una pausa de 3 segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(2));

        // Despu�s de la pausa, ejecuta la animaci�n
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
    // M�todo para inicializar el Stage
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

        // Transici�n inicial
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menuejem.setOnMouseClicked(event -> {
            if (!isTransitioning) {
                isTransitioning = true; // Bloquea clics adicionales durante la transici�n

                pane1.setVisible(true);

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), pane1);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(+600);
                translateTransition1.play();
                new BounceIn(menuejem).play();

                // Restablece la interacci�n cuando ambas transiciones terminen
                fadeTransition1.setOnFinished(e -> isTransitioning = false);
            }
        });

        pane1.setOnMouseClicked(event -> {
            if (!isTransitioning) {
                isTransitioning = true; // Bloquea clics adicionales durante la transici�n

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeTransition1.setFromValue(0.15);
                fadeTransition1.setToValue(0);
                fadeTransition1.play();

                fadeTransition1.setOnFinished(event1 -> {
                    pane1.setVisible(false);
                    isTransitioning = false; // Permite nuevamente la interacci�n
                });

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(-600);
                translateTransition1.play();
            }
        });

    }
}