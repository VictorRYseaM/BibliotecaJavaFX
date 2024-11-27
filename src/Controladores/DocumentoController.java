/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class DocumentoController implements Initializable {

    /**
     * Initializes the controller class.
     *
     *
     */
    @FXML
    private ImageView imgv;

    @FXML
    private Label labelv;

    public void setData(String titulo, Image imagen) {
        labelv.setText(titulo);
        imgv.setImage(imagen);
    }

  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
