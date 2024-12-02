/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.ChartsModel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.FlatSkin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.pdfsam.ui.RingProgressIndicator;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class IniproController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane paneprueba;
    @FXML
    private StackPane stacktesis;

    @FXML
    private StackPane stacklibros;

    @FXML
    private StackPane stackinformes;

    @FXML
    private LineChart<String, Number> linepresta;
    @FXML
    private BarChart<String, Number> barras;
    @FXML
    private Button prueba;

    private ChartsModel estadisticas = new ChartsModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Instancia para el stacktesis
        Gauge progressCircleTesis = new Gauge();
        progressCircleTesis.setSkin(new FlatSkin(progressCircleTesis));
        configurarGauge(progressCircleTesis);

        // Instancia para el stacklibros
        Gauge progressCircleLibros = new Gauge();
        progressCircleLibros.setSkin(new FlatSkin(progressCircleLibros));
        configurarGauge(progressCircleLibros);

        // Instancia para el stackinformes
        Gauge progressCircleInformes = new Gauge();
        progressCircleInformes.setSkin(new FlatSkin(progressCircleInformes));
        configurarGauge(progressCircleInformes);

        // Agregar cada Gauge a su respectivo StackPane
        stacktesis.getChildren().add(progressCircleTesis);
        stacklibros.getChildren().add(progressCircleLibros);
        stackinformes.getChildren().add(progressCircleInformes);

        estadisticas.cargarEstadisticas(progressCircleInformes, progressCircleTesis, progressCircleLibros);
        iniciarAnimacion(progressCircleTesis);
        iniciarAnimacion(progressCircleInformes);
        iniciarAnimacion(progressCircleLibros);
        estadisticas.llenarlinechart(linepresta);
        estadisticas.cargarEstadisticasBarChart(barras);
        estadisticas.cambiarColoresBarras(barras);
        estadisticas.animarBarrasConTimeline(barras);

    }

    private void configurarGauge(Gauge gauge) {
        gauge.setMinValue(0);               // Valor mínimo
        gauge.setMaxValue(100);             // Valor máximo

        gauge.setUnit("%");                 // Unidad (por ejemplo, %)
        gauge.setAnimated(true);            // Animar cambios de valor
        gauge.setAnimationDuration(1000);   // Duración de la animación en ms
        gauge.setBarColor(Color.ORANGE);
    }

    public void iniciarAnimacion(Gauge gauge) {
        double valorActual = gauge.getValue(); // Obtener el valor actual
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    gauge.setValue(0); // Restablecer a 0
                    Timeline delayTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(1), delayedEvent -> gauge.setValue(valorActual)) // Restaurar el valor después de un retraso
                    );
                    delayTimeline.play();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play(); // Iniciar el timeline
    }
}
