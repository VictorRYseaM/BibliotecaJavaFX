/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.BuscarController;
import Controladores.InicioController;
import Controladores.PrestamosController;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author VictorY
 */
public class PrestamosModel {

    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            PrestamosController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public Map<String, Documento> buscarTitulosDocumentos(String textoBusqueda) {
        Map<String, Documento> titulosMap = new HashMap<>();
        String sql = "SELECT id_documento, titulo, tipo FROM documento WHERE titulo LIKE ?";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, "%" + textoBusqueda + "%"); // Filtro por texto parcial
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    int id = rs.getInt("id_documento");
                    String tipo = rs.getString("tipo");
                    titulosMap.put(titulo, new Documento(id, tipo));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return titulosMap;
    }

    public static void establecerFechaActual(JFXTextField textField) {
        // Obtener la fecha y hora actual del sistema
        LocalDateTime fechaActual = LocalDateTime.now();

        // Formatear la fecha y hora en el formato "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = fechaActual.format(formatter);

        // Asignar la fecha formateada al campo de texto
        textField.setText(fechaFormateada);
    }

    public void registrarPrestamo(int idDocumento, String fechaPrestamo, String cedula) {
        String sqlInsert = "INSERT INTO prestamo (id_documento, fecha_prestamo, Estado, Cedula) VALUES (?, ?, 0, ?)";

        try (Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sqlInsert)) {

            // Configurar los valores en la sentencia preparada
            pst.setInt(1, idDocumento);
            pst.setString(2, fechaPrestamo);
            pst.setString(3, cedula);

            // Ejecutar la inserción
            int filasInsertadas = pst.executeUpdate();
            if (filasInsertadas > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Préstamo Registrado");
                alert.setHeaderText(null);
                alert.setContentText("El préstamo se ha registrado exitosamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Préstamo Incompleto");
                alert.setHeaderText(null);
                alert.setContentText("El préstamo no se ha registrado.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatosPrestamos(TableView<Prestamo> tablaprestamos) {
        ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList();
        String sql = "SELECT p.id_prestamo, p.id_documento, d.titulo, d.tipo, p.cedula, p.fecha_prestamo, p.estado "
                + "FROM prestamo p "
                + "JOIN documento d ON p.id_documento = d.id_documento";

        try (Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Recuperar la fecha como Timestamp y convertirla a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_prestamo");
                LocalDateTime fechaPrestamo = (timestamp != null) ? timestamp.toLocalDateTime() : null;

                // Formatear la fecha como String si no es nula
                String fechaPrestamoString = (fechaPrestamo != null)
                        ? fechaPrestamo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        : null;

                // Crear el objeto Prestamo con la fecha formateada
                Prestamo prestamo = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_documento"),
                        rs.getString("titulo"), // Obtener el título de la tabla documento
                        rs.getString("tipo"),
                        rs.getString("cedula"),
                        fechaPrestamoString, // Asignar la fecha formateada
                        rs.getInt("estado")
                );

                System.out.println("Titulo: " + rs.getString("titulo"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Fecha Prestamo: " + fechaPrestamoString);
                System.out.println("Estado: " + rs.getInt("estado"));

                listaPrestamos.add(prestamo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Asignar la lista de datos al TableView
        tablaprestamos.setItems(listaPrestamos);
    }

    public void actualizarEstadoPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamo SET estado = ? WHERE id_prestamo = ?";
        
        try (Connection con = new Conexion().conectar();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, prestamo.getEstado());
            pstmt.setInt(2, prestamo.getIdPrestamo());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Estado actualizado correctamente en la base de datos.");
            } else {
                System.out.println("No se encontró el préstamo para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
