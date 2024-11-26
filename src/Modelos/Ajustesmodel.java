/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.AjustesController;
import Controladores.InicioController;
import animatefx.animation.FadeIn;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author VictorY
 */
public class Ajustesmodel {
    
    
    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";
        
        pag += page;

        try {
            FXMLLoader load =new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            AjustesController ac = load.getController();
            ac.setviewpane(viewpane);
            
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }
}
