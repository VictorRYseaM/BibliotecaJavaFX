/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private JFXButton btnvolver;
    
    public void setviewpane(AnchorPane viewpane){
        this.viewp=viewpane;
    }
    @FXML
    public void volver(MouseEvent e){
        stmodel.loadpage("Inipro", viewp);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
