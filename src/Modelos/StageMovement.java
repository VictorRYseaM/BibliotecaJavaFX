/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.InicioController;
import Controladores.LoginController;
import animatefx.animation.FadeIn;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author VictorY
 */
public class StageMovement {

    @FXML
    public void closewin(ActionEvent e) {
        Node n = (Node) e.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";
        pag += page;
        
        try {
            root = FXMLLoader.load(getClass().getResource(pag + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();
        
    }

    @FXML
    public void showStage(String dirstage, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(dirstage)); //permite obtener la vista
            Parent root = loader.load(); //carga la vista en un parent

            InicioController inicioController = loader.getController(); //Se carga de el controlador de la vista

            // Crea y configura el Stage contenedor de nivel superior que contiene escenas
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root)); // se le asigna una escena a un stage
            stage.setResizable(true);

            // Pasa el Stage al controlador de InicioController
            inicioController.setStage(stage);

            stage.show();

            new FadeIn(root).play();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}