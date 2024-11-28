/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.Buscarmodel;
import Modelos.Documento;
import Modelos.InformePasantia;
import Modelos.Libro;
import Modelos.TrabajoGrado;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import animatefx.animation.GlowBackground;
import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXToolbar;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
    private VBox itemholder = null;
    @FXML
    private AnchorPane panetoolbar;
    @FXML
    private ScrollPane scroll;

    private HBox toolbarContent;
    private Buscarmodel filtroscb = new Buscarmodel();
    private Buscarmodel busquedamdl = new Buscarmodel();
    private Buscarmodel pdfimg = new Buscarmodel();

    private JFXComboBox<String> pricingType; // ComboBox para tipo de documento
    private TextField searchField;          // Campo de texto para búsqueda
    private HBox filtrosAdicionales;
    public List<Documento> resultados;
    private JFXComboBox<String> authorFilter;
    private JFXComboBox<String> editorFilter;
    private JFXComboBox<String> careerFilter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        crearBarraDeBusqueda();

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Ocultar barra horizontal
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Mostrar barra vertical cuando sea necesario
        scroll.setFitToWidth(true); // Ajustar contenido al ancho del ScrollPane

// Crear los nodos dinámicamente
        Node[] nodes = new Node[10]; // Puedes cambiar 10 por la cantidad que necesites

        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;

                // Cargar nodo FXML
                nodes[i] = FXMLLoader.load(getClass().getResource("/Vistas/documento.fxml"));

                // Cambiar color al pasar el mouse
                nodes[i].setOnMouseEntered(event -> nodes[j].setStyle("-fx-background-color: #FA731F"));
                nodes[i].setOnMouseExited(event -> nodes[j].setStyle("-fx-background-color: white"));

                // Agregar nodo al VBox
                itemholder.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void crearBarraDeBusqueda() {
        // Crear JFXToolbar
        JFXToolbar toolbar = new JFXToolbar();

        // TextField para búsqueda
        searchField = new TextField();
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

        // Botón de búsqueda
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

            // Configurar el tamaño de la imagen
            lupaImageView.setFitHeight(20); // Ajusta la altura
            lupaImageView.setFitWidth(20);  // Ajusta el ancho
            lupaImageView.setPreserveRatio(true); // Mantén la proporción

            // Añadir la imagen al botón
            searchButton.setGraphic(lupaImageView);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        // HBox para campo de búsqueda y botón
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(5); // Separación mínima entre el TextField y el botón

        // ComboBox para tipos de filtros
        pricingType = new JFXComboBox<>();
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
        filtrosAdicionales = new HBox();
        filtrosAdicionales.setSpacing(10);
        filtrosAdicionales.setAlignment(Pos.CENTER_LEFT); // Alineación de los filtros

        // Agregar un manejador de eventos para cuando se cambia la selección del ComboBox
        pricingType.setOnAction(event -> {
            String tipoSeleccionado = pricingType.getValue();  // Obtiene el valor seleccionado
            // Limpiar los filtros anteriores
            filtrosAdicionales.getChildren().clear();
            // Crear filtros según el tipo seleccionado
            crearFiltrosPorTipo(tipoSeleccionado, filtrosAdicionales);
            new FadeInRight(filtrosAdicionales).play();
        });

        // HBox para organizar el ComboBox con la barra de búsqueda
        toolbarContent = new HBox(searchBox, pricingType);
        toolbarContent.setAlignment(Pos.CENTER_LEFT); // Centrado del contenido
        toolbarContent.setSpacing(10); // Espacio reducido entre el TextField y el ComboBox
        toolbarContent.setPadding(new Insets(5)); // Márgenes internos para mejor apariencia

        // Agregar HBox de filtros adicionales al toolbarContent
        toolbarContent.getChildren().add(filtrosAdicionales);

        // Agregar contenido al JFXToolbar
        toolbar.setCenter(toolbarContent);

        // Agregar JFXToolbar al AnchorPane
        panetoolbar.getChildren().clear(); // Limpiar cualquier contenido previo
        panetoolbar.getChildren().add(toolbar);

        // Ajustar la posición del toolbar en el AnchorPane
        AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setLeftAnchor(toolbar, 0.0);
        AnchorPane.setRightAnchor(toolbar, 0.0);
        AnchorPane.setBottomAnchor(toolbar, 0.0); // Ojo con esto, si no quieres que se pegue al fondo

        searchButton.setOnAction(event -> realizarBusqueda());
    }

    private void crearFiltrosPorTipo(String tipo, HBox filtrosAdicionales) {
        if (tipo.equals("Trabajos de Grado") || tipo.equals("Informes de Pasantia")) {
            // Filtro de Año
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

            // Agregar años del 2024 al 2014
            for (int i = year; i >= (year - 10); i--) {
                yearFilter.getItems().add(i);
            }
            yearFilter.setPromptText("Año");

            // Filtro de Carrera
            careerFilter = new JFXComboBox<>();
            careerFilter.getItems().addAll("Arquitectura", "Ing. Civil", "Ing. Eléctrica", "Ing. Electrónica", "Ing. Industrial",
                    "Ing. en Mtto. Mecánico", "Ing. de Sistemas", "Ing. en Diseño Industrial", "Ing. Química", "Ing. de Petróleo");
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
            authorFilter = new JFXComboBox<>();
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
            authorFilter.setItems(filtroscb.obtenerAutores()); // Aquí no hay error, es correcto

            // Filtro de Editorial
            editorFilter = new JFXComboBox<>();
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
            editorFilter.setItems(filtroscb.obtenerEditoriales()); // Aquí es donde deberías corregir

            filtrosAdicionales.getChildren().addAll(authorFilter, editorFilter);
        }
    }

    private void realizarBusqueda() {
        // Texto introducido en la barra de búsqueda
        String textoBusqueda = searchField.getText();

        // Tipo de documento seleccionado
        String tipoSeleccionado = pricingType.getValue();

        // Recoger los filtros dinámicos
        List<Node> filtrosActuales = filtrosAdicionales.getChildren(); // Obtiene los nodos actuales en el contenedor de filtros

        // Variables para almacenar los valores de los filtros
        String filtroAño = null;
        String filtroCarrera = null;
        String filtroAutor = null;
        String filtroEditorial = null;

        // Iterar sobre los nodos de los filtros dinámicos
        for (Node nodo : filtrosActuales) {
            if (nodo instanceof JFXComboBox) {
                JFXComboBox<?> comboBox = (JFXComboBox<?>) nodo;

                // Identificar el filtro según su texto de indicación (prompt text)
                switch (comboBox.getPromptText()) {
                    case "Año":
                        filtroAño = comboBox.getValue() != null ? comboBox.getValue().toString() : null;
                        break;
                    case "Carrera":
                        filtroCarrera = comboBox.getValue() != null ? comboBox.getValue().toString() : null;
                        break;
                    case "Autor":
                        filtroAutor = comboBox.getValue() != null ? comboBox.getValue().toString() : null;
                        break;
                    case "Editorial":
                        filtroEditorial = comboBox.getValue() != null ? comboBox.getValue().toString() : null;
                        break;
                }
            }
        }

        // Imprimir los valores de los filtros en consola (opcional, para depuración)
        System.out.println("Texto de búsqueda: " + textoBusqueda);
        System.out.println("Tipo seleccionado: " + tipoSeleccionado);
        System.out.println("Filtro Año: " + filtroAño);
        System.out.println("Filtro Carrera: " + filtroCarrera);
        System.out.println("Filtro Autor: " + filtroAutor);
        System.out.println("Filtro Editorial: " + filtroEditorial);

        // Crear un mapa de filtros para pasarlos al modelo
        Map<String, String> filtros = new HashMap<>();
        if (filtroAño != null) {
            filtros.put("Año", filtroAño);
        }
        if (filtroCarrera != null) {
            filtros.put("Carrera", filtroCarrera);
        }
        if (filtroAutor != null) {
            filtros.put("Autor", filtroAutor);
        }
        if (filtroEditorial != null) {
            filtros.put("Editorial", filtroEditorial);
        }

        // Llamar al modelo para realizar la búsqueda
        List<Documento> resultados = busquedamdl.buscarDocumentos(textoBusqueda, tipoSeleccionado, filtros);

        // Actualizar la vista con los resultados obtenidos
        actualizarResultadosEnUI(resultados);
    }

    private void cargarAutores() {
        ObservableList<String> autores = busquedamdl.obtenerAutores(); // Obtener lista de autores del modelo
        authorFilter.setItems(autores); // Configurar los valores en el ComboBox
    }

    private void cargarEditoriales() {
        ObservableList<String> editoriales = busquedamdl.obtenerEditoriales(); // Obtener lista de editoriales del modelo
        editorFilter.setItems(editoriales); // Configurar los valores en el ComboBox
    }

     public void actualizarResultadosEnUI(List<Documento> docus) {

        docus.stream().forEach(d -> d.getdatos());
        itemholder.getChildren().clear(); // Limpiar VBox antes de agregar nuevos resultados

        for (Documento documento : docus) {
            try {
                // Cargar nodo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/documento.fxml"));
                Node nodo = loader.load();

                // Obtener el controlador del nodo
                DocumentoController controller = loader.getController();

                byte[] pdfdata = documento.getImg_portada();
                File pdfFile = pdfimg.savePdfToTempFile(pdfdata);
                Image imagenPortada = pdfimg.convertPdfToImage(pdfFile);

                // Configurar datos del nodo
                controller.setData(documento.getTitulo(), imagenPortada);
                
                // Cambiar estilo al pasar el mouse
                nodo.setOnMouseEntered(event -> nodo.setStyle("-fx-background-color: #FA731F"));
                nodo.setOnMouseExited(event -> nodo.setStyle("-fx-background-color: white"));

                // Agregar nodo al VBox
                itemholder.getChildren().add(nodo);
                pdfFile.delete();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
