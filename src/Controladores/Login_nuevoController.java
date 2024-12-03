/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.sql.*;
import Modelos.Conexion;
import Modelos.StageMovement;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.SlideInUp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class Login_nuevoController implements Initializable {

    private Conexion cn = new Conexion();
    private StageMovement stmodel = new StageMovement();
    /**
     * Initializes the controller class.
     */
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    ImageView logopsm;
    @FXML
    ImageView logobanda;
    @FXML
    JFXButton button;
    @FXML
    Pane panelogo;
    @FXML
    JFXButton buton2;
    @FXML
    JFXTextField txtuser;
    @FXML
    JFXPasswordField txtpass;

    @FXML
    public void closewin(ActionEvent e) {
        Node n = (Node) e.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showStage(String dirstage, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dirstage));
            Parent root = loader.load();

            // Obtén una instancia del controlador de InicioController
            InicioController inicioController = loader.getController();

            // Crea y configura el Stage
            Stage stage = new Stage();
            stage.setTitle(titulo);
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

    @FXML
    public void PantallaInicio(ActionEvent e) throws InterruptedException {
        String sql, contradb = "";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        
        try {

            java.sql.Connection conexion = cn.conectar();

            if (txtuser.getText().isBlank()) {
                alert1.setHeaderText(null);
                alert1.setTitle("Rellene el Campo");
                alert1.setContentText("El usuario es requerido");
                alert1.showAndWait();
            } else if (txtpass.getText().isBlank()) {
                alert1.setHeaderText(null);
                alert1.setTitle("Rellene el Campo");
                alert1.setContentText("La contraseña es requerida");
                alert1.showAndWait();
            } else {
                String usuario = txtuser.getText().trim();
                String contra = txtpass.getText().trim();

                sql = "SELECT * FROM user WHERE username=?";
                PreparedStatement pst = conexion.prepareStatement(sql);
                pst.setString(1, usuario);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    contradb = rs.getString("password");
                    if (contra.equals(contradb)) {

                        stmodel.closewin(e);
                        stmodel.showStage("/Vistas/inicio.fxml", "Inicio");
                    } else {
                        
                        alert.setHeaderText(null);
                        alert.setTitle("Datos de Inicio Incorrectos");
                        alert.setContentText("Contraseña Incorrecta");
                        txtpass.setText("");
                        alert.showAndWait();
                    }
                } else {
                    alert.setHeaderText(null);
                    alert.setTitle("Datos de Inicio Incorrectos");
                    alert.setContentText("Usuario No Existente");
                    txtpass.setText("");
                    txtuser.setText("");
                    alert.showAndWait();
                }
            }
        } catch (HeadlessException | SQLException err) {
            JOptionPane.showMessageDialog(null, "error" + err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    public void animacionlogo() {
        new Flash(panelogo).play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
