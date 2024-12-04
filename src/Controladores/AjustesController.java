/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.FormateadorTexto;
import Modelos.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JPasswordField;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class AjustesController implements Initializable {

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
    private JFXTextField visibleText1;

    @FXML
    private JFXTextField visibleText2;
    @FXML
    private JFXButton btnvolver;
    @FXML
    private JFXTextField textuser1;

    @FXML
    private JFXPasswordField textpass1;

    @FXML
    private JFXTextField textpregunta;

    @FXML
    private JFXTextField textuser2;

    @FXML
    private JFXPasswordField textpass2;

    @FXML
    private JFXTextField textrespuesta;
    @FXML
    private ImageView imgeye;
    private boolean isPasswordVisible = false;

    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpage("Inipro", viewp);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FormateadorTexto.limitarCantidadCaracteres(textuser1, 100);
        FormateadorTexto.limitarCantidadCaracteres(textuser2, 100);
        FormateadorTexto.limitarCantidadCaracteresPass(textpass1, 100);
        FormateadorTexto.limitarCantidadCaracteresPass(textpass2, 100);
        FormateadorTexto.limitarCantidadCaracteres(textpregunta, 50);
        FormateadorTexto.limitarCantidadCaracteres(textrespuesta, 50);
    }

    @FXML
    public void actualizardatos(MouseEvent e) {
        new Usuario().actualizarUsuario(textuser1, textuser2, textpass1, textpass2, textpregunta, textrespuesta);
    }

    @FXML
    public void mostrarpass(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            System.out.println("mostrar");
            imgeye.setImage(new Image("/img/eye.png"));
            alternarVisibilidad(true);
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            System.out.println("ocultar");
            imgeye.setImage(new Image("/img/hidden.png"));
            alternarVisibilidad(false);
        }
    }

    private void alternarVisibilidad(boolean mostrar) {
        if (mostrar) {
            // Copiar texto del JFXPasswordField al JFXTextField
            visibleText1.setText(textpass1.getText());
            visibleText2.setText(textpass2.getText());

            // Mostrar los campos visibles y ocultar los de contraseña
            visibleText1.setVisible(true);
            visibleText2.setVisible(true);
            textpass1.setVisible(false);
            textpass2.setVisible(false);
        } else {
            // Restaurar texto en los JFXPasswordField
            textpass1.setText(visibleText1.getText());
            textpass2.setText(visibleText2.getText());

            // Ocultar los campos visibles y mostrar los de contraseña
            visibleText1.setVisible(false);
            visibleText2.setVisible(false);
            textpass1.setVisible(true);
            textpass2.setVisible(true);
        }
    }
}
