/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtuser;
    @FXML
    private PasswordField txtpass;
    @FXML
    private Button blogin;

    @FXML
    public void closewin(ActionEvent e) {
        Node n = (Node) e.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showStage(String dirstage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dirstage));
            Parent root = loader.load();

            // Obtén una instancia del controlador de InicioController
            InicioController inicioController = loader.getController();

            // Crea y configura el Stage
            Stage stage = new Stage();
            stage.setTitle("SEGUNDA PANTALLA WUUUU");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            // Pasa el Stage al controlador de InicioController
            inicioController.setStage(stage);

            stage.show();

            new FadeIn(root).play();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void CULO(ActionEvent event) {
        String user, pass;
        user = this.txtuser.getText().trim();
        pass = this.txtpass.getText().trim();

        if (user.equals("Victor") && pass.equals("12345")) {
            JOptionPane.showMessageDialog(null, "Inicio de sesion Correcto", "Trifasico", JOptionPane.INFORMATION_MESSAGE);
            closewin(event);
            showStage("/vistas/inicio.fxml");
            

        } else {
            JOptionPane.showMessageDialog(null, "Tas pelando bola papito");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
