/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Controladores.BuscarController;
import Controladores.DepuracionController;
import Controladores.InicioController;
import animatefx.animation.FadeIn;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author VictorY
 */
public class DepuracionModel {

    public void loadpage(String page, AnchorPane viewpane) {
        Parent root = null;
        String pag = "/Vistas/";

        pag += page;

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource(pag + ".fxml"));
            root = load.load();
            DepuracionController ac = load.getController();
            ac.setviewpane(viewpane);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InicioController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        viewpane.getChildren().clear();
        viewpane.getChildren().add(root);
        new FadeIn(root).play();

    }

    public ObservableList<DocumentoDepuracion> cargarDocumentos() throws SQLException {
        ObservableList<DocumentoDepuracion> documentos = FXCollections.observableArrayList();
        String sql = "SELECT id_documento, titulo, fecha_publicacion, tipo FROM documento WHERE fecha_publicacion <= ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Calcular la fecha límite para documentos con 10 años o más de antigüedad
            LocalDate fechaLimite = LocalDate.now().minusYears(10);
            stmt.setDate(1, java.sql.Date.valueOf(fechaLimite));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_documento");
                    String titulo = rs.getString("titulo");
                    LocalDate fechaPublicacion = rs.getTimestamp("fecha_publicacion").toLocalDateTime().toLocalDate();
                    String tipo = rs.getString("tipo");

                    // Calcular años de vejez
                    int aniosVejez = LocalDate.now().getYear() - fechaPublicacion.getYear();

                    documentos.add(new DocumentoDepuracion(id, titulo, fechaPublicacion, tipo, aniosVejez));
                }
            }
        }

        return documentos;
    }

    public void eliminarDocumentosAntiguos() throws SQLException {
        String sql = "DELETE FROM documento WHERE fecha_publicacion <= ?";

        try (Connection conn = new Conexion().conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Calcular la fecha límite para documentos con 10 años o más de antigüedad
            LocalDate fechaLimite = LocalDate.now().minusYears(10);
            stmt.setDate(1, java.sql.Date.valueOf(fechaLimite));

            int filasEliminadas = stmt.executeUpdate();
            System.out.println("Se eliminaron " + filasEliminadas + " documentos antiguos.");
        }
    }

}
