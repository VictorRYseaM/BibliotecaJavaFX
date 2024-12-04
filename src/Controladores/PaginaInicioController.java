/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUp;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author VictorY
 */
public class PaginaInicioController implements Initializable {

    @FXML
    private VBox vboxestudiantes;

    @FXML
    private JFXButton btningresar;

    @FXML
    private Pane panelogo;

    @FXML
    private ImageView logopsm;

    @FXML
    private ImageView logobanda;

    @FXML
    private VBox vboxadmin;

    @FXML
    private JFXButton btningresar1;

    @FXML
    private Label labelbienvenida;

    @FXML
    private Label texttipo;

    @FXML
    private ImageView iconbook;

    @FXML
    private Line lineabienvenida;

    private final StageMovement stmodel = new StageMovement();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar la visibilidad inicial para que est�n ocultos y preparados para la animaci�n
        labelbienvenida.setOpacity(0);
        iconbook.setOpacity(0);
        lineabienvenida.setOpacity(0);
        texttipo.setOpacity(0);
        panelogo.setOpacity(0);
        vboxadmin.setOpacity(0);
        vboxestudiantes.setOpacity(0);

        // Crear un timeline para controlar el orden de las animaciones
        Timeline timeline = new Timeline();

        // Configurar las animaciones con la secuencia
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), event -> {
            new FadeIn(labelbienvenida).play();
        });
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), event -> {
            new FadeInUp(iconbook).play();
        });
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1.5), event -> {
            new FadeIn(lineabienvenida).play();
        });
        KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(2), event -> {
            new FadeInUp(texttipo).play();
        });
        KeyFrame keyFrame5 = new KeyFrame(Duration.seconds(2.5), event -> {
            new BounceIn(panelogo).play();
        });
        KeyFrame keyFrame6 = new KeyFrame(Duration.seconds(3), event -> {
            new FadeIn(vboxadmin).play();
        });
        KeyFrame keyFrame7 = new KeyFrame(Duration.seconds(3.5), event -> {
            new FadeIn(vboxestudiantes).play();
        });

        // A�adir los keyframes al timeline
        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5, keyFrame6, keyFrame7);

        // Iniciar la animaci�n
        timeline.play();
    }

    @FXML
    void estudiantes(ActionEvent event) {
        // Crear el cuadro de di�logo de confirmaci�n
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmaci�n");
        alert.setHeaderText("�Seguro que desea proceder?");
        alert.setContentText("No podr� volver a la pantalla de inicio por motivos de seguridad y tendr� que reiniciar el programa.\n"
                + "Para los evaluadores del programa, se recomienda abrir primero la vista de administraci�n.");

        // Agregar los botones de "Aceptar" y "Cancelar"
        ButtonType botonAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(respuesta -> {
            if (respuesta == botonAceptar) {
                // Cambiar de vista si el usuario confirma
                cargarventanas("/Vistas/estudiantes.fxml", "�Realiza tu b�squeda!");
            } else {
                // Mensaje opcional al cancelar
                System.out.println("El usuario cancel� el cambio de vista.");
            }
        });
    }

    @FXML
    void login(ActionEvent event) {
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
            Stage currentStage = (Stage) btningresar.getScene().getWindow();
            currentStage.close();
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cargarventanas(String dir, String Titulo) {
        try {
            // Cargar la vista de login desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dir));
            Parent root = loader.load();

            // Crear y mostrar la nueva ventana (Stage)
            Stage loginStage = new Stage();
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo psm dorado sin fondo.png")));
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root));
            loginStage.setTitle(Titulo);

            // Mostrar la nueva ventana
            loginStage.show();

            // Cerrar la ventana actual si es necesario
            Stage currentStage = (Stage) btningresar.getScene().getWindow();
            currentStage.close();
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
