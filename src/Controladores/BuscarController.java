/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.Buscarmodel;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import animatefx.animation.GlowBackground;
import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToolbar;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class BuscarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane panetoolbar;

    private HBox toolbarContent;
    private Buscarmodel filtroscb = new Buscarmodel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        crearBarraDeBusqueda();

    }

    private void crearBarraDeBusqueda() {
        // Crear JFXToolbar
        JFXToolbar toolbar = new JFXToolbar();

        // TextField para b�squeda
        TextField searchField = new TextField();
        searchField.setPromptText("Ingrese datos para realizar la busqueda");
        searchField.setPrefWidth(550); // Ancho
        searchField.setPrefHeight(40); // Altura
        searchField.setStyle("-fx-background-color: #032E67; "
                + "-fx-border-color: #FA731F; "
                + "-fx-border-width: 3px;"
                + "-fx-border-radius: 20; "
                + "-fx-background-radius: 20; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 16px; "
                + "-fx-font-family: 'Open Sans';");

        // Bot�n de b�squeda
        JFXButton searchButton = new JFXButton();
        searchButton.setStyle("-fx-background-color: #FA731F; "
                + "-fx-border-radius: 20; "
                + "-fx-background-radius: 20; ");
        searchButton.setPrefHeight(40);
        searchButton.setPrefWidth(50); // Ajusta el ancho

        try {
            // Cargar la imagen desde los recursos
            Image lupaImage = new Image(getClass().getResource("/img/buscarblanco.png").toExternalForm());
            ImageView lupaImageView = new ImageView(lupaImage);

            // Configurar el tama�o de la imagen
            lupaImageView.setFitHeight(20); // Ajusta la altura
            lupaImageView.setFitWidth(20);  // Ajusta el ancho
            lupaImageView.setPreserveRatio(true); // Mant�n la proporci�n

            // A�adir la imagen al bot�n
            searchButton.setGraphic(lupaImageView);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        // HBox para campo de b�squeda y bot�n
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(5); // Separaci�n m�nima entre el TextField y el bot�n

        // ComboBox para tipos de filtros
        JFXComboBox<String> pricingType = new JFXComboBox<>();
        pricingType.getItems().addAll("Todos", "Trabajos de Grado", "Informes de Pasantia", "Libros");
        pricingType.setValue("Todos");
        pricingType.setStyle("-fx-border-color: #FA731F; "
                + "-fx-background-color: white; "
                + "-fx-border-radius: 20; "
                + "-fx-background-radius: 20; "
                + "-fx-font-size: 16px; "
                + "-fx-font-family: 'Open Sans'; "
                + "-fx-text-fill: #032E67;");
        pricingType.setPrefHeight(40); // Altura similar al campo de texto
        pricingType.setPrefWidth(150); // Ancho del ComboBox

        // Crear HBox para los filtros adicionales
        HBox filtrosAdicionales = new HBox();
        filtrosAdicionales.setSpacing(10);
        filtrosAdicionales.setAlignment(Pos.CENTER_LEFT); // Alineaci�n de los filtros
        
        // Agregar un manejador de eventos para cuando se cambia la selecci�n del ComboBox
        pricingType.setOnAction(event -> {
            String tipoSeleccionado = pricingType.getValue();  // Obtiene el valor seleccionado
            // Limpiar los filtros anteriores
            filtrosAdicionales.getChildren().clear();
            // Crear filtros seg�n el tipo seleccionado
            crearFiltrosPorTipo(tipoSeleccionado, filtrosAdicionales);
            new FadeInRight(filtrosAdicionales).play();
        });

        // HBox para organizar el ComboBox con la barra de b�squeda
        toolbarContent = new HBox(searchBox, pricingType);
        toolbarContent.setAlignment(Pos.CENTER_LEFT); // Centrado del contenido
        toolbarContent.setSpacing(10); // Espacio reducido entre el TextField y el ComboBox
        toolbarContent.setPadding(new Insets(5)); // M�rgenes internos para mejor apariencia

        // Agregar HBox de filtros adicionales al toolbarContent
        toolbarContent.getChildren().add(filtrosAdicionales);

        // Agregar contenido al JFXToolbar
        toolbar.setCenter(toolbarContent);

        // Agregar JFXToolbar al AnchorPane
        panetoolbar.getChildren().clear(); // Limpiar cualquier contenido previo
        panetoolbar.getChildren().add(toolbar);

        // Ajustar la posici�n del toolbar en el AnchorPane
        AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);
        AnchorPane.setBottomAnchor(toolbar, 0.0); // Ojo con esto, si no quieres que se pegue al fondo
    }

    private void crearFiltrosPorTipo(String tipo, HBox filtrosAdicionales) {
        if (tipo.equals("Trabajos de Grado") || tipo.equals("Informes de Pasantia")) {
            // Filtro de A�o
            JFXComboBox<Integer> yearFilter = new JFXComboBox<>();
            yearFilter.setStyle("-fx-border-color: #FA731F; "
                    + "-fx-background-color: white; "
                    + "-fx-border-radius: 20; "
                    + "-fx-background-radius: 20; "
                    + "-fx-font-size: 16px; "
                    + "-fx-font-family: 'Open Sans'; "
                    + "-fx-text-fill: #032E67;");
            yearFilter.setPrefHeight(40);
            yearFilter.setPrefWidth(150);
            yearFilter.setPadding(new Insets(5));
            int year = LocalDate.now().getYear();

            // Agregar a�os del 2024 al 2014
            for (int i = year; i >= (year - 10); i--) {
                yearFilter.getItems().add(year);
            }
            yearFilter.setPromptText("A�o");

            // Filtro de Carrera
            JFXComboBox<String> careerFilter = new JFXComboBox<>();
            careerFilter.getItems().addAll("Arquitectura", "Ing. Civil", "Ing. El�ctrica", "Ing. Electr�nica", "Ing. Industrial",
                    "Ing. en Mtto. Mec�nico", "Ing. de Sistemas", "Ing. en Dise�o Industrial", "Ing. Qu�mica", "Ing. de Petr�leo");
            careerFilter.setStyle("-fx-border-color: #FA731F; "
                    + "-fx-background-color: white; "
                    + "-fx-border-radius: 20; "
                    + "-fx-background-radius: 20; "
                    + "-fx-font-size: 16px; "
                    + "-fx-font-family: 'Open Sans'; "
                    + "-fx-text-fill: #032E67;");
            careerFilter.setPrefHeight(40);
            careerFilter.setPrefWidth(200);
            careerFilter.setPadding(new Insets(5));
            careerFilter.setPromptText("Carrera");
            filtrosAdicionales.getChildren().addAll(yearFilter, careerFilter);

        } else if (tipo.equals("Libros")) {
            // Filtro de Autor
            JFXComboBox<String> authorFilter = new JFXComboBox<>();
            authorFilter.setStyle("-fx-border-color: #FA731F; "
                    + "-fx-background-color: white; "
                    + "-fx-border-radius: 20; "
                    + "-fx-background-radius: 20; "
                    + "-fx-font-size: 16px; "
                    + "-fx-font-family: 'Open Sans'; "
                    + "-fx-text-fill: #032E67;");
            authorFilter.setPrefHeight(40);
            authorFilter.setPrefWidth(200);
            authorFilter.setPadding(new Insets(5));
            authorFilter.setPromptText("Autor");
            authorFilter.setItems(filtroscb.obtenerAutores()); // Aqu� no hay error, es correcto

            // Filtro de Editorial
            JFXComboBox<String> editorFilter = new JFXComboBox<>();
            editorFilter.setStyle("-fx-border-color: #FA731F; "
                    + "-fx-background-color: white; "
                    + "-fx-border-radius: 20; "
                    + "-fx-background-radius: 20; "
                    + "-fx-font-size: 16px; "
                    + "-fx-font-family: 'Open Sans'; "
                    + "-fx-text-fill: #032E67;");
            editorFilter.setPrefHeight(40);
            editorFilter.setPrefWidth(200);
            editorFilter.setPadding(new Insets(5));
            editorFilter.setPromptText("Editorial");
            editorFilter.setItems(filtroscb.obtenerEditoriales()); // Aqu� es donde deber�as corregir

            filtrosAdicionales.getChildren().addAll(authorFilter, editorFilter);
        }
    }
}
