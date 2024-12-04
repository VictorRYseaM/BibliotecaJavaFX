/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.sql.*;
import Modelos.Conexion;
import Modelos.StageMovement;
import Modelos.Usuario;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    @FXML
    private JFXButton btnolvidado;
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
    private JFXTextField visibleText1;
    @FXML
    private JFXTextField visibleText2;
    @FXML
    private JFXButton btningresar;

    @FXML
    private VBox vboxpregunta;

    @FXML
    private JFXTextField textpregunta;

    @FXML
    private JFXTextField textrespuesta;

    @FXML
    private JFXButton btnrespuesta;
    @FXML
    private JFXButton btninicio;

    @FXML
    private JFXButton btnmeacorde;

    @FXML
    private VBox vboxnuevouser;
    @FXML
    private VBox vboxingreso;

    @FXML
    private JFXTextField textnuevouser;

    @FXML
    private JFXPasswordField textnuevopass1;

    @FXML
    private JFXPasswordField textnuevopass2;

    @FXML
    private JFXButton btncambiarusuario;

    @FXML
    private JFXButton btnmeacorde2;

    @FXML
    private ImageView imgeye;

    final private Usuario usr = new Usuario();

    @FXML
    public void closewin(ActionEvent e) {
        Node n = (Node) e.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void showStage(String dirstage, String titulo) {
        try {
            // Carga el archivo FXML y obtiene el controlador genérico
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dirstage));
            Parent root = loader.load();
            Object controller = loader.getController(); // Instancia genérica del controlador

            // Crea y configura el Stage
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            // Verifica el tipo de controlador para acciones específicas
            if (controller instanceof InicioController) {
                stage.initStyle(StageStyle.UNDECORATED);
                ((InicioController) controller).setStage(stage); // Acciones específicas para InicioController
            }

            Stage currentStage = (Stage) btningresar.getScene().getWindow();
            currentStage.close();

            stage.show();

            // Animación de entrada
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

    /*
    @FXML
    public void programadormimido(MouseEvent e) {
        // Crear el alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mensaje del Programador");
        alert.setHeaderText(null);

        // Crear un HBox para contener la imagen y el texto
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10); // Espacio entre la imagen y el texto

        // Cargar la imagen desde recursos
        Image image = new Image(getClass().getResourceAsStream("/img/gatotriste.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Ajusta el tamaño de la imagen
        imageView.setFitHeight(200);

        // Crear un label con el mensaje
        Label messageLabel = new Label("El programador está cansado ;(");
        messageLabel.setStyle("-fx-font-family: 'Open Sans'; -fx-font-size: 20px;");

        // Agregar la imagen y el label al HBox
        content.getChildren().addAll(imageView, messageLabel);

        // Establecer el contenido personalizado del alert
        alert.getDialogPane().setContent(content);

        // Mostrar el alert
        alert.showAndWait();
    }*/
    @FXML
    public void olvidado(ActionEvent e) {
        vboxingreso.setVisible(false);
        vboxpregunta.setOpacity(0);
        vboxpregunta.setVisible(true);
        textpregunta.clear();
        textrespuesta.clear();
        new FadeIn(vboxpregunta).play();
        usr.setpregunta(textpregunta);
    }

    @FXML
    public void responderpregunta(ActionEvent e) {
        if (usr.verificarRespuesta(textrespuesta)) {
            vboxpregunta.setVisible(false);
            vboxnuevouser.setOpacity(0);
            textnuevouser.clear();
            textnuevopass1.clear();
            textnuevopass2.clear();
            imgeye.setOpacity(0);
            imgeye.setVisible(true);
            vboxnuevouser.setVisible(true);
            new FadeIn(vboxnuevouser).play();
            new FadeIn(imgeye).play();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Esa no es la respuesta correcta.");
            alert.showAndWait();
            textrespuesta.clear();
        }

    }

    @FXML
    public void volverlogin(ActionEvent e) {
        vboxpregunta.setVisible(false);
        vboxnuevouser.setVisible(false);
        imgeye.setVisible(false);

        txtpass.clear();
        txtuser.clear();
        vboxingreso.setOpacity(0);
        vboxingreso.setVisible(true);
        new FadeIn(vboxingreso).play();
    }

    @FXML
    public void regnuevouser(ActionEvent e) {
        if (usr.actualizarUsuario(textnuevouser, textnuevopass1, textnuevopass2)) {
            volverlogin(e);
        }

    }

    @FXML
    public void irapaginicio(ActionEvent e) {
        showStage("/Vistas/paginadeinicioporquesi.fxml", "¡Bienvenido!");
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
            visibleText1.setText(textnuevopass1.getText());
            visibleText2.setText(textnuevopass2.getText());

            // Mostrar los campos visibles y ocultar los de contraseña
            visibleText1.setVisible(true);
            visibleText2.setVisible(true);
            textnuevopass1.setVisible(false);
            textnuevopass2.setVisible(false);
        } else {
            // Restaurar texto en los JFXPasswordField
            textnuevopass1.setText(visibleText1.getText());
            textnuevopass2.setText(visibleText2.getText());

            // Ocultar los campos visibles y mostrar los de contraseña
            visibleText1.setVisible(false);
            visibleText2.setVisible(false);
            textnuevopass1.setVisible(true);
            textnuevopass2.setVisible(true);
        }
    }
}
