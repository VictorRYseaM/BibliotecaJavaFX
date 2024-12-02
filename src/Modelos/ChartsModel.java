/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Conexion;
import animatefx.animation.BounceIn;
import eu.hansolo.medusa.Gauge;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 *
 * @author VictorY
 */
public class ChartsModel {

    public void cargarEstadisticas(Gauge gaugeInformes, Gauge gaugeTesis, Gauge gaugeLibros) {
        try {
            java.sql.Connection con = new Conexion().conectar();
            // Consulta SQL para obtener las cantidades
            String sql = """
                SELECT 
                    COUNT(*) AS total,
                    SUM(CASE WHEN Tipo = 'Informes de Pasantía' THEN 1 ELSE 0 END) AS informes,
                    SUM(CASE WHEN Tipo = 'Trabajos de Grado' THEN 1 ELSE 0 END) AS tesis,
                    SUM(CASE WHEN Tipo = 'Libros' THEN 1 ELSE 0 END) AS libros
                FROM documento;
            """;

            // Preparar y ejecutar la consulta
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Obtener los valores desde la base de datos
                int total = resultSet.getInt("total");
                int informes = resultSet.getInt("informes");
                int tesis = resultSet.getInt("tesis");
                int libros = resultSet.getInt("libros");

                // Calcular porcentajes
                double porcentajeInformes = total > 0 ? (informes * 100.0 / total) : 0;
                double porcentajeTesis = total > 0 ? (tesis * 100.0 / total) : 0;
                double porcentajeLibros = total > 0 ? (libros * 100.0 / total) : 0;

                // Actualizar los Gauge
                //actualizarGauge(gaugeTotal, "Total Documentos", total, total);
                actualizarGauge(gaugeInformes, "Informes", porcentajeInformes, informes);
                actualizarGauge(gaugeTesis, "Tesis", porcentajeTesis, tesis);
                actualizarGauge(gaugeLibros, "Libros", porcentajeLibros, libros);

                resultSet.close();
            }
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText(e.toString());
        }
    }

    private void actualizarGauge(Gauge gauge, String titulo, double porcentaje, int valor) {
        gauge.setTitle(titulo);
        gauge.setValue(porcentaje);
        gauge.setUnit("% | " + valor);
    }

    public void llenarlinechart(LineChart<String, Number> line) {
        // Crear una serie para los datos
        XYChart.Series<String, Number> serie1 = new XYChart.Series<>();
        serie1.setName("Datos de Prueba 1");

        // Agregar puntos de datos a la serie
        serie1.getData().add(new XYChart.Data<>("Enero", 200));
        serie1.getData().add(new XYChart.Data<>("Febrero", 300));
        serie1.getData().add(new XYChart.Data<>("Marzo", 250));

        // Crear una segunda serie opcional
        XYChart.Series<String, Number> serie2 = new XYChart.Series<>();
        serie2.setName("Datos de Prueba 2");

        serie2.getData().add(new XYChart.Data<>("Enero", 150));
        serie2.getData().add(new XYChart.Data<>("Febrero", 280));
        serie2.getData().add(new XYChart.Data<>("Marzo", 320));

        // Agregar las series al LineChart
        line.getData().addAll(serie1, serie2);

    }

    public void cargarEstadisticasBarChart(BarChart<String, Number> barras) {
        try {
            java.sql.Connection con = new Conexion().conectar();

            // Lista de carreras esperadas
            List<String> carrerasEsperadas = Arrays.asList(
                    "Arquitectura", "Ing. Civil", "Ing. Eléctrica", "Ing. Electrónica",
                    "Ing. Industrial", "Ing. en Mtto. Mecánico", "Ing. de Sistemas",
                    "Ing. en Diseño Industrial", "Ing. Química", "Ing. de Petróleo"
            );

            // Consulta para obtener la cantidad de documentos por carrera
            String sql = """
        SELECT 
            Carrera,
            SUM(CASE WHEN Tipo = 'Trabajos de Grado' THEN 1 ELSE 0 END) AS tesis,
            SUM(CASE WHEN Tipo = 'Informes de Pasantía' THEN 1 ELSE 0 END) AS informes
        FROM 
            (SELECT Carrera, 'Trabajos de Grado' AS Tipo FROM tesis
             UNION ALL
             SELECT Carrera, 'Informes de Pasantía' AS Tipo FROM informepasantia) AS documentos
        GROUP BY Carrera;
        """;

            // Preparar y ejecutar la consulta
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Crear las series de datos para el BarChart
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            barras.setTitle("Cantidad de Documentos por Carrera");
            xAxis.setLabel("Carrera");
            yAxis.setLabel("Cantidad");

            XYChart.Series<String, Number> seriesTesis = new XYChart.Series<>();
            seriesTesis.setName("Tesis");

            XYChart.Series<String, Number> seriesInformes = new XYChart.Series<>();
            seriesInformes.setName("Informes de Pasantía");

            // Map para almacenar los datos de carreras con conteos
            Map<String, int[]> carreraData = new HashMap<>();

            // Inicializar el mapa con 0 para cada carrera esperada
            for (String carrera : carrerasEsperadas) {
                carreraData.put(carrera, new int[]{0, 0}); // {tesis, informes}
            }

            // Llenar el mapa con los resultados de la base de datos
            while (resultSet.next()) {
                String carrera = resultSet.getString("Carrera");
                int tesis = resultSet.getInt("tesis");
                int informes = resultSet.getInt("informes");

                if (carreraData.containsKey(carrera)) {
                    carreraData.put(carrera, new int[]{tesis, informes});
                }
            }

            // Agregar los datos al gráfico y configurar Tooltips
            for (String carrera : carrerasEsperadas) {
                int[] data = carreraData.get(carrera);
                XYChart.Data<String, Number> dataTesis = new XYChart.Data<>(carrera, data[0]);
                XYChart.Data<String, Number> dataInformes = new XYChart.Data<>(carrera, data[1]);

                // Agregar los datos a las series correspondientes
                seriesTesis.getData().add(dataTesis);
                seriesInformes.getData().add(dataInformes);
            }

            // Agregar las series al BarChart
            barras.getData().addAll(seriesTesis, seriesInformes);

            // Instalar Tooltips manualmente en cada dato de las series
            for (XYChart.Series<String, Number> series : barras.getData()) {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    String tipo = series.getName();
                    Tooltip tooltip = new Tooltip("Carrera: " + data.getXValue()
                            + "\nTipo: " + tipo
                            + "\nCantidad: " + data.getYValue());
                    Tooltip.install(data.getNode(), tooltip);
                }
            }

            // Cerrar los recursos
            resultSet.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText(e.toString());
            a.showAndWait();
        }
    }

    public void cambiarColoresBarras(BarChart<String, Number> barChart) {
        // Definir colores específicos para cada serie
        String colorTesis = "-fx-bar-fill: #03306C;"; // Color azul para la serie de Tesis
        String colorInformes = "-fx-bar-fill: #FA731F;"; // Color verde para la serie de Informes

        // Recorremos cada serie de datos en el BarChart
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            // Determinamos el color basado en el nombre de la serie
            String color = series.getName().equals("Tesis") ? colorTesis : colorInformes;

            // Recorremos cada dato individual en la serie
            for (XYChart.Data<String, Number> data : series.getData()) {
                // Establecemos el color de la barra
                data.getNode().setStyle(color);
            }
        }
    }

    public void animarBarrasConTimeline(BarChart<String, Number> barChart) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            for (XYChart.Series<String, Number> series : barChart.getData()) {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    Node bar = data.getNode();
                    if (bar != null) {
                        new BounceIn(bar).play();
                    }
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play(); // Iniciar la animación
    }
}
