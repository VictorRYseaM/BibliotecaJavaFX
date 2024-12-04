/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.BuscarController;
import Controladores.EstudiantesController;
import Controladores.InicioController;
import Controladores.LoginController;
import Controladores.Login_nuevoController;
import Controladores.RegistrarController;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    public void loadpagevolverabuscar(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            BuscarController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }
    public void loadpagevolverabuscarestudiantes(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            EstudiantesController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpagemodificarlibro(String page, AnchorPane viewpane, Libro lib) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            RegistrarController ac = load.getController();
            ac.setviewpane(viewpane);
            ac.setpanemodlibro(lib);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpagemodificartesis(String page, AnchorPane viewpane, TrabajoGrado tesis) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            RegistrarController ac = load.getController();
            ac.setviewpane(viewpane);
            ac.setpanemodtesis(tesis);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public void loadpagemodificarinforme(String page, AnchorPane viewpane, InformePasantia inf) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            RegistrarController ac = load.getController();
            ac.setviewpane(viewpane);
            ac.setpanemodinforme(inf);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo psm dorado sin fondo.png")));
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
