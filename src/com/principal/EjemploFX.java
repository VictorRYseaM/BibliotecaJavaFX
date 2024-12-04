package com.principal;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author VictorY
 */
public class EjemploFX extends Application {

   
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/paginadeinicioporquesi.fxml"));

        primaryStage.setTitle("¡Bienvenido!");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo psm dorado sin fondo.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        
        new FadeIn(root).play();
        
        
        /*
        Tambien se pude hacer asi
        FXMLLoader fxmll = new FXMLLoader(EjemploFX.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmll.load(),600,400);
        primaryStage.setTitle("Epale");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        */
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
